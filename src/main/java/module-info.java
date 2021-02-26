module poo2.doodle {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;

    opens poo2.doodle to javafx.fxml;
    exports poo2.doodle;
}