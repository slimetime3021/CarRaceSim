/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package derick.carracesim;

import java.util.Random;
import javafx.scene.paint.Color;

/**
 *
 * @author deric
 */
public class Car {
    
    public static final double START_X = 15.0;
    public static final double FINISH_X = 440.0;

    public enum CarType {
        SPORTS("Sports"), SEDAN("Sedan"), COUPE("Coupe"), HATCHBACK("Hatchback");

        private final String label;

        CarType(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    private final CarType type;
    private final int number;
    private final Color color;
    private final String colorName;
    private final double startX;
    private final double startY;
    private final double baseSpeed;   // pixels per second

    private double x;
    private double y;
    private double speed;
    private double raceTime;
    private boolean finished;

    public Car(CarType type, int number, Color color, String colorName,
               double startX, double startY, double baseSpeed) {
        this.type = type;
        this.number = number;
        this.color = color;
        this.colorName = colorName;
        this.startX = startX;
        this.startY = startY;
        this.baseSpeed = baseSpeed;
        reset();
    }

    public CarType getType()     { return type; }
    public int getNumber()       { return number; }
    public Color getColor()      { return color; }
    public String getColorName() { return colorName; }
    public double getX()         { return x; }
    public double getY()         { return y; }
    public double getSpeed()     { return speed; }
    public double getBaseSpeed() { return baseSpeed; }
    public boolean isFinished()  { return finished; }

    public double getDistance()  { return FINISH_X - startX; }

    /** Seconds this car needs to finish, at simulation speed 1.0x. */
    public double getRaceTime()  { return raceTime; }

    public void setSpeed(double speed) { this.speed = Math.max(0, speed); }

    /** Gives the car a random speed (70%-130% of its base speed) and works out its race time. */
    public void randomizeSpeed() {
        Random random = new Random();
        speed = baseSpeed * (0.7 + random.nextDouble() * 0.6);
        raceTime = getDistance() / speed;
    }

    /** Marks the car as having crossed the finish line. */
    public void markFinished() {
        x = FINISH_X;
        finished = true;
    }

    /** Puts the car back on the starting line. */
    public void reset() {
        x = startX;
        y = startY;
        speed = baseSpeed;
        raceTime = getDistance() / baseSpeed;
        finished = false;
    }
}
