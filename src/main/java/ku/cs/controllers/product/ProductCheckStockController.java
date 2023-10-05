package ku.cs.controllers.product;

import fxrouter.FXRouter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ku.cs.model.product.Product;

import java.io.File;
import java.io.IOException;

public class ProductCheckStockController {
    @FXML
    private Label productNameLabel;
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
        Color paint = new Color(1.0, 0.0, 0.0, 1.0); // todo: Text Red
        quantityLabel.setTextFill(paint);

    }

    @FXML
    private void handleMouseEvent(MouseEvent event) throws IOException {

    }
}
