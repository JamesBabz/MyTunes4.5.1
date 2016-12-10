package mytunes.bll;

import java.io.File;
import javafx.beans.Observable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import mytunes.be.Song;

/**
 * This is the class which contains all the operational logic for the songs.
 * Whenever a new instance of SongManager is instantiated the developer can call
 * it and perform tasks such as play, pause & stop.
 *
 * @author Simon Birkedal
 */
public class SongManager {

    private Song song;
    private MediaPlayer player;

    /**
     * Plays the specified song if any is found.
     *
     * @param song The song to be played.
     * @param overwrite
     */
    public void playSong(Song song, boolean overwrite)
    {
        pauseSong();
        if (this.song == null || overwrite)
        {
            this.song = song;
            File soundFile = new File(this.song.getPath());
            Media media = new Media(soundFile.toURI().toString());
            player = new MediaPlayer(media);
        }
        player.play();
    }

    /**
     * Pauses the currently playing song.
     */
    public void pauseSong()
    {
        if (song != null)
        {
            player.pause();
        }
    }

    /**
     * Gets the currently playing song.
     *
     * @return Returns a song object representing the song currently playing.
     */
    public Song getCurrentlyPlayingSong()
    {
        return this.song;
    }

    /**
     * Gets the songs length.
     *
     * @return Returns the length of the song.
     */
    public Duration getSongLength()
    {
        return player.getTotalDuration();
    }

    /**
     * Gets the time elapsed of the currently playing song.
     * @return Returns a duration value representing the time elapsed.
     */
    public Duration getSongTimeElapsed()
    {
        return player.getCurrentTime();
    }

    /**
     * Gets the mediaplayer.
     * @return Returns the current media player.
     */
    public MediaPlayer getMediaPlayer()
    {
        return player;
    }
    
    /**
     * Adjusts the volume.
     * @param value The new value to set the volume equal to. Ranging from 0.0 to
     * 1.0
     */
    public void adjustVolume(double value)
    {
        player.setVolume(value);
    }  
}
