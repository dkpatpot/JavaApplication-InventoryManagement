package ku.cs.controllers.product;

import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import ku.cs.model.product.Lot;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static ku.cs.controllers.login.LoginController.ACCOUNT;

public class ProductDetailController {

    @FXML
    private Button btnBuy;
    @FXML
    private Button btnPreorder;
    @FXML
    private Circle btnClose;
    @FXML
    private Circle productPicture;
    @FXML
    private ImageView btnBack;
    @FXML
    private Label NameLabel;
    @FXML
    private TextField nameLabel;
    @FXML
    private TextField priceLabel;
    @FXML
    private TextField quantityLabel;
    @FXML
    private TextField quantityTextField;
    @FXML
    private TextField productTypeLabel;

    private Product product = (Product) FXRouter.getData();

    ArrayList<Lot> lotList;
    private Lot lots;
    private Alert alert;

    int temp = 0;
    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.NONE);
        this.lots = queryLotProduct();
        this.product = queryProduct();
        this.lotList = queryLot();
        showList();
        showProductDetail();
        System.out.println("initialize ProductDetailController");
    }

    public void showList(){
        for (Lot lot : lotList){
            System.out.println(lot.getL_ID()+ "  " +lot.getL_Quantity());
        }
    }
    public void showProductDetail() {
        NameLabel.setText(product.getP_Name());
        nameLabel.setText(product.getP_Name());
        nameLabel.setEditable(false);
        priceLabel.setText(product.getP_Price() + " ฿/kg.");
        priceLabel.setEditable(false);
        quantityLabel.setText(product.getP_Quantity() + " kg.");
        quantityLabel.setEditable(false);
        productTypeLabel.setText(product.getP_Type());
        productTypeLabel.setEditable(false);
        File f = new File("images/" + product.getP_Image());
        productPicture.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
    }

    private Product queryProduct() {
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String products = "SELECT * FROM PRODUCT WHERE P_ID = '" + product.getP_ID() + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(products);

            while (queryResult.next()) {
                int p_id = Integer.parseInt(queryResult.getString(1));
                String p_name = queryResult.getString(2);
                int p_quantity = Integer.parseInt(queryResult.getString(3));
                String p_type = queryResult.getString(4);
                int p_price = Integer.parseInt(queryResult.getString(5));
                String p_image = queryResult.getString(6);

                Product product1 = new Product(p_id, p_name, p_quantity, p_type, p_price, p_image);
                return product1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private Lot queryLotProduct() {
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String lots = "SELECT * FROM LOT WHERE P_ID = '" + product.getP_ID() + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(lots);


            while (queryResult.next()) {
                int l_id = Integer.parseInt(queryResult.getString(1));
                LocalDate l_date = LocalDate.parse(queryResult.getString(2));
                int p_id = Integer.parseInt(queryResult.getString(3));
                LocalDate l_exp = LocalDate.parse(queryResult.getString(4));
                int l_quantity = Integer.parseInt(queryResult.getString(5));
                Lot lot = new Lot(l_id, l_date, p_id, l_exp, l_quantity);
                return lot;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean checkQuantity(int amount) {
        if (amount > this.product.getP_Quantity()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Product out of stock.");
            alert.show();
            btnBuy.setVisible(false);
            btnPreorder.setVisible(true);
            btnPreorder.toFront();
            return false;
        }
        return true;
    }

    public int totalPrice(int amount) {
        return this.product.getP_Price() * amount;
    }

    private void queryOrder() throws SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String formattedDateTime = currentDateTime.format(formatter);
        String op_quantity = quantityTextField.getText();
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String orderSQL = "INSERT INTO ORDER_PRODUCT (OP_Quantity, OP_Price, P_ID, OP_Date, OP_Type, OP_Status, Username)VALUES('"
                + op_quantity + "','" + totalPrice(Integer.parseInt(op_quantity)) + "','" + product.getP_ID() + "','"
                + formattedDateTime + "','" + "0" + "','" + "0" + "','" + ACCOUNT.getUsername() + "')";
        Statement statement = connectDB.createStatement();
        statement.executeUpdate(orderSQL);
    }


    public boolean isValidQuantity(String quantity) {
        return ((quantity != null)
                && (!quantity.equals(""))
                && (quantity.matches("^[0-9]{1,5}$")));
    }

    // ต้องตัด lot แทน
    private void cutStock(int quantity, int op_quantity) {
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();
        String updateProductQuantity = "UPDATE PRODUCT SET P_Quantity = '" + quantity + "'-'" + op_quantity + "' WHERE P_ID = '"
                + product.getP_ID() + "';";
        try {
            for (Lot lot: lotList) {
                    if (lot.getL_ID() == lots.getL_ID()) {
                        String updateLotQuantity = "UPDATE LOT SET L_Quantity = '" + lot.getL_Quantity() + "'-'" + op_quantity + "' WHERE L_ID = '"
                                + lot.getL_ID() + "' AND P_ID = '" + lot.getP_ID() + "'";
                        String updateLotStatus = "DELETE FROM LOT WHERE L_Quantity <= '" + 0 + "'";
                        PreparedStatement statement2 = connectDB.prepareStatement(updateLotQuantity);
                        statement2.executeUpdate();
                        PreparedStatement statement = connectDB.prepareStatement(updateLotStatus);
                        statement.executeUpdate();
                        temp = lots.getL_Quantity() - op_quantity;
                    }
                    else if (temp < 0 ){
                        String updateLotQuantity2 = "UPDATE LOT SET L_Quantity = '" + lot.getL_Quantity() + "'+'" + temp + "' WHERE L_ID = '"
                                + lot.getL_ID() + "' AND P_ID = '" + lot.getP_ID() + "'";
                        String updateLotStatus = "DELETE FROM LOT WHERE L_Quantity <= '" + 0 + "'";
                        PreparedStatement statement2 = connectDB.prepareStatement(updateLotQuantity2);
                        statement2.executeUpdate();
                        PreparedStatement statement = connectDB.prepareStatement(updateLotStatus);
                        statement.executeUpdate();
                        temp = lot.getL_Quantity() + temp;
                    }
                }
                PreparedStatement statement1 = connectDB.prepareStatement(updateProductQuantity);
                statement1.executeUpdate();
                System.out.println("cut stock complete!!!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void confirmCutStock(){
        int quantity = product.getP_Quantity();
        int op_quantity = Integer.parseInt(quantityTextField.getText());
        System.out.println("product : " + quantity
                + " order : " + op_quantity);
        cutStock(quantity, op_quantity);
    }

    public ArrayList<Lot> queryLot() {
        ArrayList<Lot> lotArrayList = new ArrayList<Lot>();
        ConnectionClass connectionClass = new ConnectionClass();
        Connection connectDB = connectionClass.getConnection();
        String sql = "SELECT * FROM LOT WHERE P_ID = '" + product.getP_ID() + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);

            while (queryResult.next()) {
                int l_id = Integer.parseInt(queryResult.getString(1));
                LocalDate l_date = LocalDate.parse(queryResult.getString(2));
                int p_id = Integer.parseInt(queryResult.getString(3));
                LocalDate l_exp = LocalDate.parse(queryResult.getString(4));
                int l_quantity = Integer.parseInt(queryResult.getString(5));
                Lot lot = new Lot(l_id, l_date, p_id, l_exp, l_quantity);
                lotArrayList.add(lot);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lotArrayList;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // todo: Button Buy
        if (event.getSource() == btnBuy) {
            String op_quantity = quantityTextField.getText();
            try {
                if (!isValidQuantity(op_quantity) || Integer.parseInt(op_quantity) <= 0) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Invalid Quantity");
                    alert.show();
                } else if (checkQuantity(Integer.parseInt(op_quantity))) {
                    queryOrder();
                    confirmCutStock();
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Success");
                    alert.show();
                    FXRouter.goTo("marketplace", 1000, 600);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        // todo: Buy Pre-Order
        if (event.getSource() == btnPreorder) {
            try {
                FXRouter.goTo("preorder",product, 750, 500);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า preorder ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
    @FXML
    private void handleMouseEvent(MouseEvent event) {

        // todo: Close Program
        if (event.getSource() == btnClose) {
            System.exit(0);
        }

        // todo: Back To Marketplace
        if (event.getSource() == btnBack) {
            try {
                FXRouter.goTo("marketplace",1000, 600);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
