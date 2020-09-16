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
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}

import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters._

class DataCenterUtils {

  var which: String = _
  var cloudSim: CloudSim = _

  def createSimpleBroker(cloudSim: CloudSim): DatacenterBrokerSimple = {
    this.cloudSim = cloudSim
    new DatacenterBrokerSimple(cloudSim)
  }

  def createSimpleDataCenter(which: String, vmAllocationPolicy: VmAllocationPolicy): DatacenterSimple = {
    this.which = which
    val simulatedDataCenter = new SimulatedDataCenter(which)
    val dc = new DatacenterSimple(cloudSim, createHost(), vmAllocationPolicy)
    dc.getCharacteristics
      .setCostPerBw(simulatedDataCenter.costPerBw)
      .setCostPerMem(simulatedDataCenter.costPerMemory)
      .setCostPerSecond(simulatedDataCenter.costPerSecond)
      .setCostPerStorage(simulatedDataCenter.costPerStorage)
    println(dc.getCharacteristics.getArchitecture)
    dc
  }

  def configureNetwork(NETWORK_TOPOLOGY_FILE : String, dc: Datacenter, broker: DatacenterBroker): Unit = {

    val networkTopology = BriteNetworkTopology.getInstance(NETWORK_TOPOLOGY_FILE)
    cloudSim.setNetworkTopology(networkTopology)
    networkTopology.mapNode(dc.getId, 0)
    networkTopology.mapNode(broker.getId, 3)

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

  def createVm(): util.List[Vm] ={
    val simulatedVm = new SimulatedVm(which)
    val vmList: List[Vm] = List.tabulate(simulatedVm.number) (_ => {
      val vm = new VmSimple(simulatedVm.mips, simulatedVm.pes)
      vm.setRam(simulatedVm.ram).setBw(simulatedVm.bw).setSize(simulatedVm.size)
      vm
    })
    vmList.asJava
  }

  def createCloudlet() : util.List[Cloudlet] = {
    val simulatedCloudlet = new SimulatedCloudLet(which)
    val cloudletList: List[Cloudlet] = List.tabulate(simulatedCloudlet.number)(_ => new CloudletSimple(simulatedCloudlet.length, simulatedCloudlet.pesNumber, new UtilizationModelFull()))
    cloudletList.asJava
  }

  def cost(dataCenter: Datacenter, cloudletList: util.List[Cloudlet]) : Double = {
    var cost: Double = 0.0
    val pricePerSecond = cloudletList.asScala.map(x => x.getCostPerSec(dataCenter))
    val finishTime = cloudletList.asScala.map(x => x.getFinishTime)
    cost = List.tabulate(finishTime.size)(x => pricePerSecond(x) * finishTime(x)).sum
    cost
  }

}
