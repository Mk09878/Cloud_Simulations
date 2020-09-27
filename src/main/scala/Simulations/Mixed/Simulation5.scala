package Simulations.Mixed

import Utils.DataCenterUtils
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicyRoundRobin, VmAllocationPolicyWorstFit}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

object Simulation5 extends App {

  val logger = LoggerFactory.getLogger("simulation5")

  val dataCenterUtils = new DataCenterUtils()

  // Creating CloudSim object
  val cloudSim = new CloudSim()

  // Creating Broker
  val dataCenterBrokerSimple = dataCenterUtils.createSimpleBroker(cloudSim)

  // Creating DataCenter
  val dataCenterSimple = dataCenterUtils.createSimpleDataCenter("simulation5", new VmAllocationPolicyRoundRobin)

  // Creating VMs
  val vmList = dataCenterUtils.createVm()

  // Creating Cloudlets
  val cloudletList = dataCenterUtils.createCloudlet()

  // Submit VmList and CloudletLists to the broker
  dataCenterBrokerSimple.submitVmList(vmList).submitCloudletList(cloudletList)

  logger.info("Simulation 5 starting")

  cloudSim.start()

  new CloudletsTableBuilder(dataCenterBrokerSimple.getCloudletFinishedList).build()

  // Display the total costs for running the simulation
  logger.info("Cost for simulation 5 is: " + dataCenterUtils.cost(dataCenterSimple, cloudletList).toString)

}
