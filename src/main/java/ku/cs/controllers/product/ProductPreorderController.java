package ku.cs.controllers.product;

import fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;

import java.io.File;
import java.io.IOException;

public class ProductPreorderController {
    @FXML private Label productNameLabel;
    @FXML private Label priceLabel;
    @FXML private Label quantityLabel;
    @FXML private Label typeLabel;
    @FXML private Rectangle productPicture;

    private Order order;
    private Product product;

    @FXML
    private void initialize() {
        Image image = new Image(getClass().getResource("/ku/cs/icons/product.png").toExternalForm());
        productPicture.setFill(new ImagePattern(image));
        System.out.println("initialize ProductPreorderController");
    }

    public void setData(Order order,Product product) {
        this.order = order;
        this.product = product;
        File f = new File("images/"+ product.getP_Image());
        productPicture.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
        productNameLabel.setText(product.getP_Name());
        priceLabel.setText(order.getOP_Price() + " ฿/kg.");
        quantityLabel.setText(order.getOP_Quantity() + " kg.");
        String type = String.valueOf(order.getOP_Type());
        if (type.equals("0")) {
            typeLabel.setText("By-Order");
            Color paint = new Color(0.0, 0.6902, 0.1882, 1.0);
            typeLabel.setTextFill(paint);
        } else if (type.equals("1")){
            typeLabel.setText("Pre-Order");
            Color paint = new Color(0.0, 0.6902, 0.1882, 1.0);
            typeLabel.setTextFill(paint);
        } else if (type.equals("2")){
            typeLabel.setText("Wait for approve");
            Color paint = new Color(0.6392, 0.5843, 0.0078, 1.0);
            typeLabel.setTextFill(paint);
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event) throws IOException {
        if (event.getSource() == productPicture) {
            try {
                FXRouter.goTo("approve_preorder", order, product, 750, 500);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า approve_preorder ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
