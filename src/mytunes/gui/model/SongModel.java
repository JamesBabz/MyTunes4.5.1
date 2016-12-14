/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;
import mytunes.dal.SongDAO;

/**
 *
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class SongModel {

    private Song contextSong;

    private SongDAO songDAO;

    private static SongModel instance;

    //Singleton
    public static SongModel getInstance()
    {
        if (instance == null)
        {
            instance = new SongModel();
        }
        return instance;
    }

    private SongModel()
    {
        songDAO = new SongDAO();
    }

    ObservableList<Song> songs = FXCollections.observableArrayList();

    public void addSong(Song song)
    {
        songs.add(song);

    }

    /*
    Gets the song with new text from the edit view and replaces the original 
    one in the list of current songs
     */
    public void editSong(Song contextSong)
    {

        for (int i = 0; i < songs.size(); i++)
        {

            Song song = songs.get(i);
            if (song.getId() == contextSong.getId())
            {

                song.setTitle(contextSong.getTitle());
                song.setArtist(contextSong.getArtist());
                song.setGenre(contextSong.getGenre());
                song.setRating(contextSong.getRating());
                //Replace the song
                songs.set(i, song);

            }

        }

    }

    public ObservableList<Song> getSongs()
    {
        return songs;
    }

    public void setSongs()
    {

    }

    public Song getContextSong()
    {
        return contextSong;

    }

    public void setContextSong(Song contextSong)
    {
        this.contextSong = contextSong;
    }

    //Loads songs from .dat file
    public void loadSongData() throws FileNotFoundException
    {
        songs.clear();
        songs.addAll(songDAO.readObjectData("SongsData.dat"));
    }

    //Save songs to .dat file
    public void saveSongData() throws IOException
    {

        ArrayList<Song> songsToSave = new ArrayList<>();
        for (Song song : songs)
        {
            songsToSave.add(song);
        }
        songDAO.writeObjectData(songsToSave, "SongsData.dat");
    }

}
