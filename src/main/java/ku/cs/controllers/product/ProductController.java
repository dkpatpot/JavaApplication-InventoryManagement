package ku.cs.controllers.product;

import fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ku.cs.controllers.login.LoginController;
import ku.cs.model.account.Account;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductController {
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label quantityLabel;
    @FXML private Rectangle productPicture;

    private Product product;

    @FXML
    private void initialize() {
        Image image = new Image(getClass().getResource("/ku/cs/icons/product.png").toExternalForm());
        productPicture.setFill(new ImagePattern(image));
        System.out.println("initialize ProductController");
    }
    public void setData(Product product) {
        this.product = product;
        File f = new File("images/"+product.getP_Image());
        productPicture.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
        productNameLabel.setText(product.getP_Name());
        priceLabel.setText(product.getP_Price() + " ฿/kg.");
        quantityLabel.setText(product.getP_Quantity() + " kg.");

    }

    @FXML
    private void handleMouseEvent(MouseEvent event) throws IOException {
        if (event.getSource() == productPicture) {
            if (LoginController.ACCOUNT.getRole().equals("Customer")) {
                try {
                    FXRouter.goTo("product_detail", product,750, 500);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า product_detail ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
    }

}
