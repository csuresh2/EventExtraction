package TwitterEvents;

import java.io.FileReader;
import java.io.IOException;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.json.DataObjectFactory;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Rewriting the training data in the following format: (Utilize the raw JSON object)
 * <Event/No-Event label> <feature-1> <feature-2> ... <feature-N>
 * 
 * Write out in a csv formatted file.
 * 
 * @author chethans
 */
public class TweetsTrainer {

	public static void addAllTweetFeatures(String label, 
		Status tweetStatus, String csvFile) throws IOException {
		
		TweetFeatures features = new TweetFeatures(new Tweet(tweetStatus));
		features.updateFeatures();
		features.flushFeaturesToFile(csvFile, true, label);
	}
	
	public static void main(String[] args) {
		
		try {
			if(args.length < 1) {
				throw new Exception("Error. Please provide the input csv file name " +
					"containing the twitter training data.");
			}
			
			CSVReader csvReader = new CSVReader(new FileReader(args[0]));
			String[] row = null;
			int numTweets = 0;
			
			// Fifth column is the raw JSON object and fourth column is the event-label
			while((row = csvReader.readNext()) != null && numTweets < 2500) {
			    if(row.length > 4) {
			    	System.out.println(row[0]);
				    System.out.println(row[4]);

				    String label = row[3].equals("0")? "NotEvent" : "Event";
				    try {
				    	Status status = DataObjectFactory.createStatus(row[4]);
				    	System.out.println(status.toString());
						addAllTweetFeatures(label, status, "/home/chethans/twitterOutput.csv");
				    }
					catch(TwitterException e){
						System.out.println(e.getMessage());
					}
			    }
				
			    numTweets++;
			}
			
			csvReader.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
