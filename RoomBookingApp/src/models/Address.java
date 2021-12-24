package models;

public class Address {
	 private String state;
	 private String street;
	 private int index;

	    public Address(String street, String state, int index) {
	        this.street = street;
	        this.state = state;
	        this.index = index;
	    }
	    public String getState() {
	        return state;
	    }

	    public String getStreet() {
	        return street;
	    }

	    public int getIndex() {
	        return index;
	    }
	    
}
