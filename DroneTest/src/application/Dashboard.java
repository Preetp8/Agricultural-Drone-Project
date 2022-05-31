package application;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;



public class Dashboard extends Application {

    private static Stage stage;

    public static Stage getPrimaryStage() {
        return stage;
    }


    @Override
    public void start(Stage primaryStage){
    	try {
    	Parent root = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
        Scene scene = new Scene(root);
        stage = primaryStage;
        stage.setTitle("Farm Dashboard");
        stage.setScene(scene);
        stage.show();
        
    	} catch(Exception ex) {
    		
    		ex.printStackTrace();
    	}
    }
    
    
	public static void main(String[] args) {
		launch(args);
	}


}




    

