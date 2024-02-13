package fr.esgi.motus;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class rejouerController {

    // Method to return to play, presumably called by a button in your FXML
    @FXML
    private void returnToPlay(ActionEvent event) {
        try {
            // Adjust the path if your FXML file is located in a different package
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fr/esgi/motus/SecondaryController.fxml"));
            Parent root = loader.load();
            
            // Get the current stage using the event source
            Stage stage = (Stage)((javafx.scene.Node)event.getSource()).getScene().getWindow();
            
            // Set the scene to the new layout
            stage.setScene(new Scene(root));
            
            // Optional: if you want to set the title of the stage
            stage.setTitle("SecondaryController");
            
            // Show the new scene
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue rejouer.fxml");
        }
    }
    
    // Utility method to show an alert
    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
