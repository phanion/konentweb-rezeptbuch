/**
 * Autor: Florian
 * Refactoring: Lorenz
 * 
 */

package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import classes.Ingredient;

public class RezeptBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private User creator;
	private String name;
	private String zutatenText;
	private String description;
	private Integer durationPreparation;
	private Integer durationCooking;
	private Integer difficulty;
	private Integer ratingCount;
	private Integer ratingSum;
	private Integer servings;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();


	public RezeptBean(){}



	



	







	@Override
	public String toString() {
		return "RezeptBean [id=" + id + ", creator=" + creator + ", name=" + name + ", zutatenText=" + zutatenText
				+ ", description=" + description + ", durationPreparation=" + durationPreparation + ", durationCooking="
				+ durationCooking + ", difficulty=" + difficulty + ", ratingCount=" + ratingCount + ", ratingSum="
				+ ratingSum + ", servings=" + servings + ", ingredients=" + ingredients + "]";
	}















	public Integer getServings() {
		return servings;
	}



	public void setServings(Integer servings) {
		this.servings = servings;
	}



	public String getRezept() {
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDurationPreparation() {
		return durationPreparation;
	}

	public void setDurationPreparation(Integer durationPreparation) {
		this.durationPreparation = durationPreparation;
	}

	public Integer getDurationCooking() {
		return durationCooking;
	}

	public void setDurationCooking(Integer durationCooking) {
		this.durationCooking = durationCooking;
	}

	public Integer getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Integer difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Integer getRatingSum() {
		return ratingSum;
	}

	public void setRatingSum(Integer ratingSum) {
		this.ratingSum = ratingSum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public String getZutatenText() {
		return zutatenText;
	}

	public void setZutatenText(String zutaten) {
		this.zutatenText = zutaten;
	}

	public void addIngredient(String ingredient, String unit, Integer quantity){
		ingredients.add(new Ingredient(ingredient,quantity, unit));
		
	}
	
	public String ingredientsToString(){
		String result = "";
		
		for(int i = 0; i < ingredients.size(); i++){
			result += ingredients.get(i).outputString() + "</br>";
		}
		
		return result;

	}















	public List<Ingredient> getIngredients() {
		return ingredients;
	}















	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}









	

}
