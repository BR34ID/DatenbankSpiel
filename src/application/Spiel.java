package application;

public class Spiel {
	int id;
	Spieler spieler1;
	Spieler spieler2;
	int winner;
	
	public void setSpieler1(Spieler spieler1) {
		this.spieler1 = spieler1;
	}

	public void setSpieler2(Spieler spieler2) {
		this.spieler2 = spieler2;
	}

	public int getId() {
		return id;
	}

	public Spieler getSpieler1() {
		return spieler1.clone();
	}

	public Spieler getSpieler2() {
		if(spieler2 == null) return null;
		return spieler2.clone();
	}

	public int getWinner() {
		return winner;
	}

	public Spiel(int id, Spieler spieler1, Spieler spieler2, int winner)
	{
		this.id = id;
		this.spieler1 = spieler1.clone();
		this.spieler2 = spieler2.clone();
		this.winner = winner;
	}
	public boolean isSpielbar()
	{
		return (this.spieler1.getId() != 0 && this.spieler2.getId() != 0 && this.winner == 0);
	}
	public Spiel(int id, int spieler1id, int spieler2id, int winner)
	{
		this.id = id;
		this.spieler1 = new Spieler(spieler1id);
		this.spieler2 = new Spieler(spieler2id);
		this.winner = winner;
	}
	public String ToString()
	{
		System.out.println("Test");
		return "Spiel: (ID:" + this.id + " Spieler 1: " + this.spieler1 + " Spieler 2: " + this.spieler2 + " Sieger: (ID) " + this.winner + ")";
	}
	//Gibt den anderen Mitspieler zurück
	public Spieler getMitspieler(Spieler spieler)
	{
		if(this.spieler1 == spieler) return this.spieler2;
		else if(this.spieler2 ==  spieler) return this.spieler1;
		else return null;
	}
	public boolean laden(int spielerid, SpielerTyp spielertyp)
	{
		System.out.println("laden(SpielerTyp spielertyp);");
		return true;
	}
}
