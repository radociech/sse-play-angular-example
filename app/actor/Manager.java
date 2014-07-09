package actor;

import java.util.concurrent.TimeUnit;

import play.Logger;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Manager extends UntypedActor {

	public void start() {
		
		ActorSystem system = context().system();
		final ActorRef senderActor = system.actorOf(Props.create(Internal.class),"sender");
				
		//create schedule for internal actor
		system.scheduler().schedule(
				Duration.apply(1, TimeUnit.SECONDS),
				Duration.apply(8, TimeUnit.SECONDS), 
				senderActor, 
				new Internal.SendMessage(),
				system.dispatcher(),
				self()
				);
		Logger.debug("Actor created and scheduled");
		
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		Logger.debug("on Recive Manager Actor");
		start();
	}
	
}
