
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;

public class Main extends Application
{
	@Override
	public void start(Stage pGrundStage)
	{
		try
		{
			FXMLLoader lLoader = new FXMLLoader(getClass().getResource("LoginAndRegister.fxml"));
			AnchorPane lGrundPane = lLoader.load();

			Scene lScene = new Scene(lGrundPane);
			
			pGrundStage.setScene(lScene);
			pGrundStage.setTitle("Test");
			pGrundStage.getIcons().add(new Image(getClass().getResourceAsStream("icon.bmp")));
			pGrundStage.setResizable(false);
			pGrundStage.initStyle(StageStyle.UNDECORATED);
			pGrundStage.show();
			
			LoginAndRegisterController lController = lLoader.getController();
			lController.setScene(lScene);
			lController.setStage(pGrundStage);
			
			//lControllerLogin.setActualScene(lScene);
			//lControllerLogin.setStage(pGrundStage);
		} 
	
		catch(Exception e) 
		{
			System.out.println("Fehler!\nDie Anwendung konnte nicht geladen werden: ");
			e.printStackTrace();
		}
	
	}


	
	public static void main(String[] args)
	{
		launch(args);
	}
}
