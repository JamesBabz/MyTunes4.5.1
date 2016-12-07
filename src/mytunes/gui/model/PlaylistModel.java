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
import mytunes.be.Playlist;
import mytunes.dal.PlaylistDAO;

/**
 *
 * @author James
 */
public class PlaylistModel {

    private PlaylistDAO playlistDAO;
    private static PlaylistModel instance;

    ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    ObservableList<String> playlistTitles = FXCollections.observableArrayList();

    public static PlaylistModel getInstance()
    {
        if (instance == null)
        {
            instance = new PlaylistModel();
        }
        return instance;
    }

    private PlaylistModel()
    {
        playlistDAO = new PlaylistDAO();
    }

    public void addPlaylist(Playlist playlist)
    {
        playlists.add(playlist);
    }

    public ObservableList<Playlist> getPlaylists()
    {
        return playlists;
    }

    public void setPlaylistTitles()
    {
        playlistTitles.clear();
        for (Playlist playlist : playlists)
        {
            playlistTitles.add(playlist.getTitle());
        }

    }

    public ObservableList<String> getPlaylistTitles()
    {
        setPlaylistTitles();
        return playlistTitles;
    }

    public void updatePlaylistView()
    {

    }

    public void loadPlaylistData() throws FileNotFoundException
    {
        playlists.clear();
        playlists.addAll(playlistDAO.readObjectData("PlaylistTest.dat"));
    }

    public void savePlaylistData()
    {
        try
        {
            ArrayList<Playlist> playlistToSave = new ArrayList<>();
            for (Playlist playlist : playlists)
            {
                playlistToSave.add(playlist);

            }
            playlistDAO.writeObjectData(playlistToSave, "PlaylistTest.dat");
        }
        catch (IOException ex)
        {
            // TODO: exception handling.
        }
    }

}
