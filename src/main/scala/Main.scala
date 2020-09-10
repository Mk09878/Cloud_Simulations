import com.typesafe.config.ConfigFactory
import org.slf4j.{Logger, LoggerFactory}

object Main extends App {
  val logger = LoggerFactory.getLogger("Main")
  val config = ConfigFactory.load()
  println("Hello World from Main")
  println(config.getString("app.answer"))
  logger.debug("Hello World from Debug!")
}
