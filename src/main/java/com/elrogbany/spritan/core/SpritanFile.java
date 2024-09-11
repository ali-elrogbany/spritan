package com.elrogbany.spritan.core;

import com.elrogbany.spritan.utilities.SpritanColor;

import java.io.Serializable;
import java.util.ArrayList;

public class SpritanFile implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<ArrayList<SpritanColor>> pixelData;
    private SpritanColor selectedColor;
    
    public SpritanFile(){
        pixelData = new ArrayList<>();
        selectedColor = SpritanColor.BLACK;
    }
    
    public ArrayList<ArrayList<SpritanColor>> GetPixelData() {
        return pixelData;
    }

    public void SetPixelData(ArrayList<ArrayList<SpritanColor>> pixelData) {
        this.pixelData = pixelData;
    }
    
    public SpritanColor GetSelectedColor() {
        return selectedColor;
    }

    public void SetSelectedColor(SpritanColor selectedColor) {
        this.selectedColor = selectedColor;
    }
    
    public void CreateEmptyGrid(int height, int width){
        SpritanColor defaultColor = SpritanColor.WHITE;
        pixelData = new ArrayList<>();
        
        for (int i = 0; i < height; i++){
            pixelData.add(new ArrayList<>());
            for (int j = 0; j < width; j++){
                pixelData.get(i).add(defaultColor);
            }
        }
    }
    
    public void ChangePixelColor(int row, int column){
        pixelData.get(row).set(column, selectedColor);
    }
}
