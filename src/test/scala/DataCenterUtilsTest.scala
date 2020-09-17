import java.util

import Utils.{SimulatedCloudLet, SimulatedDataCenter, SimulatedHost, SimulatedVm}
import org.cloudbus.cloudsim.allocationpolicies.{VmAllocationPolicy, VmAllocationPolicyRoundRobin}
import org.cloudbus.cloudsim.brokers.{DatacenterBroker, DatacenterBrokerSimple}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.{Datacenter, DatacenterSimple}
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.network.topologies.BriteNetworkTopology
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelFull
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

class DataCenterUtilsTest extends FunSuite {

  var which: String = _
  var cloudSim: CloudSim = _

  def createSimpleBroker(cloudSim: CloudSim): DatacenterBrokerSimple = {
    this.cloudSim = cloudSim
    new DatacenterBrokerSimple(cloudSim)
  }

  def createSimpleDataCenter(which: String, vmAllocationPolicy: VmAllocationPolicy): DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenter(which)
    test("Simulated Data Center is created properly"){
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

  def configureNetwork(NETWORK_TOPOLOGY_FILE : String, dc: Datacenter, broker: DatacenterBroker): Unit = {
    val networkTopology = BriteNetworkTopology.getInstance(NETWORK_TOPOLOGY_FILE)
    test("Network Topology object is created properly"){
      assert(networkTopology != null)
    }
    cloudSim.setNetworkTopology(networkTopology)
    networkTopology.mapNode(dc.getId, 0)
    networkTopology.mapNode(broker.getId, 3)

  }

  def createHost(): util.List[Host] = {
    val simulatedHost = new SimulatedHost(which)
    test("Simulated Host is created properly"){
      assert(simulatedHost != null)
    }
    val number = simulatedHost.number
    val peList: List[Pe] = List.tabulate(simulatedHost.pesNumber) (_ => new PeSimple(simulatedHost.mips))

    test("peList is created properly"){
      assert(peList != null)
      assert(peList.size == simulatedHost.pesNumber)
    }

    var hostList: ListBuffer[Host] = new ListBuffer[Host]
    if(simulatedHost.scheduler == "time"){
      hostList = ListBuffer.tabulate(number) (_ => new HostSimple(simulatedHost.ram, simulatedHost.bw, simulatedHost.storage, peList.asJava).setVmScheduler(new VmSchedulerTimeShared()))
    }
    else{
      hostList = ListBuffer.tabulate(number) (_ => new HostSimple(simulatedHost.ram, simulatedHost.bw, simulatedHost.storage, peList.asJava).setVmScheduler(new VmSchedulerSpaceShared()))
    }

    hostList.asJava
  }

  def createVm(): util.List[Vm] ={
    val simulatedVm = new SimulatedVm(which)

    test("Simulated Vm is created properly"){
      assert(simulatedVm != null)
    }

    val vmList: List[Vm] = List.tabulate(simulatedVm.number) (_ => {
      val vm = new VmSimple(simulatedVm.mips, simulatedVm.pes)
      vm.setRam(simulatedVm.ram).setBw(simulatedVm.bw).setSize(simulatedVm.size)
      vm
    })
    vmList.asJava
  }

  def createCloudlet() : util.List[Cloudlet] = {
    val simulatedCloudlet = new SimulatedCloudLet(which)

    test("Simulated Cloudlet is created properly"){
      assert(simulatedCloudlet != null)
    }

    val cloudletList: List[Cloudlet] = List.tabulate(simulatedCloudlet.number)(_ => new CloudletSimple(simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    cloudletList.asJava
  }

  cloudSim = new CloudSim()
  val broker: DatacenterBrokerSimple = createSimpleBroker(cloudSim)
  val dc: DatacenterSimple = createSimpleDataCenter("simulation1", new VmAllocationPolicyRoundRobin)
  configureNetwork("topology.brite", dc, broker)
  createVm()
  createCloudlet()

}
