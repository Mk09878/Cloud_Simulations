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
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
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
   * @param cloudSim: CloudSim object
   * @return SimpleBroker object
   */
  def createSimpleBroker(cloudSim: CloudSim): DatacenterBrokerSimple = {
    this.cloudSim = cloudSim
    new DatacenterBrokerSimple(cloudSim)
  }

  /**
   * Creates a Simple DataCenter and assigns operating costs to it
   * @param which: Simulation Number
   * @param vmAllocationPolicy: Allocation Policy for the Vm of the DataCenter
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
    logger.info("DataCenter Created")
    dc
  }

  /**
   * Creates a SaaS (Same as Simple) DataCenter and assigns operating costs to it
   * @param which: Simulation Number
   * @param vmAllocationPolicy: Allocation Policy for the Vm of the DataCenter
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
    logger.info("SaaS DataCenter Created")
    dc
  }

  /**
   * Creates a IaaS DataCenter, assigns the os and operating costs to it
   * @param which: Simulation Number
   * @param vmAllocationPolicy: Allocation Policy for the Vm of the DataCenter
   * @return SimpleDataCenter object
   */
  def createIaaSDataCenter(cloudSim: CloudSim, which: String, vmAllocationPolicy: VmAllocationPolicy) : DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenterIaaS(which)
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    logger.info("IaaS DataCenter Created")
    dc
  }

  /**
   * Creates a PaaS DataCenter and assigns operating costs to it
   * @param which: Simulation Number
   * @param vmAllocationPolicy: Allocation Policy for the Vm of the DataCenter
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
    logger.info("PaaS DataCenter Created")
    dc
  }

  /**
   * Sets the network topology
   * @param NETWORK_TOPOLOGY_FILE: Name of the topology file
   * @param dc: DataCenter object
   * @param broker: Broker object
   */
  def configureNetwork(NETWORK_TOPOLOGY_FILE : String, dc: Datacenter, broker: DatacenterBroker): Unit = {
    val networkTopology = BriteNetworkTopology.getInstance(NETWORK_TOPOLOGY_FILE)
    cloudSim.setNetworkTopology(networkTopology)
    networkTopology.mapNode(dc.getId, 0)
    networkTopology.mapNode(broker.getId, 3)
    logger.info("Network Configured")
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
    logger.info("Host List Created")
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
    logger.info("Vm List Created")
    vmList.asJava
  }

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
    logger.info("Vm List Created")
    vmList.asJava
  }

  /**
   * Creates a list of Cloudlets based on the specified parameters in the config file
   * @return List of Cloudlets
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
    logger.info("Cloudlet list created")
    cloudletList.asJava
  }

  /**
   * Creates cloudlets of all the services and returns those in a list
   * @param Lang: Language for PaaS Implementation
   * @param dataStore: DataStore for PaaS Implementation
   * @return List of Custom Cloudlets
   */
  def createAllCloudlets(Lang: String, dataStore: String): util.List[CustomCloudlet] = {
    val simulatedCloudlet = new SimulatedCloudLet(which)
    val cloudletList1: List[CustomCloudlet] = List.tabulate(simulatedCloudlet.number)(_ => new CustomCloudlet("IaaS", simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    val cloudletList2: List[CustomCloudlet] = List.tabulate(simulatedCloudlet.number)(_ => new CustomCloudlet("PaaS", Lang, dataStore, simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    val cloudletList3: List[CustomCloudlet] = List.tabulate(simulatedCloudlet.number)(_ => new CustomCloudlet("SaaS", simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    logger.info("Cloudlet list created")
    (cloudletList1 ::: cloudletList2 ::: cloudletList3).asJava
  }

  /**
   * Calculates the total cost of running the simulation on a datacenter with default cloudlets
   * @param dataCenter: dataCenter object
   * @param cloudletList: List of cloudlets
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
   * Calculates the total cost of running the simulation on a datacenter with Custom Cloudlet
   * @param dataCenter: dataCenter object
   * @param cloudletList: List of custom cloudlets
   * @return Total cost of the simulation
   */
  def cost1(dataCenter: Datacenter, cloudletList: util.List[CustomCloudlet]): Double = {
    var cost: Double = 0.0
    val pricePerSecond = cloudletList.asScala.map(x => x.getCostPerSec(dataCenter))
    val finishTime = cloudletList.asScala.map(x => x.getFinishTime)
    cost = List.tabulate(finishTime.size)(x => pricePerSecond(x) * finishTime(x)).sum
    cost
  }

}
