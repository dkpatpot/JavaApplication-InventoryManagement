module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires java.sql;

    opens ku.cs to javafx.fxml;
    exports ku.cs;

    exports ku.cs.controllers.register;
    opens ku.cs.controllers.register to javafx.fxml;

    exports ku.cs.controllers.login;
    opens ku.cs.controllers.login to javafx.fxml;

    exports ku.cs.controllers.product;
    opens ku.cs.controllers.product to javafx.fxml;

    exports ku.cs.controllers.market;
    opens ku.cs.controllers.market to javafx.fxml;

    exports ku.cs.controllers.manager;
    opens ku.cs.controllers.manager to javafx.fxml;
}
