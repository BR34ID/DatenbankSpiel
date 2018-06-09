package nodes;

import application.Datenbank;
import application.Spiel;
import application.SpielerTyp;
import javafx.scene.Node;
import javafx.scene.control.Button;

public class PlayButton extends Button{
	private int spielid;
	private int spielerid;
	
	public PlayButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayButton(String arg0, Node arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public PlayButton(String arg0, int spielid, int spielerid) {
		super(arg0);
		this.spielid = spielid;
		this.spielerid = spielerid;
		this.setOnAction(e -> {
			Spiel lSpiel = Datenbank.loadSpiel(spielid);
			lSpiel.laden(spielerid, SpielerTyp.SPIELER_OWN);
		});
		Spiel lSpiel = Datenbank.loadSpiel(spielid);
		this.setDisable(!lSpiel.isSpielbar());
		// TODO Auto-generated constructor stub
	}
}
