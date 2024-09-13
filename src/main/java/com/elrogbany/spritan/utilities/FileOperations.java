package com.elrogbany.spritan.utilities;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.sptitan.App;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FileOperations {
    private static File currentFile;

    public static void OnNewFile(Stage parentStage, App app) {
        // Handle the dialog window for creating a new file
        Stage dialog = new Stage();
        dialog.setTitle("New File");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentStage);

        GridPane grid = new GridPane();
        Label heightLabel = new Label("Height:");
        TextField heightInput = new TextField();
        Label widthLabel = new Label("Width:");
        TextField widthInput = new TextField();
        Button okButton = new Button("OK");

        okButton.setOnAction(e -> {
            int height = Integer.parseInt(heightInput.getText());
            int width = Integer.parseInt(widthInput.getText());
            
            app.CreateGrid(height, width);
            
            dialog.close();
        });

        grid.add(heightLabel, 0, 0);
        grid.add(heightInput, 1, 0);
        grid.add(widthLabel, 0, 1);
        grid.add(widthInput, 1, 1);
        grid.add(okButton, 1, 2);

        Scene dialogScene = new Scene(grid, 300, 200);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    public static void OnOpenFile(App app) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SPRITAN Files", "*.spt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                SpritanFile spritanFile = (SpritanFile) in.readObject();
                currentFile = file;
                app.CreateGrid(spritanFile.GetPixelData().size(), spritanFile.GetPixelData().get(0).size(), spritanFile);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void OnSaveFile(App app) {
        if (currentFile == null) {
            OnSaveAsFile(app);
        } else {
            SaveToFile(currentFile, app);
        }
    }

    public static void OnSaveAsFile(App app) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SPRITAN Files", "*.spt"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            currentFile = file;
            SaveToFile(file, app);
        }
    }

    private static void SaveToFile(File file, App app) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(app.GetSpritanGrid().GetSpritanFile());
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
