package mytunes.be;

import java.io.IOException;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.UUID;
import mytunes.bll.TimeFormat;
import mytunes.dal.ReadSongProperty;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * The playlist business entity contains all information regarding a playlist;
 * Title, Total Duration and what songs have been added to the playlist.
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class Playlist implements Serializable {

    private static final long serialVersionUID = 1L;
    private final UUID id;
    private String title;
    private String totalDuration;
    private ArrayList<Song> songList;
    private int numSongs;

    /**
     * Default constructor for the playlist.
     *
     * @param title The title of the playlist.
     */
    public Playlist(String title)
    {
        this.id = UUID.randomUUID();
        this.title = title;
        songList = new ArrayList<Song>();
    }

    /**
     * Gets the title representing the playlist.
     *
     * @return Returns the playlist title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Set a new title for the playlist.
     *
     * @param title A string representing the new title.
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets the id of the playlist.
     *
     * @return Returns an integer value representing the playlist's id.
     */
    public UUID getId()
    {
        return id;
    }

    /**
     * Retrieves the total duration of all the songs added to the playlist.
     *
     * @return Returns the total duration.
     */
    public String getTotalDuration()
    {
        int summedDuration = 0;
        
        for (Song song : songList)
        {
            try
            {
                summedDuration += new ReadSongProperty(song.getPath()).getDuration();
            }
            catch (CannotReadException ex)
            {
                summedDuration += 0;
            }
            catch (TagException | ReadOnlyFileException | InvalidAudioFrameException | IOException ex)
            {
                System.out.println(ex.getCause());
            }
        }
        
        totalDuration = TimeFormat.formatDouble(summedDuration);
        
        return totalDuration;
    }

    /**
     * Retrieves all songs added to this playlist.
     *
     * @return Returns a list of songs representing the playlist.
     */
    public ArrayList<Song> getSongList()
    {
        return songList;
    }

    /**
     * Retrieves the total amount of songs in this playlist.
     *
     * @return Returns all songs in the playlist.
     */
    public int getNumSongs()
    {
        numSongs = getSongList().size() + 1;
        return numSongs;
    }
}
