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
    
    private int height;
    private int width;
    
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
    
    public int GetSelectedLayerIndex(){
        return selectedLayerIndex;
    }
    
    public void SetSelectedLayerIndex(int selectedLayerIndex){
        this.selectedLayerIndex = selectedLayerIndex;
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
        this.height = height;
        this.width = width;
        
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
    
    public void AddLayer(){
        AddLayer(height, width);
    }
    
    public void AddLayer(int height, int width){
        SpritanLayer newLayer = new SpritanLayer(height, width);
        layers.add(newLayer);
    }
    
    public void RemoveSelectedLayer(){
        if (layers.size() == 0){
            return;
        }
        
        SpritanLayer selectedLayer = layers.get(selectedLayerIndex);
        
        if (layers.size() == 1){
            AddLayer(height, width);
        }
        
        layers.remove(selectedLayer);
        selectedLayerIndex = 0;
    }
    
    public ArrayList<ArrayList<SpritanColor>> GetBlendedImage(){
        ArrayList<ArrayList<SpritanColor>> pixelData = new ArrayList<>();
        
        for (int i = 0; i < height; i++){
            pixelData.add(new ArrayList<>());
            
            for (int j = 0; j < width; j++){
                SpritanColor pixelColor = SpritanColor.TRANSPARENT;
                
                for (int k = (layers.size() - 1); k >= 0; k--){
                    pixelColor = SpritanColor.BlendTwoColors(pixelColor, layers.get(k).GetPixelData().get(i).get(j));
                }
                
                pixelData.get(i).add(pixelColor);
            }
        }
        return pixelData;
    }
}
