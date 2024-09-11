package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.tools.EraserTool;
import com.elrogbany.spritan.tools.ITool;
import com.elrogbany.spritan.tools.PenTool;
import com.elrogbany.sptitan.App;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;

public class SpritanToolbar {
    private static Button activeButton;

    public static ToolBar GetToolbar(Stage stage, App app) {
        ToolBar toolbar = new ToolBar();
        toolbar.setOrientation(Orientation.VERTICAL);

        toolbar.getStyleClass().add("spritan-toolbar");

        Button penButton = CreateStyledButton("Pen");
        Button eraserButton = CreateStyledButton("Eraser");
        
        penButton.setOnAction(e -> {
            SetActiveButton(penButton);
            SetCurrentTool(app, new PenTool());
        });
        
        eraserButton.setOnAction(e -> {
            SetActiveButton(eraserButton);
            SetCurrentTool(app, new EraserTool());
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
    
    private static void SetCurrentTool(App app, ITool tool){
        app.GetSpritanGrid().SetCurrentTool(tool);
    }
}
