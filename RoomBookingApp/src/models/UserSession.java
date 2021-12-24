package models;

public class UserSession {
	private static String userEmail;
	private static int thediscount;
	
	public static void setUserSession(String email) {
		userEmail=email;
	}
	
	public static String getUserEmail() {
		return userEmail;
	}
	public static int getDiscount() {
		return thediscount;
	}

	public static void setDiscount(int discount) {
		thediscount = discount;
	}
}
