package AmariShop.Login;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        setInterface(primaryStage, "AmariShop Login", "LoginInterface.fxml");
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
