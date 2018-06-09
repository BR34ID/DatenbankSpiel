package application;

public class Spieler {
	private int id;
	private String name;
	private int spiele;
	private int gewonnen;
	private int verloren;
	private boolean online;
	

	public Spieler(int id, String name, int spiele, int gewonnen, int verloren, boolean online) {
		super();
		this.id = id;
		this.name = name;
		this.spiele = spiele;
		this.gewonnen = gewonnen;
		this.verloren = verloren;
		this.online = online;
	}
	public Spieler (int id)
	{
		super();
		this.id = id;
		this.name = "n/a";
		this.spiele = 999;
		this.gewonnen = 999;
		this.verloren = 999;
		this.online = false;
	}
	
	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	public int getGewonnen() {
		return gewonnen;
	}

	public void setGewonnen(int gewonnen) {
		this.gewonnen = gewonnen;
	}

	public int getVerloren() {
		return verloren;
	}

	public void setVerloren(int verloren) {
		this.verloren = verloren;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSpiele() {
		return spiele;
	}
	public void setSpiele(int spiele) {
		this.spiele = spiele;
	}
	@Override
	public Spieler clone()
	{
		return new Spieler(this.id, this.name, this.spiele, this.gewonnen, this.verloren, this.online);
	}
	public String ToString()
	{
		return "Spieler (" + this.id + ") " + this.name + " Spiele (gesamt): " + this.spiele + " Gewonnen/Verloren: " + this.gewonnen + "/" + this.verloren + " online: " + this.online;
	}
	@Override
	public boolean equals(Object other){
	    if (other == null) return false;
	    if (other == this) return true;
	    if (!(other instanceof Spieler))return false;
	    Spieler otherSpieler = (Spieler) other;
	    if(this.getId() == otherSpieler.getId()) return true;
	    return false;
	}
	public void update()
	{
		Spieler lSpieler = Datenbank.loadUser(this.id);
		this.name = lSpieler.name;
		this.gewonnen = lSpieler.gewonnen;
		this.verloren = lSpieler.verloren;
		this.online = lSpieler.online;
	}
}
