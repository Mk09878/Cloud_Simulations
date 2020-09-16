package Utils

import com.typesafe.config.ConfigFactory

class SimulatedHost(which : String) {
  private val config = ConfigFactory.load(which)
  val number: Int = config.getInt(which + ".host.number")
  val pesNumber: Int = config.getInt(which + ".host.pesNumber")
  val mips: Int = config.getInt(which + ".host.mips")
  val ram: Int = config.getInt(which + ".host.ram")
  val bw: Int = config.getInt(which + ".host.bw")
  val storage: Int = config.getInt(which + ".host.storage")
  val scheduler: String = config.getString(which + ".host.scheduler")
}
