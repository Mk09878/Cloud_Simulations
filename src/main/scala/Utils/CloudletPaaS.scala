package Utils

import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModel
import org.slf4j.{Logger, LoggerFactory}

class CloudletPaaS(language: String, datastore: String, length: Int, pesNumber: Int, model: UtilizationModel) extends CloudletSimple(length: Int, pesNumber: Int, model: UtilizationModel){
  val logger: Logger = LoggerFactory.getLogger("CloudletPaaS")
  logger.debug("Cloudlet created with language: "+language+", dataBase: "+datastore)

}
