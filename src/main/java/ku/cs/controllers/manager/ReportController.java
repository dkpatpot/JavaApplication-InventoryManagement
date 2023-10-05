 package ku.cs.controllers.manager;

import fxrouter.FXRouter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import ku.cs.model.product.Order;
import ku.cs.model.product.Product;
import ku.cs.service.ConnectionClass;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import static ku.cs.controllers.login.LoginController.ACCOUNT;

 public class ReportController {

    @FXML private Button btn;
    @FXML private Button btnSignOut;
    @FXML private Circle btnClose;
    @FXML private Circle productImage;
    @FXML private ImageView iconBtn;
    @FXML private ImageView iconSignOut;
    @FXML private Label roleLabel;
    @FXML private Label nameLabel;
    @FXML private ListView<Order> orderListView;
    @FXML private TextField productName;
    @FXML private TextField productQuantity;
    @FXML private TextField productType;
    @FXML private TextField totalPrice;
    @FXML private TextField customerName;

//    ListView<String> listView =new ListView<String>();
    public ArrayList<Order> orderList ;
    private ArrayList<Product> productList;
    private Product product;
    private Order order;

    @FXML public void initialize() {
        this.orderList = queryOrderProducts();
        this.productList = queryAllProduct();
        this.product = queryProduct();
        this.order = queryOrder();
        showOrderListView();
//        clearSelectReport();
        handleSelectListView();
        roleLabel.setText(ACCOUNT.getRole());
        nameLabel.setText(ACCOUNT.getName());
        btn.setText("Back");
        Image icon_back = new Image(getClass().getResource("/ku/cs/icons/backButton.png").toExternalForm());
        iconBtn.setImage(icon_back);
        Image icon_signOut = new Image(getClass().getResource("/ku/cs/icons/log_out.png").toExternalForm());
        iconSignOut.setImage(icon_signOut);
        Alert alert = new Alert(Alert.AlertType.NONE);
        System.out.println("initialize ReportController");
    }

     public class PersonCellFactory implements Callback<ListView<Order>, ListCell<Order>> {
         @Override
         public ListCell<Order> call(ListView<Order> param) {
             return new ListCell<>(){
                 @Override
                 public void updateItem(Order orderListview, boolean empty) {
                     super.updateItem(orderListview, empty);
                     if (empty || orderListview == null) {
                         setText(null);
                     } else {
                         setText(orderListview.getOP_ID() + " Customer" + orderListview.getUsername());
                     }
                 }
             };
         }
     }

    public void showOrderListView(){
        for (Order order : orderList) {
            for (Product products : productList) {
                if (products.getP_ID() == order.getP_ID()) {
                    orderListView.getItems().addAll(order);
                }
            }
        }
    }

     private void handleSelectListView(){
         orderListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Order>() {
             @Override
             public void changed(ObservableValue<? extends Order> observable, Order oldValue, Order newValue) {
                 showOrderDetail(newValue);
             }
         });
     }

     public void showOrderDetail(Order order) {

        productQuantity.setText(String.valueOf(order.getOP_Quantity()));
        productQuantity.setEditable(false);

        totalPrice.setText("฿" + order.getOP_Price());
        totalPrice.setEditable(false);

        customerName.setText(order.getUsername());
        customerName.setEditable(false);

        for (Product products : productList){
            if (products.getP_ID() == order.getP_ID()){
                File f = new File("images/" + products.getP_Image());
                productImage.setFill(new ImagePattern(new Image("file:///" + f.getAbsolutePath())));

                productName.setText(products.getP_Name());
                productName.setEditable(false);

                productType.setText(products.getP_Type());
                productType.setEditable(false);
            }
        }

     }

     private ArrayList<Product> queryAllProduct(){
         ArrayList<Product> productArrayList = new ArrayList<Product>();
         ConnectionClass connectionClass= new ConnectionClass();
         Connection connectDB =connectionClass.getConnection();
         String sql = "SELECT P_ID,P_Name,P_Quantity,P_Type,P_Price,P_Image FROM PRODUCT;";
         try {
             Statement statement = connectDB.createStatement();
             ResultSet queryResult = statement.executeQuery(sql);

             while (queryResult.next()) {
                 int p_id = Integer.parseInt(queryResult.getString(1));
                 String p_name = queryResult.getString(2);
                 int p_quantity = Integer.parseInt(queryResult.getString(3));
                 String p_type = queryResult.getString(4);
                 int p_price = Integer.parseInt(queryResult.getString(5));
                 String p_image = queryResult.getString(6);
                 Product product = new Product(p_id, p_name, p_quantity, p_type, p_price, p_image);
                 productArrayList.add(product);
             }

         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
         return productArrayList;
     }

     private Product queryProduct() {
         ConnectionClass connectNow = new ConnectionClass();
         Connection connectDB = connectNow.getConnection();

         String products = "SELECT * FROM PRODUCT WHERE P_ID";
         try {
             Statement statement = connectDB.createStatement();
             ResultSet queryResult = statement.executeQuery(products);

             while (queryResult.next()) {
                 int p_id = Integer.parseInt(queryResult.getString(1));
                 String p_name = queryResult.getString(2);
                 int p_quantity = Integer.parseInt(queryResult.getString(3));
                 String p_type = queryResult.getString(4);
                 int p_price = Integer.parseInt(queryResult.getString(5));
                 String p_image = queryResult.getString(6);

                 Product product1 = new Product(p_id, p_name, p_quantity, p_type, p_price, p_image);
                 return product1;
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
         return null;
     }
     private ArrayList<Order> queryOrderProducts(){
         ArrayList<Order> orderArrayList = new ArrayList<Order>();
         ConnectionClass connectionClass= new ConnectionClass();
         Connection connectDB =connectionClass.getConnection();
         String orderSQL = "SELECT OP_ID,OP_Quantity,OP_Price,P_ID,OP_Type,OP_Status,Username FROM ORDER_PRODUCT WHERE OP_Status = 1 ;";
         try {
             Statement statement = connectDB.createStatement();
             ResultSet queryResult = statement.executeQuery(orderSQL);

             while (queryResult.next()) {
                 int op_id = Integer.parseInt(queryResult.getString(1));
                 int op_quantity = Integer.parseInt(queryResult.getString(2));
                 int op_price = Integer.parseInt(queryResult.getString(3));
                 int p_id = Integer.parseInt(queryResult.getString(4));
                 int op_type = Integer.parseInt(queryResult.getString(5));
                 int op_status = Integer.parseInt(queryResult.getString(6));
                 String username = queryResult.getString(7);
                 Order orderProduct = new Order(op_id, op_quantity, op_price, p_id, op_type,op_status,username);
                 orderArrayList.add(orderProduct);
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
         return orderArrayList;
     }

     private Order queryOrder(){
         Order orderArrayList = new Order();
         ConnectionClass connectionClass= new ConnectionClass();
         Connection connectDB =connectionClass.getConnection();
         String orderSQL = "SELECT OP_ID,OP_Quantity,OP_Price,P_ID,OP_Type,OP_Status,Username FROM ORDER_PRODUCT ";
         try {
             Statement statement = connectDB.createStatement();
             ResultSet queryResult = statement.executeQuery(orderSQL);

             while (queryResult.next()) {
                 int op_id = Integer.parseInt(queryResult.getString(1));
                 int op_quantity = Integer.parseInt(queryResult.getString(2));
                 int op_price = Integer.parseInt(queryResult.getString(3));
                 int p_id = Integer.parseInt(queryResult.getString(4));
                 int op_type = Integer.parseInt(queryResult.getString(5));
                 int op_status = Integer.parseInt(queryResult.getString(6));
                 String username = queryResult.getString(7);
                 Order orderProduct = new Order(op_id, op_quantity, op_price, p_id, op_type,op_status,username);
                 return orderProduct;
             }
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }
         return null;
     }
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        // todo: Back
        if (event.getSource().equals(btn)) {
            FXRouter.goTo("marketplace", 1000, 600);
        }

        // todo: Sign Out
        if (event.getSource().equals(btnSignOut)) {
            FXRouter.goTo("login", 750, 500);
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
