/**
* Tony Thompson
* Nov 23, 2017
* Fetcher.java
* 
*/
package webcrawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Producer
 * takes links from linkQueue and processes them downloading the page
 * then adding the contents to the pageQueue 
 * @author Tony Thompson
 *
 */
public class Fetcher extends Thread {
	//Producer
	
	//field
	private LinkQueue linkQueue;
	private PageQueue pageQueue;
	private static int failedDl;
	public boolean threadAlive = true;
	private int id;
	
	
	/**
	 * constructor for Producer Fetcher
	 * @param linkQueue linkQueue to get link
	 * @param pageQueue pageQueue to give page text
	 */
	public Fetcher(LinkQueue linkQueue, PageQueue pageQueue, int id) {
		this.linkQueue = linkQueue;
		this.pageQueue = pageQueue;
		this.id = id;
	}//end constructor
	
	
	/**
	 * pulls link from link queue
	 * downloads HTML page text of given URL
	 * stored HTML as a String and adds to pageQueue
	 */
	public void run() {
		currentThread().setName("Fetcher #" + id);
		while (threadAlive) {
			String url = null;
			try {
		
				//pull url from queue
				url = linkQueue.getNextLink();
				
				Document page = Jsoup.connect(url).get();
				pageQueue.addPage(page);
				
			} catch (InterruptedException | IOException | RuntimeException e) {
				WebCrawlerDriver.LOGGER.severe(e.toString());
				failedDl++;  //tracks times try/catch fails
			}//end try/catch
		}//end while
	}//end run
	
	
	/**
	 * getter for failedDl tracking failed downloads
	 * @return int failedDl
	 */
	public static int getFailedDl() {
		return failedDl;
	}//end getFailedDl
	
}//end class
