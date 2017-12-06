/**
* Tony Thompson
* Nov 23, 2017
* Parser.java
* 
*/
package webcrawler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

import org.jsoup.nodes.Document;

public class Parser extends Thread {
	//Consumer
	
	//field
	private LinkQueue linkQueue;
	private PageQueue pageQueue;
	private static int keywordsFound;
	public boolean threadAlive = true;
	private int id;
	public static HashMap<String, Integer> keywordCounts = new HashMap<>();

	
	
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
				org.jsoup.select.Elements links = pageText.select("a[href]");
				
				for (org.jsoup.nodes.Element link : links)
				{
					String url = link.absUrl("href");
					linkQueue.addLink(url);
				}
				/*pageText.select(links)
				
				//search page for all links in anchor href elements
				Pattern pattern = Pattern.compile("href=\"(http:.*?)\"");
				Matcher matcher = pattern.matcher((CharSequence) pageText);
				
			    //add link to link queue
				while (matcher.find()) {
				    String link = matcher.group(1);
					linkQueue.addLink(link);
				}//end while*/
			} catch (InterruptedException e) {e.printStackTrace();}
			
			//search the page for any keywords
			ArrayList<String> keywords = ThreadHandler.getKeywords();
			for (int j = 0; j < keywords.size(); j++) {
				//Document testString = pageText;
				org.jsoup.select.Elements keywordcount = pageText.select(keywords.get(j));
				keywordCounts.put(keywords.get(j), keywordcount.size());
				//String[] parts = (testString).split(keywords.get(j));
				//keeps track of keywords found
				//keywordsFound += (parts.length - 1);
			}//end for
		}//end while
	}//end run
	
	/**
	 * returns number of user submitted keywords found so far on pages
	 * @return int keywordsFound
	 */
	public static HashMap<String,Integer> getKeywordsFound() {
		return keywordCounts;
	}//end getKeywordsFound
	
}//end class
