package com.lpoo.bombic.Tools;

import com.lpoo.bombic.Screens.StoryModeScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Responsible for loading and saving the available levels in a file.
 */
public class StorageLevels {

    /**
     * Constructor. Writes levels in the file.
     * @param pLevels - to save
     * @param file - file to save to
     */
    public static void StorageLevels(final int pLevels, File file){
        try {
            final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeInt(pLevels);
            outputStream.close();
        } catch (final IOException pE) {
            System.out.println("Couldn't save Game!");
            pE.printStackTrace();
        }
    }

    /**
     * Read levels from file
     * @param file - to read from
     * @return levels
     */
    public static int loadLevels(File file) {
       int levels = 1;
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
            levels = inputStream.readInt();
            inputStream.close();
        } catch (final IOException pE) {
            System.out.println("Couldn't load Game!");
            pE.printStackTrace();
        }
        return levels;
    }

}