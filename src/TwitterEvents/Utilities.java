package TwitterEvents;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.LatLng;

/**
 * This class encloses all the utility methods to extract useful fields from 
 * twitter text or tweets
 * 
 * @author chethans
 *
 */
public class Utilities {
	
	public static LatLng getCoordinatesFromAddress(String address) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address)
			.setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		
		LatLng locCoordinates = geocoderResponse.getResults().get(0).getGeometry().getLocation();
		
		return locCoordinates;
	}
	
	public static String getAddressFromCoordinates(LatLng location) {
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setLocation(location)
			.setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		
		String address = geocoderResponse.getResults().get(0).getFormattedAddress();
		
		return address;
	}
}
