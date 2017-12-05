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

private Timer timer = new Timer();
	
		/**
		 * starts a timer thread to trigger every 5 seconds showing statLoop()
		 */
	    @Override
	    public void run() {
	    	//loops every 5 seconds no delay
		    timer.scheduleAtFixedRate(new StatLoop(), 0, 5000);
	    }//end run  
	    
	    class StatLoop extends TimerTask {
	        public void run() {
		    	WebCrawlerDriver.printStats();
	        }//end run
	    }//end StatLoop
	    
	    /**
	     * calls timer.cancel() to stop the timer thread
	     */
	    public void stopTimer()  {
	    	timer.cancel();
	    }
	    
}//end class
//Michael Testing