/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package derick.carracesim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author deric
 */
public class CarRaceModel {
        
    /** Where cars line up and where they count as finished (matches secondary.fxml). */
    public static final double START_X = 15.0;
    public static final double FINISH_X = 440.0;

    /** Kinds of cars that can take part in the race. */
    public enum CarType { SPORTS, SEDAN, COUPE, HATCHBACK }

    /** A single car in the race. */
    public static class Car {

        private final CarType type;
        private final int number;
        private final Color color;
        private final double startX;
        private final double startY;
        private final double baseSpeed;   // pixels per second

        private double x;
        private double y;
        private double speed;             // current speed, pixels per second
        private boolean finished;

        public Car(CarType type, int number, Color color,
                   double startX, double startY, double baseSpeed) {
            this.type = type;
            this.number = number;
            this.color = color;
            this.startX = startX;
            this.startY = startY;
            this.baseSpeed = baseSpeed;
            reset();
        }

        public CarType getType()     { return type; }
        public int getNumber()       { return number; }
        public Color getColor()      { return color; }
        public double getX()         { return x; }
        public double getY()         { return y; }
        public double getSpeed()     { return speed; }
        public double getBaseSpeed() { return baseSpeed; }
        public boolean isFinished()  { return finished; }

        public void setSpeed(double speed) { this.speed = Math.max(0, speed); }

        /**
         * Moves the car forward along the track.
         * @param deltaSeconds time since the last update, in seconds
         * @param multiplier   simulation speed multiplier (e.g. from the slider)
         */
        public void updatePosition(double deltaSeconds, double multiplier) {
            if (finished) {
                return;
            }
            x += speed * multiplier * deltaSeconds;
            if (x >= FINISH_X) {
                x = FINISH_X;
                speed = 0;
                finished = true;
            }
        }

        /** Puts the car back on the starting line. */
        public void reset() {
            x = startX;
            y = startY;
            speed = baseSpeed;
            finished = false;
        }
    }

    private final List<Car> cars = new ArrayList<>();
    private final List<Car> finishOrder = new ArrayList<>();
    private final Random random = new Random();

    /** Creates the default race: blue 23, red 45, green 78, yellow 88 (one per lane). */
    public CarRaceModel() {
        cars.add(new Car(CarType.SPORTS,    23, Color.BLUE,      START_X,  20, 80));
        cars.add(new Car(CarType.SEDAN,     45, Color.RED,       START_X,  85, 80));
        cars.add(new Car(CarType.COUPE,     78, Color.LIMEGREEN, START_X, 152, 80));
        cars.add(new Car(CarType.HATCHBACK, 88, Color.GOLD,      START_X, 235, 80));
    }

    public List<Car> getCars() {
        return Collections.unmodifiableList(cars);
    }

    /** Cars in the order they crossed the finish line (first = winner). */
    public List<Car> getFinishOrder() {
        return Collections.unmodifiableList(finishOrder);
    }

    public Car getWinner() {
        return finishOrder.isEmpty() ? null : finishOrder.get(0);
    }

    public boolean isRaceFinished() {
        return finishOrder.size() == cars.size();
    }

    /**
     * Advances the whole race by one tick: gives each car a small random
     * speed variation, moves it, and records who has finished.
     */
    public void updateRace(double deltaSeconds, double multiplier) {
        for (Car car : cars) {
            if (car.isFinished()) {
                continue;
            }
            // speed wanders between 70% and 130% of the car's base speed
            car.setSpeed(car.getBaseSpeed() * (0.7 + random.nextDouble() * 0.6));
            car.updatePosition(deltaSeconds, multiplier);
            if (car.isFinished()) {
                finishOrder.add(car);
            }
        }
    }

    /** Resets every car to the start and clears the results. */
    public void reset() {
        for (Car car : cars) {
            car.reset();
        }
        finishOrder.clear();
    }
    
}
