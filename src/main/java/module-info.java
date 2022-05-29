module com.example.simplest2djavafxgameengine {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simplest2djavafxgameengine to javafx.fxml;
    exports com.example.simplest2djavafxgameengine;
}