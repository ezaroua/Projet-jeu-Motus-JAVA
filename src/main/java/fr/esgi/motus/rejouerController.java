package fr.esgi.motus;

import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class rejouerController {

	
	@FXML
	private Button btnRejouer;

	
	   
	    @FXML
	    private void returnToPlay(ActionEvent event) {
	        try {
	            //App.setRoot("primary");
	            App.setRoot("secondary");
	        } catch (IOException e) {
	            e.printStackTrace(); // Cela va imprimer la trace de pile complète
	        }
	    }
	    
	    
	    @FXML
	    private void ToMenuPrincipal(ActionEvent event) {
	        try {
	            App.setRoot("primary");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    
	    

	


    
   
}
