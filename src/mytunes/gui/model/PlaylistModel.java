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
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class PlaylistModel {

    private Playlist contextPlaylist;
    private PlaylistDAO playlistDAO;
    private static PlaylistModel instance;

    ObservableList<Playlist> playlists = FXCollections.observableArrayList();
    ObservableList<String> playlistTitles = FXCollections.observableArrayList();

    //Singleton
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

    //Sets the titles for the playlists for use in the view
    public void setPlaylistTitles()
    {
        playlistTitles.clear();
        for (Playlist playlist : playlists)
        {
            playlistTitles.add(playlist.getTitle());
        }

    }

    /*
    Gets the playlist and new name from the renameview and replaces the current
    playlist with the new one with the new name
     */
    public void renamePlaylist(Playlist contextPlaylist)
    {
        for (int i = 0; i < playlists.size(); i++)
        {

            Playlist playlist = playlists.get(i);
            if (playlist.getId() == contextPlaylist.getId())
            {

                playlist.setTitle(contextPlaylist.getTitle());
                //Replace the playlist
                playlists.set(i, playlist);
            }
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
        playlists.addAll(playlistDAO.readObjectData("PlaylistData.dat"));
    }

    public void savePlaylistData() throws IOException
    {
        ArrayList<Playlist> playlistToSave = new ArrayList<>();
        for (Playlist playlist : playlists)
        {
            playlistToSave.add(playlist);

        }
        playlistDAO.writeObjectData(playlistToSave, "PlaylistData.dat");
    }

    public Playlist getContextPlaylist()
    {
        return contextPlaylist;
    }

    public void setContextPlaylist(Playlist contextPlaylist)
    {
        this.contextPlaylist = contextPlaylist;
    }

}
