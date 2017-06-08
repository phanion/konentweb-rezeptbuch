package bean;

/**
 * 
 */
public class User {
	private Long id;
	private String mail;
	private String lastName;
	private String firstName;
	private String password;

	public User() {
	}

	public User(Long id, String mail, String lastName, String firstName, String password) {
		this.id = id;
		this.mail = mail;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", mail=" + mail + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", password=" + password + "]";
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setID(Long id) {
		this.id = id;
	}

	public Long getID() {
		return id;
	}

}
