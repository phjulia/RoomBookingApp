package app;
/**
 *
 *  @author Likhytska Yuliia S19516
 *
 */


import java.io.IOException;

import java.net.URL;
import dao.BookingConnector;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.layout.VBox;



public class Main extends Application {
	private static Scene theScene;
	private static Stage stage;
	
    
    public static void main(String[] args) {
    	 
        launch(args);
    }

    public void start(Stage primaryStage){
    	 BookingConnector.connect();
    	stage=primaryStage;
        primaryStage.setTitle("RoomBooking.com");
        FXMLLoader loader = new FXMLLoader();
        try {
        loader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/SignIn.fxml"));
        VBox vbox = loader.<VBox>load();

        theScene = new Scene(vbox);
        setScene(theScene, primaryStage);
        primaryStage.setScene(theScene);
        primaryStage.show();
        

        }catch( IOException e) {
        	e.printStackTrace();
        	
        }

    }
    public static void setScene(Scene scene, Stage primaryStage) {
    	theScene=scene;
    	 stage.setScene(theScene);
    	 stage.show();
    }
    public static Stage getStage() {
    	return stage;
    }
    public static Scene getScene() {
    	return theScene;
    }

}