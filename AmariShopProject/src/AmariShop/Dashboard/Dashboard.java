/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.FXMain;
import AmariShop.Models.User;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

//This class is only for tesing purpose
public class Dashboard extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMain fxmain=new FXMain();
//        User user=new User(1,1,"admin@gmail.com","Fahim Rahman","1234","address","01729273");
        fxmain.startActivity(primaryStage,"AmariShop Dashboard", "Dashboard/DashboardLayout");
    }

    public static void main(String[] args) {
        launch(args);
    }

}
