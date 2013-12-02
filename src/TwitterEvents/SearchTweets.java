package TwitterEvents;

import twitter4j.*;
import twitter4j.json.DataObjectFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * Code to query for tweets with event hashtags and write it to csv file
 * in a predefined format for the purpose of labeling this data and generating
 * a training set.
 * 
 * @author chethans
 */
public class SearchTweets {
	/**
	 * Usage: java twitter4j.examples.search.SearchTweets [query]
	 *
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		/*if (args.length < 1) {
			System.out.println("java twitter4j.examples.search.SearchTweets [query]");
			System.exit(-1);
		}*/
		Twitter twitter = new TwitterFactory().getInstance();
		CSVWriter writer = new CSVWriter(new FileWriter("/home/chethans/twitterOutput.csv"));
		
		while(true){
			try {
				Query query = new Query("#event");
				QueryResult result;
				StringBuilder csvOutputString;
				Thread.sleep(1000);
				do {
					result = twitter.search(query);
					List<Status> tweets = result.getTweets();
					for (Status tweet : tweets) {
						System.out.println(tweet.toString());
						csvOutputString = new StringBuilder();
						csvOutputString.append(tweet.getId() + "#@XF" + tweet.getUser()
							.getScreenName() + "#@XF" + tweet.getText() + "#@XF" + "0" +
							"#@XF" + DataObjectFactory.getRawJSON(tweet));
						String[] data = csvOutputString.toString().split("#@XF");
						writer.writeNext(data);
					}
				} while ((query = result.nextQuery()) != null);
			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Failed to search tweets: " + te.getMessage());
				System.exit(-1);
			}
		}
	}
}