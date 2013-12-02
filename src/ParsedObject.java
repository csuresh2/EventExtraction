import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses the classified string and stores all the 
 * location, person and organization information
 * 
 * @author chethans
 *
 */
public class ParsedObject {

	private static final Pattern LOC_REGEX = Pattern.compile("<LOCATION>(.+?)</LOCATION>");
	private static final Pattern ORG_REGEX = Pattern.compile("<ORGANIZATION>(.+?)</ORGANIZATION>");
	private static final Pattern PERSON_REGEX = Pattern.compile("<PERSON>(.+?)</PERSON>");
	private static final Pattern TIME_REGEX = Pattern.compile("<TIME>(.+?)</TIME>");
	private static final Pattern DATE_REGEX = Pattern.compile("<DATE>(.+?)</DATE>");
	
	ArrayList<String> locations;
	ArrayList<String> organizations;
	ArrayList<String> people;
	ArrayList<String> timeList;
	ArrayList<String> dateList;
	
	public void parseThreeClassifiedString(String classifiedString) {
		
		// Build the list of classified objects
		locations = getTagValues(LOC_REGEX, classifiedString);

		/*for(int i = 0; i < locations.size(); i++) {
			System.out.println("Location: " + locations.get(i));
		}*/
	}
	
	public void parseSevenClassifiedString(String classifiedString) {
		timeList = getTagValues(TIME_REGEX, classifiedString);
		dateList = getTagValues(DATE_REGEX, classifiedString);
	}

	private ArrayList<String> getTagValues(Pattern pattern, final String str) {
	    final ArrayList<String> tagValues = new ArrayList<String>();
	    final Matcher matcher = pattern.matcher(str);
	    while (matcher.find()) {
	        tagValues.add(matcher.group(1));
	    }
	    return tagValues;
	}
	
	public ArrayList<String> getLocations() {
		return locations;
	}
	
	public ArrayList<String> getOrganizations() {
		return organizations;
	}
	
	public ArrayList<String> getPeople() {
		return people;
	}
	
	public ArrayList<String> getTimeList() {
		return timeList;
	}
	
	public ArrayList<String> getDateList() {
		return dateList;
	}
}
