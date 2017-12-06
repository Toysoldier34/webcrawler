/**
* Tony Thompson
* Nov 23, 2017
* Fetcher.java
* 
*/
package webcrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
			URL url = null;
			HttpURLConnection connection = null;
			BufferedReader download = null;
			String pageText = null;
			String lineText = null;
			try {
				//pull url from queue
				url = new URL(linkQueue.getNextLink());
				
				//uses url to open http connection
				connection = (HttpURLConnection)url.openConnection();
				//opens buffered reader to download url with http connection
				download = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
			
				//download page text to string
				while ((lineText = download.readLine()) != null) {
					pageText += lineText;
				}
				
				//close connection and send page to queue
				download.close();
				pageQueue.addPage(pageText);
			} catch (InterruptedException | IOException e) {
				failedDl++;  //tracks times try/catch fails
				e.printStackTrace();
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
