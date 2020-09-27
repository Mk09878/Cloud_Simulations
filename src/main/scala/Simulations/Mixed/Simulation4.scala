package Simulations.Mixed

import Utils.DataCenterUtils
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

object Simulation4 extends App {

  val logger = LoggerFactory.getLogger("simulation4")

  val dataCenterUtils = new DataCenterUtils()

  // Creating CloudSim object
  val cloudSim = new CloudSim()

  // Creating Broker
  val dataCenterBrokerSimple = dataCenterUtils.createSimpleBroker(cloudSim)

  // Creating DataCenter
  val dataCenterSimple = dataCenterUtils.createSimpleDataCenter("simulation4", new VmAllocationPolicyRoundRobin)

  // Configuring the Network
  dataCenterUtils.configureNetwork("topology.brite", dataCenterSimple, dataCenterBrokerSimple)

  // Creating VMs
  val vmList = dataCenterUtils.createVm()

  // Creating Cloudlets
  val cloudletList = dataCenterUtils.createCloudlet()

  // Submit VmList and CloudletLists to the broker
  dataCenterBrokerSimple.submitVmList(vmList).submitCloudletList(cloudletList)

  logger.info("Simulation 4 starting")

  cloudSim.start()

  new CloudletsTableBuilder(dataCenterBrokerSimple.getCloudletFinishedList).build()

  // Display the total costs for running the simulation
  logger.info("Cost for simulation 4 is: " + dataCenterUtils.cost(dataCenterSimple, cloudletList).toString)

}
