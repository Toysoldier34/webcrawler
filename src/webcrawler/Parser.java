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
				Elements links = pageText.select("a[href]");
				
				for (Element link : links)
				{
					String url = link.absUrl("href");
					linkQueue.addLink(url);
				}
				
				//search the page for any keywords
				HashSet<String> keywords = Keywords.getKeywords();
				Iterator<String> words = keywords.iterator();
			    while(words.hasNext()){
					//Document testString = pageText;
					Elements keywordcount = pageText.select(words.next());
					Keywords.incrementKeywordCounts(words.next(), keywordcount.size());
					
					if (keywordcount.size() != 0) {  
						//if a keyword is found calls to add to number of pages value is found on
						Keywords.incrementKeywordPagesCount(words.next(), 1);
					}
					
					
					//String[] parts = (testString).split(keywords.get(j));
					//keeps track of keywords found
					//keywordsFound += (parts.length - 1);
				}//end while iterator
				
			} catch (InterruptedException e) {e.printStackTrace();}
			
		}//end while
	}//end run
	
	
}//end class
