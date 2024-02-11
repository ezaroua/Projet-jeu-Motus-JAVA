module fr.esgi {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	requires javafx.graphics;

    opens fr.esgi.motus to javafx.fxml;
    exports fr.esgi.motus;
}
