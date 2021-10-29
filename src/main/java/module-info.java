module com.example.tuitionfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tuitionfx to javafx.fxml;
    exports com.example.tuitionfx;
}