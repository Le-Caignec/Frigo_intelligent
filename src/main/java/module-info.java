module fr.tse.thextremdevs.smartfridge_app {
    requires javafx.controls;
    requires javafx.fxml;
	requires json;
	requires javafx.graphics;
	requires java.sql;
	requires javafx.base;

    opens fr.tse.thextremdevs.smartfridge_app to javafx.fxml;
    opens fr.tse.thextremdevs.smartfridge_app.view_manager to javafx.fxml;
    opens fr.tse.thextremdevs.smartfridge_app.fridge to javafx.base;
    
    exports fr.tse.thextremdevs.smartfridge_app;
    exports fr.tse.thextremdevs.smartfridge_app.view_manager;
}
