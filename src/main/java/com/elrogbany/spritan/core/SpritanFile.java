package com.elrogbany.spritan.core;

import com.elrogbany.spritan.utilities.SpritanColor;

import java.io.Serializable;
import java.util.ArrayList;

public class SpritanFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<SpritanLayer> layers;
    private int selectedLayerIndex;
    
    private SpritanColor selectedColor;
    private SpritanColorPalette colorPalette;
    
    public SpritanFile(){
        layers = new ArrayList<SpritanLayer>();
        layers.add(new SpritanLayer());
        selectedLayerIndex = 0;
        
        selectedColor = SpritanColor.BLACK;
        colorPalette = new SpritanColorPalette();
    }
    
    public ArrayList<SpritanLayer> GetLayers(){
        return layers;
    }
    
    public SpritanLayer GetSelectedLayer(){
        return layers.get(selectedLayerIndex);
    }
    
    public SpritanColor GetSelectedColor() {
        return selectedColor;
    }

    public void SetSelectedColor(SpritanColor selectedColor) {
        this.selectedColor = selectedColor;
    }
    
    public SpritanColorPalette GetColorPalette(){
        return colorPalette;
    }
    
    public void SetColorPalette(SpritanColorPalette colorPalette){
        this.colorPalette = colorPalette;
    }
    
    public void CreateEmptyGrid(int height, int width){
        for (int i = 0; i < layers.size(); i++){
            layers.get(i).CreateEmptyGrid(height, width);
        }
    }
    
    public void ChangePixelColor(int row, int column){
        GetSelectedLayer().ChangePixelColor(row, column, selectedColor);
    }
    
    public void ErasePixelColor(int row, int column){
        GetSelectedLayer().ErasePixelColor(row, column);
    }
}
