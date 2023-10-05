package ku.cs.controllers.product;

import animatefx.animation.SlideInUp;
import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static ku.cs.controllers.login.LoginController.ACCOUNT;

public class PreorderAllController {

    @FXML private Button btn_I;
    @FXML private Button btn_II;
    @FXML private Button btn_III;
    @FXML private Button btnSignOut;
    @FXML private Circle btnClose;
    @FXML private GridPane gridProduct;
    @FXML private ImageView iconButton_I;
    @FXML private ImageView iconButton_II;
    @FXML private ImageView iconButton_III;
    @FXML private Label nameLabel;
    @FXML private Label roleLabel;
    public ArrayList<Order> orderList ;

    private ArrayList<Product> productList;

    @FXML public void initialize() {

        Alert alert = new Alert(Alert.AlertType.NONE);

        this.orderList = queryPreorderProducts();
        this.productList = getProductPreorder();


        showPreorderProducts();
        InterfaceManage();
        System.out.println("initialize PreorderAllController");
    }

    private ArrayList<Order> queryPreorderProducts(){
        ArrayList<Order> orderArrayList = new ArrayList<Order>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();
        String preorder = "SELECT OP_ID,OP_Quantity,OP_Price,P_ID,OP_Type,OP_Status,Username FROM ORDER_PRODUCT WHERE OP_Type = '" + 2 + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(preorder);

            while (queryResult.next()) {
                int op_id = Integer.parseInt(queryResult.getString(1));
                int op_quantity = Integer.parseInt(queryResult.getString(2));
                int op_price = Integer.parseInt(queryResult.getString(3));
                int p_id = Integer.parseInt(queryResult.getString(4));
                int op_type = Integer.parseInt(queryResult.getString(5));
                int op_status = Integer.parseInt(queryResult.getString(6));
                String username = queryResult.getString(6);
                Order orderProduct = new Order(op_id, op_quantity, op_price, p_id, op_type, op_status, username);
                orderArrayList.add(orderProduct);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderArrayList;
    }

    private void showPreorderProducts() {
        gridProduct.getChildren().clear();
        int column = 0;
        int row = 0;
        try {
            for (Order order : orderList) {
                for (Product product : productList) {
                    if(order.getP_ID() == product.getP_ID()){
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/ku/cs/interfaces/product_preorder.fxml"));
                        AnchorPane anchorPane = fxmlLoader.load();
                        ProductPreorderController productPreorderController = fxmlLoader.getController();
                        productPreorderController.setData(order, product);
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

    private ArrayList<Product> getProductPreorder(){
        ArrayList<Product> productArrayList = new ArrayList<Product>();
        ConnectionClass connectionClass= new ConnectionClass();
        Connection connectDB =connectionClass.getConnection();

        String products = "SELECT P_ID,P_Name,P_Quantity,P_Price,P_Image FROM PRODUCT WHERE P_ID";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(products);
            while (queryResult.next()) {
                int p_id = Integer.parseInt(queryResult.getString(1));
                String p_name = queryResult.getString(2);
                int p_quantity = Integer.parseInt(queryResult.getString(3));
                int p_price = Integer.parseInt(queryResult.getString(4));
                String p_image = queryResult.getString(5);
                Product product = new Product(p_id, p_name, p_quantity, p_price, p_image);
                productArrayList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productArrayList;
    }

    private void InterfaceManage() {
        nameLabel.setText(ACCOUNT.getName());
        roleLabel.setText(ACCOUNT.getRole());
        btn_I.setText("Back");
        Image icon_addProduct = new Image(getClass().getResource("/ku/cs/icons/backButton.png").toExternalForm());
        iconButton_I.setImage(icon_addProduct);

        btn_II.setVisible(false);
        btn_III.setVisible(false);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        // todo: Back to Marketplace
        if (event.getSource() == btn_I) {
            try {
                FXRouter.goTo("marketplace", 1000, 600);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }

        // todo: Sign Out
        if (event.getSource().equals(btnSignOut)) {
            FXRouter.goTo("login", 750, 500);
        }
    }
    @FXML
    private void handleMouseEvent(MouseEvent event) {

        // todo: Close Program
        if (event.getSource() == btnClose) {
            System.exit(0);
        }
    }
}
