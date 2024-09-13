package com.elrogbany.sptitan;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanColorPaletteBar;
import com.elrogbany.spritan.ui.SpritanGrid;
import com.elrogbany.spritan.ui.SpritanMenu;
import com.elrogbany.spritan.ui.SpritanToolbar;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
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
        ToolBar toolbar = SpritanToolbar.GetToolbar(stage, this);

        VBox colorPaletteBar = SpritanColorPaletteBar.GetColorPaletteBar(stage, this);

        VBox gridPaneContainer = new VBox();
        gridPaneContainer.setAlignment(javafx.geometry.Pos.CENTER);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(toolbar, gridPaneContainer, colorPaletteBar);

        HBox.setHgrow(gridPaneContainer, Priority.ALWAYS);

        root = new VBox(menuBar, hbox);
        VBox.setVgrow(hbox, Priority.ALWAYS);

        var scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
    
    public SpritanGrid GetSpritanGrid(){
        return spritanGrid;
    }
    
    public void CreateGrid(int height, int width) {
        spritanGrid = new SpritanGrid(height, width);
        UpdateGridPane(spritanGrid.GetGridPane());
    }
    
    public void CreateGrid(int height, int width, SpritanFile spritanFile) {
        spritanGrid = new SpritanGrid(height, width, spritanFile);
        UpdateGridPane(spritanGrid.GetGridPane());
    }
    
    private void UpdateGridPane(GridPane gridPane) {
        VBox gridPaneContainer = (VBox) ((HBox) root.getChildren().get(1)).getChildren().get(1);
        gridPaneContainer.getChildren().clear();
        gridPaneContainer.getChildren().add(gridPane);
    }

    public static void main(String[] args) {
        launch();
    }
}