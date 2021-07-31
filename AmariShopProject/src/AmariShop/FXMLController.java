
package AmariShop;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author fahim
 */
public class FXMLController implements Initializable {

    @FXML
    Label myMessage;
    
    public void changeText(){
        myMessage.setText("Hello world");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
