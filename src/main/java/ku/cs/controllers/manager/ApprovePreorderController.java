package ku.cs.controllers.manager;

import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static ku.cs.controllers.login.LoginController.ACCOUNT;

public class ApprovePreorderController {

    @FXML private Button btnAccept;
    @FXML private Button btnReject;
    @FXML private Circle btnClose;
    @FXML private Circle productPicture;
    @FXML private ImageView btnBack;
    @FXML private Label NameLabel;
    @FXML private TextField nameLabel;
    @FXML private TextField priceLabel;
    @FXML private TextField quantityLabel;
    @FXML private TextField totalPriceLabel;

    private Order order = (Order) FXRouter.getData();
    private Product product = (Product) FXRouter.getDataII();

    private Alert alert;
    @FXML
    public void initialize() {

        alert = new Alert(Alert.AlertType.NONE);
        showProductPreorderDetail();
        System.out.println("initialize ApproveController");
    }

    public void showProductPreorderDetail() {

        NameLabel.setText(product.getP_Name());
        nameLabel.setText(product.getP_Name());
        nameLabel.setEditable(false);

        priceLabel.setText(product.getP_Price() + " ฿/kg.");
        priceLabel.setEditable(false);

        quantityLabel.setText(order.getOP_Quantity() + " kg.");
        quantityLabel.setEditable(false);

        totalPriceLabel.setText(String.valueOf(order.getOP_Price()));
        totalPriceLabel.setEditable(false);

        File f = new File("images/"+product.getP_Image());
        productPicture.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
    }

    private void acceptPreorder() {

        ConnectionClass connectionClass= new ConnectionClass();
        Connection connection =connectionClass.getConnection();

        String userStatus = "UPDATE ORDER_PRODUCT SET OP_Type = 1 WHERE OP_ID = '" + order.getOP_ID() + "';";
        try {
            PreparedStatement statement = connection.prepareStatement(userStatus);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    private void rejectPreorder() {

        ConnectionClass connectionClass= new ConnectionClass();
        Connection connection =connectionClass.getConnection();

        String userStatus = "UPDATE ORDER_PRODUCT SET OP_Type = 3 WHERE OP_ID = '" + order.getOP_ID() + "';";

        try {
            PreparedStatement statement = connection.prepareStatement(userStatus);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // todo: Accept
        if (event.getSource() == btnAccept) {
            acceptPreorder();
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Approve Preorder");
            alert.show();
            try {
                FXRouter.goTo("preorder_all", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า preorder_all ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }

        }
        // todo: Reject
        if (event.getSource() == btnReject) {
            rejectPreorder();
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Preorder Denied");
            alert.show();
            try {
                FXRouter.goTo("preorder_all", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า preorder_all ไม่ได้");
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
                FXRouter.goTo("preorder_all", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า preorder_all ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
