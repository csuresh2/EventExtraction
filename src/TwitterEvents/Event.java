package TwitterEvents;

import java.util.Date;

enum EventType {SPORTS, LUNCH, DINNER, PARTY};

public class Event {

	private String tweetText;
	private String location;
	private Date date;
	private EventType eventType;
	
	public Event(String tweetText, String location, Date date, EventType eventType) {
		this.tweetText = tweetText;
		this.location = location;
		this.date = date;
		this.eventType = eventType;
	}
	
}
