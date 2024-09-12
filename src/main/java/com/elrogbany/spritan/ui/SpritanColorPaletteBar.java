package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.core.SpritanColorPalette;
import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.utilities.SpritanColor;
import com.elrogbany.sptitan.App;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class SpritanColorPaletteBar {
    private static final int COLOR_SIZE = 30;
    
    private static App app;
    private static GridPane colorsGrid;

    // Method to create the ColorPaletteBar UI
    public static VBox GetColorPaletteBar(Stage stage, App app) {
        SpritanColorPaletteBar.app = app;
        
        colorsGrid = new GridPane();
        colorsGrid.setHgap(5);
        colorsGrid.setVgap(5);
        colorsGrid.setPadding(new Insets(10));
        colorsGrid.setPrefWidth(COLOR_SIZE * 2 + 10);
        
        ScrollPane scrollPane = new ScrollPane(colorsGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        scrollPane.setMaxWidth(COLOR_SIZE * 2 + 10);

        SpritanGrid spritanGrid = app.GetSpritanGrid();

        if (spritanGrid != null) {
            SpritanColorPalette palette = spritanGrid.GetSpritanFile().GetColorPalette();
            UpdateColorPalette(palette);
        }

        Button createPaletteButton = new Button("New");
        createPaletteButton.setOnAction(e -> {
            SpritanColorPalette newPalette = new SpritanColorPalette();
            UpdateColorPalette(newPalette);
            System.out.println("New Color Palette!");
        });

        Button savePaletteButton = new Button("Save");
        savePaletteButton.setOnAction(e -> {
            System.out.println("Save Color Palette!");
        });

        Button loadPaletteButton = new Button("Load");
        loadPaletteButton.setOnAction(e -> {
            System.out.println("Load Color Palette!");
        });

        VBox buttonToolbar = new VBox(10, createPaletteButton, savePaletteButton, loadPaletteButton);
        buttonToolbar.setPadding(new Insets(10));

        VBox vbox = new VBox(10, buttonToolbar, scrollPane);
        vbox.setPadding(new Insets(10));
        VBox.setVgrow(colorsGrid, Priority.ALWAYS);

        return vbox;
    }

    public static void UpdateColorPalette(SpritanColorPalette palette) {
        colorsGrid.getChildren().clear();
        colorsGrid.getColumnConstraints().clear();
        colorsGrid.getRowConstraints().clear();
        
        for (int i = 0; i < 2; i++) {
            colorsGrid.getColumnConstraints().add(new ColumnConstraints(COLOR_SIZE));
        }

        for (int i = 0; i < palette.GetColors().size(); i++) {
            SpritanColor spritanColor = palette.GetColors().get(i);
            Color javafxColor = javafx.scene.paint.Color.rgb(spritanColor.GetRed(), spritanColor.GetGreen(), spritanColor.GetBlue(), spritanColor.GetAlpha() / 255.0);
            Rectangle colorRect = new Rectangle(COLOR_SIZE, COLOR_SIZE, javafxColor);
            colorRect.setOnMouseClicked(event -> HandleColorClick(spritanColor));

            colorsGrid.add(colorRect, i % 2, i / 2);
        }

        AddColorPickerButton(palette);
    }

    private static void AddColorPickerButton(SpritanColorPalette palette) {
        Rectangle colorPickerRect = new Rectangle(COLOR_SIZE, COLOR_SIZE, Color.LIGHTGRAY);
        colorPickerRect.setStroke(Color.DARKGRAY);
        colorPickerRect.setOnMouseClicked(event -> {
            Stage colorPickerDialog = new Stage();
            colorPickerDialog.setTitle("Pick a Color");

            ColorPicker colorPicker = new ColorPicker();

            Button confirmButton = new Button("OK");
            confirmButton.setOnAction(e -> {
                Color selectedColor = colorPicker.getValue();
                OnAddNewColor(selectedColor, palette);
                colorPickerDialog.close();
            });

            VBox dialogLayout = new VBox(10, colorPicker, confirmButton);
            dialogLayout.setPadding(new Insets(20));

            colorPickerDialog.setScene(new javafx.scene.Scene(dialogLayout));
            colorPickerDialog.showAndWait();
        });

        int nextPosition = palette.GetColors().size();
        colorsGrid.add(colorPickerRect, nextPosition % 2, nextPosition / 2);
    }
    
    private static void OnAddNewColor(Color selectedColor, SpritanColorPalette palette){
        int red = (int) (selectedColor.getRed() * 255);
        int green = (int) (selectedColor.getGreen() * 255);
        int blue = (int) (selectedColor.getBlue() * 255);
        int alpha = (int) (selectedColor.getOpacity() * 255);
        
        SpritanColor spritanColor = new SpritanColor(red, green, blue, alpha);
        palette.AddColor(spritanColor);
        UpdateColorPalette(palette);
    }

    private static void HandleColorClick(SpritanColor color) {
        SpritanGrid grid = app.GetSpritanGrid();
        
        if (grid != null){
            SpritanFile spritanFile = grid.GetSpritanFile();
            
            if (spritanFile != null){
                spritanFile.SetSelectedColor(color);
            }
        }
    }
}
