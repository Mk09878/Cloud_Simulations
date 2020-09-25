package Utils

import java.util

import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicy
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmScheduler, VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels._
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

class DataCenterUtils {

  val logger: Logger = LoggerFactory.getLogger("DataCenterUtils")
  var which: String = _
  var cloudSim: CloudSim = _


  /**
   * Creates a SimpleBroker object
   * @param cloudSim
   * @return SimpleBroker object
   */
  def createSimpleBroker(cloudSim: CloudSim): DatacenterBrokerSimple = {
    this.cloudSim = cloudSim
    new DatacenterBrokerSimple(cloudSim)
  }

  /**
   * Creates a Simple DataCenter, assigns operating costs to it
   * @param which
   * @param vmAllocationPolicy
   * @return SimpleDataCenter object
   */
  def createSimpleDataCenter(which: String, vmAllocationPolicy: VmAllocationPolicy): DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenter(which)
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    dc
  }

  /**
   * Creates a Simple DataCenter, assigns operating costs to it
   * @param which
   * @param vmAllocationPolicy
   * @return SimpleDataCenter object
   */
  def createSaaSDataCenter(cloudSim: CloudSim, which: String, vmAllocationPolicy: VmAllocationPolicy): DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenterSaaS(which)
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
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    dc
  }



  /**
   * Sets the network topology
   * @param NETWORK_TOPOLOGY_FILE
   * @param dc
   * @param broker
   */
  def configureNetwork(NETWORK_TOPOLOGY_FILE : String, dc: Datacenter, broker: DatacenterBroker): Unit = {
    val networkTopology = BriteNetworkTopology.getInstance(NETWORK_TOPOLOGY_FILE)
    cloudSim.setNetworkTopology(networkTopology)
    networkTopology.mapNode(dc.getId, 0)
    networkTopology.mapNode(broker.getId, 3)
  }

  /**
   * Creates a list of hosts based on the specified parameters in the config file
   * @return List of Host
   */
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
   * Creates a list of Vms based on the specified parameters in the config file
   * @return List of Vm
   */
  def createVm(): util.List[Vm] ={
    val simulatedVm = new SimulatedVm(which)
    val vmList: List[Vm] = List.tabulate(simulatedVm.number) (_ => {
      val vm = new VmSimple(simulatedVm.mips, simulatedVm.pes)
      vm.setRam(simulatedVm.ram).setBw(simulatedVm.bw).setSize(simulatedVm.size)
      vm
    })
    vmList.asJava
  }

  /*def createCustomVm(service: String): util.List[CustomVm] ={
    val simulatedVm = new SimulatedVm(which)
    val vmList: List[CustomVm] = List.tabulate(simulatedVm.number) (_ => {
      val vm = new CustomVm(service, simulatedVm.mips, simulatedVm.pes)
      vm.setRam(simulatedVm.ram).setBw(simulatedVm.bw).setSize(simulatedVm.size)
      vm
    })
    vmList.asJava
  }*/

  /**
   * Creates a list of IaaS Vms based on the user input
   * @return List of Vm
   */
  def createIaaSVm(number: Int, mips: Int, pes: Int, ram: Int, bw: Int, size: Int): util.List[Vm] ={
    val vmList: List[Vm] = List.tabulate(number) (_ => {
      val vm = new VmSimple(mips, pes)
      vm.setRam(ram).setBw(bw).setSize(size)
      vm
    })
    vmList.asJava
  }

  /**
   * Creates a list of Cloudlets based on the specified parameters in the config file
   * @return List of Cloudlet
   */
  def createCloudlet() : util.List[Cloudlet] = {
    val simulatedCloudlet = new SimulatedCloudLet(which)
    var cloudletList = new ListBuffer[Cloudlet]
    if(simulatedCloudlet.utilizationModel == "Stochastic"){
      cloudletList = ListBuffer.tabulate(simulatedCloudlet.number) (_ => new CloudletSimple(simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelStochastic(5)))
    }
    else{
      cloudletList = ListBuffer.tabulate(simulatedCloudlet.number) (_ => new CloudletSimple(simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    }
    cloudletList.asJava
  }

  /**
   * Creates a list of PaaS Cloudlets based on the specified parameters in the config file and the user input
   * @return List of Cloudlet
   */
  def createPaaSCloudlet(language: String, dataStore: String) : util.List[CloudletPaaS] = {
    val simulatedCloudlet = new SimulatedCloudLet(which)
    val cloudletList: List[CloudletPaaS] = List.tabulate(simulatedCloudlet.number)(_ => new CloudletPaaS(language, dataStore, simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    cloudletList.asJava
  }

  /**
   * Calculates the total cost of running the simulation on the specified datacenter
   * @param dataCenter
   * @param cloudletList
   * @return Total cost of the simulation
   */
  def cost(dataCenter: Datacenter, cloudletList: util.List[Cloudlet]) : Double = {
    var cost: Double = 0.0
    val pricePerSecond = cloudletList.asScala.map(x => x.getCostPerSec(dataCenter))
    val finishTime = cloudletList.asScala.map(x => x.getFinishTime)
    cost = List.tabulate(finishTime.size)(x => pricePerSecond(x) * finishTime(x)).sum
    cost
  }

  /**
   * Calculates the total cost of running the simulation on the FaaS datacenter
   * @param dataCenter
   * @param cloudletList
   * @return Total cost of the simulation
   */
  def costPaaS(dataCenter: Datacenter, cloudletList: util.List[CloudletPaaS]): Double = {
    var cost: Double = 0.0
    val pricePerSecond = cloudletList.asScala.map(x => x.getCostPerSec(dataCenter))
    val finishTime = cloudletList.asScala.map(x => x.getFinishTime)
    cost = List.tabulate(finishTime.size)(x => pricePerSecond(x) * finishTime(x)).sum
    cost
  }
}
