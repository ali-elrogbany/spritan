package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.utilities.SpritanColor;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpritanGrid {
    private GridPane pixelsGrid;
    private SpritanFile spritanFile;
    
    public SpritanGrid(int height, int width) {
        spritanFile = new SpritanFile();
        spritanFile.CreateEmptyGrid(height, width);
        CreateGrid(height, width);
    }

    public SpritanGrid(int height, int width, SpritanFile spritanFile) {
        this.spritanFile = spritanFile;
        CreateGrid(height, width);
        DrawSavedImage();
    }
    
    public SpritanFile GetSpritanFile(){
        return spritanFile;
    }
    
    private void CreateGrid(int height, int width) {
        pixelsGrid = new GridPane();

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Rectangle pixel = new Rectangle(20, 20); 
                pixel.setFill(Color.WHITE);  
                pixel.setStroke(Color.BLACK);  
                pixelsGrid.add(pixel, col, row);
            }
        }

        pixelsGrid.setOnMouseDragged(e -> HandleDrag(e.getX(), e.getY()));
        
        pixelsGrid.getStyleClass().add("grid-pane");
    }

    private void HandleDrag(double x, double y) {
        int row = (int) (y / (pixelsGrid.getHeight() / pixelsGrid.getRowCount()));
        int col = (int) (x / (pixelsGrid.getWidth() / pixelsGrid.getColumnCount()));
        Node node = GetNodeAt(row, col);

        if (node != null) {
            Rectangle pixel = (Rectangle) node;
            
            Color javafxColor = javafx.scene.paint.Color.rgb(spritanFile.GetSelectedColor().GetRed(), spritanFile.GetSelectedColor().GetGreen(), spritanFile.GetSelectedColor().GetBlue(), spritanFile.GetSelectedColor().GetAlpha() / 255.0);
            pixel.setFill(javafxColor);
            
            spritanFile.ChangePixelColor(row, col);
        }
    }

    private Node GetNodeAt(int row, int col) {
        for (Node child : pixelsGrid.getChildren()) {
            Integer childRow = GridPane.getRowIndex(child);
            Integer childCol = GridPane.getColumnIndex(child);
            if (childRow != null && childCol != null && childRow == row && childCol == col) {
                return child;
            }
        }
        return null;
    }
    
    private void DrawSavedImage(){
        ArrayList<ArrayList<SpritanColor>> pixelData = spritanFile.GetPixelData();
        
        for (int i = 0; i < pixelData.size(); i++) {
            for (int j = 0; j < pixelData.get(0).size(); j++) {
                SpritanColor color = pixelData.get(i).get(j);
                Color javafxColor = javafx.scene.paint.Color.rgb(color.GetRed(), color.GetGreen(), color.GetBlue(), color.GetAlpha() / 255.0);

                Node node = GetNodeAt(i, j);
                if (node instanceof Rectangle) {
                    Rectangle pixel = (Rectangle) node;
                    pixel.setFill(javafxColor);
                }
            }
        }
    }

    public GridPane GetGridPane() {
        return pixelsGrid;
    }
}
