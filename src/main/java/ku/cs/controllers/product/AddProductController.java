package ku.cs.controllers.product;

import animatefx.animation.Tada;
import fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.service.ConnectionClass;
import ku.cs.service.ThemeMode;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AddProductController {
    @FXML private BorderPane borderAddProduct;
    @FXML private Circle btnClose;
    @FXML private Circle productPicture;
    @FXML private ImageView btnBack;
    @FXML private Label  errorLabel;
    @FXML private TextField productNameTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField quantityTextField;
    @FXML private Button btnAddProduct;
    @FXML private Button btnChangePicture;
    @FXML private ComboBox categoryComboBox;

    private String currentCategory = "-";
    private Alert alert;
    File selectedFile;

    private String imageName;
    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.NONE);
        categoryComboBox.getItems().addAll(
                "spices",
                "vegetables",
                "fruits"
        );
        quantityTextField.setText("0");
        quantityTextField.setEditable(false);
        errorLabel.setVisible(false);
        System.out.println("initialize AddProductController");
    }

    @FXML
    public void handleAddProductBtn(ActionEvent actionEvent) throws SQLException, IOException {

        String name = productNameTextField.getText();
        String quantity = quantityTextField.getText();
        String price = productPriceTextField.getText();

        if (!isValidProductName(name)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid Name");
            alert.show();
        } else if (!isValidPrice(price)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid Price");
            alert.show();
        } else if (!isValidProductType(currentCategory)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid Type");
            alert.show();
        } else if (selectedFile==null) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please choose an image.");
            alert.show();
        } else {
            ConnectionClass connectionClass = new ConnectionClass();
            Connection connection = connectionClass.getConnection();
            String sql = "INSERT INTO PRODUCT (P_Name,P_Quantity,P_Type,P_Price,P_Image)VALUES('"
                    + name + "','" + quantity + "','" + currentCategory + "','" + price + "','" + imageName + "')";
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            File file = new File("images");
            Path path = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
            Files.copy(selectedFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
            try {
                FXRouter.goTo("add_product");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า add_product ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Add product success.");
            alert.show();
//
        }
    }

    @FXML
    public void handleBackToStoreBtn(MouseEvent actionEvent) {
        try {
            FXRouter.goTo("marketplace",1000, 600);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML void handleUploadProductImageBtn(ActionEvent actionEvent) throws IOException {

        if (actionEvent.getSource() == btnChangePicture) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image", "*.jpg", "*.png"));
            Stage stage = (Stage) borderAddProduct.getScene().getWindow();
            selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                String formattedDateTime = currentDateTime.format(formatter);
                String fileName = selectedFile.toString();
                int index = fileName.lastIndexOf('.');
                String fileExtension = fileName.substring(index + 1);
                imageName = "picture-" + formattedDateTime + "." + fileExtension;
                System.out.println(imageName);
                Image image = new Image("file:///" + selectedFile.getAbsolutePath());
                productPicture.setFill(new ImagePattern(image));
            }
        }
    }

    public boolean isValidProductName(String productName) {
        return ((productName!= null)
                && (!productName.equals(""))
                && (productName.matches("^[a-zA-Z]+(.+){1,13}$")));
    }

    public boolean isValidPrice(String price) {
        return ((price!= null)
                && (!price.equals(""))
                && (price.matches("^[0-9]{1,7}$")));
    }

    public boolean isValidProductType(String productType) {
        return ((productType!= null)
                && (!productType.equals("-")));
    }

    @FXML
    public void chooseCategory(ActionEvent actionEvent) {
        if (categoryComboBox.getValue().equals("spices")) {
            currentCategory = "spices";
        }
        if (categoryComboBox.getValue().equals("vegetables")) {
            currentCategory = "vegetables";
        }
        if (categoryComboBox.getValue().equals("fruits")) {
            currentCategory = "fruits";
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
                FXRouter.goTo("marketplace", 1000, 600);
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
