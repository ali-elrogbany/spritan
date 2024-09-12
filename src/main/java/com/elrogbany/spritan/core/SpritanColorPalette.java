package com.elrogbany.spritan.core;

import com.elrogbany.spritan.utilities.SpritanColor;
import java.io.Serializable;
import java.util.ArrayList;

public class SpritanColorPalette implements Serializable {
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
}
