/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import mytunes.be.Song;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Class which reads and writes songs from and to a file.
 *
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class SongDAO 
{

    /**
     * Writes an object to a file, in this case the object must be an arraylist
     * of Songs.
     *
     * @param songs The songs to be saved.
     * @param fileName  The name of the file to save the songs in.
     * @throws IOException
     */
    public void writeObjectData(ArrayList<Song> songs, String fileName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(songs);
        }
    }

    /**
     * Reads an object from a file, the object must be an arraylist of
     * songs.
     *
     * @param fileName The fileName to read the information from.
     * @return Returns a new array containing all the stored data.
     * @throws FileNotFoundException
     */
    public ArrayList<Song> readObjectData(String fileName) throws FileNotFoundException
    {
        ArrayList<Song> songList = new ArrayList<Song>();

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try (ObjectInputStream ois = new ObjectInputStream(bis))
        {

            songList = (ArrayList<Song>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            // Handle exception
        }

        return songList;
    }
}
