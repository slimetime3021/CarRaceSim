module derick.carracesim {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens derick.carracesim to javafx.fxml;
    exports derick.carracesim;
}
