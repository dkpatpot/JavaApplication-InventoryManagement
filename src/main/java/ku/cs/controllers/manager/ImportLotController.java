package ku.cs.controllers.manager;

import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ImportLotController {

    @FXML private BorderPane borderProduct;
    @FXML private Button btnApply;
    @FXML private ComboBox selectComboBox;
    @FXML private Circle btnClose;
    @FXML private Circle pictureProduct;
    @FXML private DatePicker expDate;
    @FXML private ImageView btnBack;
    @FXML private Label nameProduct;
    @FXML private TextField nameTextField;
    @FXML private TextField quantityTextField;
    @FXML private TextField addQuantityTextField;

    private String currentProduct = "-";
    private Alert alert;
    File selectedFile;

    private String imageName;
    private ArrayList<Product> productList;
    private Product product;


    @FXML
    public void initialize() {

        alert = new Alert(Alert.AlertType.NONE);
        this.product = queryProduct();
        this.productList = queryInStockProduct();
        selectProduct();
        expDate.setValue(LocalDate.now());
        System.out.println("initialize ImportLotController");
    }

    private Product queryProduct(){
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String products = "SELECT * FROM PRODUCT WHERE P_Quantity < 100 ;";
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

    private ArrayList<Product> queryInStockProduct() {
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();
        String inStock = "SELECT * FROM PRODUCT WHERE P_Quantity < 100 ;";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(inStock);

            while (queryResult.next()) {
                int p_id = Integer.parseInt(queryResult.getString(1));
                String p_name = queryResult.getString(2);
                int p_quantity = Integer.parseInt(queryResult.getString(3));
                String p_type = queryResult.getString(4);
                int p_price = Integer.parseInt(queryResult.getString(5));
                String p_image = queryResult.getString(6);
                Product product = new Product(p_id, p_name, p_quantity, p_type, p_price, p_image);
                productArrayList.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productArrayList;
    }
    private void selectProduct() {
        for (Product product : productList) {
            selectComboBox.getItems().addAll(product.getP_Name());
//            currentCategory = String.valueOf(selectComboBox.getValue());
        }
    }
    private void updateQuantity(){
        System.out.println(currentProduct);
        System.out.println(product.getP_Name());
        for (Product product : productList) {
            if (product.getP_Name().equals(currentProduct)) {
                ConnectionClass connectNow = new ConnectionClass();
                Connection connectDB = connectNow.getConnection();

                String updateQuantity = "UPDATE PRODUCT SET P_Quantity = '" + product.getP_Quantity() + "'+'" + Integer.parseInt(addQuantityTextField.getText()) + "' WHERE P_ID = '"
                        + product.getP_ID() + "';";
                try {
                    PreparedStatement statement = connectDB.prepareStatement(updateQuantity);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
//    private void confirmUpdateQuantity(){
//        int quantity = product.getP_Quantity();
//        int import_quantity = Integer.parseInt(addQuantityTextField.getText());
//        updateQuantity(quantity,import_quantity);
//    }

    private void queryImportStock() throws SQLException {
        LocalDate now = LocalDate.now();
        String l_quantity = addQuantityTextField.getText();
        for (Product product : productList) {
            if (product.getP_Name().equals(currentProduct)) {
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String importStock = "INSERT INTO LOT (L_Date, P_ID, L_Exp, L_Quantity)VALUES('"
                + now + "','" + product.getP_ID() + "','" + expDate.getValue() + "','" + l_quantity + "')";
        Statement statement = connectDB.createStatement();
        statement.executeUpdate(importStock);
        alert.setAlertType(Alert.AlertType.WARNING);
        alert.setContentText("Add stock success.");
        alert.show();
            }
        }
    }


    @FXML
    public void chooseProduct(ActionEvent actionEvent) {
        this.currentProduct = String.valueOf(selectComboBox.getValue());
        for (Product product : productList) {
            if (product.getP_Name().equals(currentProduct)) {
                nameProduct.setText(product.getP_Name());
                nameTextField.setText(product.getP_Name());
                nameTextField.setEditable(false);
                quantityTextField.setText(String.valueOf(product.getP_Quantity()));
                quantityTextField.setEditable(false);
                File f = new File("images/"+product.getP_Image());
                pictureProduct.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
            }
        }
    }

    public boolean isValidQuantity(String quantity) {
        return ((quantity!= null)
                && (!quantity.equals(""))
                && (quantity.matches("^[0-9]{1,5}$")));
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws SQLException {
        LocalDate now = LocalDate.now();
        if (event.getSource() == btnApply) {
            if (addQuantityTextField.getText().isEmpty()) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Invalid Quantity");
                alert.show();
            } else if (!isValidQuantity(addQuantityTextField.getText()) || Integer.parseInt(addQuantityTextField.getText()) <= 0) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Invalid Quantity");
                alert.show();
            } else if (now.isAfter(expDate.getValue()) || now.equals(expDate.getValue())) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Invalid EXP DATE");
                alert.show();
            } else {
                queryImportStock();
                updateQuantity();
                try {
                    FXRouter.goTo("check_lot", 1000, 600);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า check_lot ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event) {

        // todo: Close Program
        if (event.getSource() == btnClose) {
            System.exit(0);
        }

        // todo: Button Back
        if (event.getSource() == btnBack) {
            try {
                FXRouter.goTo("check_lot", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า check_stock ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
