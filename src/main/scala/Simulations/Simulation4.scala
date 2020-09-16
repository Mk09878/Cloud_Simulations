package Simulations

import Utils.DataCenterUtils
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

object Simulation4 extends App {

  val logger = LoggerFactory.getLogger("simulation4")

  val dataCenterUtils = new DataCenterUtils()
  val cloudSim = new CloudSim()
  val dataCenterBrokerSimple = dataCenterUtils.createSimpleBroker(cloudSim)
  val dataCenterSimple = dataCenterUtils.createSimpleDataCenter("simulation4", new VmAllocationPolicyRoundRobin)
  dataCenterUtils.configureNetwork("topology.brite", dataCenterSimple, dataCenterBrokerSimple)
  val vmList = dataCenterUtils.createVm()
  val cloudletList = dataCenterUtils.createCloudlet()
  dataCenterBrokerSimple.submitVmList(vmList).submitCloudletList(cloudletList)
  logger.info("Simulation 4 starting")
  cloudSim.start()

  new CloudletsTableBuilder(dataCenterBrokerSimple.getCloudletFinishedList).build()
  logger.info("Cost for simulation 4 is: " + dataCenterUtils.cost(dataCenterSimple, cloudletList).toString)

}
