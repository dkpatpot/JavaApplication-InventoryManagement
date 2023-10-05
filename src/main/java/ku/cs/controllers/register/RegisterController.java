package ku.cs.controllers.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import fxrouter.FXRouter;
import ku.cs.service.ConnectionClass;
import ku.cs.service.ThemeMode;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {

    @FXML private Circle btnClose;
    @FXML private ImageView btnBack;
    @FXML private TextField nameTextField;
    @FXML private TextField usernameTextField;
    @FXML private TextField emailTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
//    @FXML private Circle circleImage;
//    @FXML private AnchorPane pane;

    private Alert alert;
    private File imageFile;

    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.NONE);
//        circleImage.setFill(new ImagePattern(new Image(getClass().getResource("/ku/cs/images/default_profile.png").toExternalForm())));
//        ThemeMode.setThemeMode(pane);
        System.out.println("initialize RegisterController");
    }

    @FXML
    public void handleRegisterBtn(ActionEvent actionEvent) throws SQLException {
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String email = emailTextField.getText();
        String phone = phoneTextField.getText();
        String address = addressTextField.getText();
        String postcode = postTextField.getText();
        String confirmPassword = confirmPasswordField.getText();
        int status = 0;

        if (name.isEmpty() || username.isEmpty() || phone.isEmpty() ||
            address.isEmpty() || postcode.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Please complete the information.");
            alert.show();
        } else if (!isValidName(name)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid name");
            alert.show();
        } else if (!isValidUsername(username)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid username");
            alert.show();
            usernameTextField.setStyle("-fx-text-fill: red");
        } else if (!isValidPhone(phone)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid phone");
            alert.show();
        } else if (!isValidAddress(address)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid address");
            alert.show();
        } else if (!isValidPostcode(postcode)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid postcode");
            alert.show();
        } else if (!isValidPassword(password)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Invalid password");
            alert.show();
        } else if (!password.equals(confirmPassword)) {
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setContentText("Password doesn't match.");
            alert.show();
        } else {
            if(checkUsername()) {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection connection = connectionClass.getConnection();
                String sql = "INSERT INTO USER (Username,Password,U_Name,Email,Phone,Address,Role,Postcode,U_Status)VALUES('"
                        + username + "','" + password + "','" + name + "','" + email + "','" + phone + "','" + address + "','"
                        + "Customer" + "','" + postcode + "','" + status + "')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setContentText("Register Success");
                alert.show();

                try {
                    FXRouter.goTo("login");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า login ไม่ได้");
                }
                    System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    public boolean checkUsername(){
        String username = usernameTextField.getText();
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String verifyUsername = "SELECT count(1) FROM USER WHERE Username = '" + username + "'";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyUsername);

            while (queryResult.next()) {
                if (queryResult.getInt(1)==0) {
                    return true;
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Username it already!");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean isValidPassword(String password) {
        return ((password!= null)
                && (!password.equals(""))
                && (password.matches("^[a-zA-Z0-9]{8,20}$")));
    }

    public boolean isValidUsername(String username) {
        return ((username!= null)
                && (!username.equals(""))
                && (username.matches("^[a-zA-Z]+(.+){8,20}$")));
    }

    public boolean isValidAddress(String address) {
        return ((address!= null)
                && (!address.equals(""))
                && (address.matches("^[a-zA-Z0-9]+(.+){5,100}$")));
    }

    public boolean isValidPhone(String phone) {
        return ((phone!= null)
                && (!phone.equals(""))
                && (phone.matches("^[0-9]{10}$")));
    }

    public boolean isValidPostcode(String postcode) {
        return ((postcode!= null)
                && (!postcode.equals(""))
                && (postcode.matches("^[0-9]{5}$")));
    }
    public boolean isValidName(String name) {
        return ((name!= null) && (!name.equals("")));
    }

    @FXML
    private void handleMouseEvent(MouseEvent event) {

        // todo: Close Program
        if (event.getSource() == btnClose) {
            System.exit(0);
        }

        // todo: Button back
        if (event.getSource() == btnBack) {
            try {
                FXRouter.goTo("login");
            } catch (IOException e) {
                System.err.println("กลับไปที่หน้า login ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

}
