/**
* Tony Thompson
* Nov 23, 2017
* PageQueue.java
* 
*/
package webcrawler;

import java.util.LinkedList;
import java.util.Queue;

import org.jsoup.nodes.Document;

/**
 * stores pages downloaded by adding and removing from a queue
 * also tracks total number downloaded
 * @author Tony Thompson
 *
 */
public class PageQueue {
	
	//field
	private static final int MAX_SIZE = 50000;
	private static Queue<org.jsoup.nodes.Document> pageList = new LinkedList<org.jsoup.nodes.Document>();
	private static int pagesFound;
	
	/**
	 * adds page to pageList queue if not full
	 * increments count of pages checked
	 * @param page page text to add to queue
	 * @throws InterruptedException
	 */
	public void addPage(Document page) throws InterruptedException {
		synchronized(pageList) {
			while (pageList.size() == MAX_SIZE) {
				pageList.wait();
			}//end while
			pageList.add((Document) page);
			pageList.notify();
			pagesFound++;
		}//end synchronized
	}//end addPage
	
	/**
	 * removes a page String from queue if not empty
	 * @return String of next page in queue
	 * @throws InterruptedException
	 */
	public Document getNextPage() throws InterruptedException {
		synchronized(pageList) {
			while (pageList.size() == 0) {
				pageList.wait();
			}//end while
			Document page = pageList.remove();
			pageList.notify();
			return page;
		}//end synchronized
	}//end getNextPage
	
	/**
	 * returns count of pages downloaded
	 * @return int pages downloaded
	 */
	public int getPagesDownloaded() {
			return pagesFound;
	}//end getPagesDownloaded
	
	
}
