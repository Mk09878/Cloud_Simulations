package Utils

import org.slf4j.{Logger, LoggerFactory}

class Inputs {

  val logger: Logger = LoggerFactory.getLogger("IaaS_Inputs")

  logger.info("---- IaaS Inputs ----")
  logger.info("Select OS")
  logger.info("\n1. Windows\n2. Linux\n3. macOS")

  val os: String = scala.io.StdIn.readLine()

  logger.info("---- PaaS Inputs ----")
  logger.info("Select Programming Language")
  logger.info("\n1. Scala\n2. Java\n3. Python")

  val lang: String = scala.io.StdIn.readLine()

  logger.info("Select Database")
  logger.info("\n1. mySQL\n2. PostgreSQL\n3. Microsoft Access")

  val db: String = scala.io.StdIn.readLine()

  logger.info("---- Cloudlet Inputs  ----")

  logger.info("Enter number of IaaS Cloudlets")

  val number_IaaS: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter number of PaaS Cloudlets")

  val number_PaaS: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter number of SaaS Cloudlets")

  val number_SaaS: Int = scala.io.StdIn.readLine().toInt

}
