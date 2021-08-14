package AmariShop.Login;

import AmariShop.Database.ConnectDB;
import AmariShop.FXMain;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

public class Login extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMain fxmain=new FXMain(primaryStage);
        fxmain.startLoginActivity();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
