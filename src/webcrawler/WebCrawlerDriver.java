/**
* Tony Thompson
* Nov 23, 2017
* WebCrawlerDriver.java
* 
*/
package webcrawler;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * prompts a menu for user to enter a url and keywords to check
 * by creating producers and consumers as well as printing the results
 * @author Tony Thompson
 *
 */
public class WebCrawlerDriver {
	
	/*
	 * URLs for testing to copy from
	 * 
	 * http://athompson.greenrivertech.net/305/
	 * http://athompson.greenrivertech.net/305/inclass/
	 * 
	 */
	
	public static final Logger LOGGER = Logger.getLogger(WebCrawlerDriver.class.toString());
	
	
	
	/**
	 * prints welcome message then calls for menu to print and start program
	 * @param args
	 */
	public static void main(String[] args) {		
		boolean killProgram = false;
		System.out.println("Web Crawler");
		while (!killProgram) {
			Console.printMenu();
			//gets user choice then carries out the action returning here
			killProgram = menuChoice(Console.getInt("Enter number for desired action"));
		}//end while
		
		/*
		 * If all threads need to be finished before forcing program to end
		 * 
		 * Can be replaced with a method call that runs through and calls
		 * for all running threads to stop by looping and calling the 
		 * removeParse/Fetch a number of times equal to number currently running. 
		 */
		System.exit(0);
		
	}//end main
	
	
	//triggers action based on menu choice
	private static boolean menuChoice(int choice) {
        switch (choice) {  //cases match corresponding menu items
        	case 0:  return true;
            case 1:  try {ThreadHandler.seedUrl(Console.getString("Enter URL to fetch"));
					 } catch (InterruptedException e) {e.printStackTrace();}
            		 return false;
            case 2:  ThreadHandler.addParser(); 
            		 return false; 
			case 3:  ThreadHandler.addFetcher(); 
				 	 return false;
            case 4:  ThreadHandler.addKeywords(Console.getString("Enter Keyword to search")); 
            		 return false;
            case 5:  Console.printStats(); 
            		 return false;
            case 6:  if (ThreadHandler.loopNotRunning) ThreadHandler.infoLoopControl(true); 
            		 return false;
            case 7:  if (!ThreadHandler.loopNotRunning) ThreadHandler.infoLoopControl(false); 
            		 return false;
            case 8:  ThreadHandler.removeParser();
            		 return false;
            case 9:  ThreadHandler.removeFetcher();
            		 return false;
            default: System.out.println("Menu Switch Error");
            		 return false;
        }//end switch
	}//end menuChoice
	
	
	public static void foo() {
		try {
			LOGGER.addHandler(new FileHandler("webcrawler.log"));
			LOGGER.setUseParentHandlers(false);
		}
		catch (Exception e) {
			System.out.println("Error setting up log file.");
		}
	}
	
	
}//end class





