package mytunes.gui.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import mytunes.be.Song;
import mytunes.bll.TimeFormat;
import mytunes.dal.ReadSongProperty;
import mytunes.gui.model.SongModel;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddSongViewController implements Initializable
{
    private ReadSongProperty rsp;
    private SongModel songModel;
    private Song song;
    ObservableList<Song> songs = FXCollections.observableArrayList();

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtPath;
    @FXML
    private Button closeButton;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        songModel = SongModel.getInstance();

        txtPath.textProperty().addListener((observable, oldValue, newValue)
                -> 
                {
                    if (newValue.isEmpty())
                    {
                        txtTitle.setDisable(true);
                        txtArtist.setDisable(true);
                        txtGenre.setDisable(true);
                    }
                    else
                    {
                        txtTitle.setDisable(false);
                        txtArtist.setDisable(false);
                        txtGenre.setDisable(false);
                    }
        });
    }

    @FXML
    private void closeWindow()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void addSong()
    {
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String genre = txtGenre.getText();
        String duration = TimeFormat.formatDouble(rsp.getDuration());
        String path = txtPath.getText();
        song = new Song(title, artist, genre, duration, 0, path);
        songModel.addSong(song);
        closeWindow();

    }

    @FXML
    private void browseForFile()
    {

        FileChooser fileChooser = new FileChooser();
        Window win = root.getScene().getWindow();
        File file = fileChooser.showOpenDialog(win);
        txtPath.setText(file.getPath());

        prepopulateFields(file);

    }

    /**
     * When a song is loaded, information about its title, genre, artist, etc
     * is automatically placed in the txtFields.
     * @param file The file to read the data from.
     */
    private void prepopulateFields(File file)
    {
        try
        {
            rsp = new ReadSongProperty(file.getPath());
            txtTitle.setText(rsp.getTitle());
            txtArtist.setText(rsp.getArtist());
            txtGenre.setText(rsp.getGenre());
            txtDuration.setText(TimeFormat.formatDouble(rsp.getDuration()));
        }
        catch (CannotReadException | IOException | TagException | ReadOnlyFileException | InvalidAudioFrameException e)
        {
            Logger.getLogger(AddSongViewController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
