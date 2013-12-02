package TwitterEvents;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extract and build features from the tweeted text.
 * 
 * @author chethans
 *
 */
public class TweetFeatures {

	Tweet tweet;
	String location;
	String date;
	String time;
	
	// Modeling only presence and absence of features
	// Like location, date, time, etc.	
	HashMap<String, Boolean> featuresMap;
	
	public TweetFeatures(Tweet tweet) {
		this.tweet = tweet;
	}
	
	public void initFeaturesMap() {
		featuresMap = new HashMap<String, Boolean>();
		featuresMap.put("location", false);
		featuresMap.put("time", false);
		featuresMap.put("date", false);
		
		// Update hashtags info to featuresMap
		ArrayList<String> hashTags = tweet.getHashTags();
		for(int i=0; i < hashTags.size(); i++) {
			featuresMap.put(hashTags.get(i), true);
		}
	}
	
	public boolean getPresence(String feature) {
		if(!featuresMap.containsKey(feature))
			featuresMap.put(feature, false);
		
		return featuresMap.get(feature);
	}
	
	public void updatePresence(String feature) {
		featuresMap.put(feature, true);
	}
}
