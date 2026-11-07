/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package derick.carracesim;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author deric
 */
public class PrimaryController implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private Button startRaceBtn;
    @FXML
    private Label lblImage;

// Display order: red, green, blue, yellow
    private static final String[] CAR_FILES = {
        "/images/redCarSprite.png",
        "/images/greenCarSprite.png",
        "/images/blueCarSprite.png",
        "/images/yellowCarSprite.png"
    };

    private static final int DELAY_SECONDS = 10;
 
    private int currentIndex = 0;

    private Timeline carCycle;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

// Show the first car right away
        showCar(currentIndex);

// Every 10 seconds, move to the next car; wraps back to red after yellow
        carCycle = new Timeline(new KeyFrame(Duration.seconds(DELAY_SECONDS), e -> {
            currentIndex = (currentIndex + 1) % CAR_FILES.length;
            showCar(currentIndex);
        }));
        
        carCycle.setCycleCount(Timeline.INDEFINITE);
        
        carCycle.play();
    }    

    /**
     * Loads sprite at given index and sets it on lblImage
     * @param index the given index of the sprite
     */
    private void showCar(int index) {
        
        URL res = getClass().getResource(CAR_FILES[index]);
        
        if (res == null) {
            throw new IllegalStateException("Missing sprite: " + CAR_FILES[index]);
        }
        
        ImageView view = new ImageView(new Image(res.toExternalForm()));
        
        view.setPreserveRatio(true);
        
        view.setFitWidth(400);
 
        lblImage.setText("");
        
        lblImage.setGraphic(view);
    }
    
    @FXML
    private void switchToRaceView(ActionEvent event) throws IOException {
        if (carCycle != null) {
            carCycle.stop();
        }
        
        App.setRoot("secondary");
    }
    
}
