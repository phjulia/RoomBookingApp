package Helpers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BookingHelper {
	private static String imagesLocation="/Users/rachel_green/eclipse-workspace/RoomBookingApp/img/";
	
	public static ImageView showImage(String imgName) {
		 InputStream stream;
		 ImageView imageView = null;
		try {
			stream = new FileInputStream(imagesLocation+imgName);
			Image image = new Image(stream);
	        imageView = new ImageView();
	        imageView.setImage(image);
	        imageView.setX(10);
	        imageView.setY(10);
	        imageView.setFitWidth(200);//width of a pic
	        imageView.setPreserveRatio(true);
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
		return imageView;
         
	}

}
