package application;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import nodes.PlayerDisplay;
import nodes.PlayerInfo;

public class GameController extends SceneSwitching{

    @FXML
    private TableColumn<?, ?> tcSpieler;

    @FXML
    private HBox hbGameRow;

    @FXML
    private TableView<?> ivZug;

    @FXML
    private TableColumn<?, ?> tcZugArt;

    @FXML
    private AnchorPane apRoot;

    @FXML
    private VBox vbGameContainer;

    @FXML
    void btCloseKlick(ActionEvent event) {
    	this.setUserOfflineOnLogOut();
    	this.getStage().close();
    }

    public void setUserOfflineOnLogOut()
    {
    	this.getStage().setOnCloseRequest(e -> {Datenbank.setOffline(this.getSpieler().getName());});
    }
    
    public void loadGame(Spiel spiel)
    {
    	ArrayList<Spielzug> spielzüge = (ArrayList<Spielzug>) Datenbank.loadGame(spiel.getId());
    }
    @FXML
    void initialize() {

    }

	public static void spielLaden()
	{
		try
		{
			FXMLLoader lLoader = new FXMLLoader(Main.class.getResource("Game.fxml"));
			AnchorPane lGrundPane = lLoader.load();
			
			Scene lScene = new Scene(lGrundPane);
			Stage lGrundStage = new Stage();

			GameController lController = lLoader.getController();
			lController.setScene(lScene);
			lController.setStage(lGrundStage);
			lController.setUserOfflineOnLogOut();
			lGrundStage.setScene(lScene);
			lGrundStage.setTitle("Test");
			lGrundStage.getIcons().add(new Image(Main.class.getClass().getResourceAsStream("icon.bmp")));
			lGrundStage.setResizable(false);
			lGrundStage.initStyle(StageStyle.UNDECORATED);
			lGrundStage.show();
		} 
	
		catch(Exception e) 
		{
			System.out.println("Fehler!\nDie Anwendung konnte nicht geladen werden: ");
			e.printStackTrace();
		}
	}
}
