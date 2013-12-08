package TwitterEvents;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
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
	
	/*public static void main(String[] args) throws TwitterException, IOException {
		Twitter twitter = new TwitterFactory().getInstance();
		QueryResult result = twitter.search(new Query("#event"));
		List<Status> tweets = result.getTweets();
		System.out.println(tweets.get(0).toString());
		String rawJSON = DataObjectFactory.getRawJSON(tweets.get(0));
		System.out.println("Stored raw JSON object: " + rawJSON);
		CSVWriter writer = new CSVWriter(new FileWriter("/home/chethans/test.csv"));
		writer.writeNext(new String[] {rawJSON});
		
		CSVReader reader = new CSVReader(new FileReader("/home/chethans/test.csv"));
		String[] row = null;
		while((row = reader.readNext()) != null) {
			Status tweet = DataObjectFactory.createStatus(row[0]);
			System.out.println(tweet.toString());
		}
		
		writer.close();
		reader.close();
	}*/
	
	public static void main(String[] args) throws TwitterException, IOException{
		StatusListener listener = new StatusListener() {
			// Create file 
			CSVWriter writer = new CSVWriter(new FileWriter("/home/chethans/test.csv"));
			HashMap<Long, StringBuilder> map = new HashMap<Long, StringBuilder>();
			
			public void onStatus(Status status) {
				try {
					// if(status.getUser().getLang().equals("en")) {
						System.out.println(map.size());
						if(!map.containsKey(status.getId())) {
							StringBuilder csvOutputString = new StringBuilder();
							csvOutputString.append(status.getId() + "#@XF" + status.getUser()
								.getScreenName() + "#@XF" + status.getText() + "#@XF" + "0" +
								"#@XF" + DataObjectFactory.getRawJSON(status));
							System.out.println(DataObjectFactory.getRawJSON(status));
							System.out.println(DataObjectFactory.createObject(DataObjectFactory.getRawJSON(status)));
							System.out.println(map.size());
							map.put(status.getId(), csvOutputString);
						}
					// }
					
					if(map.size() > 2) {
						// Write the map contents to the csv file
						for(Map.Entry<Long, StringBuilder> entry: map.entrySet()) {
							String[] data = entry.getValue().toString().split("#@XF");
							writer.writeNext(data);
						}
						
						finalize();
						System.exit(0);
					}
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
			
			@Override
			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
				System.out.println("Stall Warning: " + arg0.getMessage());
			}
			
			@Override
			protected void finalize() throws Throwable {
				super.finalize();
				writer.close();
			}
		};
		
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);

		// Filter by terms
		FilterQuery query = new FilterQuery();
		String[] keywordsArray = {"#event"};
		
		query.track(keywordsArray);
		twitterStream.filter(query);
	}
}
