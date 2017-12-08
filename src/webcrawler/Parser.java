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
			Document pageText = null;
			try {
				//pull page from page queue
				pageText = pageQueue.getNextPage();
				System.out.println("pageText:\n" + pageText +"\n");
				Elements links = pageText.select("a[href]");
				System.out.println("links:" + links.toString());
				for (Element link : links) {
					String url = link.absUrl("href");
					linkQueue.addLink(url);
				}
				
				//search the page for any keywords
				HashSet<String> keywords = Keywords.getKeywords();
				System.out.println("keywords:" +keywords);
				Iterator<String> words = keywords.iterator();
				System.out.println("words:" +words.toString());
			    while(words.hasNext()){
			    	Elements keywordcount = pageText.select(words.next());
			    	System.out.println("keywordcount:" + keywordcount.toString());
			    	System.out.println("keywordcount.size:" + keywordcount.size());
					if (keywordcount.size() != 0) {  
						System.out.println("INSIDE INCREMENT IF");
						//if a keyword is found calls to add to number of pages value is found on
						Keywords.incrementKeywordPagesCount(words.next(), 1);
						Keywords.incrementKeywordCounts(words.next(), keywordcount.size());

					}
					
				}//end while iterator
				
			} catch (InterruptedException e) {e.printStackTrace();}
			
		}//end while
	}//end run
	
	
}//end class
