package Utils

import org.slf4j.{Logger, LoggerFactory}

class IaaS_Inputs {

  val logger: Logger = LoggerFactory.getLogger("IaaS_Inputs")

  logger.info("---- IaaS  ----")

  logger.info("Select OS")
  logger.info("\n1. Windows\n2. Linux\n3. macOS")
  val os: String = scala.io.StdIn.readLine()

  logger.info("RANGE OF PARAMETERS ARE PROVIDED. DEVIATING FROM THESE MIGHT LEAD TO INFINITE LOOPS")
  logger.info("Enter number of VMs (Between 2 and 4 inclusive)")
  val number: Int = scala.io.StdIn.readLine().toInt
  if(!(2 to 4 contains number))
    logger.warn("You violated the expected range")

  logger.info("Enter VM Mips (Between 300 and 1000 inclusive)")
  val mips: Int = scala.io.StdIn.readLine().toInt
  if(!(300 to 1000 contains mips))
    logger.warn("You violated the expected range")

  logger.info("Enter number of Pes for VM (Between 3 and 4 inclusive)")
  val pes: Int = scala.io.StdIn.readLine().toInt
  if(!(3 to 4 contains pes))
    logger.warn("You violated the expected range")

  logger.info("Enter VM Ram (Between 5000 and 10000 inclusive)")
  val ram: Int = scala.io.StdIn.readLine().toInt
  if(!(5000 to 10000 contains ram))
    logger.warn("You violated the expected range")

  logger.info("Enter VM Bandwidth (Between 5000 and 10000 inclusive)")
  val bw: Int = scala.io.StdIn.readLine().toInt
  if(!(5000 to 10000 contains bw))
    logger.warn("You violated the expected range")

  logger.info("Enter VM Size (Between 5000 and 100000 inclusive)")
  val size: Int = scala.io.StdIn.readLine().toInt
  if(!(5000 to 100000 contains size))
    logger.warn("You violated the expected range")

}
