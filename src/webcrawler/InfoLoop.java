/**
* Tony Thompson
* Nov 30, 2017
* InfoLoop.java
* 
*/
package webcrawler;

import java.util.Timer;
import java.util.TimerTask;

public class InfoLoop extends Thread {
	
	    @Override
	    public void run() {
	    	Timer timer = new Timer();
	    	//loops every 5 seconds no delay
		    timer.scheduleAtFixedRate(new StatLoop(), 0, 5000);
	    }//end run  
	    
	    class StatLoop extends TimerTask {
	        public void run() {
		    	WebCrawlerDriver.printStats();
	        }//end run
	    }//end StatLoop
}//end class
//Michael Testing