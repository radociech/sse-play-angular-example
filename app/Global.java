import play.Application;
import play.GlobalSettings;
import play.Logger;
import actor.Manager;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class Global extends GlobalSettings {

  @Override
  public void onStart(Application app) {
	  Logger.debug("onStart Global");
	  
	  //create actor system
	  final ActorSystem system = ActorSystem.create("ActorSystem");
	  //create manager actor
	  final ActorRef manager = system.actorOf(Props.create(Manager.class),"manager");
	  //tell manager to start
	  manager.tell(new String("Start"), system.guardian());
	  
  }

  @Override
  public void onStop(Application app) {
    Logger.debug("onStop Global");
  }
}
