package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.core.SpritanLayer;
import com.elrogbany.sptitan.App;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class SpritanLayerToolbar {

    private static VBox layerContainer;

    public static ToolBar GetLayerToolbar() {
        ToolBar toolbar = new ToolBar();
        toolbar.getStyleClass().add("spritan-layer-toolbar");
        
        layerContainer = new VBox();
        layerContainer.getStyleClass().add("scroll-pane");
        layerContainer.setSpacing(10);
        layerContainer.setStyle("-fx-padding: 10px;");

        ScrollPane scrollPane = new ScrollPane(layerContainer);
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setPrefViewportHeight(100);
        
        Button addLayerButton = new Button("Add Layer");
        addLayerButton.getStyleClass().add("spritan-layer-toolbar-button");
        addLayerButton.setTooltip(new Tooltip("Add a new layer"));
        addLayerButton.setOnAction(event -> {
            SpritanGrid grid = App.instance.GetSpritanGrid();

            if (grid != null) {
                App.instance.SaveState();

                SpritanFile spritanFile = grid.GetSpritanFile();
                spritanFile.AddLayer();
                DisplayLayers(spritanFile);
                grid.DrawSavedImage();
            }
        });

        Button removeLayerButton = new Button("Remove Layer");
        removeLayerButton.getStyleClass().add("spritan-layer-toolbar-button");
        removeLayerButton.setTooltip(new Tooltip("Remove the selected layer"));
        removeLayerButton.setOnAction(event -> {
            SpritanGrid grid = App.instance.GetSpritanGrid();

            if (grid != null) {
                App.instance.SaveState();

                SpritanFile spritanFile = grid.GetSpritanFile();
                spritanFile.RemoveSelectedLayer();
                DisplayLayers(spritanFile);
                grid.DrawSavedImage();
            }
        });

        toolbar.getItems().addAll(addLayerButton, removeLayerButton, scrollPane);

        return toolbar;
    }

    public static void DisplayLayers(SpritanFile spritanFile) {
        layerContainer.getChildren().clear();
        ArrayList<SpritanLayer> layers = spritanFile.GetLayers();
        int selectedLayerIndex = spritanFile.GetSelectedLayerIndex();

        for (int i = 0; i < layers.size(); i++) {
            SpritanLayer layer = layers.get(i);
            Button layerButton = new Button(layer.GetName() != null ? layer.GetName() : "Layer " + (i + 1));
            layerButton.getStyleClass().add("spritan-layer-button");
            layerButton.setTooltip(new Tooltip("Click to select or edit " + (layer.GetName() != null ? layer.GetName() : "Layer " + (i + 1))));

            if (i == selectedLayerIndex) {
                layerButton.getStyleClass().add("active");
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

