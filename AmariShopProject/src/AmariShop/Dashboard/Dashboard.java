/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.FXMain;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author fuads
 */
public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        new FXMain(primaryStage, "AmariShop Dashboard", "Dashboard/DashboardLayout").startActivity();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
