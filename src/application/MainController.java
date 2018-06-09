package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import nodes.PlayButton;

public class MainController extends SceneSwitching{

	private double offsetX = 0;
	private double offsetY = 0;
    @FXML
    private AnchorPane apRoot;
    
    @FXML
    private TableColumn<Spiel, Integer> tcSpielid;

    @FXML
    private TableColumn<Spiel, Spieler> tcSpielerName1;
    
    @FXML
    private TableColumn<Spiel, Spieler> tcSpielerName2;

    @FXML
    private TableColumn<Spiel, Integer> tcSpielen;

    @FXML
    private TableView<Spiel> tvOffeneSpiele;
    
    @FXML
    void btCloseKlick(ActionEvent event) {
    	Datenbank.setOffline(this.getSpieler().getName());
    	this.getStage().close();
    }
    @FXML
    void initialize()
    {
    	//Fenster verschiebbar machen
    	apRoot.setOnMousePressed(mouseEvent -> {
    		this.offsetX = this.getStage().getX() - mouseEvent.getScreenX();
    		this.offsetY = this.getStage().getY() - mouseEvent.getScreenY();
    	});
    	apRoot.setOnMouseDragged(mouseEvent -> {

            this.getStage().setX(mouseEvent.getScreenX() + this.offsetX);
            this.getStage().setY(mouseEvent.getScreenY() + this.offsetY);
    	});

    	tvOffeneSpiele.setItems(Datenbank.loadUnfinishedGames(1));
    	tcSpielid.setCellValueFactory(new PropertyValueFactory<Spiel,Integer>("id"));

    	tcSpielerName1.setCellValueFactory(new PropertyValueFactory<Spiel,Spieler>("Spieler1"));
    	tcSpielerName1.setCellFactory(new Callback<TableColumn<Spiel, Spieler>, TableCell<Spiel, Spieler>>(){
			@Override
			public TableCell<Spiel, Spieler> call(TableColumn<Spiel, Spieler> arg0) {
				// TODO Auto-generated method stub
				return new Spieler1NamenZelle();
			}
    		
    	});	
    	tcSpielerName2.setCellValueFactory(new PropertyValueFactory<Spiel,Spieler>("Spieler2"));
    	tcSpielerName2.setCellFactory(new Callback<TableColumn<Spiel, Spieler>, TableCell<Spiel, Spieler>>(){

			@Override
			public TableCell<Spiel, Spieler> call(TableColumn<Spiel, Spieler> arg0) {
				// TODO Auto-generated method stub
				return new Spieler2NamenZelle();
			}
    		
    	});
    	tcSpielen.setCellValueFactory(new PropertyValueFactory<Spiel, Integer>("id"));
    	tcSpielen.setCellFactory(new Callback<TableColumn<Spiel, Integer>, TableCell<Spiel, Integer>>(){

			@Override
			public TableCell<Spiel, Integer> call(TableColumn<Spiel, Integer> arg0) {
				System.out.println(SceneSwitching.spieler.getId());
				return new PlayButtonZelle(SceneSwitching.spieler.getId());
			}
    	});
    	tcSpielen.setStyle("-fx-aligment: CENTER-RIGHT");
    	tvOffeneSpiele.refresh();
    }
    public void setUserOfflineOnLogOut()
    {
    	this.getStage().setOnCloseRequest(e -> {Datenbank.setOffline(this.getSpieler().getName());});
    }
    private static class Spieler1NamenZelle extends TableCell<Spiel, Spieler>
    {
    	String spielername;
    	public Spieler1NamenZelle(){
    		this.spielername = "";
    	}
    	@Override
    	protected void updateItem(Spieler spieler, boolean empty)
    	{
    		if(!empty && spieler != null)
    		{
    			setText(spieler.getName());
    		}
    		else
    			setText("");
    	}
    }   
    private static class Spieler2NamenZelle extends TableCell<Spiel, Spieler>
    {
    	String spielername;
    	public Spieler2NamenZelle(){
    		this.spielername = "";
    	}
    	@Override
    	protected void updateItem(Spieler spieler, boolean empty)
    	{
    		if(!empty && spieler != null && spieler.getName() != null)
    		{
    			setText(spieler.getName());
    		}
    		else
    			setText("");
    	}
    }   
    private static class PlayButtonZelle extends TableCell<Spiel, Integer>
    {
    	private HBox hBox;
    	private int spielerid;
    	public PlayButtonZelle(int spielerid){
    		this.hBox = new HBox();
    		this.spielerid = spielerid;
    	}
    	@Override
    	protected void updateItem(Integer spiel, boolean empty)
    	{
    		if(!empty && spiel != null)
    		{
    			this.hBox.getChildren().add(new PlayButton("Play", spiel, this.spielerid));
    			setGraphic(hBox);
    			this.setAlignment(Pos.CENTER);
    		}
    		else
    			setText("");
    	}
    }
}