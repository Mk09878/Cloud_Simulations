package Utils

import com.typesafe.config.ConfigFactory

class SimulatedVm(which : String) {
  private val config = ConfigFactory.load(which)
  val number: Int = config.getInt(which + ".vm.number")
  val mips: Int = config.getInt(which + ".vm.mips")
  val ram: Int = config.getInt(which + ".vm.ram")
  val bw: Int = config.getInt(which + ".vm.bw")
  val size: Int = config.getInt(which + ".vm.size")
  val pes: Int = config.getInt(which + ".vm.pes")
}
