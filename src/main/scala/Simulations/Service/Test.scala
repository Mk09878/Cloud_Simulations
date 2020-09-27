package Simulations.Service

import Utils.{CustomBroker, CustomCloudlet, DataCenterUtils, Inputs}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.CloudletSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

object Test extends App {

  val logger = LoggerFactory.getLogger("models_simulation1")
  val cloudSim = new CloudSim()
  val dataCenterUtils = new DataCenterUtils()
  val dataCenterIaaS = dataCenterUtils.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val dataCenterPaaS = dataCenterUtils.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val dataCenterSaaS = dataCenterUtils.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val vmList = dataCenterUtils.createVm()
  val inputs = new Inputs()
  dataCenterIaaS.getCharacteristics.setOs(inputs.os)
  val cloudletList1 = dataCenterUtils.createCustomCloudlet("IaaS", inputs.number_IaaS)
  val cloudletList2 = dataCenterUtils.createCustomCloudlet("PaaS", inputs.number_PaaS)
  val cloudletList3 = dataCenterUtils.createCustomCloudlet("SaaS", inputs.number_SaaS)
  val broker = new CustomBroker(cloudSim)
  val list = broker.assign(dataCenterIaaS, dataCenterPaaS, dataCenterSaaS, cloudletList1, cloudletList2, cloudletList3)
  broker.submitVmList(vmList).submitCloudletList(list.get(0)).submitCloudletList(list.get(1)).submitCloudletList(list.get(2))
  cloudSim.start()

  // Creates and Displays the CloudletTableBuilder
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()


}
