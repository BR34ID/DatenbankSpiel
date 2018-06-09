package application; 

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SpielerInfoController extends SceneSwitching{
	private double offsetX = 0;
	private double offsetY = 0;
	
	private Spieler spieler;
	public SpielerInfoController() {}
    
	public Spieler getSpieler() {
		return this.spieler;
	}
	public void setSpieler(Spieler spieler)
	{
		this.spieler = spieler;
		this.update();
	}
	@FXML
    private AnchorPane apRoot;

    @FXML
    private BarChart bcWinLoss;

    @FXML
    private Label lbUsername;

    @FXML
    void btCloseKlick(ActionEvent event) {
    	this.setUserOfflineOnLogOut();
    	this.getStage().close();
    }
    @FXML
    void initialize() {
    	//Fenster verschiebbar machen
    	apRoot.setOnMousePressed(mouseEvent -> {
    		this.offsetX = this.getStage().getX() - mouseEvent.getScreenX();
    		this.offsetY = this.getStage().getY() - mouseEvent.getScreenY();
    	});
    	apRoot.setOnMouseDragged(mouseEvent -> {

            this.getStage().setX(mouseEvent.getScreenX() + this.offsetX);
            this.getStage().setY(mouseEvent.getScreenY() + this.offsetY);
    	});       
}
    void update()
    {
    	this.lbUsername.setText(this.spieler.getName() + " (" + this.spieler.getId() + ")");
    	this.bcWinLoss.getData().clear();
       	XYChart.Series SeriesWins= new XYChart.Series();
       	SeriesWins.setName("Wins");
       	SeriesWins.getData().add(new XYChart.Data("Wins", this.spieler.getGewonnen()));
       	XYChart.Series SeriesLosses = new XYChart.Series();
       	SeriesLosses.setName("Losses");
       	SeriesLosses.getData().add(new XYChart.Data("Wins", this.spieler.getVerloren()));
    	this.bcWinLoss.getData().addAll(SeriesWins, SeriesLosses);
    }
    public void setUserOfflineOnLogOut()
    {
    	this.getStage().setOnCloseRequest(e -> {Datenbank.setOffline(this.getSpieler().getName());});
    }
}
