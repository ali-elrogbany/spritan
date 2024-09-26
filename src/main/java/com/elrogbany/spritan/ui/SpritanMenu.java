package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.utilities.SpritanFileOperations;
import com.elrogbany.sptitan.App;
import java.io.File;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SpritanMenu {
    public static MenuBar GetMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        fileMenu.getStyleClass().add("menu");
        
        MenuItem newFile = CreateStyledMenuItem("New");
        MenuItem openFile = CreateStyledMenuItem("Open");
        MenuItem saveFile = CreateStyledMenuItem("Save");
        MenuItem saveAsFile = CreateStyledMenuItem("Save As");
        MenuItem export = CreateStyledMenuItem("Export");
        
        newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN));
        openFile.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.SHORTCUT_DOWN));
        saveFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
        saveAsFile.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        export.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
        
        newFile.setOnAction(e -> SpritanFileOperations.OnNewFile(stage));
        openFile.setOnAction(e -> SpritanFileOperations.OnOpenFile());
        saveFile.setOnAction(e -> SpritanFileOperations.OnSaveFile());
        saveAsFile.setOnAction(e -> SpritanFileOperations.OnSaveAsFile());
        export.setOnAction(event -> {
            // Open a file chooser dialog to save the file
            FileChooser fileChooser = new FileChooser();

            // Set a title for the file chooser
            fileChooser.setTitle("Save Image As");

            // Add file extension filters for multiple formats
            FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
            FileChooser.ExtensionFilter jpgFilter = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
            FileChooser.ExtensionFilter bmpFilter = new FileChooser.ExtensionFilter("BMP files (*.bmp)", "*.bmp");

            // Add all filters to the file chooser
            fileChooser.getExtensionFilters().addAll(pngFilter, jpgFilter, bmpFilter);

            // Show the save dialog and get the selected file
            File file = fileChooser.showSaveDialog(new Stage());

            if (file != null) {
                // Get the selected file extension
                String fileName = file.getAbsolutePath();
                String format = "";

                // Automatically append the appropriate extension if not present
                if (fileChooser.getSelectedExtensionFilter() == pngFilter) {
                    format = "png";
                    if (!fileName.endsWith(".png")) {
                        fileName += ".png";
                    }
                } else if (fileChooser.getSelectedExtensionFilter() == jpgFilter) {
                    format = "jpg";
                    if (!fileName.endsWith(".jpg")) {
                        fileName += ".jpg";
                    }
                } else if (fileChooser.getSelectedExtensionFilter() == bmpFilter) {
                    format = "bmp";
                    if (!fileName.endsWith(".bmp")) {
                        fileName += ".bmp";
                    }
                }

                // Call the method to export to the selected format
                SpritanFileOperations.ExportToImage(fileName, format);
            }
        });
        
        fileMenu.getItems().addAll(newFile, openFile, saveFile, saveAsFile, export);
        
        Menu editMenu = new Menu("Edit");
        editMenu.getStyleClass().add("menu");
        
        MenuItem undoItem = CreateStyledMenuItem("Undo");
        MenuItem redoItem = CreateStyledMenuItem("Redo");
        
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.SHORTCUT_DOWN));
        
        undoItem.setOnAction(e -> App.instance.Undo());
        redoItem.setOnAction(e -> App.instance.Redo());
        
        editMenu.getItems().addAll(undoItem, redoItem);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);
        menuBar.getStyleClass().add("menu-bar");

        return menuBar;
    }

    private static MenuItem CreateStyledMenuItem(String text) {
        MenuItem menuItem = new MenuItem(text);

        menuItem.getStyleClass().add("menu-item");

        return menuItem;
    }
}
