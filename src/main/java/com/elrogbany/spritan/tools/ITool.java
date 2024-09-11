package com.elrogbany.spritan.tools;

import com.elrogbany.spritan.ui.SpritanGrid;
import javafx.scene.shape.Rectangle;

public interface ITool {
    public void OnPixelClick(SpritanGrid spritanGrid, Rectangle pixel, int row, int column);
}
