/**
* Tony Thompson
* Nov 23, 2017
* Parser.java
* 
*/
package webcrawler;

import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser extends Thread {
	//Consumer
	
	//field
	private LinkQueue linkQueue;
	private PageQueue pageQueue;
	public boolean threadAlive = true;
	private int id;
	
	
	/**
	 * constructor for Consumer Parser
	 * @param linkQueue linkQueue to get link
	 * @param pageQueue pageQueue to give page text
	 */
	public Parser(LinkQueue linkQueue, PageQueue pageQueue, int id) {
		this.linkQueue = linkQueue;
		this.pageQueue = pageQueue;
		this.id = id;
	}//end constructor
	
	
	/**
	 * takes page text from queue and pulls out all links adding them to queue
	 * then checks the page for keywords given by the user
	 */
	public void run() {
		currentThread().setName("Parser #" + id);
		while (threadAlive) {
			Document pageDoc = null;
			try {
				//pull page from page queue
				pageDoc = pageQueue.getNextPage();
				//field
				Elements links = pageDoc.select("a[href]");
				HashSet<String> keywords = Keywords.getKeywords();
				Iterator<String> words = keywords.iterator();
				
				//parse out links and send to queue
				for (Element link : links) {
					String url = link.absUrl("href");
					linkQueue.addLink(url);
				}//end for
				
				//search the page for any keywords
			    while(words.hasNext()){
			    	String word = words.next();
			    	String testString = pageDoc.toString();
					String[] parts = testString.split(word);
					if (parts.length != 1) {
						//if a keyword is found calls to add to number of pages value is found on
						Keywords.incrementKeywordPagesCount(word, 1);
						Keywords.incrementKeywordCounts(word, (parts.length - 1));
					}//end if
				}//end while iterator
			} catch (InterruptedException e) {e.printStackTrace();}
			//end try
		}//end while
	}//end run
	
	
}//end class
