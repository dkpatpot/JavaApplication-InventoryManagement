package ku.cs.controllers.product;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ku.cs.model.product.Lot;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;

import java.io.File;
import java.io.IOException;

public class ProductExpController {
    @FXML private Label productNameLabel;
    @FXML private Label quantityLabel;
    @FXML private Label expLabel;
    @FXML private Rectangle productPicture;

    private Product product;

    @FXML
    private void initialize() {
        Image image = new Image(getClass().getResource("/ku/cs/icons/product.png").toExternalForm());
        productPicture.setFill(new ImagePattern(image));
        System.out.println("initialize ProductController");
    }
    public void setData(Lot lot, Product product) {
        this.product = product;
        File f = new File("images/"+product.getP_Image());
        productPicture.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));
        productNameLabel.setText(product.getP_Name());
        expLabel.setText(String.valueOf(lot.getL_Exp()));
        Color paint = new Color(1.0, 0.0, 0.0, 1.0); // todo: Text Red
        expLabel.setTextFill(paint);
        quantityLabel.setText(lot.getL_Quantity() + " kg.");

    }

    @FXML
    private void handleMouseEvent(MouseEvent event) throws IOException {

    }
}
