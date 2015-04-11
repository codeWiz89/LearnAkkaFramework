import akka.actor._
import java.math.BigInteger
import akka.routing._
import scala.collection.mutable.ListBuffer

object Main extends App {

  val system = ActorSystem("CalcSystem")
  val masterActor = system.actorOf(Props[Master], "master")
  masterActor.tell(new Calculate, ActorRef.noSender)

}

class Master extends Actor {

  private val messages: Int = 50;
  var resultList: List[BigInteger] = List()
  var startTime = 0.0

  val workerActors = context.actorOf(Props[Worker].withRouter(RoundRobinPool(5)), name = "WorkerActors")

  //  var router = {
  //    val routees = Vector.fill(5) {
  //      val r = context.actorOf(Props[Worker])
  //      context watch r
  //      ActorRefRoutee(r)
  //    }
  //    Router(RoundRobinRoutingLogic(), routees)
  //  }

  def receive() = {
    case msg: Calculate =>
      startTime = System.currentTimeMillis()
      processMessages()
    case msg: Result =>
      resultList ::= msg.getFactorial()
      if (resultList.length == messages) {
        end
      }
  }

  private def processMessages() {
    var i: Int = 0
    for (i <- 1 to messages) {
      workerActors.tell(new Work(i), self)
      //      router.route(new Work(i), self)
    }
  }

  private def end() {
    println("List = " + resultList.sorted)
    println((System.currentTimeMillis() - startTime).toFloat / 1000)
    this.context.system.shutdown
  }
}