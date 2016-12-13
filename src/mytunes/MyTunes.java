/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mytunes;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mytunes.gui.model.PlaylistModel;
import mytunes.gui.model.SongModel;

/**
 *
 * @author Simon Birkedal
 */
public class MyTunes extends Application {
    
    @Override
    public void start(Stage stage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("gui/view/MainView.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setMinWidth(820);
        stage.setMinHeight(600);
        stage.show();
        stage.setOnCloseRequest((final WindowEvent args) ->
        {
            try
            {
                SongModel.getInstance().saveSongData();
                PlaylistModel.getInstance().savePlaylistData();
            }
            catch (Exception ex)
            {
                System.out.println("Data couldn't be saved.");
            }
            System.exit(0);
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    

}
