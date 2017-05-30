package bean;

public class Comment {
	private Long id;
	private User author;
	private String comment;
	private RezeptBean receipe;

	public Comment(Long id, User author, String comment, RezeptBean receipe) {
		super();
		this.id = id;
		this.author = author;
		this.comment = comment;
		this.receipe = receipe;
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

	public RezeptBean getReceipe() {
		return receipe;
	}

	public void setReceipe(RezeptBean receipe) {
		this.receipe = receipe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
