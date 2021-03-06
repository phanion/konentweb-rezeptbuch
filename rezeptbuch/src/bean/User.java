/**
 * @author Lorenz
 */

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
	private String description;

	public User() {
	}

	public User(Long id, String mail, String lastName, String firstName, String password, String description) {
		this.id = id;
		this.mail = mail;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
		this.description = description;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
