/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package the.snakelette;;

/**
 *
 * @author User
 */

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;


public class MenuC {
    



  private Stage stage;
 private Scene scene;
 private Parent root;
 
 @FXML
 public Button startButton;
 @FXML
 public Button exitButton;
 @FXML
 public Tooltip tooltip;
 
 
 
 public void switchToSnake (ActionEvent event) throws IOException {
     
  root = FXMLLoader.load(getClass().getResource("Snake.fxml"));
  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
  scene = new Scene(root);
  stage.setScene(scene);
  stage.show();
 }
 
 @FXML
private void exitGame(ActionEvent event) {
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.close(); // Close the current stage
}




}
 