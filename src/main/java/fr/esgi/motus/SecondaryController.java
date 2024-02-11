/*
 * package fr.esgi.motus;

import java.io.IOException;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("");
    }
}
*/
package fr.esgi.motus;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class SecondaryController {
	
	@FXML
    private Button LevelOne;
	
	@FXML
    private void PlayLevelOne(ActionEvent event) throws IOException {
        
    	 App.setRoot("GameSixLengthWord");
    }

}
