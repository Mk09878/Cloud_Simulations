import java.util

import ReducedExample.{broker0, cloudlets, cloudsim, config, utilizationModel, vmList}
import Utils.SimulatedDataCenter
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.schedulers.vm.{VmSchedulerSpaceShared, VmSchedulerTimeShared}
import org.cloudbus.cloudsim.utilizationmodels.{UtilizationModelDynamic, UtilizationModelFull}
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder
import org.slf4j.LoggerFactory

import scala.collection.mutable.ListBuffer

object Simulation extends App{

  def cost() : Double = {
    var cost: Double = 0.0
    var temp = ListBuffer[Double]()
    val pricePerSecond = toSList(cloudlets).map(x => x.getCostPerSec(datacenter))
    val finishTime = toSList(cloudlets).map(x => x.getFinishTime)
    cost = List.tabulate(finishTime.size)(x => pricePerSecond(x) * finishTime(x)).sum
    cost
  }

  def toSList[T](l: util.List[T]): List[T] = {
    var a = ListBuffer[T]()
    for (r <- 0 until l.size) a += l.get(r)
    a.toList
  }

  def createHost() : Host = {
    //Pe (Processing Element) class represents a CPU core of a physical machine (PM), defined in terms of Millions Instructions Per Second (MIPS) rating.
    //Such a class allows managing the Pe capacity and allocation.
    //Creates one Hosts with a specific list of CPU cores (PEs).
    val hostPes = new util.ArrayList[Pe](1)

    //Uses a PeProvisionerSimple by default to provision PEs for VMs
    hostPes.add(new PeSimple(config.getInt("simulation.host.mips")))

    val ram = config.getInt("simulation.host.ram").toLong //in Megabytes
    val bw = config.getInt("simulation.host.bw").toLong //in Megabytes
    val storage = config.getInt("simulation.host.storage").toLong //in Megabytes

    //Uses ResourceProvisionerSimple by default for RAM and BW provisioning to provide a resource to VMs
    //Uses VmSchedulerSpaceShared by default for VM scheduling that allocates one or more PEs from a host to a Virtual Machine Monitor (VMM)
    val host = new HostSimple(ram, bw, storage, hostPes)
    host
  }

  def createVm() : Vm = {
    //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
    val vm = new VmSimple(config.getInt("simulation.vm.mips"), 1)
    val ram = config.getInt("simulation.vm.ram")
    val bw = config.getInt("simulation.vm.bw")
    val size = config.getInt("simulation.vm.size")
    vm.setRam(ram).setBw(bw).setSize(size)
    vm
  }

  def createCloudlet() : Cloudlet = {
    val length = config.getInt("simulation.cloudLet.length")
    val pesNumber = config.getInt("simulation.cloudLet.pesNumber")
    val cloudlet = new CloudletSimple(length, pesNumber, utilizationModel)
    cloudlet
  }

  val logger = LoggerFactory.getLogger("simulation")

  val config = ConfigFactory.load()
  logger.info("Simulation starting")

  //Creates a CloudSim object to initialize the simulation.
  val cloudsim = new CloudSim()

  //Creates a Broker that will act on behalf of a cloud user (customer).
  val broker = new DatacenterBrokerSimple(cloudsim)

  val hostList = new util.ArrayList[Host](1)
  hostList.add(createHost())


  val datacenter = new DatacenterSimple(cloudsim, hostList)
  val datacenter1 = new DatacenterSimple(cloudsim, hostList)
  logger.info(datacenter.getCharacteristics.getArchitecture)
  logger.info(datacenter.getCharacteristics.getOs)
  logger.info(datacenter.getCharacteristics.getVmm)
  datacenter.getCharacteristics
            .setCostPerBw(0.001)
            .setCostPerMem(0.05)
            .setCostPerSecond(5)
            .setCostPerStorage(0.005)

  datacenter1.getCharacteristics
    .setCostPerBw(0.001)
    .setCostPerMem(0.05)
    .setCostPerSecond(5)
    .setCostPerStorage(0.005)


  val vmList = new util.ArrayList[Vm](1)
  vmList.add(createVm())

  val utilizationModel = new UtilizationModelFull()
  val cloudlets = new util.ArrayList[Cloudlet](1)
  cloudlets.add(createCloudlet())
  cloudlets.add(createCloudlet())
  cloudlets.add(createCloudlet())


  /*Requests the broker to create the Vms and Cloudlets.
  It selects the Host to place each Vm and a Vm to run each Cloudlet.*/
  broker.submitVmList(vmList)
  broker.submitCloudletList(cloudlets)

  /*Starts the simulation and waits all cloudlets to be executed, automatically
        stopping when there is no more events to process.*/
  cloudsim.start()

  logger.info("Cost for the simulation: " + cost())

  /*Prints results when the simulation is over (one can use his/her own code
          here to print what he/she wants from this cloudlet list)*/
  new CloudletsTableBuilder(broker.getCloudletFinishedList).build()
  //end::cloudsim-plus-reduced-example[]

}
