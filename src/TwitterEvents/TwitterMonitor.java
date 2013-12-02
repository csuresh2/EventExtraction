package TwitterEvents;

import java.io.IOException;

import twitter4j.*;
import java.io.*;

class TwitterMonitor {

	public static void main(String[] args) throws TwitterException, IOException{
		StatusListener listener = new StatusListener(){
			// Create file 
			FileWriter fstream = new FileWriter("out5.txt");
			BufferedWriter dump = new BufferedWriter(fstream);
			public void onStatus(Status status) {
				System.out.println(status.getUser().getName() + " : " + status.getText());
				try {
					dump.write("\""+status.getCreatedAt()+"\"" + "," + "\""+status.getUser().getName()+"\""+ "," + status.getGeoLocation().getLatitude()+ "," + status.getGeoLocation().getLongitude() + "," + "\"" + status.getText()+"\"");
					dump.newLine();
					dump.flush();
				} catch (IOException e) {
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

			}
		};
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		//twitterStream.cleanUp(); // shutdown internal stream consuming thread
		//twitterStream.shutdown();

		twitterStream.addListener(listener);

		// Filter by terms
		//FilterQuery filtre = new FilterQuery();
		//String[] keywordsArray = { "university"};
		//do0uble[][] locations = {{-88.295574,40.083851},{-88.188114,40.125866}}; 
		//filtre.locations(locations);
		//filtre.track(keywordsArray);
		//twitterStream.filter(filtre);

		// sample() method internally creates a thread which manipulates TwitterStream and calls these adequate listener methods continuously.
		twitterStream.sample();
	}

}