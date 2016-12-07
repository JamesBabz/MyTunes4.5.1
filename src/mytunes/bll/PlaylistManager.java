/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import mytunes.be.Playlist;
import mytunes.be.Song;

/**
 * This class manages everything regarding the playlists; Creating a new playlist,
 * editing an existing playlist, adding and removing elements to/from the playlist.
 * @author Simon Birkedal
 */
public class PlaylistManager
{    
    /**
     * Adds a specified song to a specified playlist.
     * @param playlist A playlist to add the song to.
     * @param song The song to be added to the playlist.
     */
    public void addSong(Playlist playlist, Song song)
    {
        // TODO: Add exception handling.
        playlist.getSongList().add(song);
    }
    
    /**
     * Removes a specified song from a specified playlist.
     * @param playlist A playlist to remove the song from.
     * @param song The song to be removed from the playlist.
     */
    public void removeSong(Playlist playlist, Song song)
    {
        // TODO: Add exception handling.
        playlist.getSongList().remove(song);
    }
    
}
