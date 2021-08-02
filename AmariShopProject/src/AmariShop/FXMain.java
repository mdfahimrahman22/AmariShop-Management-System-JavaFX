/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package AmariShop;

import AmariShop.Login.Login;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author fahim
 */
public class FXMain extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
//        setInterface(primaryStage,"AmariShop Login","Login/LoginInterface.fxml");
        Login login = new Login();
        login.start(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void setInterface(Stage primaryStage, String title, String route) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(route));
        Scene scene = new Scene(root);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
