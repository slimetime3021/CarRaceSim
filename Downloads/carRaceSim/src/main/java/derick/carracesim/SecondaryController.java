/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package derick.carracesim;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author deric
 */
public class SecondaryController implements Initializable {
    
    private static final String[] PLACES = {"1st", "2nd", "3rd", "4th"};

    @FXML
    private Button startRaceBtn;
    @FXML
    private Button pauseRaceBtn;
    @FXML
    private Button resetRaceBtn;
    @FXML
    private Slider simSpeedSlider;
    @FXML
    private VBox resultsBox;
    @FXML
    private Label lblResultsTitle;
    @FXML
    private Label lblPlace1;
    @FXML
    private Label lblPlace2;
    @FXML
    private Label lblPlace3;
    @FXML
    private Label lblPlace4;
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
    private final Map<Integer, Group> carViews = new HashMap<>();
    private Label[] placeLabels;
    private ParallelTransition race;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carViews.put(23, carBlue);
        carViews.put(45, carRed);
        carViews.put(78, carGreen);
        carViews.put(88, carYellow);

        placeLabels = new Label[]{lblPlace1, lblPlace2, lblPlace3, lblPlace4};

        // A car has finished when its translateX reaches the end of its TranslateTransition
        for (Car car : model.getCars()) {
            carViews.get(car.getNumber()).translateXProperty().addListener((obs, oldV, newV) -> {
                if (newV.doubleValue() >= car.getDistance()) {
                    model.recordFinish(car);
                    showResults();
                }
            });
        }

        syncCars();
        clearResults();
    }
    
    private void syncCars() {
        for (Car car : model.getCars()) {
            Group view = carViews.get(car.getNumber());
            view.setLayoutX(car.getX());
            view.setLayoutY(car.getY());
        }
    }
    
    @FXML
    private void startRace(ActionEvent event) {
                if (race != null) {
            Animation.Status status = race.getStatus();
            if (status == Animation.Status.PAUSED) {
                race.play();          // resume
                return;
            }
            if (status == Animation.Status.RUNNING) {
                return;               // already racing
            }
        }

        // fresh race
        clearRace();
        model.prepareRace();

        race = new ParallelTransition();
        for (Car car : model.getCars()) {
            TranslateTransition move = new TranslateTransition(
                    Duration.seconds(car.getRaceTime()), carViews.get(car.getNumber()));
            move.setFromX(0);
            move.setToX(car.getDistance());
            move.setInterpolator(Interpolator.LINEAR);
            race.getChildren().add(move);
        }

        // the slider controls how fast the whole race plays
        race.rateProperty().bind(simSpeedSlider.valueProperty());
        race.setOnFinished(e -> raceFinished());

        lblResultsTitle.setText("Race in progress...");
        race.play();
    }

    @FXML
    private void pauseRace(ActionEvent event) {
        if (race != null && race.getStatus() == Animation.Status.RUNNING) {
            race.pause();
        }
    }

    @FXML
    private void resetRace(ActionEvent event) {
        clearRace();
    }
    
        private void clearRace() {
        if (race != null) {
            race.stop();
            race.rateProperty().unbind();
            race = null;
        }
        for (Group view : carViews.values()) {
            view.setTranslateX(0);
        }
        model.reset();
        syncCars();
        clearResults();
    }

    private void raceFinished() {
        Car winner = model.getWinner();
        if (winner != null) {
            lblResultsTitle.setText("Race Finished! Car #" + winner.getNumber()
                    + " (" + winner.getColorName() + ") is the winner!");
        }
    }

    private void clearResults() {
        lblResultsTitle.setText("Race Results");
        for (int i = 0; i < placeLabels.length; i++) {
            placeLabels[i].setText(PLACES[i] + "  -");
        }
    }

    /** Fills the placement labels from the model's finish order (first place at the top). */
    private void showResults() {
        List<Car> order = model.getFinishOrder();
        for (int i = 0; i < order.size(); i++) {
            Car car = order.get(i);
            placeLabels[i].setText(String.format("%s  Car #%d (%s) - %s - %.2f s",
                    PLACES[i], car.getNumber(), car.getColorName(), car.getType(), car.getRaceTime()));
        }
    }

}
