package AmariShop;

import AmariShop.Dashboard.DashboardLayoutController;
import AmariShop.Database.ConnectDB;
import AmariShop.Models.User;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Connection;
import javafx.geometry.Pos;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class FXMain {

    private Stage stage;
    private Parent root;
  
    public FXMain() {
        
    }

    public FXMain(Stage stage) {
        this.stage = stage; 
    }
   

    public void startLoginActivity() {
        try {
            root = FXMLLoader.load(getClass().getResource("Login/LoginLayout.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("AmariShop Login");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOException occured. \nEx:" + ex);
        } catch (Exception ex) {
            System.out.println("Can't load layout.");
        }
    }
    
    public void openDashboard(ActionEvent event,User user,Connection connection){
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("Dashboard/DashboardLayout.fxml"));
            Parent root = loader.load();
            DashboardLayoutController dashboardController=loader.getController();
            dashboardController.setUserInfo(user);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
            ((Node) event.getSource()).getScene().getWindow().hide();
        } catch (IOException ioException) {
            System.out.println("IOException occured in Dashboard.\nEx:"+ioException);
        } catch (Exception ex) {
            System.out.println("Unknown exception occured in Dashboard.");
        }
        
    }
    public static void showNotification(String title,String text,String type){
        //new ImageView(Notifications.class.getResource("/org/controlsfx/dialog/dialog-warning.png").toExternalForm())
        Notifications notificationBuilder=Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BASELINE_CENTER)
                .onAction(e->{
                
                }
                );
        switch(type){
            case "warning":
                Toolkit.getDefaultToolkit().beep();
                notificationBuilder.showWarning();
                break;
            case "error":
                Toolkit.getDefaultToolkit().beep();
                notificationBuilder.showError();
                break;
            case "info":
                notificationBuilder.showInformation();
                break;
            case "confirm":
                notificationBuilder.showConfirm();
                break;
            default:
                notificationBuilder.showInformation();
                break;
        }
//        Toolkit.getDefaultToolkit().beep();

    }
   
    
    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    //Testing purpose method
    public void changeActivity(ActionEvent event,String pageTitle,String pageLayout) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        openActivity(pageTitle,pageLayout);
    }
    //Testing purpose method
    public void startActivity(Stage stage,String pageTitle,String pageLayout) {
        try {
            root = FXMLLoader.load(getClass().getResource(pageLayout + ".fxml"));
            Scene scene = new Scene(root);
            stage.setTitle(pageTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOException occured. \nEx:" + ex);
        } catch (Exception ex) {
            System.out.println("Can't load layout.");
        }
    }
    //Testing purpose method
    public void openActivity(String pageTitle,String pageLayout) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource(pageLayout + ".fxml").openStream());
            Scene scene = new Scene(root);
            stage.setTitle(pageTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ioException) {
            System.out.println("IOException occured.");
        } catch (Exception ex) {
            System.out.println("Unknown exception occured.");
        }
    }

    
}
