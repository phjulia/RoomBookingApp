package models;

public class Room {
    private String roomNumber;
    private String category;
    private String roomType;
    private String roomView;
    private String hotel;
    private String imgName;
    private double roomPrice;


    public Room(String roomNumber, String category, String roomType, String roomView, String imgName, double roomPrice) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.roomType = roomType;
        this.roomView = roomView;
        this.imgName=imgName;
        this.roomPrice=roomPrice;
    }
    public void setRoomView(String roomView) {
        this.roomView = roomView;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomView() {
        return roomView;
    }


    public void setHotel(String hotel) {
        this.hotel=hotel;
    }

    public String getHotel() {
        return hotel;
    }
    public String getImageName() {
        return imgName;
    }
    public double getRoomPrice() {
        return roomPrice;
    }
    public String toString() {
    	return "Room number: "+this.getRoomNumber()+"\nRoom type: "+this.getRoomType()+"\nRoom category: "+
	    		this.getCategory()+"\nRoom view: "+this.getRoomView()+"\n\n";
    }
}
