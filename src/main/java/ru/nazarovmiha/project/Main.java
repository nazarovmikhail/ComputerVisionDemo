package ru.nazarovmiha.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Мой проект");

        initRootLayout();

        showMyView();

        /*Parent root = FXMLLoader.load(getClass().getResource("view/view.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }

    /**
     * Initializes the root layout
     */
    public void initRootLayout(){
        try{
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane)loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Show the pic
     */
    public void showMyView(){
        try{
            // Load
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/view.fxml"));
            TabPane tabPane = (TabPane)loader.load();
            //Set myView into the center or root layout.
            rootLayout.setCenter(tabPane);

            //rootLayout.getChildren().addAll(pictureView,tabPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Return the main stage.
     * @return
     */
    public Stage getPrimaryStage(){
        return primaryStage;
    }


    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        launch(args);
    }
}
