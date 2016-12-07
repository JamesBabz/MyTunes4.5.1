/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Song;
import mytunes.gui.model.SongModel;

/**
 * FXML Controller class
 *
 * @author Thomas
 */
public class EditSongViewController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtGenre;
    @FXML
    private TextField txtRating;

    @FXML
    private Button btnCancel;

    SongModel songModel;

    Song contextSong;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        songModel = SongModel.getInstance();
        
        contextSong = songModel.getContextSong();
        txtTitle.setText(contextSong.getTitle());
        txtArtist.setText(contextSong.getArtist());
        txtGenre.setText(contextSong.getGenre());
        txtRating.setText(contextSong.getRating() + "");

    }

    @FXML
    private void saveChanges() 
    {
        String title = txtTitle.getText();
        String artist = txtArtist.getText();
        String genre = txtGenre.getText();
        String rating = txtRating.getText();
        int rate = Integer.parseInt(rating);

        contextSong.setTitle(title);
        contextSong.setArtist(artist);
        contextSong.setGenre(genre);
        contextSong.setRating(rate);
        songModel.editSong(contextSong);
        
      closeWindow();
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
}
