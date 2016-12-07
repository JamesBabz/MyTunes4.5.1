/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

/**
 * FXML Controller class
 *
 * @author James
 */
public class AddPlaylistViewController implements Initializable {

    @FXML
    private Button closeButton;
    @FXML
    private TextField txtTitle;

    private Playlist playlist;
    private PlaylistModel plModel;
    private ObservableList<Playlist> playLists = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        plModel = PlaylistModel.getInstance();
    }

    @FXML
    private void addPlaylist()
    {
        String name = txtTitle.getText();

        playlist = new Playlist(name);
        plModel.addPlaylist(playlist);
        plModel.updatePlaylistView();
        closeWindow();
    }

    @FXML
    private void closeWindow()
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
