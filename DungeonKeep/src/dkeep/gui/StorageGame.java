package dkeep.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dkeep.logic.Game;


public class StorageGame {

	public static void storeGame(final Game pGame, File file){
		try {

			final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
			outputStream.writeObject(pGame);
			outputStream.close();
		} catch (final IOException pE) {
			System.out.println("Couldn't save Game!");
			pE.printStackTrace();
		}
	}

	public static Game loadGame(File file) {
		Game g = null;

		try {           
			final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
			g = (Game) inputStream.readObject();
			inputStream.close();
		} catch (final IOException pE) {
			System.out.println("Couldn't load Game!");
			pE.printStackTrace();
		} catch (ClassNotFoundException pE) {
			System.out.println("Class was removed since last execution!?");
			pE.printStackTrace();
		}
		return g;
	}

}


