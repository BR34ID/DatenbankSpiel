package application;

public class Spielzug {
	private int zugid;
	private int spielid;
	private Spieler spieler;
	private int x;
	private int y;
	private SpielzugTyp art;
	
	public Spielzug(int zugid, int spielid, int spielerid, int x, int y, int art) {
		super();
		this.zugid = zugid;
		this.spielid = spielid;
		this.spieler = Datenbank.loadUser(spielerid);
		this.x = x;
		this.y = y;
		this.art = SpielzugTyp.values()[art];
	}

	public Spielzug(int zugid, int spielid, Spieler spieler, int x, int y, SpielzugTyp art) {
		super();
		this.zugid = zugid;
		this.spielid = spielid;
		this.spieler = spieler;
		this.x = x;
		this.y = y;
		this.art = art;
	}

	public int getZugid() {
		return zugid;
	}

	public void setZugid(int zugid) {
		this.zugid = zugid;
	}

	public int getSpielid() {
		return spielid;
	}
	
	public void setSpielid(int spielid) {
		this.spielid = spielid;
	}
	public Spieler getSpieler() {
		return this.spieler;
	}
	public void setSpieler(Spieler spielerid) {
		this.spieler = spielerid;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public SpielzugTyp getArt() {
		return art;
	}
	public void setArt(SpielzugTyp art) {
		this.art = art;
	}


}
