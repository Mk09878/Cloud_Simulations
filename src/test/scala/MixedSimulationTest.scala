import java.util

import Utils.DataCenterUtils
import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.allocationpolicies.VmAllocationPolicyRoundRobin
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.cloudlets.Cloudlet
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.vms.Vm
import org.scalatest.{BeforeAndAfter, FunSuite}
import org.slf4j.{Logger, LoggerFactory}


class MixedSimulationTest extends FunSuite with BeforeAndAfter{

  def simulation(simulation: String): Unit ={
    val config = ConfigFactory.load(simulation)
    val dataCenterUtils = new DataCenterUtils()
    val cloudSim = new CloudSim()
    val dataCenterBrokerSimple: DatacenterBrokerSimple = dataCenterUtils.createSimpleBroker(cloudSim)
    val dataCenterSimple: DatacenterSimple = dataCenterUtils.createSimpleDataCenter(simulation, new VmAllocationPolicyRoundRobin)

    test(simulation + "DataCenter is created properly"){
      assert(dataCenterSimple != null)
    }
    logger.info("DataCenter is created properly for "+simulation)

    val vmList: util.List[Vm] = dataCenterUtils.createVm()

    test(simulation + "Vm list is created properly"){
      assert(vmList != null)
      assert(config.getInt(simulation + ".vm.number") === vmList.size())
    }
    logger.info("Vm list is created properly for "+simulation)

    val cloudletList: util.List[Cloudlet] = dataCenterUtils.createCloudlet()

    test(simulation + "Cloudlet list is created properly"){
      assert(cloudletList != null)
      assert(config.getInt(simulation + ".cloudLet.number") === cloudletList.size())
    }

    logger.info("Cloudlet list is created properly for "+simulation)

    dataCenterBrokerSimple.submitVmList(vmList).submitCloudletList(cloudletList)

    logger.info(simulation + " test completed")

  }
  val logger: Logger = LoggerFactory.getLogger("MixedSimulationTest")
  logger.info("Starting MixedSimulationTest")
  val simulationList = List("simulation1", "simulation2", "simulation3", "simulation4")
  simulationList.map(x => simulation(x))
  logger.info("Finished MixedSimulationTest")











}
