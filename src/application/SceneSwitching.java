package application;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class SceneSwitching {
	
	
	private Stage stage;
	private Scene scene;
	public static Spieler spieler;
	
	public Stage getStage() {
		return stage;
	}
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public Scene getScene() {
		return scene;
	}
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public SceneSwitching(Stage stage, Scene scene) {
		super();
		this.stage = stage;
		this.scene = scene;
	}
	public SceneSwitching() {
		super();
	}
	public Spieler getSpieler() {
		return spieler;
	}
	public void setSpieler(Spieler spieler) {
		spieler = spieler;
	}
}
