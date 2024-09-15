package com.elrogbany.sptitan;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanColorPaletteBar;
import com.elrogbany.spritan.ui.SpritanGrid;
import com.elrogbany.spritan.ui.SpritanGrid.Memento;
import com.elrogbany.spritan.ui.SpritanLayerToolbar;
import com.elrogbany.spritan.ui.SpritanMenu;
import com.elrogbany.spritan.ui.SpritanToolbar;
import java.util.Deque;
import java.util.LinkedList;
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
    public static App instance;
    
    private Deque<Memento> history;
    private Deque<Memento> redoHistory;
    
    private VBox root;
    private SpritanGrid spritanGrid;
    
    @Override
    public void start(Stage stage) {
        instance = this;
        
        history = new LinkedList<>();
        redoHistory = new LinkedList<>();
        
        stage.setTitle("Spritan");

        MenuBar menuBar = SpritanMenu.GetMenuBar(stage);
        ToolBar toolbar = SpritanToolbar.GetToolbar();

        VBox colorPaletteBar = SpritanColorPaletteBar.GetColorPaletteBar();

        VBox gridPaneContainer = new VBox();
        gridPaneContainer.setAlignment(javafx.geometry.Pos.CENTER);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(toolbar, gridPaneContainer, colorPaletteBar);

        HBox.setHgrow(gridPaneContainer, Priority.ALWAYS);
        
        ToolBar layerToolbar = SpritanLayerToolbar.GetLayerToolbar();

        root = new VBox(menuBar, hbox, layerToolbar);
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
    
    public void SaveState(){
        history.add(spritanGrid.TakeSnapshot());
        redoHistory.clear();
    }
    
    public void Undo(){
        if (!history.isEmpty()){
            Memento oldState = history.pop();
            redoHistory.push(spritanGrid.TakeSnapshot());
            spritanGrid.Restore(oldState);
        }
    }
    
    public void Redo() {
        if (!redoHistory.isEmpty()) {
            Memento redoState = redoHistory.pop();
            history.push(spritanGrid.TakeSnapshot());
            spritanGrid.Restore(redoState);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}