package ku.cs.controllers.market;

import animatefx.animation.SlideInUp;
import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.controllers.product.OrderProductController;
import ku.cs.model.account.Account;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static ku.cs.controllers.login.LoginController.ACCOUNT;

public class PayController {

    @FXML private Button btnPay;
    @FXML private Circle btnClose;
    @FXML private Circle productPicture;
    @FXML private ImageView btnBack;
    @FXML private Label NameLabel;
    @FXML private Label errorLabel;
    @FXML private TextField nameLabel;
    @FXML private TextField priceLabel;
    @FXML private TextField quantityLabel;
    @FXML private TextField orderProductTypeLabel;
    @FXML private TextField totalPriceTextField;

    private Order order = (Order) FXRouter.getData();
    private Product product = (Product) FXRouter.getDataII();
    public Alert alert;
    @FXML public void initialize() {

        alert = new Alert(Alert.AlertType.NONE);
        showOrderDetail();
        System.out.println("initialize PayController");
    }

    public void showOrderDetail() {
        NameLabel.setText(product.getP_Name());
        nameLabel.setText(product.getP_Name());
        nameLabel.setEditable(false);

        priceLabel.setText(product.getP_Price() + " ฿/kg.");
        priceLabel.setEditable(false);

        File f = new File("images/" + product.getP_Image());
        productPicture.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));

        quantityLabel.setText(order.getOP_Quantity() + " kg.");
        quantityLabel.setEditable(false);

        if (order.getOP_Type() == 0) {
            orderProductTypeLabel.setText("By-Order");
            orderProductTypeLabel.setEditable(false);
        } else if (order.getOP_Type() == 1){
            orderProductTypeLabel.setText("Pre-Order");
            orderProductTypeLabel.setEditable(false);
        } else if (order.getOP_Type() == 2){
            orderProductTypeLabel.setText("Wait for approve");
            orderProductTypeLabel.setEditable(false);
        }

        totalPriceTextField.setText(String.valueOf(order.getOP_Price()));
        totalPriceTextField.setEditable(false);

    }

    private void changeOPStatus() {

        ConnectionClass connectionClass= new ConnectionClass();
        Connection connection =connectionClass.getConnection();

        String orderStatus = "UPDATE ORDER_PRODUCT SET OP_Status = 1 WHERE OP_ID = '" + order.getOP_ID() + "';";

        try {
            PreparedStatement statement = connection.prepareStatement(orderStatus);
            statement.executeUpdate();
        }

        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        // todo: Button Pay
        if (event.getSource() == btnPay) {
            changeOPStatus();
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Pay Success");
            alert.show();
            FXRouter.goTo("purchase_orders", 1000, 600);
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
                FXRouter.goTo("purchase_orders", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า purchase_orders ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
