package dkeep.gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dkeep.logic.Game;


public class StorageGame {
	 
    private static final String FILE_PATH = "TESTE";
 
    public static void storeGame(final Game pGame){
        try {
            final File file = new File(FILE_PATH);
            final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            outputStream.writeObject(pGame);
            outputStream.close();
        } catch (final IOException pE) {
            System.out.println("Couldn't save Game!");
            pE.printStackTrace();
        }
    }
 
    public static Game loadGame() {
    	Game g = null;
        try {
            final File file = new File(FILE_PATH);
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

/*
public class StorageGame {

	private Game g;

	StorageGame(Game g) throws IOException {
		
		this.g= g;

		final File file = new File("/TESTE.bin");

		try {

			final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));

			outputStream.writeObject(g);          

			outputStream.close();

		} catch (IOException pE) {

			pE.printStackTrace();

		}     
	}

	StorageGame(String file) throws  ClassNotFoundException{

		
		try {

			final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));

			this.g=(Game) inputStream.readObject();
		
			inputStream.close();

		} catch (IOException pE) {

			pE.printStackTrace();

		}   
		
	}
	
	public Game getGame(){
		return g;
	}
	
}
*/