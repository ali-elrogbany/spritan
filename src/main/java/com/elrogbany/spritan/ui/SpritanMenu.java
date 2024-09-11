package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.utilities.FileOperations;
import com.elrogbany.sptitan.App;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class SpritanMenu {
    public static MenuBar GetMenuBar(Stage stage, App app) {
        Menu menu = new Menu("File");
        
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem saveAsFile = new MenuItem("Save As");
        
        newFile.setOnAction(e -> FileOperations.OnNewFile(stage, app));
        openFile.setOnAction(e -> FileOperations.OnOpenFile(app));
        saveFile.setOnAction(e -> FileOperations.OnSaveFile(app));
        saveAsFile.setOnAction(e -> FileOperations.OnSaveAsFile(app));
        
        menu.getItems().addAll(newFile, openFile, saveFile, saveAsFile);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        
        return menuBar;
    }
}
