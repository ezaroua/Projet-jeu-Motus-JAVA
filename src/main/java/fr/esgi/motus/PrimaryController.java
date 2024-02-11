package fr.esgi.motus;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PrimaryController {



    @FXML
    private void retrunToPlay(ActionEvent event) throws IOException{
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        //Parent root = loader.load();
        //Stage stage = new Stage();
        //stage.setScene(new Scene(root));
        //stage.show();
    	 App.setRoot("secondary");
    }
}