/**
 * Autor: Lorenz
 */

package classes;

public class Ingredient {
	private String ingredient;
	private Integer quantity;
	private String unit;
	
	
	
	
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
	
	
	
}
