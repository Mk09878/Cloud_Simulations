package Simulations.Service

import java.util

import Utils.{CloudletPaaS, DataCenterUtils, IaaS_Inputs, PaaS_Inputs}
import org.cloudbus.cloudsim.allocationpolicies._
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._

object Simulation1 extends App {

  val logger = LoggerFactory.getLogger("models_simulation1")
  val cloudSim = new CloudSim()
  val broker = new DatacenterBrokerSimple(cloudSim)

  /* ---- IaaS ---- */
  val IaaSInputs = new IaaS_Inputs()
  val dataCenterUtils_IaaS = new DataCenterUtils()
  val dataCenterSimple_IaaS = dataCenterUtils_IaaS.createIaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val vmList_IaaS = dataCenterUtils_IaaS.createIaaSVm(IaaSInputs.number, IaaSInputs.mips, IaaSInputs.pes, IaaSInputs.ram, IaaSInputs.bw, IaaSInputs.size)
  val cloudletList_IaaS = dataCenterUtils_IaaS.createCloudlet()
  cloudletList_IaaS.asScala.map(x => x.assignToDatacenter(dataCenterSimple_IaaS))
  logger.info(dataCenterSimple_IaaS.getCharacteristics.getOs)
  broker.submitVmList(vmList_IaaS).submitCloudletList(cloudletList_IaaS)

  /* ---- PaaS ---- */
  val PaaSInputs = new PaaS_Inputs()
  val dataCenterUtils_PaaS = new DataCenterUtils()
  val dataCenterSimple_PaaS = dataCenterUtils_PaaS.createPaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val vmList_PaaS = dataCenterUtils_PaaS.createVm()
  val cloudletList_PaaS: util.List[CloudletPaaS] = dataCenterUtils_PaaS.createPaaSCloudlet(PaaSInputs.lang, PaaSInputs.db)
  cloudletList_PaaS.asScala.map(x => x.assignToDatacenter(dataCenterSimple_PaaS))
  logger.info(dataCenterSimple_PaaS.getCharacteristics.getOs)
  broker.submitVmList(vmList_PaaS).submitCloudletList(cloudletList_PaaS)

  /* ---- SaaS ---- */
  val dataCenterUtils_SaaS = new DataCenterUtils()
  val dataCenterSimple_SaaS = dataCenterUtils_SaaS.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val vmList_SaaS = dataCenterUtils_SaaS.createVm()
  val cloudletList_SaaS = dataCenterUtils_SaaS.createCloudlet()
  cloudletList_SaaS.asScala.map(x => x.assignToDatacenter(dataCenterSimple_SaaS))
  logger.info(dataCenterSimple_SaaS.getCharacteristics.getOs)
  broker.submitVmList(vmList_SaaS).submitCloudletList(cloudletList_SaaS)

  logger.info("Simulation 1 starting")
  cloudSim.start()

  // Creates and Displays the CloudletTableBuilder
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()

  // Displays the total costs for each of the service
  logger.info("Cost for IaaS model is: " + dataCenterUtils_IaaS.cost(dataCenterSimple_IaaS, cloudletList_IaaS).toString)

  logger.info("Cost for PaaS model is: " + dataCenterUtils_PaaS.costPaaS(dataCenterSimple_PaaS, cloudletList_PaaS).toString)

  logger.info("Cost for SaaS model is: " + dataCenterUtils_SaaS.cost(dataCenterSimple_SaaS, cloudletList_SaaS).toString)
}
