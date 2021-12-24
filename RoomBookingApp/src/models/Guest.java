package models;

import java.time.LocalDate;

public class Guest {
	private String name;
	private String surname;
	private String nationality;//optional?
	private String gender; 
	private LocalDate birthDate;//zlozony
	private String email; 
	private String phoneNumber;
	private int discount;
	private String password;
	
	
	
	public Guest(String name, String surname, String gender,LocalDate birthDate, String email,
			String phoneNumber, String password, int discount) {
		super();
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthDate = birthDate;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password=password;
		this.discount = discount;
	}
	public Guest(String name, String surname,String nationality, String gender,LocalDate birthDate, String email,
			String phoneNumber,String password, int discount) {
		super();
		this.name = name;
		this.surname = surname;
		this.nationality=nationality;
		this.gender = gender;
		this.birthDate = birthDate;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.password=password;
		this.discount = discount;
	}
	
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getNationality() {
		return nationality;
	}
	public String getGender() {
		return gender;
	}
	public LocalDate getBirthDate() {
		return birthDate;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public int getDiscount() {
		return discount;
	}
	//TODO SET DISCOUNT
	@Override
	public String toString() {
		return "Guest [name=" + name + ", surname=" + surname + ", nationality=" + nationality + ", gender=" + gender
				+ ", birthDate=" + birthDate + ", email=" + email + ", phoneNumber=" + phoneNumber + ", discount="
				+ discount + "]";
	}
	
	

}
