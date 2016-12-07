/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.bll;

import java.util.ArrayList;
import java.util.List;
import mytunes.be.Song;

/**
 * Class for filtering in the table of songs.
 *
 * @author Simon Birkedal
 */
public class Search
{

    private List<Song> songs;

    /**
     * Constructor for the search class, which takes in the current list of
     * songs.
     *
     * @param songs
     */
    public Search(List<Song> songs)
    {
        this.songs = songs;
    }

    /**
     * Search in the list of songs, if the song contains the searchQuery.
     *
     * @param searchQuery
     *
     * @return an ArrayList with the results of songs, which contains the
     *         searchQuery.
     */
    public ArrayList<Song> getContains(String searchQuery)
    {
        ArrayList<Song> listResult = new ArrayList<>();

        for (Song song : songs)
        {
            if (song.getTitle().toLowerCase().contains(searchQuery) || song.getArtist().toLowerCase().contains(searchQuery) || song.getGenre().toLowerCase().contains(searchQuery))
            {
                listResult.add(song);
            }
        }
        return listResult;
    }

}
