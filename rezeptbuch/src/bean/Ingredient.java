/**
 * @author Lorenz
 */

package bean;

import java.io.Serializable;

public class Ingredient implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer quantity;
	private String unit;
	private String ingredient;

	public Ingredient() {
		super();
	}

	public Ingredient(String ingredient, Integer quantity, String unit) {
		super();
		this.ingredient = ingredient;
		this.quantity = quantity;
		this.unit = unit;
	}

	public String getIngredient() {
		return ingredient;
	}

	public void setIngredient(String ingredient) {
		this.ingredient = ingredient;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Ingredient [quantity=" + quantity + ", unit=" + unit + ", ingredient=" + ingredient + "]";
	}

	public String outputString() {
		return quantity + " " + unit + " " + ingredient;
	}

}
