package mytunes.be;

import java.io.Serializable;
import java.util.UUID;
/**
 *
 * @author Simon Birkedal
 */
public class Song implements Serializable{

    private String id;
    private String title;
    private String artist;
    private String genre;
    private String duration;
    private int rating;
    private String path;

    /**
     * The default constructor for the song.
     *
     * @param id Gets an unique ID.
     * @param title The title of the song.
     * @param artist The artist's name.
     * @param genre The song's genre.
     * @param duration The duration of the song.
     * @param rating The rating of the song.
     * @param path The song path - this is the exact path of the song e.g.
     * C:\\Desktop\\MyMp3File.mp3
     */
    public Song(String title, String artist, String genre, String duration, int rating, String path)
    {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.duration = duration;
        this.rating = rating;
        this.path = path;
    }
    

    /**
     * Gets the id of the song.
     * @return Returns an integer value representing the id of this song.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Gets the title of this song.
     * @return Returns the song's title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Gets the artist of the song.
     * @return Returns the song's artist name.
     */
    public String getArtist()
    {
        return artist;
    }

    /**
     * Gets the genre of the song.
     * @return Returns the genre represented by a string.
     */
    public String getGenre()
    {
        return genre;
    }

    // Needs rework, probably going to use a TimeSpan object.
    /**
     * Gets the duration of the song.
     * @return Returns the duration of the song.
     */
    public String getDuration()
    {
        return duration;
    }

    // Needs rework, probably make use of the Path object.
    /**
     * Gets the string representing the songs path.
     *
     * @return Returns the exact path of the song.
     */
    public String getPath()
    {
        return path;
    }

    public int getRating()
    {
        return rating;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    
    

}
