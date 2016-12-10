package mytunes.gui.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import mytunes.be.Playlist;
import mytunes.be.Song;
import mytunes.bll.PlaylistManager;
import mytunes.bll.SearchQuery;
import mytunes.bll.SongManager;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

/**
 * This class is the main controller class, it handles all the interactions
 * occuring in the main view.
 *
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class MainViewController implements Initializable
{
    private final SongManager songManager;
    private final PlaylistManager playlistManager;
    private final SearchQuery searchQuery;
    private final SongModel songModel;
    private final PlaylistModel playlistModel;
    private final ObservableList<Song> songsLibrary;
    private Stage primaryStage;
    private ObservableList<Song> currentSongsInView;
    private final ObservableList<Song> searchedSongs;
    private ObservableList<Playlist> playlists;
    private Song selectedSong;
    private Playlist selectedPlaylist;
    private boolean isPlaying;
    private boolean isMuted;
    private boolean hasBrowseButtonBeenClicked;
    private double sliderVolumeValue;
    private boolean isShuffleToggled;
    private boolean isRepeatToggled;
    private final Random rand;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView imgMute;
    @FXML
    private ImageView imgPlay;
    @FXML
    private TextField txtSearch;
    @FXML
    private Label lblSongPlaying;
    @FXML
    private Label lblSongDuration;
    @FXML
    private Label lblTimeElapsed;
    @FXML
    private Slider sliderVolume;
    @FXML
    private ProgressBar barMediaTimer;
    @FXML
    private TableView<Song> tableSongs;
    @FXML
    private TableColumn<Song, String> colTitle;
    @FXML
    private TableColumn<Song, String> colArtist;
    @FXML
    private TableColumn<Song, String> colGenre;
    @FXML
    private TableColumn<Song, Double> colDuration;
    @FXML
    private TableColumn<Song, Double> colRating;
    @FXML
    private TableView<Playlist> tablePlaylists;
    @FXML
    private TableColumn<Playlist, String> colPlaylist;
    @FXML
    private TableColumn<Playlist, String> colTime;
    @FXML
    private Menu menuAddToPL;
    @FXML
    private ContextMenu contextSong;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button btnPrev;
    @FXML
    private ImageView imgPrev;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnNext;
    @FXML
    private ImageView imgNext;
    @FXML
    private Hyperlink hlinkBrowse;
    @FXML
    private Label lblClearSearch;
    @FXML
    private Menu fileAddToPL;
    @FXML
    private ImageView imgShuffle;
    @FXML
    private ImageView imgRepeat;
    @FXML
    private MenuItem itemAddSong;
    @FXML
    private MenuItem itemEdit;
    @FXML
    private MenuItem itemDelete;

    /**
     * The default contructor for this class.
     */
    public MainViewController()
    {
        this.isShuffleToggled = false;
        this.isRepeatToggled = false;
        this.hasBrowseButtonBeenClicked = true;
        this.rand = new Random();
        this.searchQuery = new SearchQuery();
        this.songManager = new SongManager();
        this.playlistManager = new PlaylistManager();
        this.songModel = SongModel.getInstance();
        this.playlistModel = PlaylistModel.getInstance();
        this.searchedSongs = FXCollections.observableArrayList();
        this.songsLibrary = FXCollections.observableArrayList();
        this.currentSongsInView = FXCollections.observableArrayList();
        this.playlists = FXCollections.observableArrayList();
    }

    /**
     * Loads the default settings.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colPlaylist.setCellValueFactory(new PropertyValueFactory<>("title"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("totalDuration"));
        tableSongs.setItems(songModel.getSongs());
        currentSongsInView.setAll(songsLibrary);
        setPlaylists();
        tablePlaylists.setItems(playlists);
        initialLoad();
        isPlaying = false;
        processVolumeData();
        searchOnUpdate();
    }

    @FXML
    public void handleAddSongButton()
    {
        addSong();
    }
    
    @FXML
    public void handleNextSong()
    {
        prevNextSong(true);
    }

    @FXML
    public void handlePreviousSong()
    {
        prevNextSong(false);
    }

    @FXML
    private void handleEditSong()
    {
        handleContextSong();
        loadStage("EditSongView.fxml");
    }

    @FXML
    private void handleDeleteSong()
    {
        deleteSong();
    }
    
    /**
     * Loads the playlistView.
     */
    @FXML
    private void handleAddPlaylistButton()
    {
        addPlaylist();
    }
    
    @FXML
    private void handleRenamePlaylist(ActionEvent event)
    {
        renamePlaylist();

    }
    
    /**
     * Handles the selected element whenever a mouse event occurs.
     *
     * @param event The mouse event to listen for.
     */
    @FXML
    public void handleOnMousePressed(MouseEvent event)
    {
        selectedSong = tableSongs.selectionModelProperty().getValue().getSelectedItem();

        if (selectedSong != null)
        {
            if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
            {
                songManager.pauseSong();
                songManager.playSong(selectedSong, true);

                changePlayButton(false);
                processMediaUpdates();
            }

        }
    }
    
    /**
     * Plays the selected song from the currently active view table.
     */
    @FXML
    public void handlePlay()
    {
        // Making sure the song is never null before trying to play a song.
        if (selectedSong == null)
        {
            tableSongs.selectionModelProperty().get().select(0);
        }

        selectedSong = tableSongs.selectionModelProperty().getValue().getSelectedItem();

        //Play button pressed
        if (!isPlaying)
        {
            songManager.playSong(selectedSong, false);
        }
        else
        {
            songManager.pauseSong();
        }

        changePlayButton(isPlaying);
        processMediaUpdates();
    }
    
    @FXML
    private void handleOnSelectedPlaylist()
    {
        selectedPlaylist = tablePlaylists.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null)
        {
            currentSongsInView.clear();
            if (!selectedPlaylist.getSongList().isEmpty())
            {
                for (Song song : selectedPlaylist.getSongList())
                {
                    if (!currentSongsInView.contains(song))
                    {
                        currentSongsInView.add(song);
                    }
                }
            }
            hasBrowseButtonBeenClicked = false;
            tableSongs.setItems(currentSongsInView);
        }
    }

    @FXML
    private void handleMuteSound()
    {
        Image image;
        if (!isMuted)
        {
            sliderVolumeValue = sliderVolume.getValue();
            image = new Image(getClass().getResourceAsStream("/mytunes/images/mute.png"));
            sliderVolume.setValue(0.0);
            isMuted = true;
        }
        else
        {
            image = new Image(getClass().getResourceAsStream("/mytunes/images/unmute.png"));
            sliderVolume.setValue(sliderVolumeValue);
            isMuted = false;
        }

        imgMute.setImage(image);
    }

    @FXML
    private void handleClearSearch()
    {
        txtSearch.setText("");

    }

    @FXML
    private void handleBrowseOnAction()
    {
        tableSongs.requestFocus();
        tableSongs.setItems(songModel.getSongs());
        hasBrowseButtonBeenClicked = true;

    }

    @FXML
    private void handleDeletePlaylist()
    {
        deletePlaylist();
    }

    @FXML
    private void handleContextPLMenu()
    {
        updatePlaylistMenu(menuAddToPL);
        updatePlaylistMenu(fileAddToPL);
    }

    //Seeks on mouse release on the progress bar
    @FXML
    private void dragDone(MouseEvent event)
    {
        double mousePos = event.getX();
        double width = barMediaTimer.getWidth();
        double diff = 100 / width * mousePos;
        double length = songManager.getSongLength().toSeconds();
        double lenghtDiff = length / 100 * diff;

        songManager.getMediaPlayer().seek(Duration.seconds(lenghtDiff));
    }

    @FXML
    public void macros(KeyEvent key)
    {

        if (key.getCode() == KeyCode.SPACE)
        {
            handlePlay();
        }

        if (key.getCode() == KeyCode.DELETE)
        {
            if (key.getSource() == tablePlaylists)
            {

                deletePlaylist();
            }

            if (key.getSource() == tableSongs)
            {
                deleteSong();
            }
        }

        if (key.isControlDown())
        {
            if (selectedSong != null)
            {
                if (key.getCode() == KeyCode.UP)
                {
                    moveSong(true);
                }
                if (key.getCode() == KeyCode.DOWN)
                {
                    moveSong(false);
                }
            }

            if (key.getSource() == mainPane)
            {
                if (KeyCode.N == key.getCode())
                {
                    addSong();
                }

                if (KeyCode.P == key.getCode())
                {
                    addPlaylist();
                }
            }
        }
    }

    @FXML
    private void handleMoveSongUp()
    {
        moveSong(true);
    }

    @FXML
    private void handleMoveSongDown()
    {
        moveSong(false);
    }

    @FXML
    private void handleShuffle(ActionEvent event)
    {
        isShuffleToggled = !isShuffleToggled;
    }

    @FXML
    private void handleRepeat(ActionEvent event)
    {
        isRepeatToggled = !isRepeatToggled;
    }

    /**
     * Loads the add song view stage.
     */
    private void addSong()
    {
        loadStage("AddSongView.fxml");
    }

    /**
     * Loads the add playlist view stage.
     */
    private void addPlaylist()
    {
        loadStage("AddPlaylistView.fxml");

    }

    /**
     * Loads the rename playlist view stage.
     */
    private void renamePlaylist()
    {
        handleContextPlaylist();
        loadStage("RenamePlaylistView.fxml");

    }

    /**
     * Whenever the txtSearch field receives a new input, the songs view updates
     * and sets it's view to only display songs that matches the search criteria.
     */
    private void searchOnUpdate()
    {
        txtSearch.textProperty().addListener((ObservableValue<? extends String> listener, String oldQuery, String newQuery) ->
        {
            searchedSongs.setAll(searchQuery.search(getCurrentSongs(), newQuery));
            tableSongs.setItems(searchedSongs);
        });
    }
    
    /**
     * Gets the current songs in the songsview.
     * @return Returns an observablelist value representing the songs in the
     * currently viewed viewstage.
     */
    private ObservableList<Song> getCurrentSongs()
    {
        // If we are in the library view.
        if (selectedPlaylist == null || hasBrowseButtonBeenClicked)
        {
            currentSongsInView.setAll(songModel.getSongs());
        }
        else // If we are in a playlist's view.
        {
            currentSongsInView.setAll(selectedPlaylist.getSongList());
        }
        return currentSongsInView;
    }

    /**
     * Whenever the slidervolume value changes, the mediaplayer's volume changes.
     */
    private void processVolumeData()
    {
        sliderVolume.valueProperty().addListener((ObservableValue<? extends Number> listener, Number oldVal, Number newVal) -> 
        {
            songManager.adjustVolume(newVal.doubleValue() / 100);
            Image image = new Image(getClass().getResourceAsStream("/mytunes/images/unmute.png"));
            imgMute.setImage(image);
            isMuted = false;
        });
    }

    /**
     * Loads a new view on top of the main stage.
     * @param viewName The view file to be loaded.
     */
    private void loadStage(String viewName)
    {
        try
        {
            primaryStage = (Stage) tableSongs.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/" + viewName));
            Parent root = loader.load();
            
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root));
            
            newStage.initModality(Modality.WINDOW_MODAL);
            newStage.initOwner(primaryStage);
            
            newStage.show();
        }
        catch (IOException iOException)
        {
            System.out.println(iOException.getMessage());
        }
    }

    public void setPlaylists()
    {
        playlists = playlistModel.getPlaylists();
    }

    private void changePlayButton(boolean playing)
    {
        Image image;
        if (playing)
        {
            image = new Image(getClass().getResourceAsStream("/mytunes/images/play.png"));
            imgPlay.setImage(image);
            isPlaying = false;
        }
        else
        {
            image = new Image(getClass().getResourceAsStream("/mytunes/images/pause.png"));
            imgPlay.setImage(image);
            isPlaying = true;
        }
    }

    private void processMediaUpdates()
    {       
        songManager.getMediaPlayer().currentTimeProperty().addListener((ObservableValue<? extends Duration> listener, Duration oldVal, Duration newVal) -> 
        {
            long minutes = (long) newVal.toMinutes();
            long seconds = (long) (newVal.toSeconds() % 60);
            this.lblTimeElapsed.setText(String.format("%02d:%02d", minutes, seconds));
            
            double timeElapsed = newVal.toMillis() / songManager.getSongLength().toMillis();
            this.barMediaTimer.setProgress(timeElapsed);
            
            if (songManager.getCurrentlyPlayingSong().getDuration().isEmpty() && barMediaTimer.getProgress() >= 0.999
                    || !songManager.getCurrentlyPlayingSong().getDuration().isEmpty() && barMediaTimer.getProgress() >= 1)
            {
                if (isRepeatToggled)
                {
                    prevNextSong(false);
                }
                else
                {
                    prevNextSong(true);
                }
            }
        });

        lblSongPlaying.setText(songManager.getCurrentlyPlayingSong().getTitle());
        lblSongDuration.setText(songManager.getCurrentlyPlayingSong().getDuration());
    }

    private void deleteSong()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete song");
        alert.setHeaderText("Do you want to delete this song?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            if (tableSongs.getItems() == songsLibrary)
            {
                selectedPlaylist.getSongList().remove(selectedSong);
                tableSongs.getItems().remove(selectedSong);

            }
            else
            {

                tableSongs.getItems().remove(selectedSong);
                deleteFromLibrary();
            }
        }
        else
        {
            alert.close();
        }
        tablePlaylists.refresh();
    }

    private void deleteFromLibrary()
    {
        ArrayList<Song> toBeDeleted = new ArrayList<>();
        for (Playlist playlist : playlists)
        {
            for (Song song : playlist.getSongList())
            {
                if (song.getId() == selectedSong.getId())
                {
                    toBeDeleted.add(song);
                }
            }
            if (!toBeDeleted.isEmpty())
            {
                playlist.getSongList().removeAll(toBeDeleted);
            }
        }
    }

    private void handleContextSong()
    {
        songModel.setContextSong(selectedSong);
    }

    private void handleAddSongToPlaylist(Playlist playlist)
    {
        if (!playlist.getSongList().contains(selectedSong))
            playlistManager.addSong(playlist, selectedSong);
        
        tablePlaylists.refresh();
    }

    private void initialLoad()
    {

        try
        {
            songModel.loadSongData();
            playlistModel.loadPlaylistData();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("Generating song and playlist .dat files...");
            songModel.saveSongData();
            playlistModel.savePlaylistData();
        }

    }

    private void deletePlaylist()
    {
        PlaylistModel.getInstance().getPlaylists().remove(selectedPlaylist);
        playlists.remove(selectedPlaylist);
        tablePlaylists.getItems().remove(selectedPlaylist);
    }

    private boolean updatePlaylistMenu(Menu menu)
    {
        List<MenuItem> playlistSubMenu = new ArrayList<>();
        for (Playlist playlist : playlists)
        {
            MenuItem item = new MenuItem(playlist.getTitle());
            item.setOnAction(new EventHandler<ActionEvent>()
            {

                @Override
                public void handle(ActionEvent event)
                {
                    handleAddSongToPlaylist(playlist);
                }
            });
            playlistSubMenu.add(item);
        }
        if (playlistSubMenu.isEmpty())
        {
            MenuItem empty = new MenuItem("<Empty>");
            empty.setDisable(true);
            menu.getItems().set(0, empty);
            return true;
        }
        menu.getItems().setAll(playlistSubMenu);
        return false;
    }

    private void prevNextSong(boolean next)
    {
        TableViewSelectionModel<Song> selectionModel = tableSongs.selectionModelProperty().getValue();
        int selectedSongIndex = selectionModel.getSelectedIndex();
        int tableSongsTotalItems = tableSongs.getItems().size() - 1;

        if (next)
        {
            if (isShuffleToggled)
            {
                selectionModel.clearAndSelect(rand.nextInt(tableSongs.getItems().size()));
            }
            else if (selectedSongIndex == tableSongsTotalItems || selectedSong == null)
            {
                selectionModel.clearAndSelect(0);
            }
            else
            {
                selectionModel.clearAndSelect(selectedSongIndex + 1);
            }
        }
        else if (songManager.getSongTimeElapsed().toMillis() <= 3500.0)
        {
            if (selectedSongIndex == 0 || selectedSong == null)
            {
                selectionModel.clearAndSelect(tableSongsTotalItems);
            }
            else
            {
                selectionModel.clearAndSelect(selectedSongIndex - 1);
            }
        }
        else
        {
            selectionModel.clearAndSelect(selectedSongIndex);
        }
        selectedSong = selectionModel.getSelectedItem();

        songManager.playSong(selectedSong, true);
        songManager.adjustVolume(sliderVolume.getValue() / 100);

        changePlayButton(false);
        processMediaUpdates();

    }

    public void moveSong(boolean up)
    {

        System.out.println(tableSongs.getSelectionModel().getSelectedIndex());
        int currIndex = tableSongs.getSelectionModel().getSelectedIndex();
        int changeIndex = currIndex;
        boolean change = false;
        if (up && currIndex != 0)
        {
            changeIndex = currIndex - 1;
            change = true;
        }
        else if (!up && currIndex != tableSongs.getItems().size() - 1)
        {
            changeIndex = currIndex + 1;
            change = true;
        }

        if (change)
        {
            Song changeSong = tableSongs.getItems().get(changeIndex);
            tableSongs.getItems().set(changeIndex, selectedSong);
            tableSongs.getItems().set(currIndex, changeSong);
            selectedSong = tableSongs.getItems().get(changeIndex);
            if (!hasBrowseButtonBeenClicked)
            {
                selectedPlaylist.getSongList().set(changeIndex, selectedSong);
                selectedPlaylist.getSongList().set(currIndex, changeSong);
            }
        }
    }

    private void handleContextPlaylist()
    {
        playlistModel.setContextPlaylist(selectedPlaylist);
    }

}
