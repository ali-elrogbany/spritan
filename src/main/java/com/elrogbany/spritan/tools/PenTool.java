package com.elrogbany.spritan.tools;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanGrid;
import com.elrogbany.sptitan.App;
import javafx.scene.shape.Rectangle;

public class PenTool implements ITool{

    @Override
    public void OnPixelClick(SpritanGrid spritanGrid, Rectangle pixel, int row, int column) {
        App.instance.SaveState();
        
        SpritanFile spritanFile = spritanGrid.GetSpritanFile();
        
        spritanFile.ChangePixelColor(row, column);
        
        spritanGrid.DrawSavedImage();
    } 
}
