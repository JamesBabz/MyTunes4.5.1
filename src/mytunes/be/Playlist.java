/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.be;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 *
 * @author Simon Birkedal
 */
public class Playlist implements Serializable
{
    private static final long serialVersionUID = 1L;
    private int id;
    private String title;
    private double totalDuration;
    private List<Song> songList;
    private int numSongs;

    /**
     * Default constructor for the playlist.
     * @param title The title of the playlist.
     */
    public Playlist(String title)
    {
        this.title = title;
        songList = new ArrayList<Song>();
    }
    
    /**
     * Gets the title representing the playlist.
     * @return Returns the playlist title.
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * Set a new title for the playlist.
     * @param title A string representing the new title.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    /**
     * Gets the id of the playlist.
     * @return Returns an integer value representing the playlist's id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Retrieves the total duration of all the songs added to the playlist.
     * @return Returns the total duration.
     */
    public double getTotalDuration()
    {
        return totalDuration;
    }
    
    /**
     * Retrieves all songs added to this playlist.
     * @return Returns a list of songs representing the playlist.
     */
    public List<Song> getSongList()
    {
        return songList;
    }

    /**
     * Retrieves the total amount of songs in this playlist.
     * @return Returns all songs in the playlist.
     */
    public int getNumSongs()
    {
        numSongs = getSongList().size() + 1;
        return numSongs;
    }
}
