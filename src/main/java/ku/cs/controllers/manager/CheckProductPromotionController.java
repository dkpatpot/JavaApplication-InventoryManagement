package ku.cs.controllers.manager;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;

public class CheckProductPromotionController {

//    @FXML Alert alert;
//
//    @FXML
//    public void initialize() {
//
//        Alert alert = new Alert(Alert.AlertType.NONE);
//        this.product = queryProduct();
//        this.productList = queryInStockProduct();
//        selectProduct();
//        expDate.setValue(LocalDate.now());
//        System.out.println("initialize ImportLotController");
//    }
//
//    public void dateIndex (){
//        LocalDate currentDate = LocalDate.now();
//        Date date = new Date(String.valueOf(currentDate));
//        System.out.println(date);
//        long msec = date.getTime();
//        msec += 30 * 24 * 60 * 60 * 1000L;
//        date.setTime(msec);
//        System.out.println(date);
//    }
//
//    private void queryImportStock() throws SQLException {
//        LocalDate now = LocalDate.now();
//        String l_quantity = addQuantityTextField.getText();
//        for (Product product : productList) {
//            if (product.getP_Name().equals(currentProduct)) {
//                ConnectionClass connectNow = new ConnectionClass();
//                Connection connectDB = connectNow.getConnection();
//
//                String importStock = "INSERT INTO LOT (L_Date, P_ID, L_Exp, L_Quantity)VALUES('"
//                        + now + "','" + product.getP_ID() + "','" + expDate.getValue() + "','" + l_quantity + "')";
//                Statement statement = connectDB.createStatement();
//                statement.executeUpdate(importStock);
//                alert.setAlertType(Alert.AlertType.WARNING);
//                alert.setContentText("เพิ่ม stock สินค้าสำเร็จ");
//                alert.show();
//            }
//        }
//    }
}
