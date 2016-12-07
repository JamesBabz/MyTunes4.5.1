package mytunes.dal;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.ID3v24Frames;
import org.jaudiotagger.tag.id3.ID3v24Tag;

/**
 * Retrieves information about a song from a given path, such as; Title, Artist,
 * Genre and Song duration.
 * @author Simon Birkedal
 */
public class ReadSongProperty
{
    private MP3File soundFile;
    private ID3v24Tag soundTag;

    /**
     * Default constructor.
     * @param filePath Direct path to the song.
     * @throws CannotReadException
     * @throws IOException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException 
     */
    public ReadSongProperty(String filePath) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        this.soundFile = (MP3File) AudioFileIO.read(new File(filePath));
        this.soundTag = soundFile.getID3v2TagAsv24();
    }

    /**
     * Gets the song file's title definition.
     * @return Returns a string representing the file's set title.
     */
    public String getTitle()
    {
        return soundTag.getFirst(ID3v24Frames.FRAME_ID_TITLE);
    }

    /**
     * Gets the song file's artist definition.
     * @return Returns a string representing the file's set artist.
     */
    public String getArtist()
    {
        return soundTag.getFirst(ID3v24Frames.FRAME_ID_ARTIST);
    }

    /**
     * Gets the song file's genre definition.
     * @return Returns a string representing the file's set genre.
     */
    public String getGenre()
    {
        return soundTag.getFirst(ID3v24Frames.FRAME_ID_GENRE);
    }

    /**
     * Gets a songs length in total seconds, then converts it to a formatted
     * string that represents the duration in Minutes:Seconds.
     * @return Returns a string value representing the song's duration in minutes and seconds.
     */
    public String getDuration()
    {
        int trackLength = soundFile.getAudioHeader().getTrackLength();

        int minutes = trackLength / 60;
        int seconds = trackLength % 60;
        String formattedDuration = String.format("%02d:%02d", minutes, seconds);

        return formattedDuration;
    }

}
