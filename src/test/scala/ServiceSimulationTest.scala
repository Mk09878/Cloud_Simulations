import java.util

import Utils.{CustomBroker, CustomCloudlet, DataCenterUtils, IaaS_Inputs, PaaS_Inputs}
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
  val config: Config = ConfigFactory.load("models_simulation1")
  val cloudSim = new CloudSim()
  val broker = new CustomBroker(cloudSim)
  val dataCenterUtils = new DataCenterUtils()

  // Creating DataCenters
  val dataCenterSimple_IaaS: DatacenterSimple = dataCenterUtils.createIaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  test( "DataCenter IaaS is created properly"){
    assert(dataCenterSimple_IaaS != null)
  }
  val dataCenterSimple_PaaS: DatacenterSimple = dataCenterUtils.createPaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  test( "DataCenter PaaS is created properly"){
    assert(dataCenterSimple_PaaS != null)
  }
  val dataCenterSimple_SaaS: DatacenterSimple = dataCenterUtils.createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  test( "DataCenter SaaS is created properly"){
    assert(dataCenterSimple_SaaS != null)
  }

  // Creating VMs
  val vmList1: util.List[Vm] = dataCenterUtils.createIaaSVm(4, 500, 4, 10000, 10000, 10000)
  test("IaaS Vm list is created properly"){
    assert(vmList1 != null)
    assert(config.getInt("models_simulation1.vm.number") === vmList1.size())
  }
  val vmList2: util.List[Vm] = dataCenterUtils.createVm()
  val vmList3: util.List[Vm] = dataCenterUtils.createVm()

  // Creating Cloudlets
  val cloudletList: util.List[CustomCloudlet] = dataCenterUtils.createAllCloudlets("Scala", "mySQL")
  test("Cloudlet is created properly"){
    assert(cloudletList != null)
    assert(config.getInt("models_simulation1.cloudLet.number") * 3 === cloudletList.size())
  }

  // Broker assigns cloudlets to corresponding datacenters
  val list: util.List[util.List[CustomCloudlet]] = broker.assign(dataCenterSimple_IaaS, dataCenterSimple_PaaS, dataCenterSimple_SaaS, cloudletList)
  test("Broker Successfully Assigned Services Correctly"){
    list.get(0).forEach(x => assert(x.service == "IaaS"))
    list.get(1).forEach(x => assert(x.service == "PaaS"))
    list.get(2).forEach(x => assert(x.service == "SaaS"))
  }

  // Submit VmList and CloudletLists to the broker
  broker.submitVmList(vmList1).submitCloudletList(list.get(0))
  broker.submitVmList(vmList2).submitCloudletList(list.get(1))
  broker.submitVmList(vmList3).submitCloudletList(list.get(2))

}
