package tracker.login;

import java.io.*;

import biz.source_code.base64Coder.Base64Coder;

public class UserDetails implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	private	String employeeID;
	private String PRILogin; 
	private String ALTLogin; 
	private String username;
	private String givenName; 
	private String lastName; 
	private String mail;
	private String lastLogon;
	private boolean isAdmin;
	
	public UserDetails(String employeeID, String PRILogin, String ALTLogin, String givenName,
					   String lastName, String mail, boolean isAdmin, String username)
	{
		this.employeeID = employeeID;
		this.PRILogin = PRILogin;
		this.ALTLogin = ALTLogin;
		this.givenName = givenName;
		this.lastName = lastName;
		this.mail = mail;
		this.isAdmin = isAdmin;
		this.username = username;
	}

	/** Read the object from Base64 string. */
	public static UserDetails deSerialize(String s) throws IOException, ClassNotFoundException
	{
		byte [] data = Base64Coder.decode(s);
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		UserDetails o = (UserDetails) ois.readObject();
		ois.close();
		return o;
	}
	
	/** Write the object to a Base64 string. */
	public String serialize() throws IOException 
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(this);
		oos.close();
		return new String(Base64Coder.encode(baos.toByteArray()));
	} 
	
	public String getPRILogin() {
		return PRILogin;
	}

	public String getALTLogin() {
		return ALTLogin;
	}

	public String getGivenName() {
		return givenName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMail() {
		return mail;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public void setPRILogin(String PRILogin) {
		this.PRILogin = PRILogin;
	}

	public void setALTLogin(String ALTLogin) {
		this.ALTLogin = ALTLogin;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getLastLogon() {
		return lastLogon;
	}

	public void setLastLogon(String lastLogon) {
		this.lastLogon = lastLogon;
	}

}
