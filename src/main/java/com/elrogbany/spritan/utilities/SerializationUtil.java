package com.elrogbany.spritan.utilities;

import com.elrogbany.spritan.core.SpritanFile;

import java.io.*;

public class SerializationUtil {
    public static void serialize(SpritanFile file, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(file);
        }
    }

    public static SpritanFile deserialize(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (SpritanFile) in.readObject();
        }
    }
}
