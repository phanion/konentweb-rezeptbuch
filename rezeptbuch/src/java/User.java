package java;

/**
 * Created by Lorenz on 17.04.2017.
 */
public class User {
    String user_mail;
    String user_name;
    String user_prename;
    String user_password;




    /**
	 * @param user_mail
	 * @param user_name
	 * @param user_prename
	 * @param user_password
	 */
	public User(String user_mail, String user_name, String user_prename, String user_password) {
		super();
		this.user_mail = user_mail;
		this.user_name = user_name;
		this.user_prename = user_prename;
		this.user_password = user_password;
	}

	public String getUser_mail() {
        return user_mail;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_prename(String user_prename) {
        this.user_prename = user_prename;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_password() {
        return user_password;
    }

    public String getUser_prename() {
        return user_prename;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }





}
