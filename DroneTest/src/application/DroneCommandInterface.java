package application;

public interface DroneCommandInterface {

    public void takeoff();
    public void land();
    public void turnCW(int deg);
    public void turnCCW(int deg);
    public void flip(String dir);
    public void gotoXYZ(int x, int y, int z, int speed);
    public void gotoXY(int x, int y, int speed);
    public void activateSDK();
    public void flyForward(int forward);
    public void hoverInPlace(int seconds);
    public void end();

}

