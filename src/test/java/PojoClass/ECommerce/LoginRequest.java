package PojoClass.ECommerce;

public class LoginRequest {

	private String userEmail;
	private String userPassword;
	
	public String getuserEmail() {
		return userEmail;
	}
	public void setuserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getuserPassword() {
		return userPassword;
	}
	public void setuserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}
