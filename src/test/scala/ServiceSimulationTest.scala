import java.util

import Utils.{CloudletPaaS, DataCenterUtils, IaaS_Inputs, PaaS_Inputs}
import com.typesafe.config.{Config, ConfigFactory}
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.vms.Vm
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.scalatest.FunSuite
import org.slf4j.{Logger, LoggerFactory}

import scala.jdk.CollectionConverters._

class ServiceSimulationTest extends FunSuite{

  val logger: Logger = LoggerFactory.getLogger("models_simulation1")
  val cloudSim = new CloudSim()
  val broker = new DatacenterBrokerSimple(cloudSim)
  val config: Config = ConfigFactory.load("models_simulation1")

  /* ---- IaaS ---- */
  val dataCenterUtils_IaaS = new DataCenterUtils()
  val dataCenterSimple_IaaS: DatacenterSimple = dataCenterUtils_IaaS.createIaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  test( "DataCenter IaaS is created properly"){
    assert(dataCenterSimple_IaaS != null)
  }
  val vmList_IaaS: util.List[Vm] = dataCenterUtils_IaaS.createIaaSVm(4, 500, 4, 10000, 10000, 10000)
  test("IaaS Vm list is created properly"){
    assert(vmList_IaaS != null)
    assert(config.getInt("models_simulation1.vm.number") === vmList_IaaS.size())
  }
  val cloudletList_IaaS: util.List[Cloudlet] = dataCenterUtils_IaaS.createCloudlet()
  cloudletList_IaaS.asScala.map(x => x.assignToDatacenter(dataCenterSimple_IaaS))
  logger.info(dataCenterSimple_IaaS.getCharacteristics.getOs)
  broker.submitVmList(vmList_IaaS).submitCloudletList(cloudletList_IaaS)

  /* ---- PaaS ---- */
  val dataCenterUtils_PaaS = new DataCenterUtils()
  val dataCenterSimple_PaaS: DatacenterSimple = dataCenterUtils_PaaS.createPaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  test( "DataCenter PaaS is created properly"){
    assert(dataCenterSimple_PaaS != null)
  }
  val vmList_PaaS: util.List[Vm] = dataCenterUtils_PaaS.createVm()
  val cloudletList_PaaS: util.List[CloudletPaaS] = dataCenterUtils_PaaS.createPaaSCloudlet("Scala", "mySQL")
  test("PaaS cloudlet list is created properly"){
    assert(cloudletList_PaaS != null)
    assert(config.getInt("models_simulation1.cloudLet.number") === cloudletList_PaaS.size())
  }
  cloudletList_PaaS.asScala.map(x => x.assignToDatacenter(dataCenterSimple_PaaS))
  logger.info(dataCenterSimple_PaaS.getCharacteristics.getOs)
  broker.submitVmList(vmList_PaaS).submitCloudletList(cloudletList_PaaS)

  /* ---- SaaS ---- */
  val dataCenterUtils_SaaS = new DataCenterUtils()
  val dataCenterSimple_SaaS: DatacenterSimple = dataCenterUtils_SaaS.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val vmList_SaaS: util.List[Vm] = dataCenterUtils_SaaS.createVm()
  val cloudletList_SaaS: util.List[Cloudlet] = dataCenterUtils_SaaS.createCloudlet()
  cloudletList_SaaS.asScala.map(x => x.assignToDatacenter(dataCenterSimple_SaaS))
  logger.info(dataCenterSimple_SaaS.getCharacteristics.getOs)
  broker.submitVmList(vmList_SaaS).submitCloudletList(cloudletList_SaaS)

}
