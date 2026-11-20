/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package derick.carracesim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author deric
 */
public class CarRaceModel {
        
    private final List<Car> cars = new ArrayList<>();
    private final List<Car> finishOrder = new ArrayList<>();
    private final Random random = new Random();
    
    public CarRaceModel() {
        cars.add(new Car(Car.CarType.SPORTS,    23, Color.BLUE,      "Blue",   Car.START_X,  20, 50));
        cars.add(new Car(Car.CarType.SEDAN,     45, Color.RED,       "Red",    Car.START_X,  85, 50));
        cars.add(new Car(Car.CarType.COUPE,     78, Color.LIMEGREEN, "Green",  Car.START_X, 152, 50));
        cars.add(new Car(Car.CarType.HATCHBACK, 88, Color.GOLD,      "Yellow", Car.START_X, 235, 50));
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }
    
    public List<Car> getFinishOrder() {
        return Collections.unmodifiableList(finishOrder);
    }

    public Car getWinner() {
        return finishOrder.isEmpty() ? null : finishOrder.get(0);
    }

    public boolean isRaceFinished() {
        return finishOrder.size() == cars.size();
    }
    
    public void prepareRace() {
        reset();
        for (Car car : cars) {
            car.randomizeSpeed();
        }
    }

    public void recordFinish(Car car) {
        if (finishOrder.contains(car)) {
            return;
        }
        car.markFinished();
        finishOrder.add(car);
        finishOrder.sort(Comparator.comparingDouble(Car::getRaceTime));
    }
    
    /** Resets every car to the start and clears the results. */
    public void reset() {
        for (Car car : cars) {
            car.reset();
        }
        finishOrder.clear();
    }
}
