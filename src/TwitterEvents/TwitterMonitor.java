package TwitterEvents;

import java.io.FileWriter;
import java.io.IOException;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.json.DataObjectFactory;

import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

class TwitterMonitor {

	public static void main(String[] args) throws TwitterException, IOException{
		StatusListener listener = new StatusListener() {
			// Create file 
			CSVWriter writer = new CSVWriter(new FileWriter("/home/chethans/twitterOutput.csv"));
			HashMap<Long, StringBuilder> map = new HashMap<Long, StringBuilder>();
			
			public void onStatus(Status status) {
				try {
					if(status.getUser().getLang().equals("en")) {
						System.out.println(map.size());
						if(!map.containsKey(status.getId())) {
							StringBuilder csvOutputString = new StringBuilder();
							csvOutputString.append(status.getId() + "#@XF" + status.getUser()
								.getScreenName() + "#@XF" + status.getText() + "#@XF" + "0" +
								"#@XF" + DataObjectFactory.getRawJSON(status));
							map.put(status.getId(), csvOutputString);
						}
					}
					
					if(map.size() > 100) {
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
		FilterQuery filter = new FilterQuery();
		String[] keywordsArray = { "#event"};
		
		/*
		// This is an example of applying location filter
		double[][] locations = {{-88.295574,40.083851},{-88.188114,40.125866}}; 
		filter.locations(locations);
		*/
		
		filter.track(keywordsArray);
		twitterStream.filter(filter);

		// This method internally creates a thread which manipulates TwitterStream and 
		// calls these adequate listener methods continuously.
		twitterStream.sample();
	}

}