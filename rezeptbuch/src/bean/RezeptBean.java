/**
 * Autor: Florian
 * Erweiterung: Lorenz
 * 
 */

package bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	private String filename;
	private byte[] image;
	private List<Ingredient> ingredients = new ArrayList<Ingredient>();
	private List<Comment> comments = new ArrayList<Comment>();
	private Timestamp created;
	private Timestamp modified;

	public RezeptBean() {
	}

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

	public void addIngredient(String ingredient, String unit, Integer quantity) {
		ingredients.add(new Ingredient(ingredient, quantity, unit));

	}

	public String ingredientsToString() {
		String result = "";

		for (int i = 0; i < ingredients.size(); i++) {
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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp timestamp) {
		this.created = timestamp;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public String timestampToDate(Timestamp timestamp) {
		//Autor: ChssPly76 URL: https://stackoverflow.com/questions/1156468/how-to-format-a-java-sql-timestamp-for-displaying
		return new SimpleDateFormat("dd.MM.yyyy").format(timestamp);
	}
	
	
	public Integer calculateRatingInt(){
		Integer sum = getRatingSum();
		Integer count = getRatingCount();
		return (count == 0) ? 0 : (Math.round((float) sum / (float) count));
	}
	
	public Float calculateRatingFloat(){
		Integer sum = getRatingSum();
		Integer count = getRatingCount();
		return ((count == 0) ? 0 : (float)(((int)((float) sum / (float) count*100))/100.0) );
		
	}

}
