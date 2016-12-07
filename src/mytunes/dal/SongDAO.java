/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import mytunes.be.Song;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.ObservableList;

/**
 * Class which reads and writes songs from and to a file.
 *
 * @author Simon Birkedal
 */
public class SongDAO
{

    public void writeObjectData(ArrayList<Song> songs, String fileName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(songs);
        }
    }

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
