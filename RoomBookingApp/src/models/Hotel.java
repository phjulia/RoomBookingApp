package models;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Hotel {
    private String name;
    private Address address;
    private List<String> phoneNumbers= new ArrayList<>();
    private String website;//optional
    private int stars;
//    private List<Room> rooms = new ArrayList<>();
//    private static List<Employment> employments = new ArrayList<>();
    private TreeMap<String, Room> roomsInHot = new TreeMap<String, Room>();
    public Hotel(String name, Address address, int stars, List<String> phoneNumbers) {
        this.name = name;
        this.address = address;
        this.phoneNumbers = phoneNumbers;
        this.stars = stars;
    }
    public Hotel(String name, Address address, String website, int stars, List<String> phoneNumbers) {
        this.name = name;
        this.address = address;
        this.website=website;
        this.phoneNumbers = phoneNumbers;
        this.stars = stars;
    }

//    public static List<Employment> getEmployments() {
//        return employments;
//    }

//    public void addEmployee(Employee employee) {
//       employees.add(employee);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getWebsite() throws Exception {
        if(website==null){
            throw new Exception("This hotel does not have a website");
        }
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

//    public void addRoom(Room newRoom) {
//// Sprawdz czy nie mamy juz informacji o tym pokoju
//        if(!roomsInHot.containsKey(newRoom.getRoomNumber())) {
//            roomsInHot.put(newRoom.getRoomNumber(), newRoom);
//// Dodaj informację zwrotną
//            newRoom.addHotel(this);
//        }
//    }
    public Room findRoom(String number) throws Exception {
// Sprawdz czy nie mamy  informacji o tym pokoju
        if(!roomsInHot.containsKey(number)) {
            throw new Exception("Didn't find a room with this number: " + number);
        }
        return roomsInHot.get(number);
    }
    public void printAllRooms(){
       for(String roomNum: roomsInHot.keySet()){
           System.out.print(roomNum+"; ");
       }
        System.out.println();
    }
}
