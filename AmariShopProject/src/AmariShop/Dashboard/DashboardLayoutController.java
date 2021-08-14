/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package AmariShop.Dashboard;

import AmariShop.Database.ConnectDB;
import AmariShop.FXMain;
import AmariShop.Models.User;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import java.sql.Connection;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DashboardLayoutController implements Initializable {
    @FXML
    private ImageView shopIcon;

    @FXML
    private Label userName;

    @FXML
    private Circle circle;
    
    @FXML
    private Circle circle2;

    @FXML
    private ImageView Logout;

    @FXML
    private ImageView Menu;

    @FXML
    private ImageView MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private HBox dashboardMenu,profileMenu;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private TabPane tabPane;

    @FXML
    private Label copyrightLabel;
    
    @FXML
    private Button updateProfileBtn;
    
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField contactTextField;

    @FXML
    private TextArea addressTextField;
    
    @FXML
    private Label acIdLabel;
    
    @FXML
    private Label profileNameLabel;
    
    private User user;
    private Connection connection;

    public DashboardLayoutController(){
    }

    public DashboardLayoutController(User user, Connection connection) {
        this.user = user;
        this.connection = connection;
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCopyrightLabelText();
        setOnClickListeners();      
        setProfileSettingsEditable(false);
    }
    public void setUserInfo(User user){
        setUser(user);
        userName.setText(user.getName());
        acIdLabel.setText("Account ID: "+Integer.toString(user.getId()));
        profileNameLabel.setText(user.getName());
        nameTextField.setText(user.getName());
        emailTextField.setText(user.getEmail());
        contactTextField.setText(user.getContact());
        addressTextField.setText(user.getAddress());
        setProfileImage();  
    }
    private void setProfileSettingsEditable(boolean editable) {
            nameTextField.setEditable(editable);
            emailTextField.setEditable(editable);
            contactTextField.setEditable(editable);
            addressTextField.setEditable(editable);
    }
    public void animateOnWidthChange(final AnchorPane pane, double width) {
        Duration cycleDuration = Duration.millis(350);
        Timeline timeline = new Timeline(
                new KeyFrame(cycleDuration,
                        new KeyValue(pane.maxWidthProperty(), width, Interpolator.EASE_BOTH))
        );
        timeline.play();
        timeline.setOnFinished(event -> {

        });
    }

    private void setOnClickListeners() {
        Menu.setOnMouseClicked(event -> {
            animateOnWidthChange(slider, 237);
            Menu.setVisible(false);
            MenuClose.setVisible(true);
        });

        MenuClose.setOnMouseClicked(event -> {
            animateOnWidthChange(slider, 80);
            Menu.setVisible(true);
            MenuClose.setVisible(false);
        });
        dashboardMenu.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(0);
        });
        profileMenu.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(1);
        });
        updateProfileBtn.setOnMouseClicked(event->{
        
        FXMain.showNotification("Update Successful", "Your profile has been updated successfully", "confirm");
        
        });

    }

    private void setCopyrightLabelText() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        String yearInString = String.valueOf(year);
        copyrightLabel.setText("Copyright \u00a9 " + yearInString + ", AmariShop. All rights are reserved.");
    }

    private void setProfileImage() {
        Image profilePic = new Image("AmariShop/Dashboard/img/face1.jpg");
        circle.setFill(new ImagePattern(profilePic));
        circle2.setFill(new ImagePattern(profilePic));
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
