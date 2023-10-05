package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import fxrouter.FXRouter;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Image icon = new Image(getClass().getResource("/ku/cs/icons/moon.png").toExternalForm());
        stage.getIcons().add(icon);
        FXRouter.bind(this, stage, "โชคชัยพัฒนา", 750, 500);
        configRoute();
        FXRouter.goTo("login");
    }

    private static void configRoute() {
        String packageStr = "ku/cs/interfaces/";
        FXRouter.when("add_product", packageStr+ "add_product.fxml");
        FXRouter.when("approve_preorder", packageStr+ "approve_preorder.fxml");
        FXRouter.when("check_lot", packageStr+ "check_lot.fxml");
        FXRouter.when("create_employee", packageStr+ "create_employee.fxml");
        FXRouter.when("create_promotion",packageStr+ "create_promotion.fxml");
        FXRouter.when("create_user", packageStr+ "create_user.fxml");
        FXRouter.when("import_lot", packageStr+ "import_lot.fxml");
        FXRouter.when("login", packageStr+ "login.fxml");
        FXRouter.when("marketplace", packageStr+ "market.fxml");
        FXRouter.when("order_product", packageStr+ "order_product.fxml");
        FXRouter.when("pay", packageStr+ "pay.fxml");
        FXRouter.when("preorder", packageStr+ "preorder.fxml");
        FXRouter.when("preorder_all", packageStr+ "preorder_all.fxml");
        FXRouter.when("product", packageStr+ "product.fxml");
        FXRouter.when("product_check_stock", packageStr+ "product_check_stock.fxml");
        FXRouter.when("product_detail", packageStr+ "product_detail.fxml");
        FXRouter.when("product_exp", packageStr+ "product_exp.fxml");
        FXRouter.when("product_preorder", packageStr+ "product_preorder");
        FXRouter.when("purchase_orders",packageStr+ "purchase_orders.fxml");
        FXRouter.when("register", packageStr+ "register.fxml");
        FXRouter.when("report", packageStr+ "report.fxml");
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}