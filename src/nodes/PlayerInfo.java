package nodes;

import application.Main;
import application.MainController;
import application.Spieler;
import application.SpielerInfoController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PlayerInfo extends Pane {

	private Spieler spieler;
	private ImageView onlineIcon;
	private Hyperlink nameHyperlink;
	
	public PlayerInfo(Spieler spieler) {
		this.spieler = spieler;
		super.setPrefSize(198, 27);
		this.setPrefSize(198, 27);
		
		//Icon (Online/Offline)
		String bildUrl = "";
		if(this.spieler.isOnline()) bildUrl = "nodes/online_0.png";
		else bildUrl = "nodes/online_1.png";
		
		this.onlineIcon = new ImageView(bildUrl);
		this.onlineIcon.setFitHeight(16);
		this.onlineIcon.setLayoutX(6);
		this.onlineIcon.setLayoutY(6);
		this.getChildren().add(this.onlineIcon);
		
		//Label (Playername)
		this.nameHyperlink = new Hyperlink(this.spieler.getName());
		this.nameHyperlink.setLayoutX(29);
		this.nameHyperlink.setLayoutY(2);
		this.nameHyperlink.setPrefSize(161, 17);
		this.getChildren().add(this.nameHyperlink);
		
		this.nameHyperlink.setOnAction(event -> {
		
    		try
    		{
    			FXMLLoader lLoader = new FXMLLoader(Main.class.getResource("SpielerInfo.fxml"));
    			AnchorPane lGrundPane = lLoader.load();
    			Scene lScene = new Scene(lGrundPane);
    			Stage lGrundStage = new Stage();
    			lGrundStage.setScene(lScene);
    			lGrundStage.setTitle("Test");
    			lGrundStage.getIcons().add(new Image(Main.class.getResourceAsStream("icon.bmp")));
    			lGrundStage.setResizable(false);
    			lGrundStage.initStyle(StageStyle.UNDECORATED);
    			lGrundStage.show();
    			
    			SpielerInfoController lController = lLoader.getController();
    			lController.setScene(lScene);
    			lController.setStage(lGrundStage);
    			lController.setSpieler(this.spieler);
    			
    			//lControllerLogin.setActualScene(lScene);
    			//lControllerLogin.setStage(lGrundStage);
    		} 
    	
    		catch(Exception exc) 
    		{
    			System.out.println("Fehler!\nDie Anwendung konnte nicht geladen werden: ");
    			exc.printStackTrace();
    		}
		
		});
	}

	public PlayerInfo(Node... children) {
		super(children);
		// TODO Auto-generated constructor stub
	}
}
