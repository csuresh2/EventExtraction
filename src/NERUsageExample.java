import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;


/**
 * This is an custom sample program to illustrate the usage of NER 
 * classification
 * 
 * @author chethans
 *
 */
public class NERUsageExample {
	
	public static void main(String[] args) {

		try {
			String threeClassSerializedClassifier = "/home/chethans/stanford-ner/classifiers/" +
				"english.all.3class.distsim.crf.ser.gz";
			
			String sevenClassSerializedClassifier = "/home/chethans/stanford-ner/classifiers/" +
				"english.muc.7class.distsim.crf.ser.gz";

			AbstractSequenceClassifier<CoreLabel> threeClassClassifier = CRFClassifier
					.getClassifierNoExceptions(threeClassSerializedClassifier);
			
			AbstractSequenceClassifier<CoreLabel> sevenClassClassifier = CRFClassifier
					.getClassifierNoExceptions(sevenClassSerializedClassifier);

			if(args.length > 0) {
				BufferedReader br = new BufferedReader(new FileReader(args[0]));
				String line;
				ArrayList<String> parsedTexts = new ArrayList<String>();
				
				line = br.readLine();
				while(line != null) {
					if(line.equals("ParseText::"))
					{
						line = br.readLine();
						parsedTexts.add(line);
					}
					line = br.readLine();
				}

				ParsedObject po;
				
				for(int i = 0; i < parsedTexts.size(); i++) {
					po = new ParsedObject();
					po.parseThreeClassifiedString(threeClassClassifier.classifyWithInlineXML(
						parsedTexts.get(i)));
					System.out.println("Text-" + i + ":\n");
					System.out.println(po.getLocations());
					po.parseSevenClassifiedString(sevenClassClassifier.classifyWithInlineXML(
						parsedTexts.get(i)));
					System.out.println(po.getDateList());
					System.out.println(po.getTimeList());
				}
			}
			else {
				String s1 = "Good afternoon Rajat Raina, how are you today?";
				String s2 = "I go to school at Stanford University, which is located in California.";
				String s3 = "Academics Arts & Culture Athletics International Library Public Engagement Research General Events General Events Event Detail Information Event Detail Information Illini Men's Basketball vs. IPFW Date Nov 29, 2013 Time 7:00 pm - 9:00 pm   Location State Farm Center Sponsor FightingIllini.com Event type Basketball Views 1044 Originating Calendar Fighting Illini Men's Basketball Schedule Sunnyvale, California -- Home Stream: ESPN3 Social Media Campus Highlights More News & Events News Calendars Campus RSS Feeds Featured Resources Addtional links About this Site Contact Us Emergency Info Employment Privacy     University Administration UI Chicago UI Springfield U of I Online";
				
				String classifiedString = threeClassClassifier.classifyWithInlineXML(s3);
				System.out.println(classifiedString);
				
				ParsedObject po = new ParsedObject();
				po.parseThreeClassifiedString(classifiedString);
				System.out.println(po.getLocations());
				/*System.out.println(po.getDateList());
				System.out.println(po.getTimeList());*/
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
