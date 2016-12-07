package mytunes.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import mytunes.bll.SongManager;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

/**
 * See ya later
 *
 * @author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */
public class MainViewController implements Initializable {

    private SongManager songManager;
    private PlaylistManager playlistManager;
    private ObservableList<Song> songsLibrary;
    private ObservableList<Song> currentSongsInView;
    private ObservableList<Playlist> playlists;
    private SongModel songModel;
    private Song selectedSong;
    private boolean isPlaying;
    private boolean isMuted;
    private double sliderVolumeValue;
    private PlaylistModel playlistModel;
    private Playlist selectedPlaylist;
    private boolean hasBrowseButtonBeenClicked;

    private Stage primaryStage;
    private Stage addSongStage;
    private Stage addPlaylistStage;

    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button btnPlay;
    @FXML
    private Button btnPrev;
    @FXML
    private Button btnNext;
    @FXML
    private ImageView imgMute;
    @FXML
    private ImageView imgPlay;
    @FXML
    private ImageView imgPrev;
    @FXML
    private ImageView imgNext;
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
    private MenuBar menuBar;
    @FXML
    private ContextMenu contextSong;
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
    private Hyperlink hlinkBrowse;
    @FXML
    private Label lblClearSearch;
    @FXML
    private Menu menuAddToPL;

    /**
     * The default contructor for this class.
     */
    public MainViewController()
    {
        this.songsLibrary = FXCollections.observableArrayList();
        this.currentSongsInView = FXCollections.observableArrayList();
        this.playlists = FXCollections.observableArrayList();
    }

    /**
     * Loads the addSongView.
     */
    @FXML
    public void handleAddSongButton()
    {
        addSong();
    }

    private void addSong()
    {
        try
        {
            loadStage("AddSongView");
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Simon sutter!");
        }
    }

    /**
     * Loads the playlistView.
     */
    @FXML
    private void handleAddPlaylistButton()
    {
        addPlaylist();
    }

    private void addPlaylist()
    {
        try
        {
            loadStage("AddPlaylistView");
        }
        catch (IOException ex)
        {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Simon sutter!");
        }
    }

    /**
     * Handles the selected element whenever a mouse event occours.
     *
     * @param event The mouse event to listen for.
     */
    @FXML
    public void handleOnMousePressed(MouseEvent event)
    {
        selectedSong = tableSongs.selectionModelProperty().getValue().getSelectedItem();
        if (event.isPrimaryButtonDown() && event.getClickCount() == 2)
        {
            if (selectedSong != null)
            {
                songManager.pauseSong();
                songManager.playSong(selectedSong, true);

                changePlayButton(false);
                processMediaInfo();
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
        processMediaInfo();
    }

    /**
     * Initializes the default settings.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {

        tester();

        songManager = new SongManager();
        playlistManager = new PlaylistManager();
        songModel = SongModel.getInstance();
        playlistModel = PlaylistModel.getInstance();
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        colPlaylist.setCellValueFactory(new PropertyValueFactory<>("title"));
        tableSongs.setItems(songModel.getSongs());
        currentSongsInView = songsLibrary;
        setPlaylists();
        tablePlaylists.setItems(playlists);
        initialLoad();
        isPlaying = false;
        processVolumeData();
        searchOnUpdate();
    }

    private void searchOnUpdate()
    {
        txtSearch.textProperty().addListener((listener, oldVal, newVal)
                -> 
                {
                    ObservableList<Song> searchedSongs = FXCollections.observableArrayList();
                    searchedSongs.clear();
                    ObservableList<Song> allSongsInCurrentView = currentSongsInView;
                    if (selectedPlaylist == null || hasBrowseButtonBeenClicked)
                    {
                        allSongsInCurrentView.setAll(songModel.getSongs());
                    }
                    else
                    {
                        allSongsInCurrentView.setAll(selectedPlaylist.getSongList());
                    }

                    for (Song song : allSongsInCurrentView)
                    {
                        if (song.getTitle().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                                || song.getArtist().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                                || song.getGenre().trim().toLowerCase().contains(newVal.trim().toLowerCase())
                                || String.valueOf(song.getRating()).trim().toLowerCase().contains(newVal.trim().toLowerCase())
                                && !searchedSongs.contains(song))
                        {
                            searchedSongs.add(song);
                        }
                    }

                    tableSongs.setItems(searchedSongs);
        });
    }

    private void processVolumeData()
    {
        sliderVolume.valueProperty().addListener(listener
                -> 
                {
                    songManager.getMediaPlayer().setVolume(sliderVolume.getValue() / 100);
                    Image image = new Image(getClass().getResourceAsStream("/mytunes/images/unmute.png"));
                    imgMute.setImage(image);
                    isMuted = false;
        });
    }

    /**
     * Loads a new view on top of the main stage.
     *
     * @param viewName The view file to be loaded.
     * @throws IOException
     */
    private void loadStage(String viewName) throws IOException
    {
        primaryStage = (Stage) tableSongs.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/" + viewName + ".fxml"));
        Parent root = loader.load();

        Stage newStage = new Stage();
        newStage.setScene(new Scene(root));

        newStage.initModality(Modality.WINDOW_MODAL);
        newStage.initOwner(primaryStage);

        newStage.show();
    }

    /**
     *
     * @return
     */
    public TableView<Song> getTableSongs()
    {
        return tableSongs;
    }

    public void setSongs()
    {
        songsLibrary.addAll(songModel.getSongs());
    }

    public ObservableList<Playlist> getPlaylists()
    {
        return playlists;
    }

    public TableView<Playlist> getTablePlaylists()
    {
        return tablePlaylists;
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

    private void processMediaInfo()
    {
        try
        {
            songManager.getMediaPlayer().currentTimeProperty().addListener((listener, oldVal, newVal)
                    -> 
                    {
                        long minutes = (long) newVal.toMinutes();
                        long seconds = (long) (newVal.toSeconds() % 60);
                        this.lblTimeElapsed.setText(String.format("%02d:%02d", minutes, seconds));

                        double timeElapsed = newVal.toMillis() / songManager.getSongLength().toMillis();
                        this.barMediaTimer.setProgress(timeElapsed);
                        if (newVal.toMillis() >= songManager.getSongLength().toMillis())
                        {
                            nextSongInList();
                        }
            });

            if (!isPlaying)
            {
                lblSongPlaying.setText(songManager.getCurrentlyPlayingSong().getTitle());
            }
            else
            {
                lblSongPlaying.setText(songManager.getCurrentlyPlayingSong().getTitle());
            }

            lblSongDuration.setText(songManager.getCurrentlyPlayingSong().getDuration());
        }
        finally
        {

        }
    }

    @FXML
    public void handleNextSong()
    {
        nextSongInList();
    }

    @FXML
    public void handlePreviousSong()
    {
        TableViewSelectionModel<Song> selectionModel = tableSongs.selectionModelProperty().getValue();
        int selectedSongIndex = selectionModel.getSelectedIndex();
        int tableSongsTotalItems = tableSongs.getItems().size() - 1;

        if (songManager.getSongTimeElapsed().toMillis() <= 3500.0)
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

        songManager.pauseSong();
        songManager.playSong(selectedSong, true);
        songManager.getMediaPlayer().setVolume(sliderVolume.getValue() / 100);

        changePlayButton(false);
        processMediaInfo();
    }

    @FXML
    private void handleEditSong(ActionEvent event) throws IOException
    {
        handleContextSong();
        Stage primStage = (Stage) tableSongs.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mytunes/gui/view/EditSongView.fxml"));
        Parent root = loader.load();

        EditSongViewController editSongViewController = loader.getController();

        Stage editSongViewStage = new Stage();
        editSongViewStage.setScene(new Scene(root));

        editSongViewStage.initModality(Modality.WINDOW_MODAL);
        editSongViewStage.initOwner(primStage);

        editSongViewStage.show();

    }

    @FXML
    private void handleDeleteSong(ActionEvent event)
    {
        deleteSong();

    }

    private void deleteSong()
    {
        songModel.getSongs().remove(selectedSong);
        tableSongs.getItems().remove(selectedSong);

    }

    private void handleContextSong()
    {

        songModel.setContextSong(selectedSong);

    }

    private void handleAddSongToPlaylist(Playlist playlist)
    {
//        selectedPlaylist = tablePlaylists.getSelectionModel().getSelectedItem();
//        if (selectedPlaylist != null)
//        {
//            playlistManager.addSong(selectedPlaylist, selectedSong);
//        }

        playlistManager.addSong(playlist, selectedSong);

    }

    @FXML
    private void handleOnSelectedPlaylist(MouseEvent event)
    {
        selectedPlaylist = tablePlaylists.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null)
        {
            currentSongsInView.clear();

            for (Song song : selectedPlaylist.getSongList())
            {
                if (!currentSongsInView.contains(song))
                {
                    currentSongsInView.add(song);
                }
                if (!songModel.getSongs().contains(song))
                {
                    currentSongsInView.remove(song);
                }
            }
        }
        tableSongs.setItems(currentSongsInView);
        hasBrowseButtonBeenClicked = false;
    }

    private void initialLoad()
    {
        try
        {
            songModel.loadSongData();
            playlistModel.loadPlaylistData();
        }
        catch (Exception e)
        {
            //Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableSongs.setItems(songModel.getSongs());
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
    private void handleClearSearch(MouseEvent event)
    {
        txtSearch.setText("");

    }

    @FXML
    private void handleSetTimeElapsed(MouseEvent event)
    {

    }

    @FXML
    private void handleBrowseOnAction(ActionEvent event)
    {
        tableSongs.requestFocus();
        tableSongs.setItems(songModel.getSongs());
        hasBrowseButtonBeenClicked = true;

    }

    @FXML
    private void DeletePlaylist(ActionEvent event)
    {

        deletePlaylist();

    }

    private void deletePlaylist()
    {
        PlaylistModel.getInstance().getPlaylists().remove(selectedPlaylist);
        tablePlaylists.getItems().remove(selectedPlaylist);
    }

    @FXML
    private void updatePLMenu()
    {
        List<MenuItem> playlistSubMenu = new ArrayList<>();
        for (Playlist playlist : playlists)
        {
            MenuItem item = new MenuItem(playlist.getTitle());
            item.setOnAction(new EventHandler<ActionEvent>() {

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
            menuAddToPL.getItems().set(0, empty);
            return;
        }
        menuAddToPL.getItems().setAll(playlistSubMenu);
    }

    public void tester()
    {
        barMediaTimer.setDisable(false);
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
        
        if(key.getCode() == KeyCode.SPACE)
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
            

    private void nextSongInList()
    {

        TableViewSelectionModel<Song> selectionModel = tableSongs.selectionModelProperty().getValue();
        int selectedSongIndex = selectionModel.getSelectedIndex();
        int tableSongsTotalItems = tableSongs.getItems().size() - 1;

        if (selectedSongIndex == tableSongsTotalItems || selectedSong == null)
        {
            selectionModel.clearAndSelect(0);
        }
        else
        {
            selectionModel.clearAndSelect(selectedSongIndex + 1);
        }

        selectedSong = selectionModel.getSelectedItem();

        songManager.pauseSong();
        songManager.playSong(selectedSong, true);
        songManager.getMediaPlayer().setVolume(sliderVolume.getValue() / 100);

        changePlayButton(false);
        processMediaInfo();

    }

}
