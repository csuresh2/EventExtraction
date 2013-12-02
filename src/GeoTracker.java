import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

/**
 * This class is used to get the co-ordinates (latitude, longitude) given an address 
 * as a String.
 * 
 * @author chethans
 *
 */
public class GeoTracker {
	
	public static void calculateLocation(String address) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address)
			.setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		
		LatLng location = geocoderResponse.getResults().get(0).getGeometry().getLocation();
		System.out.println("Latitude: " + location.getLat() + "\tLongitute: "
			+ location.getLng());
		
		// System.out.println(geocoderResponse.toString());
	}
	
	public static void main(String[] args) {
		// calculateLocation("Paris, France");
		calculateLocation("Taj Mahal, India");
	}
}
