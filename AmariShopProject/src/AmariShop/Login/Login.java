package AmariShop.Login;

import AmariShop.FXMain;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        new FXMain(primaryStage, "AmariShop Login", "Login/LoginLayout").startActivity();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
