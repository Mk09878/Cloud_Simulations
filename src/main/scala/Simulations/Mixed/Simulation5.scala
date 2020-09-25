package Simulations.Mixed

import Utils.DataCenterUtils
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyWorstFit
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

object Simulation5 extends App {

  val logger = LoggerFactory.getLogger("simulation5")

  val dataCenterUtils = new DataCenterUtils()
  val cloudSim = new CloudSim()
  val dataCenterBrokerSimple = dataCenterUtils.createSimpleBroker(cloudSim)
  val dataCenterSimple = dataCenterUtils.createSimpleDataCenter("simulation5", new VmAllocationPolicyWorstFit)
  val vmList = dataCenterUtils.createVm()
  val cloudletList = dataCenterUtils.createCloudlet()
  dataCenterBrokerSimple.submitVmList(vmList).submitCloudletList(cloudletList)
  logger.info("Simulation 5 starting")
  cloudSim.start()

  new CloudletsTableBuilder(dataCenterBrokerSimple.getCloudletFinishedList).build()
  logger.info("Cost for simulation 5 is: " + dataCenterUtils.cost(dataCenterSimple, cloudletList).toString)

}
