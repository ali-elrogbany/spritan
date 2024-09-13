package com.elrogbany.spritan.utilities;

import com.elrogbany.spritan.core.SpritanColorPalette;
import com.elrogbany.spritan.core.SpritanFile;
import com.elrogbany.spritan.ui.SpritanColorPaletteBar;
import com.elrogbany.spritan.ui.SpritanGrid;
import com.elrogbany.sptitan.App;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SpritanColorPaletteFileOperations {
    public static void SavePalette(SpritanColorPalette palette) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Color Palette");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SPRITAN COLOR PALETTE Files", "*.scp"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileOutputStream fileOut = new FileOutputStream(file);
                 ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

                out.writeObject(palette);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static SpritanColorPalette LoadPalette(App app) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Color Palette");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("SPRITAN COLOR PALETTE Files", "*.scp"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileInputStream fileIn = new FileInputStream(file);
                 ObjectInputStream in = new ObjectInputStream(fileIn)) {

                SpritanColorPalette palette = (SpritanColorPalette) in.readObject();
                
                SpritanGrid grid = app.GetSpritanGrid();
                
                if (grid != null){
                    grid.ChangeColorPalette(palette);
                }
                else{
                    SpritanColorPaletteBar.UpdateColorPalette(palette);
                }
                
                return palette;

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
