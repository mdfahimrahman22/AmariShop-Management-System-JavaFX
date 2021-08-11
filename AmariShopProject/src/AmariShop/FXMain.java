package AmariShop;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXMain {

    private Stage stage;
    private String pageTitle;
    private String pageLayout;
    private Parent root;

    public FXMain(String pageTitle, String pageLayout) {
        this.pageTitle = pageTitle;
        this.pageLayout = pageLayout;
    }

    public FXMain(Stage stage, String pageTitle, String pageLayout) {
        this.stage = stage;
        this.pageTitle = pageTitle;
        this.pageLayout = pageLayout;
    }

    public void startActivity() {
        try {
            root = FXMLLoader.load(getClass().getResource(pageLayout + ".fxml"));
            Scene scene = new Scene(root);
//            scene.getStylesheets().add(pageLayout + ".css");
            stage.setTitle(pageTitle);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("IOException occured. \nEx:" + ex);
//            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            System.out.println("Can't load layout.");
        }
    }

    public void changeActivity(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
        openActivity();
    }

    public void openActivity() {
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
//            Logger.getLogger(FXMain.class.getName()).log(Level.SEVERE, null, ioException);
        } catch (Exception ex) {
            System.out.println("Unknown exception occured.");
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getPageLayout() {
        return pageLayout;
    }

    public void setPageLayout(String pageLayout) {
        this.pageLayout = pageLayout;
    }

}
