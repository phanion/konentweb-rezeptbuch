package bean;


/**
 * Created by Lorenz on 17.04.2017.
 * habe ein bisschen refactoring betrieben - Michael
 */
public class User {
    private String mail;
    private String lastName;
    private String firstName;
    private String password;



    /**
	 * @param mail
	 * @param lastName
	 * @param firstName
	 * @param password
	 */
	public User(String mail, String lastName, String firstName, String password) {
		this.mail = mail;
		this.lastName = lastName;
		this.firstName = firstName;
		this.password = password;
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





}
