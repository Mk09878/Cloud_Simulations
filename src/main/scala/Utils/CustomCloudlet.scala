package Utils

import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModel

import org.slf4j.{Logger, LoggerFactory}

class CustomCloudlet(which: String, lang: String, dataStore: String, length: Int, pesNumber: Int, model: UtilizationModel) extends CloudletSimple(length: Int, pesNumber: Int, model: UtilizationModel){
  val service: String = which
  val logger: Logger = LoggerFactory.getLogger("CustomCloudlet")
  if(lang != "" && dataStore != ""){
    logger.info("PaaS Cloudlet created with language: "+lang+" and Data Storage: "+dataStore)
  }
  def this(which: String, length: Int, pesNumber: Int, model: UtilizationModel) = this(which, "", "", length, pesNumber, model)
}
