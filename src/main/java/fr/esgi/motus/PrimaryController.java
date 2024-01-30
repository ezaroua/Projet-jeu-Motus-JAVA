package fr.esgi.motus;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;






public class PrimaryController {

    @FXML
    private Button boutonJouer;

    @FXML
    private void retrunToPlay(ActionEvent event) throws IOException {
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        //Parent root = loader.load();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.show();
    	 App.setRoot("secondary");
    }
}