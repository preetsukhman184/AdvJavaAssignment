module com.jdbc.economydata {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;

    opens com.jdbc.economydata to javafx.fxml;
    exports com.jdbc.economydata;
}