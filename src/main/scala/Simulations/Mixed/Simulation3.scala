package Simulations.Mixed

import Utils.DataCenterUtils
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

object Simulation3 extends App {
  val logger = LoggerFactory.getLogger("simulation3")

  val dataCenterUtils = new DataCenterUtils()
  val cloudSim = new CloudSim()
  val dataCenterBrokerSimple = dataCenterUtils.createSimpleBroker(cloudSim)
  val dataCenterSimple = dataCenterUtils.createSimpleDataCenter("simulation3", new VmAllocationPolicyRoundRobin)
  val vmList = dataCenterUtils.createVm()
  val cloudletList = dataCenterUtils.createCloudlet()
  dataCenterBrokerSimple.submitVmList(vmList).submitCloudletList(cloudletList)
  logger.info("Simulation 3 starting")
  cloudSim.start()

  new CloudletsTableBuilder(dataCenterBrokerSimple.getCloudletFinishedList).build()
  logger.info("Cost for simulation 3 is: " + dataCenterUtils.cost(dataCenterSimple, cloudletList).toString)

}
