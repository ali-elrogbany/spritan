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
        int height = 0;
        int width = 0;
        if (layers.size() > 0){
            ArrayList<ArrayList<SpritanColor>> firstLayerPixels = layers.get(0).GetPixelData();
            height = firstLayerPixels.size();
            width = firstLayerPixels.get(0).size();
        }
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
            int height = selectedLayer.GetPixelData().size();
            int width = selectedLayer.GetPixelData().get(0).size();
            AddLayer(height, width);
        }
        
        layers.remove(selectedLayer);
        selectedLayerIndex = 0;
    }
}
