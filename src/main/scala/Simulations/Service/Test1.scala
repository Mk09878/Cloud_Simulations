package Simulations.Service

import java.util

import Utils.{CloudletPaaS, CustomBroker, DataCenterUtils, IaaS_Inputs, PaaS_Inputs}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

object Test1 extends App {

  val logger = LoggerFactory.getLogger("models_simulation1")
  val cloudSim = new CloudSim()
  val broker = new CustomBroker(cloudSim)
  val dataCenterUtils = new DataCenterUtils()

  // Creating DataCenters
  val dataCenterSimple_IaaS = dataCenterUtils.createIaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val dataCenterSimple_PaaS = dataCenterUtils.createPaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val dataCenterSimple_SaaS = dataCenterUtils.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)

  // Take inputs
  val IaaSInputs = new IaaS_Inputs()
  val PaaSInputs = new PaaS_Inputs()

  // Creating VMs
  val vmList1 = dataCenterUtils.createIaaSVm(IaaSInputs.number, IaaSInputs.mips, IaaSInputs.pes, IaaSInputs.ram, IaaSInputs.bw, IaaSInputs.size)
  val vmList2 = dataCenterUtils.createVm()
  val vmList3 = dataCenterUtils.createVm()

  // Creating Cloudlets
  val cloudletList = dataCenterUtils.createAllCloudlets(PaaSInputs.lang, PaaSInputs.db)

  // Broker assigns cloudlets to corresponding datacenters
  val list = broker.assign1(dataCenterSimple_IaaS, dataCenterSimple_PaaS, dataCenterSimple_SaaS, cloudletList)

  // Submit VmList and CloudletLists to the broker
  broker.submitVmList(vmList1).submitCloudletList(list.get(0))
  broker.submitVmList(vmList2).submitCloudletList(list.get(1))
  broker.submitVmList(vmList3).submitCloudletList(list.get(2))

  logger.info("Simulation 1 starting")
  cloudSim.start()

  // Creates and Displays the CloudletTableBuilder
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()

  // Displays the total costs for each of the service
  logger.info("Cost for IaaS model is: " + dataCenterUtils.cost1(dataCenterSimple_IaaS, list.get(0)).toString)

  logger.info("Cost for PaaS model is: " + dataCenterUtils.cost1(dataCenterSimple_PaaS, list.get(1)).toString)

  logger.info("Cost for SaaS model is: " + dataCenterUtils.cost1(dataCenterSimple_SaaS, list.get(2)).toString)

}
