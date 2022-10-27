module com.example.aisproject {
    requires javafx.controls;
    requires javafx.fxml;
            
                        requires org.kordamp.bootstrapfx.core;
            
    opens com.example.aisproject to javafx.fxml;
    exports com.example.aisproject;
}