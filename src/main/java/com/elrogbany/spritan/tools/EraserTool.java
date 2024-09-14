package com.elrogbany.spritan.tools;

import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanGrid;
import javafx.scene.shape.Rectangle;

public class EraserTool implements ITool {

    @Override
    public void OnPixelClick(SpritanGrid spritanGrid, Rectangle pixel, int row, int column) {
        SpritanFile spritanFile = spritanGrid.GetSpritanFile();
        
        spritanFile.ErasePixelColor(row, column);
        
        spritanGrid.DrawSavedImage();
    }
    
}
