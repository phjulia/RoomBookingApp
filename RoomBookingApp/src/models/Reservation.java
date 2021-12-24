package models;

public class Reservation {
	private String hotelName;
	private String roomNumber;
	private String checkIn;
	private String checkOut;
	private double reservationPrice;
	private String roomPic;
	private int bookingId;
	
	
	public Reservation(String hotelName, String checkIn, String checkOut,String roomNumber,  double reservationPrice,String roomPic) {
		this.hotelName = hotelName;
		this.roomNumber = roomNumber;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.reservationPrice = reservationPrice;
		this.roomPic=roomPic;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public double getReservationPrice() {
		return reservationPrice;
	}
	public void setReservationPrice(double reservationPrice) {
		this.reservationPrice = reservationPrice;
	}
	
	public String getRoomPic() {
		return roomPic;
	}
	public void setRoomPic(String roomPic) {
		this.roomPic = roomPic;
	}
	
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	@Override
	public String toString() {
		return " Booking \n Hotel: " + hotelName + "\n RoomNumber=" + roomNumber + "\n Check in: " + checkIn
				
				+ "\n Check out:" + checkOut + "\n Price: " + reservationPrice +"\n\n";
	}
	
	
	

}
