/**
* Tony Thompson
* Nov 23, 2017
* PageQueue.java
* 
*/
package webcrawler;

import java.util.LinkedList;
import java.util.Queue;

/**
 * stores pages downloaded by adding and removing from a queue
 * also tracks total number downloaded
 * @author Tony Thompson
 *
 */
public class PageQueue {
	
	//field
	private static final int MAX_SIZE = 50000;
	private static Queue<String> pageList = new LinkedList<String>();
	private static int pagesFound;
	
	/**
	 * adds page to pageList queue if not full
	 * increments count of pages checked
	 * @param pageText page text to add to queue
	 * @throws InterruptedException
	 */
	public void addPage(String pageText) throws InterruptedException {
		synchronized(pageList) {
			while (pageList.size() == MAX_SIZE) {
				pageList.wait();
			}//end while
			pageList.add(pageText);
			pageList.notify();
			pagesFound++;
		}//end synchronized
	}//end addPage
	
	/**
	 * removes a page String from queue if not empty
	 * @return String of next page in queue
	 * @throws InterruptedException
	 */
	public String getNextPage() throws InterruptedException {
		synchronized(pageList) {
			while (pageList.size() == 0) {
				pageList.wait();
			}//end while
			String page = pageList.remove();
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
