module com.amg.simplecalculatorclient {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.amg.simplecalculatorclient to javafx.fxml;
    exports com.amg.simplecalculatorclient;
}