/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.dal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import mytunes.be.Playlist;

/**
 * Class which reads and writes playlists from and to a file.
 *
 * @author Simon Birkedal
 */
public class PlaylistDAO {

    public void writeObjectData(ArrayList<Playlist> associationHashMap, String fileName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(associationHashMap);
        }
    }

    public ArrayList<Playlist> readObjectData(String fileName) throws FileNotFoundException
    {
        ArrayList<Playlist> playlists = new ArrayList<>();

        FileInputStream fis = new FileInputStream(fileName);
        BufferedInputStream bis = new BufferedInputStream(fis);
        try (ObjectInputStream ois = new ObjectInputStream(bis))
        {
            playlists = (ArrayList<Playlist>) ois.readObject();
        }
        catch (IOException | ClassNotFoundException ex)
        {
            // Handle exception
        }

        return playlists;
    }
}
