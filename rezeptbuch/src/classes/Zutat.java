/**
 * 
 */
package classes;

import java.util.EnumSet;

import enums.Allergen;
import enums.Einheit;

/**
 * @author michael
 *
 */
public class Zutat {
	private String name;
	private float menge;
	private Einheit einheit;
	private EnumSet<Allergen> allergene;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public float getMenge() {
		return menge;
	}
	public void setMenge(float menge) {
		this.menge = menge;
	}
	
	/**
	 * @return the einheit
	 */
	public Einheit getEinheit() {
		return einheit;
	}
	/**
	 * @param einheit the einheit to set
	 */
	public void setEinheit(Einheit einheit) {
		this.einheit = einheit;
	}
	/**
	 * @return the allergenes
	 */
	public EnumSet<Allergen> getAllergene() {
		return allergene;
	}

	
}