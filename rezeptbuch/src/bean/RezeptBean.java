package bean;

import java.io.Serializable;
import java.util.List;

import classes.Zutat;

public class RezeptBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private String rezept;
	private String zutatenText;
	private String beschreibung;

	private List<Zutat> zutatenListe;
	

	public String getRezept() { return rezept; }
	public void setRezept(String rezept) { this.rezept = rezept; }

	public String getZutatenText() { return zutatenText; }
	public void setZutatenText(String zutaten) { this.zutatenText = zutaten; }

	public String getBeschreibung() { return beschreibung; }
	public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
		
	public List<Zutat> getZutatenListe() {
		return zutatenListe;
	}
	
}
