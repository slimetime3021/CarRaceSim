module derick.carracesim {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.media;

    opens derick.carracesim to javafx.fxml;
    exports derick.carracesim;
}
