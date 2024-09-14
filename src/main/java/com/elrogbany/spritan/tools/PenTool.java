package com.elrogbany.spritan.tools;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanGrid;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PenTool implements ITool{

    @Override
    public void OnPixelClick(SpritanGrid spritanGrid, Rectangle pixel, int row, int column) {
        SpritanFile spritanFile = spritanGrid.GetSpritanFile();
        
        spritanFile.ChangePixelColor(row, column);
        
        spritanGrid.DrawSavedImage();
    }
    
}
