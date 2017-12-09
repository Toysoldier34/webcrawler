/**
* Tony Thompson
* Dec 5, 2017
* Console.java
* 
*/
package webcrawler;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

/**
 * handles console IO
 * prints menu and prompts
 * gets user inputs
 * @author Tony Thompson
 *
 */
public class Console {
	
	//field
	private static Scanner console = new Scanner(System.in);
	private static final int NUM_MENU = 9;
	
	
	/**
	 * prints out text for menu
	 */
	public static void printMenu() {
		System.out.println("0. End Program");
		System.out.println("1. Add seed url");
		System.out.println("2. Add consumer parser");
		System.out.println("3. Add producer fetcher");
		System.out.println("4. Add keyword search");
		System.out.println("5. Print stats");
		System.out.println("6. Print updated stats every 5 seconds");
		System.out.println("7. Stop displaying stats");
		System.out.println("8. Kill consumer parser");
		System.out.println("9. Kill producer fetcher");
	}//end printMenu
	
	
	/**
	 * prints out multiple lines of stats
	 * info based on pages web crawler reaches
	 */
	public static void printStats() {
		System.out.println("\n****Updated Stats****");
		Date date = new Date();
		System.out.println("-Current Time-");
		System.out.println(date.toString());
		System.out.println("Links found: " + ThreadHandler.linkQueue.getLinksFound());
		System.out.println("Pages found: " + ThreadHandler.pageQueue.getPagesDownloaded());
		System.out.println("Failed downloads: " + Fetcher.getFailedDl());
		System.out.println("Producers: " + ThreadHandler.fetcherThreads.size());
		System.out.println("Consumers: " + ThreadHandler.parserThreads.size());
		printKeywords();
		System.out.println("*********************");
	}//end printStat
	
	
	//gets keywordCount HashMap then iterates through keys printing values
	public static void printKeywords() {
		HashMap<String, Integer> hmap = Keywords.getKeywordCounts();
		String fullPrint = "";
		int count = 0;
		int pages = 0;
		DecimalFormat df = new DecimalFormat();  //formats 2 decimal places
		df.setMaximumFractionDigits(2);
		
		Set<Entry<String, Integer>> set = hmap.entrySet();
		Iterator<Entry<String, Integer>> iterator = set.iterator();
		while(iterator.hasNext()) {  //loops through keywords
		   Entry<String, Integer> mentry = iterator.next();
		   pages = Keywords.keywordPagesCount.get(mentry.getKey());
		   count += mentry.getValue();
		   double average = 0;
		   if (pages != 0) { average = ((double) mentry.getValue() / pages); };
		   fullPrint += (mentry.getKey() + ": " + mentry.getValue() + " total, " + (df.format(average)) + " times per page average \n");
		}//end while
		
		System.out.println("Total keywords found: " + count);
		System.out.println("-Individual keyword counts-");
		System.out.println(fullPrint);
	}//end printKeywords
	
	
	/**
	 * prompts user for an int between 0-NUM_MENU to return
	 * @param prompt String to display to user
	 * @return int entered by user
	 */
	public static int getInt(String prompt) {
		System.out.println(prompt + ": ");
		boolean valid = false;
		int result = 0;
		while (!valid) {
			while (!console.hasNextInt()) {  //waits for user input
				System.out.println("Please enter a valid integer between 0 and "+NUM_MENU+": ");
				//clear the scanner buffer
				console.nextLine();
			}//end while
			result = console.nextInt();  //store user input
			if ((result >= 0) && (result <= NUM_MENU)) {  //validates user input
				valid = true;  //breaks while after good input
			} else {
				System.out.println("Please enter a valid integer between 0 and "+NUM_MENU+": ");
			}//end if
		}//end while
		//clear the scanner buffer
		console.nextLine();
		return result;
	}//end getInt
	
	
	/**
	 * prompts user for a String
	 * @param prompt String to display to user
	 * @return String entered by user
	 */
	public static String getString(String prompt) {
		System.out.println(prompt + ": ");
		return console.nextLine();
	}//end getString
	
	
}//end class
