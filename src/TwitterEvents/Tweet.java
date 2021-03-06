package TwitterEvents;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Status;

import com.google.code.geocoder.model.LatLng;

/**
 * This object is built after parsing the JSON object and populate only the important 
 * and selective fields. This is a wrapper class over 
 * 
 * @author chethans
 */
public class Tweet {

	// Tweet's status object
	private Status tweetStatus;
	
	// User's/Device's location from tweeted status
	private String userLocation = null;
	
	// This is purified text (stemmed and removed stop words) from tweets.
	private String cleanedText = null;
	
	private static Stemmer stemmer = new Stemmer();
	
	public Tweet(Status tweetStatus) {
		this.tweetStatus = tweetStatus;
	}
	
	public Status getStatusObject() {
		return tweetStatus;
	}
	
	public GeoLocation getCoordinates() {
		return tweetStatus.getGeoLocation();
	}
	
	public String getAddress() {
		LatLng location = new LatLng(new BigDecimal(tweetStatus.getGeoLocation().getLatitude()), 
			new BigDecimal(tweetStatus.getGeoLocation().getLongitude()));
		
		if(userLocation == null)
			userLocation = Utilities.getAddressFromCoordinates(location);
		
		return userLocation;
	}
	
	public String getTweetedText() {
		return tweetStatus.getText();
	}
	
	public String getPurifiedText() {
		if(cleanedText == null) {
			// Using Porter stemming module after removing stop words
			cleanedText = stemmer.startStem(StopWords.removeStopWords(tweetStatus.getText()));
		}
		
		return cleanedText;
	}
	
	/*public Date getTweetCreationDate() throws ParseException
	{
	    final String twitter = "EEE, dd MMM yyyy HH:mm:ss Z";
	    SimpleDateFormat sf = new SimpleDateFormat(twitter, Locale.ENGLISH);
	    sf.setLenient(true);
	    return sf.parse(dateTime);
	}*/
	
	public Date getTweetCreationDate() throws ParseException
	{
	    return tweetStatus.getCreatedAt();
	}
	
	public HashtagEntity[] getHashTags() {
		return tweetStatus.getHashtagEntities();
	}
	
	public int getRetweetCount() {
		return tweetStatus.getRetweetCount();
	}
}
