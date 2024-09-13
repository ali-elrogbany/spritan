package com.elrogbany.spritan.core;

import com.elrogbany.spritan.utilities.SpritanColor;
import java.io.Serializable;
import java.util.ArrayList;

public class SpritanLayer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private ArrayList<ArrayList<SpritanColor>> pixelData;
    
    public SpritanLayer(){
        pixelData = new ArrayList();
    }
    
    public SpritanLayer(int height, int width){
        CreateEmptyGrid(height, width);
    }

    public String GetName() {
        return name;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public ArrayList<ArrayList<SpritanColor>> GetPixelData() {
        return pixelData;
    }

    public void SetPixelData(ArrayList<ArrayList<SpritanColor>> pixelData) {
        this.pixelData = pixelData;
    }
    
    public void CreateEmptyGrid(int height, int width){
        SpritanColor defaultColor = SpritanColor.TRANSPARENT;
        pixelData = new ArrayList<>();
        
        for (int i = 0; i < height; i++){
            pixelData.add(new ArrayList<>());
            for (int j = 0; j < width; j++){
                pixelData.get(i).add(defaultColor);
            }
        }
    }
    
    public void ChangePixelColor(int row, int column, SpritanColor color){
        pixelData.get(row).set(column, color);
    }
    
    public void ErasePixelColor(int row, int column){
        pixelData.get(row).set(column, SpritanColor.TRANSPARENT);
    }
}
