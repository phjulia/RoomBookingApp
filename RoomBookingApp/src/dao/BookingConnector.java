package dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import models.Address;
import models.Guest;
import models.Hotel;
import models.Reservation;
import models.Room;
import models.UserSession;

public  class BookingConnector {
	private static Connection con;
	private static Statement stmt;
	public static void connect() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:8889/HotelSchema","root","root");
		con.setAutoCommit(false);
		 stmt=con.createStatement();  
	} catch (ClassNotFoundException e) {

		e.printStackTrace();
	}   catch (SQLException e) {
		
		e.printStackTrace();
	}
	}
	
	public static void addGuest(Guest guest) {
		String query = "INSERT INTO guest(name, surname,nationality, gender,birthDate,email, phoneNumber,password,discount) VALUES ('"+
				guest.getName()+"','"+guest.getSurname()+"','"+guest.getNationality()+"','"+guest.getGender() +"', STR_TO_DATE('"+guest.getBirthDate().toString()+"','%Y-%m-%d')"+",'"+guest.getEmail()+
				"','"+guest.getPhoneNumber()+"','"+guest.getPassword()+"',"+guest.getDiscount()+")";
	
		try {
			
			 stmt = con.createStatement();
			stmt.executeUpdate(query);
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public static List<Hotel> getHotels(){ 
		List<Hotel> hotels = new ArrayList<>();
		 try {
			 
			 stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT id, name,address,website,stars FROM hotel");

			
			Hotel hotel;
			List<String> numbers = new ArrayList<>();

			int id;
			ResultSet rs2;
			while(rs.next()) {
				id=rs.getInt(1);
				 stmt = con.createStatement();
			rs2= stmt.executeQuery("SELECT hotelId, number FROM numbers WHERE hotelId="+id);
			String [] addressParts = rs.getString(3).split(",");
			Address address = new Address(addressParts[0],addressParts[1],Integer.parseInt((addressParts[2]).trim()));
			
			if(rs.getString(4)==null) {//if website is not available
				hotel = new Hotel(rs.getString(2),address, Integer.valueOf(rs.getString(5)),numbers);
			}
			hotel = new Hotel(rs.getString(2),address,rs.getString(4),Integer.parseInt(rs.getString(5)),numbers);
				while(rs2.next()) {
					hotel.getPhoneNumbers().add(rs2.getString(2));
				
				}
			hotels.add(hotel);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		 return hotels;
		 
	}

	public static List<Room> getRooms(String hotel,String category, String type, String view,
			String checkIn, String checkOut) {
		
		List<Room> rooms = new ArrayList<>();
String query = "SELECT DISTINCT number,imgName,price FROM room "
		+"LEFT JOIN booking ON booking.roomNumber=room.number WHERE category='"+category+"' "
		+ "AND roomType='"+type+"' AND roomView='"+view+"' AND "
		+ "((checkIn < '"+checkIn+"' AND checkOut <= '"+checkIn+"') "
		+ "OR (checkIn >= '"+checkOut+"' AND checkOut > '"+checkOut+"') "
				+ "OR (ISNULL(checkIn) AND ISNULL(checkOut)))";

		
		try {
		stmt = con.createStatement();
		Statement stmt1 = con.createStatement();
		
			ResultSet rs= stmt.executeQuery("SELECT id,name FROM Hotel WHERE name='"+hotel+"'");
			while(rs.next()) {
				 stmt = con.createStatement();
				 ResultSet rs2= stmt1.executeQuery(query);

				 while(rs2.next()) {
					Room room=new Room(rs2.getString(1),category,type,view,rs2.getString(2),rs2.getDouble(3));
					room.setHotel(rs.getString(2));
					 rooms.add(room);
					 
				 }
				 rs2.close();
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return rooms;
	}
	public static List<Reservation> getClientBookings(String email) {
		List<Reservation> clientBookings = new ArrayList<>();
		Statement stmt;
		ResultSet rs;
		try {
			stmt = con.createStatement();
			rs=stmt.executeQuery("SELECT hotel.name,checkIn,checkOut,roomNumber,reservationPrice,imgName,booking.Id FROM booking "
					+ "JOIN hotel ON hotel.id = booking.hotelId "
					+ "JOIN guest ON guest.Id = booking.guestId "+
					"JOIN room ON room.number = booking.roomNUmber WHERE email='"+email+"'");
			while(rs.next()) {
			Reservation r = new Reservation(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getDouble(5),rs.getString(6));
			r.setBookingId(rs.getInt(7));
			clientBookings.add(r);
			}
			rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return clientBookings;
	}
	
	
	public static String userExists(String email, String password) {
		ResultSet rs,rs1;
		Statement stmt1;
		try {
			stmt=con.createStatement();
			
			 rs= stmt.executeQuery("SELECT 1 FROM Guest WHERE email='"+email+"'");
			 stmt1=con.createStatement();
			 rs1=stmt1.executeQuery("SELECT 1 FROM Guest WHERE password='"+password+"' AND email='"+email+"'");
			if(!rs.next())
				return "User with such email address does not exist";
			else if(!rs1.next()){
				return "Password is incorrect";
			}
			rs.close();
			rs1.close();
		}catch (SQLException e) {
			e.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return "Yes";
	}
	public static double addBooking(String user, String checkIn, String checkOut, String roomNumber, String hotelName) {
		double resPrice=0;
		int discount;
		
		try {
	
			stmt = con.createStatement();
			Statement stmt2 = con.createStatement();
		ResultSet rs2 = stmt2.executeQuery("SELECT id FROM hotel WHERE name='"+hotelName+"'");
		int hotelId=0;
		if(rs2.next())
		 hotelId=rs2.getInt(1);
		
		ResultSet rs = stmt.executeQuery("SELECT id FROM Guest WHERE email='"+user+"'");
		int userId=0;
		 
		if(rs.next())
		userId=rs.getInt(1);
		
		CallableStatement cs = con.prepareCall("{CALL InsertBooking(?,?,?,?,?)}");
        cs.setInt(1, hotelId);
        cs.setInt(2, userId);
        cs.setString(3, checkIn);
        cs.setString(4, checkOut);
        cs.setString(5, roomNumber);
        System.out.println(cs);
        cs.execute();

        Statement stmt3 = con.createStatement();
        ResultSet rs3 = stmt3.executeQuery("SELECT reservationPrice,discount FROM booking "
        		+ "JOIN guest g "
        		+ "ON g.id = booking.guestId ORDER BY booking.id DESC LIMIT 1");
        while(rs3.next()) {
        resPrice = rs3.getDouble(1);
        discount=rs3.getInt(2);
        UserSession.setDiscount(discount);
        }
  
		con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	return resPrice;
	}
public static void cancelBooking(Reservation r) {
	Statement stmt;
	try {
		stmt =con.createStatement();
		stmt.execute("DELETE FROM booking WHERE Id='"+r.getBookingId()+"'");
		
	}catch(SQLException e) {
		
		try {
			con.rollback();
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
		e.printStackTrace();
	}
}
	

}
