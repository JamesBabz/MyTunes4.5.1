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
import mytunes.be.Playlist;
import mytunes.gui.model.PlaylistModel;

/**
 * FXML Controller class
 *
 * @@author Stephan Fuhlendorff, Jacob Enemark, Thomas Hansen, Simon Birkedal
 */


public class RenamePlaylistViewController implements Initializable 
{

    private PlaylistModel playlistModel;
    private Playlist contextPlaylist;
    
    @FXML
    private TextField txtTitle;
    @FXML
    private Button closeButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playlistModel =  PlaylistModel.getInstance();
        contextPlaylist = playlistModel.getContextPlaylist();
        txtTitle.setText(contextPlaylist.getTitle());
    }    

    @FXML
    private void renamePlaylist() {
       
         String title = txtTitle.getText();
         
         contextPlaylist.setTitle(title);
         
         playlistModel.renamePlaylist(contextPlaylist);
         closeWindow();
    }

    @FXML
    private void closeWindow() {
        
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
    
}
