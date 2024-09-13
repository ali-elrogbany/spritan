package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.core.SpritanLayer;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

public class SpritanLayerToolbar {
    
    private static ToolBar layerToolbar;

    // Method to return the toolbar with basic layer actions
    public static ToolBar GetLayerToolbar() {
        layerToolbar = new ToolBar();

        Button addLayerButton = new Button("Add Layer");
        addLayerButton.setTooltip(new Tooltip("Add a new layer"));
        addLayerButton.setOnAction(event -> {
            // Logic to add a new layer
            System.out.println("New layer added.");
        });

        Button removeLayerButton = new Button("Remove Layer");
        removeLayerButton.setTooltip(new Tooltip("Remove the selected layer"));
        removeLayerButton.setOnAction(event -> {
            // Logic to remove a layer
            System.out.println("Layer removed.");
        });

        layerToolbar.getItems().addAll(addLayerButton, removeLayerButton);

        return layerToolbar;
    }

    // Method to update the existing toolbar to display layers dynamically
    public static void DisplayLayers(ArrayList<SpritanLayer> layers) {
        // Remove all items from the toolbar except for the first two buttons (Add Layer, Remove Layer)
        layerToolbar.getItems().removeIf(node -> layerToolbar.getItems().indexOf(node) > 1);

        for (int i = 0; i < layers.size(); i++) {
            SpritanLayer layer = layers.get(i);
            Button layerButton = new Button(layer.GetName() != null ? layer.GetName() : "Layer " + (i + 1));
            layerButton.setTooltip(new Tooltip("Click to select or edit " + layer.GetName()));

            // Set onAction for when the layer is clicked
            int layerIndex = i;
            layerButton.setOnAction(event -> {
                System.out.println("Layer " + (layerIndex + 1) + " clicked.");
                // Add your logic for selecting or editing the layer here
            });

            // Add the layer button to the toolbar
            layerToolbar.getItems().add(layerButton);
        }
    }
}
