package mytunes.bll;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mytunes.be.Song;

/**
 * 
 * @author Simon Birkedal
 */
public class SearchQuery
{ 
    
    /**
     * Searches through a list of songs, then returns a new ArrayList of songs
     * that contains the searchquery requirement.
     * @param songs The list of songs to go through.
     * @param searchQuery The string to match the songs against.
     * @return Returns a new ArrayList of songs that matches the searchquery.
     */
    public ArrayList<Song> search(List<Song> songs, String searchQuery)
    {        
        ArrayList<Song> result = new ArrayList<>();
        
        for (Song song : songs)
        {
            String title = song.getTitle().trim().toLowerCase();
            String artist = song.getArtist().trim().toLowerCase();
            String genre = song.getGenre().trim().toLowerCase();
            String rating = String.valueOf(song.getRating()).trim().toLowerCase();
            
            if (title.contains(searchQuery.toLowerCase().trim())
                    || artist.contains(searchQuery.toLowerCase().trim())
                    || genre.contains(searchQuery.toLowerCase().trim())
                    || rating.contains(searchQuery.toLowerCase().trim())
                    && !result.contains(song))
            {
                result.add(song);
            }
        }
        
        return result;
    }
}
