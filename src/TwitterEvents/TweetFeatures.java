package TwitterEvents;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import au.com.bytecode.opencsv.CSVWriter;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import twitter4j.HashtagEntity;

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
	Set<String> eventKeywords;
	
	// Modeling only presence and absence of features
	// Like location, date, time, etc.	
	HashMap<String, Boolean> featuresMap;
	
	public TweetFeatures(Tweet tweet) {
		this.tweet = tweet;
		eventKeywords = new HashSet<String>();
		eventKeywords.addAll(Arrays.asList(new String[] {"exhibition", "charity", 
			"albumrelease", "pressrelease", "eventhost"}));
	}
	
	public void initFeaturesMap() {
		featuresMap = new HashMap<String, Boolean>();
		
		// Update hashtags info to featuresMap
		HashtagEntity[] hashTags = tweet.getHashTags();
		for(int i=0; i < hashTags.length; i++) {
			featuresMap.put(hashTags[i].getText(), true);
		}
		
		updateFeatures();
	}
	
	public void updateFeatures() {
		String tweetText = StopWords.removeStopWords(tweet.getTweetedText());
		
		// Check the presence of location information
		featuresMap.put("location", Utilities.containsLocation(tweet.getTweetedText()));
		
		// Check the presence of time or date information
		featuresMap.put("time", Utilities.containsTimeOrDate(tweetText));
		
		// ScreenName with keyword event
		featuresMap.put("screenNameEvent", tweet.getStatusObject().getUser()
			.getScreenName().toLowerCase().contains("event"));
		
		// URL info in tweet text
		featuresMap.put("urlInfo", (tweet.getStatusObject().getURLEntities().length > 0));
		
		// Keywords presence
		String[] list = tweetText.toLowerCase().split("\\s+");
		for(int i=0; i < list.length; i++) {
			if(eventKeywords.contains(list[i]))
				featuresMap.put(list[i], true);
		}
	}
	
	public void flushFeaturesToFile(String csvFile, boolean append) throws IOException {
		// open the csv file in append mode
		CSVWriter writer = new CSVWriter(new FileWriter(csvFile, append));
		StringBuilder sb = new StringBuilder();
		
		for(Map.Entry<String, Boolean> entry: featuresMap.entrySet()) {
			sb.append(entry.getValue() + "#@XF");
		}
		
		sb.append("emptyLabel");
		String[] data = sb.toString().split("#@XF");
		writer.writeNext(data);
		writer.close();
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
