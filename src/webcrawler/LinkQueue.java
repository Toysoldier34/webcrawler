/**
* Tony Thompson
* Nov 23, 2017
* LinkQueue.java
* 
*/
package webcrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * tracks unique URLs to process by adding and removing them from queue
 * tracks number of unique URLs found
 * @author Tony Thompson
 *
 */
public class LinkQueue {
	
	//field
	private static final int MAX_SIZE = 50000;
	private static Queue<String> linkList = new LinkedList<String>();
	private static HashSet<String> linksFound = new HashSet<String>();
	
	
	/**
	 * adds links to linkList after ensuring they are unique
	 * @param url String to check and add
	 * @throws InterruptedException 
	 */
	public void addLink(String url) throws InterruptedException {
		synchronized(linksFound) {
			int oldSize = getLinksFound();
			linksFound.add(url);
			if (oldSize != getLinksFound()) {
				synchronized(linkList) {
					while (linkList.size() == MAX_SIZE) {
						linkList.wait();
					}//end while
					linkList.add(url);
					linkList.notify();
				}//end synchronized
			}//end if
		}//end synchronized
	}//end addLink
	
	/**
	 * checks linkList and if not empty returns next link
	 * @return String of next link
	 * @throws InterruptedException
	 */
	public String getNextLink() throws InterruptedException {
		synchronized(linkList) {
			while (linkList.size() == 0) {
				linkList.wait();
			}//end while
			String url = linkList.remove();
			linkList.notify();
			return url;
		}//end synchronized
	}//end getNextLink
	
	/**
	 * returns number of unique links found
	 * @return linksFound.size()
	 */
	public int getLinksFound() {
		synchronized(linksFound) {
			return linksFound.size();
		}//end synchronized
	}//end getLinksFound
	
}//end class
