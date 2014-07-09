package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import play.Logger;
import play.libs.EventSource;
import play.mvc.Controller;
import play.mvc.Result;
import channel.Container;

import com.fasterxml.jackson.databind.JsonNode;

public class ServerMessageController extends Controller {

  private static Map<String, List<EventSource>> socketsPerChannel
        = Container.socketsPerChannel;
  
  public static Result index() {
    return ok(views.html.messages.render("ServerSentEvents AngularJS/Play!Java example"));
  }
  
  public static void sendEvent(JsonNode msg) {
	    String channel  = msg.findPath("channel").asText();
	    Logger.debug(msg.toString());
	    Logger.debug(channel);
	    Logger.debug("Having " + socketsPerChannel.size() + " channels...") ;
	    
	    if(socketsPerChannel.containsKey(channel)) {
	    	Logger.debug("Having " + socketsPerChannel.get(channel).size() + " subscribers...") ;
	    	Logger.debug("Sending event...");
	    	
	    	if(socketsPerChannel.get(channel) != null) {
	    		for(EventSource es : socketsPerChannel.get(channel) ){
	    			es.send(EventSource.Event.event(msg));
	    		}
	    	}
	    }
	  }
  
  public static Result send() {
	  sendEvent(request().body().asJson());
	  return ok();
  }
  
  
  public static Result feed(String channel) {
	    String remoteAddress = request().remoteAddress();
	    Logger.info(remoteAddress + " - SSE conntected");
	    Logger.info(channel);
	    
	    return ok(new EventSource() {
	    	
	      @Override
	      public void onConnected() {
	        EventSource currentSocket = this;

	        Logger.info("onConnected");
	        this.onDisconnected(() -> {
	          Logger.info("onDisconected");
	          Logger.info(remoteAddress + " - SSE disconntected");
	          
	          if(socketsPerChannel.containsKey(channel)) {
	            socketsPerChannel.get(channel).remove(currentSocket);
	          }
	        });

	        if(socketsPerChannel.get(channel) == null) {
	        	socketsPerChannel.put(channel, new ArrayList<EventSource>());
	        }
	        socketsPerChannel.get(channel).add(currentSocket);
	      }
	    });
	  }
}
