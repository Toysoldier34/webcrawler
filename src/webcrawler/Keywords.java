/**
* Tony Thompson
* Dec 7, 2017
* Keywords.java
* 
*/
package webcrawler;

import java.util.HashMap;
import java.util.HashSet;

public class Keywords {
	
	public static HashSet<String> keywords = new HashSet<String>();
	public static HashMap<String, Integer> keywordCounts = new HashMap<>();
	public static HashMap<String, Integer> keywordPagesCount = new HashMap<>();
	

	
	
	/**
	 * adds given String to keyword list to check for
	 * @param keyword String to add
	 */
	public static void addKeywords(String keyword) {
		keywords.add(keyword);
		//takes new keyword and adds to hashmap sending 0 for new word
		incrementKeywordCounts(keyword, 0);  
		incrementKeywordPagesCount(keyword, 0);
	}//end setKeywords
	
	
	/**
	 * returns list of user entered keywords to check for
	 * @return ArrayList<String> of keywords
	 */
	public static HashSet<String> getKeywords() {
		return keywords;
	}//end getKeywords
	
	/**
	 * synchronized
	 * adds given String to keywordCount and sets to 0 if new
	 * or increments value if existing
	 * @param keyword String to increment or add
	 * @param adding int count to add to given keyword
	 */
	public synchronized static void incrementKeywordCounts(String keyword, int adding) {
		int value = 0;
		if (keywordCounts.containsKey(keyword)) {
			value = keywordCounts.get(keyword);
			value += adding;
		}//end if
		keywordCounts.put(keyword, value);
	}//end incrementKeywordCounts    
	
	
	/**
	 * counts number of pages a keyword is found on
	 * @param keyword String to increment or add
	 * @param adding int 0 if new or 1 if existing and found on a page
	 */
	public synchronized static void incrementKeywordPagesCount(String keyword, int adding) {
		int value = 0;
		if (keywordPagesCount.containsKey(keyword)) {
			value = keywordPagesCount.get(keyword);
			value += adding;
		}//end if
		keywordPagesCount.put(keyword, value);
	}//end incrementKeywordPagesCount   
	
	
	/**
	 * returns number of user submitted keywords found so far on pages
	 * @return int keywordsFound
	 */
	public static HashMap<String,Integer> getKeywordCounts() {
		return keywordCounts;
	}//end getKeywordsFound
	
	
	
	
}
