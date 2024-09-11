package com.elrogbany.sptitan;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.utilities.SpritanColor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private VBox root;
    private GridPane pixelsGrid;
    
    private SpritanFile spritanFile;
    private File currentFile;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("Spritan");
        
        MenuBar menuBar = GetMenuBar(stage);
        root = new VBox(menuBar);

        var scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
    
    private MenuBar GetMenuBar(Stage stage){
        Menu menu = new Menu("File");
        
        MenuItem newFile = new MenuItem("New");
        MenuItem openFile = new MenuItem("Open");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem saveAsFile = new MenuItem("Save As");
        
        newFile.setOnAction(e -> OnNewFile(stage));
        openFile.setOnAction(e -> OnOpenFile());
        saveFile.setOnAction(e -> OnSaveFile());
        saveAsFile.setOnAction(e -> OnSaveAsFile());
        
        menu.getItems().addAll(newFile, openFile, saveFile, saveAsFile);
        
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
        
        return menuBar;
    }
    
    private void OnNewFile(Stage parentStage) {
        // Create a new dialog window
        Stage dialog = new Stage();
        dialog.setTitle("New File");
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(parentStage);

        // Create a grid layout for the dialog
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        // Add height and width labels and text fields
        Label heightLabel = new Label("Height:");
        TextField heightInput = new TextField();
        Label widthLabel = new Label("Width:");
        TextField widthInput = new TextField();

        grid.add(heightLabel, 0, 0);
        grid.add(heightInput, 1, 0);
        grid.add(widthLabel, 0, 1);
        grid.add(widthInput, 1, 1);

        // Add an OK button
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            String height = heightInput.getText();
            String width = widthInput.getText();
            int intHeight = Integer.valueOf(height);
            int intWidth = Integer.valueOf(width);
            
            CreateGrid(intHeight, intWidth);
            
            spritanFile = new SpritanFile();
            spritanFile.CreateEmptyGrid(intHeight, intWidth);
            
            dialog.close();
        });

        grid.add(okButton, 1, 2);

        // Set the scene for the dialog
        Scene dialogScene = new Scene(grid, 300, 200);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
    
    private void OnOpenFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SPRITAN Files", "*.ser"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {
                spritanFile = (SpritanFile) in.readObject();
                currentFile = file; // Update the current file reference
                System.out.println("File opened successfully.");

                CreateGrid(spritanFile.GetPixelData().size(), spritanFile.GetPixelData().get(0).size());
                DrawSavedImage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void OnSaveFile() {
        if (currentFile == null) {
            OnSaveAsFile(); // If no file has been saved before, call Save As
        } else {
            SaveToFile(currentFile);
        }
    }
    
    private void OnSaveAsFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SPRITAN Files", "*.ser"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            currentFile = file; // Update the current file reference
            SaveToFile(file);
        }
    }
    
    private void CreateGrid(int height, int width) {
        pixelsGrid = new GridPane();
        pixelsGrid.setPadding(new Insets(1));
        pixelsGrid.setHgap(0);
        pixelsGrid.setVgap(0);
        
        pixelsGrid.setOnMouseDragged(e -> {
            // Get the coordinates of the mouse event relative to the GridPane
            double x = e.getX();
            double y = e.getY();

            // Determine the number of rows and columns in the GridPane
            int numRows = pixelsGrid.getRowCount();
            int numCols = pixelsGrid.getColumnCount();

            // Determine the size of each cell (assuming uniform cell size)
            double cellWidth = pixelsGrid.getWidth() / numCols;
            double cellHeight = pixelsGrid.getHeight() / numRows;

            // Calculate the row and column indices based on mouse coordinates
            int row = (int) (y / cellHeight);
            int col = (int) (x / cellWidth);

            // Ensure row and column indices are within bounds
            row = Math.max(0, Math.min(row, numRows - 1));
            col = Math.max(0, Math.min(col, numCols - 1));

            // Optionally, get the node at the specific grid location
            Node node = getNodeAt(row, col);

            // Handle the node (e.g., change color, update state, etc.)
            if (node != null) {
                // Do something with the node
                Rectangle pixel = (Rectangle) node;
                HandlePixelClick(pixel);
                spritanFile.ChangePixelColor(row, col);
            }
        });
        
        // Create a grid of rectangles representing the pixels
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Rectangle pixel = new Rectangle(20, 20);  // Set rectangle size to represent a pixel
                pixel.setFill(Color.WHITE);  // Set initial color to white
                pixel.setStroke(Color.BLACK);  // Optional: Add border to the rectangles for visibility

                pixelsGrid.add(pixel, col, row);
            }
        }

        // Clear previous content and add the new grid
        root.getChildren().removeIf(node -> node instanceof Pane);
        root.getChildren().add(pixelsGrid);
    }
    
    private Node getNodeAt(int row, int col) {
        for (Node child : pixelsGrid.getChildren()) {
            Integer childRow = GridPane.getRowIndex(child);
            Integer childCol = GridPane.getColumnIndex(child);
            if (childRow != null && childCol != null && childRow == row && childCol == col) {
                return child;
            }
        }
        return null;
    }

    private void HandlePixelClick(Rectangle pixel){
        SpritanColor color = spritanFile.GetSelectedColor();
        Color javafxColor = javafx.scene.paint.Color.rgb(color.GetRed(), color.GetGreen(), color.GetBlue(), color.GetAlpha() / 255.0);
        pixel.setFill(javafxColor);
    }
    
    private void DrawSavedImage(){
        ArrayList<ArrayList<SpritanColor>> pixelData = spritanFile.GetPixelData();
        
        for (int i = 0; i < pixelData.size(); i++) {
            for (int j = 0; j < pixelData.get(0).size(); j++) {
                SpritanColor color = pixelData.get(i).get(j);
                Color javafxColor = javafx.scene.paint.Color.rgb(color.GetRed(), color.GetGreen(), color.GetBlue(), color.GetAlpha() / 255.0);
                System.out.println(color);

                // Find the corresponding Rectangle in the pixelsGrid
                Node node = getNodeAt(i, j);
                if (node instanceof Rectangle) {
                    Rectangle pixel = (Rectangle) node;
                    pixel.setFill(javafxColor);
                }
            }
        }
    }
    
    private void SaveToFile(File file) {
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(spritanFile);
            System.out.println("File saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}