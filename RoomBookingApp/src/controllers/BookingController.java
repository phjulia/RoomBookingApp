package controllers;
import javafx.scene.control.Label;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import Helpers.BookingHelper;
import app.Main;
import dao.BookingConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import models.Guest;
import models.Hotel;
import models.Reservation;
import models.Room;
import models.SearchSession;
import models.UserSession;

/**
 * @author Yuliia Likhytska
 *
 */
public class BookingController{

	@FXML
	private ComboBox<String> nationality = new ComboBox<>();
	
	@FXML
	private ComboBox<String> hotel = new ComboBox<>();
	
	@FXML
	private DatePicker checkIn = new DatePicker();
	
	@FXML
	private DatePicker checkOut= new DatePicker();
	
	@FXML
	private ComboBox<String> category = new ComboBox<>();
	
	@FXML
	private ComboBox<String> type = new ComboBox<>();
	
	@FXML
	private ComboBox<String> view = new ComboBox<>();
	@FXML
	private TextField name;
	
	@FXML
	private TextField surname;
	
	
	@FXML
	private CheckBox male;
	
	@FXML
	private CheckBox female;
	
	@FXML
	private DatePicker birthDate;
	
	@FXML
	private TextField email;
	
	@FXML
	private TextField password;
	
	@FXML
	private TextField signInEmail;
	@FXML
	private PasswordField signInPassword;
	
	@FXML
	private TextField phone;
	
	@FXML
	private VBox roomsBox = new VBox();
	
	@FXML
	private VBox clientBookings = new VBox();
	
	
	@FXML
	private Pane error;

	@FXML
	private Button MyBookings;

	@FXML
	private TextFlow summary=new TextFlow();
	
	@FXML 
	private Label total = new Label();
	
	private ObservableList<String> nationalityList = FXCollections.observableArrayList();
	private ObservableList<String> hotelList = FXCollections.observableArrayList();
	private ObservableList<String> categoryList = FXCollections.observableArrayList("single","double","triple","quad","queen","king","twin");
	private ObservableList<String> typeList = FXCollections.observableArrayList("standard","family","apartment");
	private ObservableList<String> viewList=FXCollections.observableArrayList("sea","mountains","park");
	private ObservableList<Node> list; //will hold room items in room search 
	private static List<Room> rooms = new ArrayList<>();
	private static double price;

	
	
	private String userEmail;
	private String checkInRoom;
	private String checkOutRoom;
	
	
	public void addAvailableRooms() {
	    List<Node> vboxNodes = new ArrayList<>();//separate room items
	    	
	    roomsBox.setPadding(new Insets(20,20,20,20));
	    roomsBox.setSpacing(10);
  		 
	    list=roomsBox.getChildren(); //get children of the VBox 
  		 	

  		 	
  		HBox roomListBox; //picture + description
  
	    	for(Room room: rooms) {
	    		roomListBox = new HBox();
	    		roomListBox.setSpacing(20);
	    		
	    		List<Node> roomNodes = roomListBox.getChildren(); //getting children of the HBox
	    		
	    		ImageView imgView = BookingHelper.showImage(room.getImageName());
	    		roomNodes.add(imgView);
	    		Text roomText = new Text(room.toString());
	    		
	    		roomText.setFont(Font.font ("Verdana", 14));
	    		roomText.setFill(Color.BLACK);
	    		
	    		roomNodes.add(roomText);
	    		Button book = new Button("Book");
	    		

	    		 book.setStyle("-fx-background-color: #1e212d; -fx-text-fill: #FFFFFF");
	    		 book.setOnAction(event->{
	    				price = BookingConnector.addBooking(UserSession.getUserEmail(),SearchSession.getCheckIn(),SearchSession.getCheckOut(), room.getRoomNumber(),SearchSession.getHotel());
	    				  FXMLLoader loader = new FXMLLoader();
						try {
							loader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/SuccessPage.fxml"));
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} 
						  
						  Parent root1;
							try {
								root1 = (Parent) loader.load();
							    
								Main.getStage().setScene(new Scene(root1));  
							    Main.getStage().show();
							} catch (IOException e1) {

								e1.printStackTrace();
							}

					    
				
	    		 });
	    		
	    		 BorderPane buttonPane = new BorderPane();
	    		 
	    		 buttonPane.setBottom(book);
	    		 roomNodes.add(buttonPane);
	    		 
	    		 vboxNodes.add(roomListBox);
	    		 
	    	
	    	 }
	    	DecimalFormat df = new DecimalFormat("###.##");
	    	if(UserSession.getDiscount()>0) {
	    		total.setText("Reservation price "+df.format(price)+" \nDiscount applied: "+UserSession.getDiscount()+"%");
	    	}else {
	    
	    	total.setText("Reservation price "+df.format(price));
	    	}
	    	list.addAll(vboxNodes);
		
	}
	@FXML
	public void initialize() {
		//set up nationality drop-down options for "Create account"
		nationality.getItems().clear();
		
	    for (String iso : Locale.getISOCountries()) {
	    		 Locale obj = new Locale("", iso);
	    		 nationalityList.add(obj.getDisplayCountry(Locale.ENGLISH));
	    }
	    	nationality.getItems().addAll(nationalityList);
	
	    	 hotel.getItems().clear();
	    for (Hotel hotel : BookingConnector.getHotels()) {
	    		
	    		hotelList.add(hotel.getName());
	    	}
	    	hotel.setItems(hotelList);
	    	
	    	category.getItems().clear();
	    	category.getItems().addAll(categoryList);
	    	
	    	
	    	type.getItems().clear();
	    	type.getItems().addAll(typeList);
	    	
	    	view.getItems().clear();
	    	view.getItems().addAll(viewList);
	    	
	    	addAvailableRooms();
	    	
	    	addClientBookings();
	    	
	    	checkIn.setDayCellFactory(picker -> new DateCell() {
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                LocalDate today = LocalDate.now();

	                setDisable(empty || date.compareTo(today) < 0 );
	            }
	        });
	    	checkOut.setDayCellFactory(picker -> new DateCell() {
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                LocalDate checkInDate = checkIn.getValue();

	                setDisable(empty || date.compareTo(checkInDate) < 0 );
	            }
	        });

	}
	public void addClientBookings() {
		List<Reservation> reservations = BookingConnector.getClientBookings(UserSession.getUserEmail());
    	ObservableList<Node> vboxNodes=clientBookings.getChildren(); //vbox children
    	 HBox roomItem;
		clientBookings.setPadding(new Insets(20,20,20,20));
  		clientBookings.setSpacing(10);
    
    	for(Reservation r: reservations) {
    		
    		roomItem = new HBox();
    		roomItem.setSpacing(20);
    		
    		 List<Node> clientBookingNodes = roomItem.getChildren();
    		ImageView imgView = BookingHelper.showImage(r.getRoomPic());
    		clientBookingNodes.add(imgView);
    		Text reservation = new Text(r.toString());
 		  reservation.setFont(Font.font ("Verdana", 14));
 		 reservation.setFill(Color.BLACK);
 		 
 		Button cancel = new Button("Cancel");
		
 		clientBookingNodes.add(reservation);
 		cancel.setStyle("-fx-background-color: #1e212d; -fx-text-fill: #FFFFFF");
 		cancel.setOnAction(e->{
 			Node n = (Node) e.getSource();
 		    Node p = n.getParent().getParent();
 		    ((Pane) p.getParent()).getChildren().remove(p);
 			BookingConnector.cancelBooking(r);
 		});
		 BorderPane buttonPane = new BorderPane();
		 buttonPane.setBottom(cancel);
		 clientBookingNodes.add(buttonPane);
 		
 		vboxNodes.add(roomItem);	
	}
	}
	
	public void buttonClicked(){
		
		  FXMLLoader fxmlLoader = new FXMLLoader();
		  try {
			fxmlLoader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/CreateAccount.fxml"));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		  
		  Parent root1;
			try {
				root1 = (Parent) fxmlLoader.load();
			    
				Main.getStage().setScene(new Scene(root1));  
			    Main.getStage().show();
			} catch (IOException e1) {

				e1.printStackTrace();
			}

	    }
	    

	    public void register() {
	    Guest guest = new Guest(name.getText(), surname.getText(),nationality.getValue(),male.isSelected()?"male":"female",birthDate.getValue(),email.getText(),phone.getText(),password.getText(), 0);
	    System.out.println(guest.toString());
	    BookingConnector.addGuest(guest);
	    submit();
	    }
	   
	    public void search(){
	    	String h=hotel.getSelectionModel().getSelectedItem(),
	    			c=category.getSelectionModel().getSelectedItem(),
	    			t=type.getSelectionModel().getSelectedItem(),
	    			v=view.getSelectionModel().getSelectedItem();
	    			checkInRoom=checkIn.getValue().toString();
	    			checkOutRoom=checkOut.getValue().toString();
	    			SearchSession.setCheckIn(checkInRoom);
	    			SearchSession.setCheckOut(checkOutRoom);
	    			SearchSession.setHotel(h);
	    	 rooms=BookingConnector.getRooms(h,c,t,v,checkInRoom,checkOutRoom);

	    	 FXMLLoader fxmlLoader=new FXMLLoader();
	    	 try {
	    		 fxmlLoader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/SearchBookings.fxml"));
	    	 }catch(MalformedURLException e) {
	    		 e.printStackTrace();
	    	 }
	    	 Parent root1;
	    	
	    	
	    	 try {
	    		 root1=(Parent) fxmlLoader.load();
	    		 Main.getStage().setScene(new Scene(root1));
	    		 Main.getStage().show();
	    	 } catch (IOException e1) {

					e1.printStackTrace();
				}
	    	 

	    }
	    
	    public void submit() {
	    	

	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    
			  try {
				fxmlLoader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/Bookings.fxml"));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} 
			  
			  Parent root1;
				try {
					root1 = (Parent) fxmlLoader.load();
				    
					Main.getStage().setScene(new Scene(root1));  
				    Main.getStage().show();
				} catch (IOException e1) {

					e1.printStackTrace();
				}
	    }

	    public void checkIfExists() {
	    
	    	 userEmail = signInEmail.getText();
	    	String password = signInPassword.getText();
	    	String result =BookingConnector.userExists(userEmail,password);
	    	if((email==null || email.equals("")) && (password==null|| password.equals(""))) {
	    		signInEmail.setStyle("-fx-border-color: red ;");
	    		signInPassword.setStyle("-fx-border-color: red ;");
	    	}else 
	    	if(result.equals("Yes")) {
	    		signInEmail.setStyle("-fx-border-color: none ;");
	    		signInPassword.setStyle("-fx-border-color: none ;");
	    		UserSession.setUserSession(userEmail);
	    		submit();
	    	}else 
	    	if(result.equals("User with such email address does not exist")) {
	    		signInEmail.setStyle("-fx-border-color: none ;");
	    		signInEmail.setStyle("-fx-border-color: red ;");
	    	}else if(result.equals("Password is incorrect")) {
	    		signInEmail.setStyle("-fx-border-color: none ;");
	    		signInPassword.setStyle("-fx-border-color: red ;");
	    	}

	    }
	    public void myBookings() {
	    	FXMLLoader fxmlLoader = new FXMLLoader();
	    	try{
	    		fxmlLoader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/MyBookings.fxml"));
	    	} catch (MalformedURLException e) {
				e.printStackTrace();
			} 
	    	Parent root1;
			try {
				root1 = (Parent) fxmlLoader.load();
			    
				Main.getStage().setScene(new Scene(root1));  
			    Main.getStage().show();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
	    	
	    }
	    public void logout() {
	    	UserSession.setUserSession(null);
	    	FXMLLoader loader = new FXMLLoader();
	    	try {
	    		loader.setLocation(new URL("file:/Users/rachel_green/eclipse-workspace/RoomBookingApp/src/fxml/SignIn.fxml"));
	    	}catch(MalformedURLException e) {
	    		 e.printStackTrace();
	    	 }
	    	 Parent root1;
	    	
	    	 
	    	 try {
	    		 root1=(Parent) loader.load();
	    		
	    		 
	    		 Main.getStage().setScene(new Scene(root1));
	    		 Main.getStage().show();
	    	 } catch (IOException e1) {

					e1.printStackTrace();
				}
	    	
	    }
	    public void backToSearch() {
	    	
	    }
}

	    
