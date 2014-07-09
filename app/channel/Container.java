package channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.libs.EventSource;

/**
 * Simple container for browser per channel subscribers
 * TODO db
 */
public class Container {

	  public static Map<String, List<EventSource>> socketsPerChannel
	        = new HashMap<String, List<EventSource>>();
	
}
