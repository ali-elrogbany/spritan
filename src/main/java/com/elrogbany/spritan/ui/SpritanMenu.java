package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.utilities.SpritanFileOperations;
import com.elrogbany.sptitan.App;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SpritanMenu {
    public static MenuBar GetMenuBar(Stage stage) {
        Menu fileMenu = new Menu("File");
        fileMenu.getStyleClass().add("menu");
        
        MenuItem newFile = CreateStyledMenuItem("New");
        MenuItem openFile = CreateStyledMenuItem("Open");
        MenuItem saveFile = CreateStyledMenuItem("Save");
        MenuItem saveAsFile = CreateStyledMenuItem("Save As");
        
        newFile.setOnAction(e -> SpritanFileOperations.OnNewFile(stage));
        openFile.setOnAction(e -> SpritanFileOperations.OnOpenFile());
        saveFile.setOnAction(e -> SpritanFileOperations.OnSaveFile());
        saveAsFile.setOnAction(e -> SpritanFileOperations.OnSaveAsFile());
        
        fileMenu.getItems().addAll(newFile, openFile, saveFile, saveAsFile);
        
        Menu editMenu = new Menu("Edit");
        editMenu.getStyleClass().add("menu");
        
        MenuItem undoItem = CreateStyledMenuItem("Undo");
        MenuItem redoItem = CreateStyledMenuItem("Redo");
        
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
