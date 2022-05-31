package application;

// import src.main.address.Dashboard;

public class appLauncher {
	
    private static appLauncher instance;
    static Dashboard app;


    public appLauncher(){
        this.app = new Dashboard();
    }
    

    public static appLauncher getInstance() {
        if (instance == null) {
            instance = new appLauncher();
        }
        return instance;
    }

    
    public static void main(String[] args) {
        appLauncher runApplication = new appLauncher();

        if (runApplication.getInstance() != null) {
            app.main(args);
        }
        else
            System.out.println("Instance already running");
    }
}
