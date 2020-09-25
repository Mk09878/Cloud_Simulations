import java.util

import Utils.{CloudletPaaS, SimulatedCloudLet, SimulatedDataCenterIaaS, SimulatedDataCenterPaaS, SimulatedDataCenterSaaS, SimulatedHost}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyRoundRobin}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull
import org.scalatest.FunSuite

import scala.jdk.CollectionConverters._
import scala.collection.mutable.ListBuffer

class DataCenterUtilsServicesTest extends FunSuite{

  var which: String = _

  /**
   * Creates a Simple DataCenter, assigns operating costs to it
   * @param which
   * @param vmAllocationPolicy
   * @return SimpleDataCenter object
   */
  def createSaaSDataCenter(cloudSim: CloudSim, which: String, vmAllocationPolicy: VmAllocationPolicy): DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenterSaaS(which)
    test("Simulated SaaS Data Center is created properly"){
      assert(simulatedDataCenter != null)
    }
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    dc
  }

  /**
   * Creates a Simple IaaS DataCenter, assigns the os and operating costs to it
   * @param which
   * @param os
   * @param vmAllocationPolicy
   * @return SimpleDataCenter object
   */
  def createIaaSDataCenter(cloudSim: CloudSim, which: String, os: String, vmAllocationPolicy: VmAllocationPolicy) : DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenterIaaS(which)
    test("Simulated IaaS Data Center is created properly"){
      assert(simulatedDataCenter != null)
    }
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setOs(os)
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    dc
  }

  /**
   * Creates a PaaS DataCenter, assigns operating costs to it
   * @param which
   * @param vmAllocationPolicy
   * @return SimpleDataCenter object
   */
  def createPaaSDataCenter(cloudSim: CloudSim, which: String, vmAllocationPolicy: VmAllocationPolicy): DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenterPaaS(which)
    test("Simulated PaaS Data Center is created properly"){
      assert(simulatedDataCenter != null)
    }
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    dc
  }

  def createHost(): util.List[Host] = {
    val simulatedHost = new SimulatedHost(which)
    val number = simulatedHost.number
    val peList: List[Pe] = List.tabulate(simulatedHost.pesNumber) (_ => new PeSimple(simulatedHost.mips))

    var hostList: ListBuffer[Host] = new ListBuffer[Host]
    if(simulatedHost.scheduler == "time"){
      hostList = ListBuffer.tabulate(number) (_ => new HostSimple(simulatedHost.ram, simulatedHost.bw, simulatedHost.storage, peList.asJava).setVmScheduler(new VmSchedulerTimeShared()))
    }
    else{
      hostList = ListBuffer.tabulate(number) (_ => new HostSimple(simulatedHost.ram, simulatedHost.bw, simulatedHost.storage, peList.asJava).setVmScheduler(new VmSchedulerSpaceShared()))
    }

    hostList.asJava
  }


  /**
   * Creates a list of PaaS Cloudlets based on the specified parameters in the config file and the user input
   * @return List of Cloudlet
   */
  def createPaaSCloudlet(language: String, dataStore: String) : util.List[CloudletPaaS] = {
    val simulatedCloudlet = new SimulatedCloudLet(which)

    test("Simulated Cloudlet is created properly"){
      assert(simulatedCloudlet != null)
    }

    val cloudletList: List[CloudletPaaS] = List.tabulate(simulatedCloudlet.number)(_ => new CloudletPaaS(language, dataStore, simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    cloudletList.asJava
  }

  val cloudSim = new CloudSim()
  val saas_dc: DatacenterSimple = createSaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val paas_dc: DatacenterSimple = createPaaSDataCenter(cloudSim, "models_simulation1", new VmAllocationPolicyRoundRobin)
  val iaas_dc: DatacenterSimple = createIaaSDataCenter(cloudSim, "models_simulation1", "Linux", new VmAllocationPolicyRoundRobin)
  createPaaSCloudlet("Scala", "mySql")

}
