package com.elrogbany.sptitan;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanGrid;
import com.elrogbany.spritan.ui.SpritanMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private VBox root;
    private SpritanGrid spritanGrid;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Spritan");
        
        MenuBar menuBar = SpritanMenu.GetMenuBar(stage, this);
        root = new VBox(menuBar);

        var scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    public SpritanGrid GetSpritanGrid(){
        return spritanGrid;
    }
    
    public void CreateGrid(int height, int width) {
        spritanGrid = new SpritanGrid(height, width);
        root.getChildren().removeIf(node -> node instanceof GridPane);
        root.getChildren().add(spritanGrid.GetGridPane());
    }
    
    public void CreateGrid(int height, int width, SpritanFile spritanFile) {
        spritanGrid = new SpritanGrid(height, width, spritanFile);
        root.getChildren().removeIf(node -> node instanceof GridPane);
        root.getChildren().add(spritanGrid.GetGridPane());
    }

    public static void main(String[] args) {
        launch();
    }
}