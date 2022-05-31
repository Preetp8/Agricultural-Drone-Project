package application;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class DroneSim implements DroneCommandInterface{

    private ImageView drone_view;

    public SequentialTransition animation = new SequentialTransition();

    public void dronePass(ImageView drone){
        this.drone_view = drone;
    }

    @Override
    public void gotoXYZ(int x, int y, int z, int speed) {
        gotoXY(x, y, speed);
    }

    @Override
    public void gotoXY(int x, int y, int speed) {
        TranslateTransition fly = new TranslateTransition();
        System.out.println("Going to coordinate");
        fly.setDuration(Duration.seconds(speed));
        fly.setNode(drone_view);
        fly.setToX(x);
        fly.setToY(y);
        animation.getChildren().add(fly); // adds translation to Sequential Transition
    }

    @Override
    public void activateSDK() {
        System.out.println("SDK activated");
    }

    @Override
    public void flyForward(int forward) {
        System.out.println("Flying forward");
    }

    @Override
    public void hoverInPlace(int seconds) {
        System.out.println("Hovering");
    }

    @Override
    public void end() {
        System.out.println("ending...");
    }

    @Override
    public void takeoff() {
        System.out.println("Taking off");
    }

    @Override
    public void land() {
        System.out.println("Landing");
    }

    @Override
    public void turnCW(int deg) {
        System.out.println("Turning ClockWise: " + deg + " degrees.");
        RotateTransition turn = new RotateTransition(Duration.millis(1000), drone_view);
        turn.setByAngle(deg);
        animation.getChildren().add(turn); // adds rotation to Sequential Transition
    }

    @Override
    public void turnCCW(int deg) {
        System.out.println("Turning CounterClockWise: " + deg + " degrees.");
        RotateTransition turn = new RotateTransition(Duration.millis(1000), drone_view);
        turn.setByAngle(-deg);

        animation.getChildren().add(turn); // adds rotation to Sequential Transition
    }

    @Override
    public void flip(String dir) {
        System.out.println("Do a flip");
    }


}
