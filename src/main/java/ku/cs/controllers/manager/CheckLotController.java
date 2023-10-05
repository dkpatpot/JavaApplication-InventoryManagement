package ku.cs.controllers.manager;

import animatefx.animation.SlideInUp;
import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import ku.cs.controllers.product.ProductCheckStockController;
import ku.cs.controllers.product.ProductExpController;
import ku.cs.model.product.Lot;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

import static ku.cs.controllers.login.LoginController.ACCOUNT;

public class CheckLotController {

    @FXML private Button btn_I;
    @FXML private Button btn_II;
    @FXML private Button btn_III;
    @FXML private Button btnSignOut;
    @FXML private ComboBox selectComboBox;
    @FXML private Circle btnClose;
    @FXML private GridPane gridProduct;
    @FXML private ImageView iconButton_I;
    @FXML private ImageView iconButton_II;
    @FXML private ImageView iconButton_III;
    @FXML private Label nameLabel;
    @FXML private Label roleLabel;

    public ArrayList<Order> orderList;

    private ArrayList<Product> productList;
    private ArrayList<Product> productList2;
    private ArrayList<Lot> lotList;
    private Product product;
    @FXML
    public void initialize() {
//        ThemeMode.setThemeMode(anchorPane);
        this.lotList = queryLotExp();
        this.productList = queryCheckStockProduct();
        this.productList2 = queryCheckProductsExp();
        selectComboBox.getItems().addAll(
                "Low Quantity",
                "Expiration"
        );
        selectComboBox.getSelectionModel().selectFirst();
        showProductsLessThan100();
        InterfaceManage();
        System.out.println("initialize CheckStockController");
    }

    public void selectCheckProduct(ActionEvent actionEvent) {

        if(selectComboBox.getValue().equals("Low Quantity")) {
            showProductsLessThan100();
        }
        if(selectComboBox.getValue().equals("Expiration")) {
            showProductsExp();
        }
    }


    public void showProductsExp(){
        gridProduct.getChildren().clear();
        int column = 0;
        int row = 0;
        try {
            for (Product product : productList2) {
                for (Lot lot : lotList){
                    if(lot.getL_Exp().isAfter(lot.getL_Date()) && lot.getL_Exp().isBefore(lot.getL_Date().plusMonths(3)) && lot.getP_ID() == product.getP_ID()) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/ku/cs/interfaces/product_exp.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        ProductExpController productExpController = fxmlLoader.getController();
                        productExpController.setData(lot,product);
                        if (column == 3) {
                            column = 0;
                            row++;
                        }
                        gridProduct.add(anchorPane, column++, row);
                        GridPane.setMargin(anchorPane, new Insets(7.5));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        new SlideInUp(gridProduct).play();
    }

    public void showProductsLessThan100() {
        gridProduct.getChildren().clear();
        int column = 0;
        int row = 0;
        try {
            for (Product product : productList) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/interfaces/product_check_stock.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ProductCheckStockController productCheckStockController = fxmlLoader.getController();
                productCheckStockController.setData(product);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                gridProduct.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(7.5));
            }
        } catch (IOException e) {
                e.printStackTrace();
        }
        new SlideInUp(gridProduct).play();
    }

    public ArrayList<Product> queryCheckStockProduct(){
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();
        String sql = "SELECT * FROM PRODUCT WHERE P_Quantity < 100 ;";
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

    public ArrayList<Lot> queryLotExp() {
        ArrayList<Lot> lotArrayList = new ArrayList<Lot>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();
        String sql = "SELECT * FROM LOT ";
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

    public void InterfaceManage() {
        nameLabel.setText(ACCOUNT.getName());
        roleLabel.setText(ACCOUNT.getRole());
        btn_I.setText("Back");
        Image icon_back = new Image(getClass().getResource("/ku/cs/icons/backButton.png").toExternalForm());
        iconButton_I.setImage(icon_back);

        // todo: Admin
        if (ACCOUNT.getRole().equals("Admin")) {
            btn_II.setText("Import Stock");
            Image icon_import = new Image(getClass().getResource("/ku/cs/icons/newOrder.png").toExternalForm());
            iconButton_II.setImage(icon_import);

            btn_III.setText("Create Promotion");
            Image icon_voucher = new Image(getClass().getResource("/ku/cs/icons/voucher.png").toExternalForm());
            iconButton_III.setImage(icon_voucher);

        }

        // todo: Manager
        if (ACCOUNT.getRole().equals("Manager")) {
            btn_II.setText("Create Promotion");
            Image icon_voucher = new Image(getClass().getResource("/ku/cs/icons/voucher.png").toExternalForm());
            iconButton_II.setImage(icon_voucher);

            btn_III.setVisible(false);
        }

        // todo: Employee
        if (ACCOUNT.getRole().equals("Employee")) {
            btn_II.setText("Import Stock");
            Image icon_import = new Image(getClass().getResource("/ku/cs/icons/newOrder.png").toExternalForm());
            iconButton_II.setImage(icon_import);

            btn_III.setVisible(false);
        }
    }

    @FXML
    public void handleButtonAction(ActionEvent event) throws IOException {

        // todo: Back to Marketplace
        if (event.getSource() == btn_I) {
            try {
                FXRouter.goTo("marketplace", 1000, 600);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

        if (event.getSource() == btn_II) {
            // todo: Create Promotion
            if (btn_II.getText().equals("Create Promotion")) {
                try {
                    FXRouter.goTo("create_promotion", product, 750, 500);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า create_promotion ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
            // todo: Import Stock
            if (btn_II.getText().equals("Import Stock")) {
                try {
                    FXRouter.goTo("import_lot", product,750, 500);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า import_stock ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }

        if (event.getSource() == btn_III) {
            // todo: Create Promotion
            if (btn_III.getText().equals("Create Promotion")) {
               try {
                   FXRouter.goTo("create_promotion", product, 750, 500);
               } catch (IOException e) {
                   System.err.println("ไปที่หน้า create_promotion ไม่ได้");
                   System.err.println("ให้ตรวจสอบการกำหนด route");
               }
            }
        }

        // todo: Sign Out
        if (event.getSource().equals(btnSignOut)) {
            try {
                 FXRouter.goTo("login", 750, 500);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า create_promotion ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
    @FXML
    public void handleMouseEvent(MouseEvent event) {

        // todo: Close Program
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
    }
}
