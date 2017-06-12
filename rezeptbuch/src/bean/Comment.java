package bean;

import java.io.Serializable;

public class Comment implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private User author;
	private String comment;
	private RezeptBean recipe;


	public Comment() {
		super();
	}
	
	public Comment(Long id, User author, String comment, RezeptBean recipe) {
		super();
		this.id = id;
		this.author = author;
		this.comment = comment;
		this.recipe = recipe;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public RezeptBean getRecipe() {
		return recipe;
	}

	public void setReceipe(RezeptBean recipe) {
		this.recipe = recipe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
