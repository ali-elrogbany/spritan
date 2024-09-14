package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.core.SpritanLayer;
import com.elrogbany.sptitan.App;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import javafx.scene.layout.VBox;

public class SpritanLayerToolbar {
    
    private static VBox layerContainer;
    
    private static App app;

    public static ToolBar GetLayerToolbar(App app) {
        SpritanLayerToolbar.app = app;

        ToolBar toolbar = new ToolBar();
        layerContainer = new VBox();

        Button addLayerButton = new Button("Add Layer");
        addLayerButton.setTooltip(new Tooltip("Add a new layer"));
        addLayerButton.setOnAction(event -> {
            SpritanGrid grid = app.GetSpritanGrid();
            
            if (grid != null) {
                SpritanFile spritanFile = grid.GetSpritanFile();
                spritanFile.AddLayer();
                DisplayLayers(spritanFile);
                grid.DrawSavedImage();
            }
        });

        Button removeLayerButton = new Button("Remove Layer");
        removeLayerButton.setTooltip(new Tooltip("Remove the selected layer"));
        removeLayerButton.setOnAction(event -> {
            SpritanGrid grid = app.GetSpritanGrid();
            
            if (grid != null) {
                SpritanFile spritanFile = grid.GetSpritanFile();
                spritanFile.RemoveSelectedLayer();
                DisplayLayers(spritanFile);
                grid.DrawSavedImage();
            }
        });

        toolbar.getItems().addAll(addLayerButton, removeLayerButton);
        toolbar.getItems().add(layerContainer);
        
        return toolbar;
    }

    public static void DisplayLayers(SpritanFile spritanFile) {
        layerContainer.getChildren().clear();
        ArrayList<SpritanLayer> layers = spritanFile.GetLayers();
        int selectedLayerIndex = spritanFile.GetSelectedLayerIndex();

        for (int i = 0; i < layers.size(); i++) {
            SpritanLayer layer = layers.get(i);
            Button layerButton = new Button(layer.GetName() != null ? layer.GetName() : "Layer " + (i + 1));
            layerButton.setTooltip(new Tooltip("Click to select or edit " + layer.GetName()));

            if (i == selectedLayerIndex) {
                layerButton.setStyle("-fx-background-color: lightblue; -fx-border-color: black; -fx-border-width: 2px;");
            } else {
                layerButton.setStyle("-fx-background-color: white;");
            }

            int layerIndex = i;
            layerButton.setOnAction(event -> {
                spritanFile.SetSelectedLayerIndex(layerIndex);
                DisplayLayers(spritanFile);
            });

            layerContainer.getChildren().add(layerButton);
        }
    }
}
