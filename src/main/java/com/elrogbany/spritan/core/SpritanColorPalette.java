package com.elrogbany.spritan.core;

import com.elrogbany.spritan.utilities.SpritanColor;
import java.io.Serializable;
import java.util.ArrayList;

public class SpritanColorPalette implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private ArrayList<SpritanColor> colors;

    public SpritanColorPalette() {
        colors = new ArrayList<>();
    }

    public ArrayList<SpritanColor> GetColors() {
        return colors;
    }

    public void SetColors(ArrayList<SpritanColor> colors) {
        this.colors = colors;
    }

    public void AddColor(SpritanColor color) {
        colors.add(color);
    }

    public void RemoveColor(SpritanColor color) {
        colors.remove(color);
    }
    
    @Override
    public SpritanColorPalette clone() {
        try {
            SpritanColorPalette cloned = (SpritanColorPalette) super.clone();
            
            // Deep copy of colors
            cloned.colors = new ArrayList<>();
            for (SpritanColor color : colors) {
                cloned.colors.add(color.clone());
            }
            
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
