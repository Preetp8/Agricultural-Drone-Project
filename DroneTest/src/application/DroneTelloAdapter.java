package application;

import main.java.surelyhuman.jdrone.control.physical.tello.*;

import java.io.IOException;

public class DroneTelloAdapter implements DroneCommandInterface{

    TelloDrone drone;

    DroneTelloAdapter(TelloDrone drone){
        this.drone = drone;
    }

    @Override
    public void takeoff() {
        try {
            drone.takeoff();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void land() {
        try {
            drone.land();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnCW(int deg) {
        try {
            drone.turnCW(deg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void turnCCW(int deg) {
        try {
            drone.turnCCW(deg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flip(String dir) {
        try {
            drone.flip(dir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotoXYZ(int x, int y, int z, int speed) {
        try {
            drone.gotoXYZ(x, y, z, speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void gotoXY(int x, int y, int speed) {
        try {
            drone.gotoXY(x, y, speed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void end() {
        try{
            drone.end();

        }catch(IOException | InterruptedException e){

        }
    }

    @Override
    public void activateSDK() {
        //TODO
    }

    @Override
    public void flyForward(int forward) {
        //TODO
    }

    @Override
    public void hoverInPlace(int seconds) {
        //TODO
    }


}