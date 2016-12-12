package mytunes.dal;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

/**
 * Retrieves information about a song from a given path, such as; Title, Artist,
 * Genre and Song duration.
 *
 * @author Simon Birkedal
 */
public class ReadSongProperty 
{
    private MP3File audioFile;

    /**
     * Default constructor.
     *
     * @param filePath Direct path to the song.
     * @throws CannotReadException
     * @throws IOException
     * @throws TagException
     * @throws ReadOnlyFileException
     * @throws InvalidAudioFrameException
     */
    public ReadSongProperty(String filePath) throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException
    {
        this.audioFile = (MP3File) AudioFileIO.read(new File(filePath));
    }

    /**
     * Gets the song file's title definition.
     *
     * @return Returns a string representing the file's set title.
     */
    public String getTitle()
    {
        return audioFile.getID3v2Tag().getFirst(FieldKey.TITLE);
    }

    /**
     * Gets the song file's artist definition.
     *
     * @return Returns a string representing the file's set artist.
     */
    public String getArtist()
    {
        return audioFile.getID3v2Tag().getFirst(FieldKey.ARTIST);
    }

    /**
     * Gets the song file's genre definition.
     *
     * @return Returns a string representing the file's set genre.
     */
    public String getGenre()
    {
        return audioFile.getID3v2Tag().getFirst(FieldKey.GENRE);
    }

    /**
     * Gets a songs length in total seconds, then converts it to a formatted
     * string that represents the duration in Minutes:Seconds.
     *
     * @return Returns a string value representing the song's duration in
     * minutes and seconds.
     */
    public double getDuration()
    {
        double trackLength = audioFile.getAudioHeader().getTrackLength();

        return trackLength;
    }
}
