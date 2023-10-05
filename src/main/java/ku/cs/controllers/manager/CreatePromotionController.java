package ku.cs.controllers.manager;

import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.model.product.Lot;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class CreatePromotionController {

    @FXML private Button btnApply;
    @FXML private Circle btnClose;
    @FXML private Circle pictureProduct;
    @FXML private ComboBox selectComboBox;
    @FXML private DatePicker startDate;
    @FXML private DatePicker endDate;
    @FXML private Label nameProduct;
    @FXML private ImageView btnBack;
    @FXML private TextField promoNameTextField;
    @FXML private TextField descriptionTextField;
    @FXML private Label errorLabel;

    private String currentProduct = "-";
    private Alert alert;
    private Product product;
    private ArrayList<Product> productList;
    private ArrayList<Lot> lotList;

    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.NONE);
        this.product = queryProduct();
        this.lotList = queryLotExp();
        this.productList = queryCheckProductsExp();
        selectProduct();
        System.out.println("initialize CreatePromotionController");
    }

    public ArrayList<Lot> queryLotExp() {
        ArrayList<Lot> lotArrayList = new ArrayList<Lot>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();
        String sql = "SELECT * FROM LOT";
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
    public ArrayList<Product> queryCheckProductsExp(){
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();
        String sql = "SELECT * FROM PRODUCT";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);

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
    private Product queryProduct(){
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String products = "SELECT * FROM PRODUCT ";
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

    private void selectProduct() {
        for (Product product : productList) {
            for (Lot lot : lotList) {
                if (lot.getL_Exp().isAfter(lot.getL_Date()) && lot.getL_Exp().isBefore(lot.getL_Date().plusMonths(3)) && lot.getP_ID() == product.getP_ID()) {
                    selectComboBox.getItems().addAll(product.getP_Name());
                }
            }
        }
    }

    @FXML
    public void chooseProduct(ActionEvent actionEvent) {
        this.currentProduct = String.valueOf(selectComboBox.getValue());
        for (Product product : productList) {
            if (product.getP_Name().equals(currentProduct)) {
                nameProduct.setText(product.getP_Name());
                File f = new File("images/"+product.getP_Image());
                pictureProduct.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
            }
        }
    }


    @FXML
    private void handleButtonAction(ActionEvent event) throws SQLException {
        String name = promoNameTextField.getText();
        String description = descriptionTextField.getText();
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        if (!isValidatePromotionName(name)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid Name");
            alert.show();
        }

        else if (!isValidateDescription(description)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid Description");
            alert.show();
        }

        else if (!isValidateDate(start, end)) {

        }

        else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "INSERT INTO PROMOTION (PM_Name, PM_Description, P_ID, StartDate, EndDate) VALUE ('"
                    +  name + "','" + description + "','" + product.getP_ID() + "','" + start + "','" + end + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setContentText("Create Promotion Success");
            alert.show();
            try {
                FXRouter.goTo("create_promotion", 750, 500);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า create_promotion ไม่ได้");
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
        // todo: Button Back
        if (event.getSource() == btnBack) {
            try {
                FXRouter.goTo("check_lot", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า check_lot ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    public boolean isValidatePromotionName(String promotionName) {
        return ((promotionName != null)
                && (!promotionName.equals(""))
                && (promotionName.matches("^[a-zA-Z]+(.+){1,20}$")));
    }

    public boolean isValidateDescription(String description) {
        return ((description != null)
                && (!description.equals(""))
                && (description.matches("^[a-zA-Z]+(.+){1,200}$")));
    }

    public boolean isValidateDate(LocalDate date1, LocalDate date2) {
        LocalDate current = LocalDate.now();
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();
        if (start.equals("") || start.isBefore(current)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid Start Date");
            alert.show();
        }
        if (end.equals("") || end.isBefore(start) || end.isBefore(current)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid End Date");
            alert.show();
        }
        return true;
    }
}

