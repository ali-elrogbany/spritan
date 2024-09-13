package com.elrogbany.spritan.ui;

import com.elrogbany.spritan.core.SpritanColorPalette;
import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.core.SpritanLayer;
import com.elrogbany.spritan.tools.ITool;
import com.elrogbany.spritan.utilities.SpritanColor;
import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SpritanGrid {
    private GridPane pixelsGrid;
    private SpritanFile spritanFile;
    
    private ITool currentTool;
    
    public SpritanGrid(int height, int width) {
        spritanFile = new SpritanFile();
        spritanFile.CreateEmptyGrid(height, width);
        CreateGrid(height, width);
        
        ChangeColorPalette(spritanFile.GetColorPalette());
        SpritanLayerToolbar.DisplayLayers(spritanFile.GetLayers());
    }

    public SpritanGrid(int height, int width, SpritanFile spritanFile) {
        this.spritanFile = spritanFile;
        CreateGrid(height, width);
        DrawSavedImage();
        
        ChangeColorPalette(this.spritanFile.GetColorPalette(), spritanFile.GetSelectedColor());
        SpritanLayerToolbar.DisplayLayers(spritanFile.GetLayers());
    }
    
    public GridPane GetGridPane() {
        return pixelsGrid;
    }
    
    public SpritanFile GetSpritanFile(){
        return spritanFile;
    }
    
    public ITool GetCurrentTool(){
        return currentTool;
    }
    
    public void SetCurrentTool(ITool tool){
        currentTool = tool;
    }
    
    public void ChangeColorPalette(SpritanColorPalette colorPalette){
        spritanFile.SetColorPalette(colorPalette);
        SpritanColorPaletteBar.UpdateColorPalette(spritanFile.GetColorPalette());
        
        spritanFile.SetSelectedColor(SpritanColor.BLACK);
        SpritanColorPaletteBar.SetSelectedColorDisplay(SpritanColor.BLACK);
    }
    
    public void ChangeColorPalette(SpritanColorPalette colorPalette, SpritanColor selectedColor){
        spritanFile.SetColorPalette(colorPalette);
        SpritanColorPaletteBar.UpdateColorPalette(spritanFile.GetColorPalette());
        
        spritanFile.SetSelectedColor(selectedColor);
        SpritanColorPaletteBar.SetSelectedColorDisplay(selectedColor);
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

        if (node != null && currentTool != null) {
            Rectangle pixel = (Rectangle) node;
            
            currentTool.OnPixelClick(this, pixel, row, col);
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
        ArrayList<SpritanLayer> layers = spritanFile.GetLayers();
        
        for (int k = (layers.size() - 1); k >= 0; k--){
            ArrayList<ArrayList<SpritanColor>> pixelData = layers.get(k).GetPixelData();
        
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
    }
}
