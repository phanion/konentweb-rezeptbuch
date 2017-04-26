package bean;

import java.io.Serializable;
import java.util.List;

import classes.Zutat;

public class RezeptBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rezept;
	private String zutaten;
	private String beschreibung;

	private List<Zutat> zutatenListe;
	

	public String getRezept() { return rezept; }
	public void setRezept(String rezept) { this.rezept = rezept; }

	public String getZutaten() { return zutaten; }
	public void setZutaten(String zutaten) { this.zutaten = zutaten; }

	public String getBeschreibung() { return beschreibung; }
	public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
		
	public List<Zutat> getZutatenListe() {
		return zutatenListe;
	}
	
}
