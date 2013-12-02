package TwitterEvents;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.json.DataObjectFactory;

/**
 * This is a sample program to convert raw Json string to 
 * twitter status object.
 * Example: Twitter status object.
 * 
 * @author chethans
 *
 */
public class JSonStringToObject {
	
	public static void main(String[] args) throws TwitterException {
		Twitter twitter = new TwitterFactory().getInstance();
		QueryResult result = twitter.search(new Query("#event"));
		List<Status> tweets = result.getTweets();
		System.out.println(tweets.get(0).toString());
		String rawJSON = DataObjectFactory.getRawJSON(tweets.get(0));
		System.out.println("Stored raw JSON object: " + rawJSON);
		
		Status tweet = DataObjectFactory.createStatus(rawJSON);
		System.out.println(tweet.toString());
	}
}
