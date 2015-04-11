
import akka.actor._
import java.math.BigInteger

class Worker extends Actor {

  private val calculator = new Calculator

  def receive() = {
    case msg: Work =>
      val result = new Result(calculator.calculateFactorial(msg.getN()))
      sender.tell(result, this.context.parent)

    case _ =>
      println("I don't know what to do with this...")

  }
}