/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package derick.carracesim;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.shape.Line;
/**
 * FXML Controller class
 *
 * @author deric
 */
public class SecondaryController implements Initializable {


    @FXML
    private Line carDividerLine1;
    @FXML
    private Line carDividerLine11;
    @FXML
    private Line carDividerLine12;
    @FXML
    private Group carBlue;
    @FXML
    private Group carRed;
    @FXML
    private Group carGreen;
    @FXML
    private Group carYellow;
    
    private final CarRaceModel model = new CarRaceModel();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        syncCars();
    }

    /** Moves each car's Group to the x/y stored in the model. */
    private void syncCars() {
        for (CarRaceModel.Car car : model.getCars()) {
            Group view;
            
            switch (car.getNumber()) {
                case 23:
                    view = carBlue;
                    break;
                case 45:
                    view = carRed;
                    break;
                case 78:
                    view = carGreen;
                    break;
                default:
                    view = carYellow;
                    break;
            }
            
            view.setLayoutX(car.getX());
            view.setLayoutY(car.getY());
        }
    }
    
}
