package Utils

import org.slf4j.{Logger, LoggerFactory}

class PaaS_Inputs {

  val logger: Logger = LoggerFactory.getLogger("PaaS_Inputs")

  logger.info("---- PaaS  ----")
  logger.info("Select Programming Language")
  logger.info("\n1. Scala\n2. Java\n3. Python")

  val lang: String = scala.io.StdIn.readLine()

  logger.info("Select Database")
  logger.info("\n1. mySQL\n2. PostgreSQL\n3. Microsoft Access")

  val db: String = scala.io.StdIn.readLine()

}
