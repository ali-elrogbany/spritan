package com.elrogbany.sptitan;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanColorPaletteBar;
import com.elrogbany.spritan.ui.SpritanGrid;
import com.elrogbany.spritan.ui.SpritanGrid.Memento;
import com.elrogbany.spritan.ui.SpritanLayerToolbar;
import com.elrogbany.spritan.ui.SpritanMenu;
import com.elrogbany.spritan.ui.SpritanToolbar;
import java.util.LinkedList;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
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
    
    private Stack<Memento> history;
    private Stack<Memento> redoHistory;
    
    private VBox root;
    private SpritanGrid spritanGrid;
    
    @Override
    public void start(Stage stage) {
        instance = this;
        
        history = new Stack<>();
        redoHistory = new Stack<>();
        
        stage.setTitle("Spritan");
        
        root = new VBox();
        var scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        MenuBar menuBar = SpritanMenu.GetMenuBar(stage);
        ToolBar toolbar = SpritanToolbar.GetToolbar(scene);

        VBox colorPaletteBar = SpritanColorPaletteBar.GetColorPaletteBar();

        VBox gridPaneContainer = new VBox();
        gridPaneContainer.setAlignment(javafx.geometry.Pos.CENTER);
        VBox.setVgrow(gridPaneContainer, Priority.ALWAYS);

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(toolbar, gridPaneContainer, colorPaletteBar);
        HBox.setHgrow(gridPaneContainer, Priority.ALWAYS);

        ToolBar layerToolbar = SpritanLayerToolbar.GetLayerToolbar();

        root.getChildren().addAll(menuBar, hbox, layerToolbar);
        VBox.setVgrow(hbox, Priority.ALWAYS);
        
        scene.setRoot(root);

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

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);

        // Set the ScrollPane to expand to fill the available space
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Create a container to center the GridPane within the ScrollPane
        VBox centeredContainer = new VBox();
        centeredContainer.setAlignment(javafx.geometry.Pos.CENTER);
        centeredContainer.getChildren().add(gridPane);

        scrollPane.setContent(centeredContainer);

        gridPaneContainer.getChildren().add(scrollPane);
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