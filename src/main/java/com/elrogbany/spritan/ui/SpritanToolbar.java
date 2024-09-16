package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.tools.EraserTool;
import com.elrogbany.spritan.tools.ITool;
import com.elrogbany.spritan.tools.PenTool;
import com.elrogbany.sptitan.App;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

public class SpritanToolbar {
    private static Button activeButton;

    public static ToolBar GetToolbar(Scene scene) {
        ToolBar toolbar = new ToolBar();
        toolbar.setOrientation(Orientation.VERTICAL);

        toolbar.getStyleClass().add("spritan-toolbar");

        Button penButton = CreateStyledButton("Pen");
        Button eraserButton = CreateStyledButton("Eraser");
        
        KeyCodeCombination penShortcut = new KeyCodeCombination(KeyCode.P);
        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (penShortcut.match(event)) {
                SetCurrentTool(new PenTool(), penButton);
                event.consume();
            }
        });
        
        KeyCodeCombination eraserShortcut = new KeyCodeCombination(KeyCode.E);
        scene.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (eraserShortcut.match(event)) {
                SetCurrentTool(new EraserTool(), eraserButton);
                event.consume();
            }
        });
        
        penButton.setOnAction(e -> {
            SetCurrentTool(new PenTool(), penButton);
        });
        
        eraserButton.setOnAction(e -> {
            SetCurrentTool(new EraserTool(), eraserButton);
        });

        toolbar.getItems().addAll(penButton, eraserButton);

        return toolbar;
    }

    private static Button CreateStyledButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(100);

        button.getStyleClass().add("spritan-toolbar-button");

        button.setOnMouseEntered(e -> button.getStyleClass().add("hovered"));
        button.setOnMouseExited(e -> button.getStyleClass().remove("hovered"));

        return button;
    }

    private static void SetActiveButton(Button button) {
        if (activeButton != null) {
            activeButton.getStyleClass().remove("active");
        }
        activeButton = button;
        activeButton.getStyleClass().add("active");
    }
    
    private static void SetCurrentTool(ITool tool, Button button){
        SpritanGrid grid = App.instance.GetSpritanGrid();
        
        if (grid != null){
            grid.SetCurrentTool(tool);
            SetActiveButton(button);
        }
    }
}
