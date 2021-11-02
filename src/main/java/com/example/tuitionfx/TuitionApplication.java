package com.example.tuitionfx;

/**
 * This class is the driver class that performs the execution of the application with JavaFX.
 * @author Sharia Hussain, David Lam
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class TuitionApplication extends Application {
    /**
     * Method that runs the JavaFX loader commands and shows the applications.
     * @param stage object to show the application
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TuitionApplication.class.getResource("tuition-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Tuition Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that launches the JavaFX applications
     * @param args input arguments from command line
     */
    public static void main(String[] args) {
        launch();
    }
}