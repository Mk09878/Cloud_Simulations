import com.typesafe.config.ConfigFactory
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.hosts.{Host, HostSimple}
import org.cloudbus.cloudsim.resources.{Pe, PeSimple}
import org.cloudbus.cloudsim.cloudlets.{Cloudlet, CloudletSimple}
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import org.cloudbus.cloudsim.utilizationmodels.UtilizationModelDynamic
import org.cloudbus.cloudsim.vms.{Vm, VmSimple}
import org.cloudbus.cloudsim.resources.Pe
import org.cloudbus.cloudsim.hosts.Host
import java.util
import org.slf4j.{Logger, LoggerFactory}
import org.cloudsimplus.builders.tables.CloudletsTableBuilder

object Main extends App {
  val logger = LoggerFactory.getLogger("Main")
  val config = ConfigFactory.load()
  println("Hello World from Main")
  println(config.getString("app.answer"))
  logger.debug("Hello World from Debug!")

}
