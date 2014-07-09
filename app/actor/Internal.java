package actor;

import java.text.SimpleDateFormat;
import java.util.Date;

import play.Logger;
import play.libs.Json;
import akka.actor.UntypedActor;

import com.fasterxml.jackson.databind.JsonNode;

import controllers.ServerMessageController;

public class Internal extends UntypedActor  {

	
	@Override
	public void onReceive(Object message) throws Exception {
		Logger.debug("onReceive Internal message actor");
		if(message instanceof SendMessage) {
			JsonNode msg = Json.newObject()
			        .put("message", new SimpleDateFormat("hh:mm:ss").format(new Date()))
			        .put("channel", "1");
			
			ServerMessageController.sendEvent(msg);
		} else {
			unhandled(message);
		}
	}
	
	//define message
	public static class SendMessage {}

}
