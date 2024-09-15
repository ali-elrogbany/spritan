package com.elrogbany.spritan.utilities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpritanColor implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    
    public static final SpritanColor WHITE = new SpritanColor(255, 255, 255);
    public static final SpritanColor BLACK = new SpritanColor(0, 0, 0);
    public static final SpritanColor RED = new SpritanColor(255, 0, 0);
    public static final SpritanColor GREEN = new SpritanColor(0, 255, 0);
    public static final SpritanColor BLUE = new SpritanColor(0, 0, 255);
    public static final SpritanColor YELLOW = new SpritanColor(255, 255, 0);
    public static final SpritanColor CYAN = new SpritanColor(0, 255, 255);
    public static final SpritanColor MAGENTA = new SpritanColor(255, 0, 255);
    public static final SpritanColor GREY = new SpritanColor(128, 128, 128);
    public static final SpritanColor TRANSPARENT = new SpritanColor(255, 255, 255, 0);
    
    private int r, g, b, a;

    public SpritanColor(int r, int g, int b, int a) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.a = clamp(a);
    }

    public SpritanColor(int r, int g, int b) {
        this(r, g, b, 255);
    }

    public int GetRed() {
        return r;
    }

    public void SetRed(int r) {
        this.r = r;
    }

    public int GetGreen() {
        return g;
    }

    public void SetGreen(int g) {
        this.g = g;
    }

    public int GetBlue() {
        return b;
    }

    public void SetBlue(int b) {
        this.b = b;
    }

    public int GetAlpha() {
        return a;
    }

    public void SetAlpha(int a) {
        this.a = a;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(value, 255));
    }

    public int[] toTuple() {
        return new int[]{r, g, b, a};
    }

    public Map<String, Integer> toDict() {
        Map<String, Integer> map = new HashMap<>();
        map.put("r", r);
        map.put("g", g);
        map.put("b", b);
        map.put("a", a);
        return map;
    }

    public static SpritanColor fromDict(Map<String, Integer> map) {
        return new SpritanColor(map.get("r"), map.get("g"), map.get("b"), map.get("a"));
    }

    public String toHex() {
        return String.format("#%02X%02X%02X", r, g, b);
    }

    public static SpritanColor fromHex(String hexCode) {
        hexCode = hexCode.replace("#", "");
        int r = Integer.parseInt(hexCode.substring(0, 2), 16);
        int g = Integer.parseInt(hexCode.substring(2, 4), 16);
        int b = Integer.parseInt(hexCode.substring(4, 6), 16);
        return new SpritanColor(r, g, b);
    }

    public void lighten(double amount) {
        this.r = clamp(this.r + (int) (255 * amount));
        this.g = clamp(this.g + (int) (255 * amount));
        this.b = clamp(this.b + (int) (255 * amount));
    }

    public void darken(double amount) {
        this.r = clamp(this.r - (int) (255 * amount));
        this.g = clamp(this.g - (int) (255 * amount));
        this.b = clamp(this.b - (int) (255 * amount));
    }

    public SpritanColor blend(SpritanColor otherColor, double factor) {
        int r = (int) (this.r * (1 - factor) + otherColor.r * factor);
        int g = (int) (this.g * (1 - factor) + otherColor.g * factor);
        int b = (int) (this.b * (1 - factor) + otherColor.b * factor);
        return new SpritanColor(r, g, b);
    }


    public boolean isLight() {
        double luminance = (0.299 * r + 0.587 * g + 0.114 * b) / 255;
        return luminance > 0.5;
    }

    public boolean isDark() {
        return !isLight();
    }

    public static SpritanColor randomColor() {
        Random random = new Random();
        return new SpritanColor(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    public static SpritanColor BlendColors(SpritanColor... layers) {
        if (layers == null || layers.length == 0) {
            return SpritanColor.TRANSPARENT;
        }

        SpritanColor finalColor = layers[0];

        for (int i = 1; i < layers.length; i++) {
            finalColor = BlendTwoColors(finalColor, layers[i]);
        }

        return finalColor;
    }
    
    public static SpritanColor BlendTwoColors(SpritanColor bottom, SpritanColor top) {
        double alphaBottom = bottom.GetAlpha() / 255.0;
        double alphaTop = top.GetAlpha() / 255.0;

        double finalAlpha = alphaTop + alphaBottom * (1 - alphaTop);

        if (finalAlpha == 0) {
            return SpritanColor.TRANSPARENT;
        }

        int finalRed = (int) (
            (top.GetRed() * alphaTop + bottom.GetRed() * alphaBottom * (1 - alphaTop)) / finalAlpha
        );
        int finalGreen = (int) (
            (top.GetGreen() * alphaTop + bottom.GetGreen() * alphaBottom * (1 - alphaTop)) / finalAlpha
        );
        int finalBlue = (int) (
            (top.GetBlue() * alphaTop + bottom.GetBlue() * alphaBottom * (1 - alphaTop)) / finalAlpha
        );

        int finalAlpha255 = (int) (finalAlpha * 255);

        return new SpritanColor(finalRed, finalGreen, finalBlue, finalAlpha255);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SpritanColor color = (SpritanColor) obj;
        return r == color.r && g == color.g && b == color.b && a == color.a;
    }

    @Override
    public String toString() {
        return String.format("SpritanColor(r=%d, g=%d, b=%d, a=%d)", r, g, b, a);
    }
    
    @Override
    public SpritanColor clone() {
        try {
            return (SpritanColor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
