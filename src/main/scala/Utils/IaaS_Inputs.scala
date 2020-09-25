package Utils

import org.slf4j.{Logger, LoggerFactory}

class IaaS_Inputs {
  val logger: Logger = LoggerFactory.getLogger("IaaS_Inputs")

  logger.info("---- IaaS  ----")
  logger.info("Select OS")
  logger.info("\n1. Windows\n2. Linux\n3. macOS")

  val os: String = scala.io.StdIn.readLine()

  logger.info("Enter number of VMs")
  val number: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter VM Mips (Less than or equal to 500)")
  val mips: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter number of Pes for VM (Less than or equal to 4)")
  val pes: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter VM Ram (Less than or equal to 10000)")
  val ram: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter VM Bandwidth (Less than or equal to 10000)")
  val bw: Int = scala.io.StdIn.readLine().toInt

  logger.info("Enter VM Size (Less than or equal to 100000)")
  val size: Int = scala.io.StdIn.readLine().toInt

}
