package models;

public class SearchSession {
	
	private static String checkInRoom;
	private static String checkOutRoom;
	private static String hotel;

	public SearchSession() {
		
	}
	public static void setCheckIn(String checkIn) {
		checkInRoom=checkIn;
	}
	public static void setCheckOut(String checkOut) {
		checkOutRoom=checkOut;
	}
	public static String getCheckIn() {
		return checkInRoom;
	}
	public static String getCheckOut() {
		return checkOutRoom;
	}
	public static String getHotel() {
		return hotel;
	}
	public static void setHotel(String hotelName) {
		hotel=hotelName;	
	}
}
