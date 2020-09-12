import java.util

import Main.config
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

object ReducedExample extends App {

  val config = ConfigFactory.load()
  // Cloud Sim

  //Creates a CloudSim object to initialize the simulation.
  val cloudsim = new CloudSim()

  //Creates a Broker that will act on behalf of a cloud user (customer).
  val broker0 = new DatacenterBrokerSimple(cloudsim)

  //Pe (Processing Element) class represents a CPU core of a physical machine (PM), defined in terms of Millions Instructions Per Second (MIPS) rating.
  //Such a class allows managing the Pe capacity and allocation.
  //Creates one Hosts with a specific list of CPU cores (PEs).
  val hostList = new util.ArrayList[Host](1)
  val hostPes = new util.ArrayList[Pe](1)

  //Uses a PeProvisionerSimple by default to provision PEs for VMs
  hostPes.add(new PeSimple(20000))

  val ram = config.getInt("PeSimple.ram").toLong //in Megabytes
  val bw = config.getInt("PeSimple.bw").toLong //in Megabytes
  val storage = config.getInt("PeSimple.storage").toLong //in Megabytes

  //Uses ResourceProvisionerSimple by default for RAM and BW provisioning to provide a resource to VMs
  //Uses VmSchedulerSpaceShared by default for VM scheduling that allocates one or more PEs from a host to a Virtual Machine Monitor (VMM)
  val host0 = new HostSimple(ram, bw, storage, hostPes)
  hostList.add(host0)

  //Creates one Datacenter with a list of Hosts
  //Uses a VmAllocationPolicySimple by default to allocate VMs
  //A VmAllocationPolicy implementation that chooses, as the host for a VM, that one with fewer PEs in use.
  //It is therefore a Worst Fit policy, allocating VMs into the host with most available PEs.
  val dc0 = new DatacenterSimple(cloudsim, hostList)

  //Creates one Vm to run applications (Cloudlets).
  val vmList = new util.ArrayList[Vm](1)

  //Uses a CloudletSchedulerTimeShared by default to schedule Cloudlets
  val vm0 = new VmSimple(1000, 1)
  vm0.setRam(1000).setBw(1000).setSize(1000)
  vmList.add(vm0)

  //Creates two Cloudlets that represent applications to be run inside a Vm.
  val cloudlets = new util.ArrayList[Cloudlet](1)

  //UtilizationModel defining the Cloudlets use only 50% of any resource all the time

  val utilizationModel = new UtilizationModelDynamic(0.5)
  val cloudlet0 = new CloudletSimple(10000, 1, utilizationModel)
  val cloudlet1 = new CloudletSimple(10000, 1, utilizationModel)
  cloudlets.add(cloudlet0)
  cloudlets.add(cloudlet1)

  /*Requests the broker to create the Vms and Cloudlets.
  It selects the Host to place each Vm and a Vm to run each Cloudlet.*/
  broker0.submitVmList(vmList)
  broker0.submitCloudletList(cloudlets)

  /*Starts the simulation and waits all cloudlets to be executed, automatically
        stopping when there is no more events to process.*/
  cloudsim.start()

  /*Prints results when the simulation is over (one can use his/her own code
          here to print what he/she wants from this cloudlet list)*/
  new CloudletsTableBuilder(broker0.getCloudletFinishedList).build()
  //end::cloudsim-plus-reduced-example[]
}
