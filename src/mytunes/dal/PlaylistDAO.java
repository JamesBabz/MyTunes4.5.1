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
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class PlaylistDAO 
{

    /**
     * Writes an object to a file, in this case the object must be an arraylist
     * of Playlists.
     * @param playlists The playlists to be saved.
     * @param fileName The name of the file to save the playlist in.
     * @throws IOException 
     */
    public void writeObjectData(ArrayList<Playlist> playlists, String fileName) throws IOException
    {
        FileOutputStream fos = new FileOutputStream(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        try (ObjectOutputStream oos = new ObjectOutputStream(fos))
        {
            oos.writeObject(playlists);
        }
    }

    /**
     * Reads an object from a file, the object must be an arraylist of playlists.
     * @param fileName The fileName to read the information from.
     * @return Returns a new array containing all the stored data.
     * @throws FileNotFoundException 
     */
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
