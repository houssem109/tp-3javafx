module NewJavaFix {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
	requires java.sql;
    
    opens application to javafx.fxml, javafx.base;
    exports application;
}