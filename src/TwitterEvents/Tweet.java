package TwitterEvents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.google.code.geocoder.model.LatLng;

/**
 * This object is built after parsing the JSON object and populate only the important 
 * and selective fields.
 * 
 * @author chethans
 */
public class Tweet {

	// Location coordinates of the device from which tweet was posted
	private LatLng coordinates;
	
	// This is the tweeted text
	private String text;
	
	// User's location from profile information
	private String userLocation;
	
	// Tweeted time and date. Extracted from created_at field
	private String dateTime;
	
	// This is purified text (stemmed and removed stop words) from tweets.
	private String cleanedText = null;
	
	private int retweetCount;
	
	private static Stemmer stemmer = new Stemmer();
	
	// hashtags
	private ArrayList<String> hashTags;
	
	public LatLng getCoordinates() {
		return coordinates;
	}
	
	public String getTweetedText() {
		return text;
	}
	
	public String getPurifiedText() {
		if(cleanedText == null) {
			// Using Porter stemming module after removing stop words
			cleanedText = stemmer.startStem(StopWords.removeStopWords(text));
		}
		
		return cleanedText;
	}
	
	public Date getTweetCreationDate() throws ParseException
	{
	    final String twitter = "EEE, dd MMM yyyy HH:mm:ss Z";
	    SimpleDateFormat sf = new SimpleDateFormat(twitter, Locale.ENGLISH);
	    sf.setLenient(true);
	    return sf.parse(dateTime);
	}
	
	public ArrayList<String> getHashTags() {
		return hashTags;
	}
	
	public int getRetweetCount() {
		return retweetCount;
	}
}
