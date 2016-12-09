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
