/**
* Tony Thompson
* Nov 23, 2017
* WebCrawlerDriver.java
* 
*/
package webcrawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * prompts a menu for user to enter a url and keywords to check
 * by creating producers and consumers as well as printing the results
 * @author Tony Thompson
 *
 */
public class WebCrawlerDriver {
	
	//field
	private static LinkQueue linkQueue = new LinkQueue();
	private static PageQueue pageQueue = new PageQueue();
	private static ArrayList<String> keywords = new ArrayList<String>();
	private static ArrayList<Thread> fetcherThreads = new ArrayList<Thread>();
	private static ArrayList<Thread> parserThreads = new ArrayList<Thread>();
	private static Scanner console = new Scanner(System.in);
	private static boolean loopNotRunning = true;
	private static InfoLoop infoLoop = new InfoLoop();
	private static final int NUM_MENU = 9;
	

	/**
	 * prints welcome message then calls for menu to print and start program
	 * @param args
	 */
	public static void main(String[] args) {
		boolean killProgram = false;
		System.out.println("Web Crawler");
		while (!killProgram) {
			printMenu();
			//gets user choice then carries out the action returning here
			killProgram = menuChoice(getInt("Enter number for desired action"));
		}
		System.exit(0);
	}//end main
	
	
	//triggers action based on menu choice
	private static boolean menuChoice(int choice) {
        switch (choice) {  //cases match corresponding menu items
        	case 0:  return true;
            case 1:  try {seedUrl(getString("Enter URL to fetch"));
					 } catch (InterruptedException e) {e.printStackTrace();}
            		 return false;
            case 2:  addParser(); 
            		 return false; 
			case 3:  addFetcher(); 
				 	 return false;
            case 4:  addKeywords(getString("Enter Keyword to search")); 
            		 return false;
            case 5:  printStats(); 
            		 return false;
            case 6:  if (loopNotRunning) infoLoopControl(true); 
            		 return false;
            case 7:  if (!loopNotRunning) infoLoopControl(false); 
            		 return false;
            case 8:  removeParser();
            		 return false;
            case 9:  removeFetcher();
            		 return false;
            default: System.out.println("Switch Error");
            		 return false;
        }
	}
	
	
	//manually adds url to link queue 
	private static void seedUrl(String url) throws InterruptedException {
			linkQueue.addLink(url);
	}//end seedUrl
	
	//creates and starts new fetcher thread
	private static void addFetcher() {
		int listSize = fetcherThreads.size();
		Fetcher fetcher = new Fetcher(linkQueue, pageQueue, listSize);
		fetcherThreads.add(fetcher);
		//thread added after size check so the index is correct not needing -1
		fetcherThreads.get(listSize).start();
		System.out.println("Fetcher size: " + fetcherThreads.size());
	}//end addFetcher
	
	//creates and starts new parser thread
	private static void addParser() {
		int listSize = parserThreads.size();
		Parser parser = new Parser(linkQueue, pageQueue, listSize);
		parserThreads.add(parser);
		//thread added after size check so the index is correct not needing -1
		parserThreads.get(listSize).start();
		System.out.println("Parser size: " + parserThreads.size());
	}//end addParser
	
	//removes parser from list then kills thread
	@SuppressWarnings("null")
	private static void removeParser() {
		if (!(parserThreads.size() ==0)) {
			Parser parser = null;
			parserThreads.remove(parser);
			parser.threadAlive = false;
		}
	}//end removeParser
	
	//removes fetcher from list then kills thread
	@SuppressWarnings("null")
	private static void removeFetcher() {
		if (!(fetcherThreads.size() == 0)) {
			Fetcher fetcher = null;
			fetcherThreads.remove(fetcher);
			fetcher.threadAlive = false;
		}
	}//end removeParser
	
	//starts infoLoop thread to print stats every 5 seconds
	private static void infoLoopControl(boolean loopSwitch) {
		//send true to start thread, false to stop it
		if (loopSwitch) {
			infoLoop.start();
			loopNotRunning = false;  //prevents multiple threads
		} else if (!loopSwitch) {
			infoLoop.stopTimer();
			loopNotRunning = true;
		}
	}//end startInfoLoop
		
	/**
	 * adds given String to keyword list to check for
	 * @param keyword String to add
	 */
	public static void addKeywords(String keyword) {
		keywords.add(keyword);
	}//end setKeywords
	
	/**
	 * returns list of user entered keywords to check for
	 * @return ArrayList<String> of keywords
	 */
	public static ArrayList<String> getKeywords() {
		return keywords;
	}//end getKeywords
	
	//prints menu
	private static void printMenu() {
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
		System.out.println("Keywords found: " + Parser.getKeywordsFound());
		System.out.println("Links found: " + linkQueue.getLinksFound());
		System.out.println("Pages found: " + pageQueue.getPagesDownloaded());
		System.out.println("Failed downloads: " + Fetcher.getFailedDl());
		System.out.println("Producers: " + fetcherThreads.size());
		System.out.println("Consumers: " + parserThreads.size());
	}//end printStats
	
	//prompts user for an int between 0-NUM_MENU to return
	private static int getInt(String prompt)
	{
		System.out.println(prompt + ": ");

		boolean valid = false;
		int result = 0;
		while (!valid) {
			while (!console.hasNextInt()) {
				System.out.println("Please enter a valid integer between 0 and "+NUM_MENU+": ");
				//clear the scanner buffer
				console.nextLine();
			}
			result = console.nextInt();
			if ((result >= 0) && (result <= NUM_MENU)) {
				valid = true;
			} else {
				System.out.println("Please enter a valid integer between 0 and "+NUM_MENU+": ");
			}
		}
		//clear the scanner buffer
		console.nextLine();
		return result;
	}//end getInt
		
	//prompts user to enter a String to return
	private static String getString(String prompt)
	{
		System.out.println(prompt + ": ");
		return console.nextLine();
	}//end getString
	
		
	
}//end class





