package application;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import nodes.PlayerDisplay;
import nodes.PlayerInfo;


public class LoginAndRegisterController extends SceneSwitching{

	
	private double offsetX = 0;
	private double offsetY = 0;
	
    public LoginAndRegisterController() {
		super();
	}

	@FXML
    private Button btLogin;
	
    @FXML
    private ImageView ivLoading;

    @FXML
    private PasswordField tfRegisterPassword;

    @FXML
    private Button btRegister;

    @FXML
    private PasswordField tfLoginPassword;

    @FXML
    private PasswordField tfRegisterPasswordAgain;

    @FXML
    private TextField tfRegisterUsername;

    @FXML
    private TextField tfLoginUsername;

    @FXML
    private Button btBackToLogin;

    @FXML
    private Pane paneLogin;

    @FXML
    private Hyperlink linkToRegister;

    @FXML
    private Pane paneRegister;
    
    @FXML
    private AnchorPane apRoot;

    @FXML
    void loginUser(ActionEvent event) 
    {	
    	this.ivLoading.setVisible(true);
    	event.consume();
    	new Thread(() -> {
    		this.ivLoading.setVisible(true);
	    	if(Datenbank.LoginUser(this.tfLoginUsername.getText(), this.tfLoginPassword.getText()) == LoginStatus.SUCCEED)
	    	{

    			SceneSwitching.spieler = Datenbank.loadUser(this.tfLoginUsername.getText());
	    		Platform.runLater(()-> {
	    		try
	    		{
	    			FXMLLoader lLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
	    			AnchorPane lGrundPane = lLoader.load();
	    			PlayerDisplay lPlayerDisplay = new PlayerDisplay(new ScrollPane());
	    			//Lädt alle User in das "Display"
	    			List<Spieler> lList = Datenbank.loadAllUsers();
	    			for(int i = 0; i < lList.size(); i++)
	    			{
	    				lPlayerDisplay.addPlayer(new PlayerInfo(lList.get(i)));
	    			}
	    			lGrundPane.getChildren().add(lPlayerDisplay);
	    			Scene lScene = new Scene(lGrundPane);
	    			Stage lGrundStage = new Stage();

	    			MainController lController = lLoader.getController();
	    			lController.setScene(lScene);
	    			lController.setStage(lGrundStage);
	    			lController.setSpieler(Datenbank.loadUser(this.tfLoginUsername.getText()));
	    			lController.setUserOfflineOnLogOut();
	    			lGrundStage.setScene(lScene);
	    			lGrundStage.setTitle("Test");
	    			lGrundStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.bmp")));
	    			lGrundStage.setResizable(false);
	    			lGrundStage.initStyle(StageStyle.UNDECORATED);
	    			lGrundStage.show();
	    			this.getStage().close();
	    			
	    			//lControllerLogin.setActualScene(lScene);
	    			//lControllerLogin.setStage(lGrundStage);
	    		} 
	    	
	    		catch(Exception e) 
	    		{
	    			System.out.println("Fehler!\nDie Anwendung konnte nicht geladen werden: ");
	    			e.printStackTrace();
	    		}
	    	});
	    	}
	    	else
	    	{
	    		this.ivLoading.setVisible(false);
	    	}
    	}).start();
    	

    }
    
    
    @FXML
    void toRegister(ActionEvent event) {
    	this.ausblendenLogin();
    	this.einblendenRegister();
    }

    @FXML
    void registerUser(ActionEvent event) {
    	if(Datenbank.RegisterUser(this.tfRegisterUsername.getText(), this.tfRegisterPassword.getText()) == RegisterStatus.SUCCEED)
    	{
    		this.tfLoginPassword.setText(tfRegisterPassword.getText());
    		this.tfLoginUsername.setText(this.tfRegisterUsername.getText());
    		
				this.loginUser(new ActionEvent());
    	}
    }

    @FXML
    void toLogin(ActionEvent event) {
    	this.einblendenLogin();
    	this.ausblendenRegister();
    }
    
    @FXML
    void btCloseKlick(ActionEvent event) {
    	this.setUserOfflineOnLogOut();
    	this.getStage().close();
    }
    public void setUserOfflineOnLogOut()
    {
    	this.getStage().setOnCloseRequest(e -> {Datenbank.setOffline(this.getSpieler().getName());});
    }
    @FXML
    void initialize() {

    	this.paneRegister.setVisible(false);
    	this.paneRegister.setOpacity(0);
    	
    	this.btRegister.setDisable(true);
    	this.btLogin.setDisable(true);
    	
    	//Listeners zur Überprüfung der Eingaben (Registrierung)
    	this.tfRegisterUsername.textProperty().addListener(changeListener -> {checkRegisterFields();});
    	this.tfRegisterPassword.textProperty().addListener(changeListener -> {checkRegisterFields();});
    	this.tfRegisterPasswordAgain.textProperty().addListener(changeListener -> {checkRegisterFields();});
    	
    	//Listeners zur Überprüfung der Eingaben (Login)
    	this.tfLoginPassword.textProperty().addListener(changeListener -> {checkLoginFields();});
    	this.tfLoginUsername.textProperty().addListener(changeListener -> {checkLoginFields();});
    	
    	//Fenster verschiebbar machen
    	apRoot.setOnMousePressed(mouseEvent -> {
    		this.offsetX = this.getStage().getX() - mouseEvent.getScreenX();
    		this.offsetY = this.getStage().getY() - mouseEvent.getScreenY();
    	});
    	apRoot.setOnMouseDragged(mouseEvent -> {

            this.getStage().setX(mouseEvent.getScreenX() + this.offsetX);
            this.getStage().setY(mouseEvent.getScreenY() + this.offsetY);
    	});
    	//Login (Weiter mit Enter)
    	this.tfLoginPassword.setOnAction(e -> {this.loginUser(e);});
    	this.tfLoginUsername.setOnAction(e -> {this.tfLoginPassword.requestFocus();});
    	//Register (Weiter mit Enter)
    	this.tfRegisterUsername.setOnAction(e ->{this.tfRegisterPassword.requestFocus();});
    	this.tfRegisterPassword.setOnAction(e ->{this.tfRegisterPasswordAgain.requestFocus();});
    	this.tfRegisterPasswordAgain.setOnAction(e -> {this.registerUser(e);});
    	this.ivLoading.setVisible(false);
    }
    
    private void checkRegisterFields()
    {
    	if(this.tfRegisterPassword.getText().equals("")|| this.tfRegisterPasswordAgain.getText().equals("") || this.tfRegisterUsername.getText().length() == 0) 
    	{
    		this.btRegister.setDisable(true);
    		return;
    	}
    	if(this.tfRegisterPassword.getText().equals(this.tfRegisterPasswordAgain.getText())) this.btRegister.setDisable(false);
    	else this.btRegister.setDisable(true);
    }
    
    private void checkLoginFields()
    {
    	if(this.tfLoginPassword.getText().length() == 0 || this.tfLoginUsername.getText().length() == 0) this.btLogin.setDisable(true);
    	else this.btLogin.setDisable(false);
    }
    
    public void ausblendenRegister()
    {
    	FadeTransition ausblendenRegisterAnim = new FadeTransition(Duration.millis(500), paneRegister);
    	ausblendenRegisterAnim.setFromValue(1.0);
    	ausblendenRegisterAnim.setToValue(0.0);
    	
    	ausblendenRegisterAnim.play();
    	ausblendenRegisterAnim.statusProperty().addListener(e -> {
    		if(ausblendenRegisterAnim.getStatus() == Animation.Status.STOPPED)
    		{
    			paneRegister.setVisible(false);
    		}
    	});
    }
    public void ausblendenLogin()
    {
    	FadeTransition ausblendenLoginAnim = new FadeTransition(Duration.millis(500), paneLogin);
    	ausblendenLoginAnim.setFromValue(1.0);
    	ausblendenLoginAnim.setToValue(0.0);
    	
    	ausblendenLoginAnim.play();
    	ausblendenLoginAnim.statusProperty().addListener(e -> {
    		if(ausblendenLoginAnim.getStatus() == Animation.Status.STOPPED)
    		{
    			paneLogin.setVisible(false);
    		}
    	});
    }
    public void einblendenLogin()
    {
    	FadeTransition einblendenLoginAnim = new FadeTransition(Duration.millis(500), paneLogin);
    	einblendenLoginAnim.setFromValue(0.0);
    	einblendenLoginAnim.setToValue(1.0);
    	
    	paneLogin.setVisible(true);
    	
    	einblendenLoginAnim.play();
    }
    public void einblendenRegister()
    {
    	FadeTransition einblendenRegisterAnim = new FadeTransition(Duration.millis(500), paneRegister);
    	einblendenRegisterAnim.setFromValue(0.0);
    	einblendenRegisterAnim.setToValue(1.0);
    	
    	paneRegister.setVisible(true);
    	
    	einblendenRegisterAnim.play();
    }
}
