package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.utilities.SpritanFileOperations;
import com.elrogbany.sptitan.App;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SpritanMenu {
    public static MenuBar GetMenuBar(Stage stage, App app) {
        Menu fileMenu = new Menu("File");
        fileMenu.getStyleClass().add("menu");
        
        MenuItem newFile = CreateStyledMenuItem("New");
        MenuItem openFile = CreateStyledMenuItem("Open");
        MenuItem saveFile = CreateStyledMenuItem("Save");
        MenuItem saveAsFile = CreateStyledMenuItem("Save As");
        
        newFile.setOnAction(e -> SpritanFileOperations.OnNewFile(stage, app));
        openFile.setOnAction(e -> SpritanFileOperations.OnOpenFile(app));
        saveFile.setOnAction(e -> SpritanFileOperations.OnSaveFile(app));
        saveAsFile.setOnAction(e -> SpritanFileOperations.OnSaveAsFile(app));
        
        fileMenu.getItems().addAll(newFile, openFile, saveFile, saveAsFile);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(fileMenu);
        menuBar.getStyleClass().add("menu-bar");

        return menuBar;
    }

    private static MenuItem CreateStyledMenuItem(String text) {
        MenuItem menuItem = new MenuItem(text);

        menuItem.getStyleClass().add("menu-item");

        return menuItem;
    }
}
