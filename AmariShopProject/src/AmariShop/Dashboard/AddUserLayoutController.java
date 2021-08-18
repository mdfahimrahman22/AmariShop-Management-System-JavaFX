/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.FXMain;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author fahim
 */
public class AddUserLayoutController implements Initializable {

   @FXML
   private Button closeBtn;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setOnClickListeners();
    }    

    private void setOnClickListeners() {
        closeBtn.setOnMouseClicked(e->{
            FXMain.closePanel(e);
        });
    }
    
}
