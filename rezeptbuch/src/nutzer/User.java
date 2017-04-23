package nutzer;

/**
 * Created by Lorenz on 17.04.2017.
 * habe ein bisschen refactoring betrieben - Michael
 */
public class User {
    String mail;
    String name;
    String vorname;
    String passwort;



    /**
	 * @param mail
	 * @param name
	 * @param vorname
	 * @param passwort
	 */
	public User(String mail, String name, String vorname, String passwort) {
		this.mail = mail;
		this.name = name;
		this.vorname = vorname;
		this.passwort = passwort;
	}


	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}


}
