/**
* Tony Thompson
* Dec 5, 2017
* ThreadHandler.java
* 
*/
package webcrawler;

import java.util.ArrayList;

public class ThreadHandler {
	
	//field
	public static LinkQueue linkQueue = new LinkQueue();
	public static PageQueue pageQueue = new PageQueue();
	public static ArrayList<String> keywords = new ArrayList<String>();
	public static ArrayList<Thread> fetcherThreads = new ArrayList<Thread>();
	public static ArrayList<Thread> parserThreads = new ArrayList<Thread>();
	public static boolean loopNotRunning = true;
	public static InfoLoop infoLoop = new InfoLoop();
	
	
	/**
	 * starts infoLoop thread to print stats every 5 seconds
	 * @param loopSwitch boolean send true to start thread, false to stop it
	 */
	public static void infoLoopControl(boolean loopSwitch) {
		if (loopSwitch) {
			infoLoop.start();
			loopNotRunning = false;  //prevents multiple threads
		} else if (!loopSwitch) {
			infoLoop.stopTimer();
			loopNotRunning = true;
		}//end if
	}//end startInfoLoop
	
	
	/**
	 * manually adds url to link queue 
	 * mostly used to start program
	 * @param url String of a url
	 * @throws InterruptedException
	 */
	public static void seedUrl(String url) throws InterruptedException {
			linkQueue.addLink(url);
	}//end seedUrl
	
	
	/**
	 * creates and starts new fetcher thread
	 * sends queues and an int to be used as ID for naming thread
	 * threads managed in ArrayList fetcherThreads
	 */
	public static void addFetcher() {
		int listSize = fetcherThreads.size();
		Fetcher fetcher = new Fetcher(linkQueue, pageQueue, listSize);
		fetcherThreads.add(fetcher);
		//thread added after size check so the index is correct not needing -1
		fetcherThreads.get(listSize).start();
		WebCrawlerDriver.LOGGER.warning("Fetcher has been added");
		System.out.println("addFetcher() Fetcher size: " + fetcherThreads.size());
	}//end addFetcher
	
	
	/**
	 * creates and starts new parser thread
	 * sends queues and an int to be used as ID for naming thread
	 * threads managed in ArrayList parserThreads
	 */
	public static void addParser() {
		int listSize = parserThreads.size();
		Parser parser = new Parser(linkQueue, pageQueue, listSize);
		parserThreads.add(parser);
		//thread added after size check so the index is correct not needing -1
		parserThreads.get(listSize).start();
		WebCrawlerDriver.LOGGER.warning("Parser has been added");
		System.out.println("addParser() Parser size: " + parserThreads.size());
	}//end addParser
	
	
	/**
	 * removes parser from list then kills thread
	 */
	@SuppressWarnings("null")
	public static void removeParser() {
		int listSize = parserThreads.size();
		if (!(listSize ==0)) {
			Parser parser = null;
			parser = (Parser) parserThreads.remove(listSize - 1);
			parser.threadAlive = false;
		}//end if
	}//end removeParser
	
	
	/**
	 * removes fetcher from list then kills thread
	 */
	@SuppressWarnings("null")
	public static void removeFetcher() {
		int listSize = fetcherThreads.size();
		if (!(listSize == 0)) {
			Fetcher fetcher = null;
			fetcher = (Fetcher) fetcherThreads.remove(listSize - 1);
			fetcher.threadAlive = false;
		}//end if
	}//end removeParser

	
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
	

}//end class
