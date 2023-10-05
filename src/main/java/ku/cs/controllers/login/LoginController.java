package ku.cs.controllers.login;

import animatefx.animation.Flash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import fxrouter.FXRouter;
import javafx.scene.shape.Circle;
import ku.cs.model.account.Account;
import ku.cs.service.ConnectionClass;
import ku.cs.service.ThemeMode;

import java.io.IOException;
import java.sql.*;


public class LoginController {

    @FXML private Button btnLogin;
    @FXML private Button btnSignUp;
    @FXML private Circle btnClose;
    @FXML private Label messageLogin;
    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;

    public static Account ACCOUNT;
    private Alert alert;
    public static boolean isLightMode = true;

    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.NONE);
    }


    public void validation(){
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM mydb.user WHERE Username = '" + username + "' AND password = '" + password + "'";
        String userStatus = "UPDATE USER SET U_Status = 1 WHERE Username = '" + username + "';";
        try {
            Statement statement = connectDB.createStatement();
            PreparedStatement statement1 = connectDB.prepareStatement(userStatus);
            statement1.executeUpdate();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1)==1) {
//                    changeLoginStatus();
                    queryUser(username);
                    FXRouter.goTo("marketplace", ACCOUNT, 1000, 600);
                } else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setContentText("Username or password is incorrect.");
                    messageLogin.setText("Username or password is incorrect.");
                    alert.show();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า marketplace ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    private void queryUser(String username){
        ConnectionClass connectNow = new ConnectionClass();
        Connection connectDB = connectNow.getConnection();

        String usernameLogin = "SELECT * FROM mydb.user WHERE Username = '" + username + "';";
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(usernameLogin);

            while (queryResult.next()) {
                username = queryResult.getString(1);
                String password = queryResult.getString(2);
                String u_name = queryResult.getString(3);
                String email = queryResult.getString(4);
                String phone = queryResult.getString(5);
                String address = queryResult.getString(6);
                String role = queryResult.getString(7);
                String postcode = queryResult.getString(8);
                int status = Integer.parseInt(queryResult.getString(9));


                this.ACCOUNT = new Account(username, password, u_name, email, phone, address, role, postcode, status);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {

        // todo: Login
        if (event.getSource() == btnLogin) {
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            if (!username.isEmpty() && !password.isEmpty()) {
                validation();
            } else {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setContentText("Username or password is incorrect.");
                messageLogin.setText("Username or password is incorrect.");
                alert.show();
            }
        }

        // todo: Sign up
        if (event.getSource() == btnSignUp) {
            try {
                FXRouter.goTo("register");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า register ไม่ได้");
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
    }
}
