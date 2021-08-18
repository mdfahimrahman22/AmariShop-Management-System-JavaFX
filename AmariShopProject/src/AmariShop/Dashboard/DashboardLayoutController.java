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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardLayoutController implements Initializable {
    @FXML
    private ImageView shopIcon;

    @FXML
    private Label userName;

    @FXML
    private Circle circle;

    @FXML
    private ImageView Logout;

    @FXML
    private ImageView Menu;

    @FXML
    private ImageView MenuClose;

    @FXML
    private AnchorPane slider;

    @FXML
    private HBox dashboardBtn;

    @FXML
    private HBox userProfileBtn;

    @FXML
    private HBox usersBtn;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private TabPane tabPane;

    @FXML
    private Circle circle2;

    @FXML
    private Label profileNameLabel;


    @FXML
    private Label acIdLabel;

    @FXML
    private TextField nameTextField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField emailTextField;


    @FXML
    private TextField contactTextField;


    @FXML
    private TextArea addressTextField;

    @FXML
    private ImageView editBtn;

    @FXML
    private Button updateProfileBtn,addUserBtn;


    @FXML
    private Label copyrightLabel;
    
    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, Integer> tableUserId;

    @FXML
    private TableColumn<User, String> tableUserName;

    @FXML
    private TableColumn<User, String> tableUserBranch;

    @FXML
    private TableColumn<User, String> tableUserEmail;

    @FXML
    private TableColumn<User, String> tableUserContact;

    @FXML
    private TableColumn<User, String> tableUserAddress;
    
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
        setProfileImage();
        setUsersTableData();
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
          
    }
    
    ObservableList<User> userList=FXCollections.observableArrayList();
    
    private void setUsersTableData() {
        tableUserId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableUserBranch.setCellValueFactory(new PropertyValueFactory<>("branchName"));
        tableUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableUserContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        tableUserAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        userList.add(new User(1,"Fahim","fahimpranto002@gmail.com","01833899","Dhaka","Dhanmondi"));
        userList.add(new User(2,"Fahim","fahimpranto002@gmail.com","01833899","Dhaka","Dhanmondi"));
        userList.add(new User(3,"Fahim","fahimpranto002@gmail.com","01833899","Dhaka","Dhanmondi"));
        userList.add(new User(4,"Fahim","fahimpranto002@gmail.com","01833899","Dhaka","Dhanmondi"));
        usersTable.setItems(userList);


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
        dashboardBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(0);
        });
        userProfileBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(1);
        });
        usersBtn.setOnMouseClicked(event -> {
            tabPane.getSelectionModel().select(2);
        });
        addUserBtn.setOnMouseClicked(event -> {
           new FXMain().openAddUser();
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
