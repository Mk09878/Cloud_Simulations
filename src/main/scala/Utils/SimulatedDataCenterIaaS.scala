package Utils

import com.typesafe.config.ConfigFactory

class SimulatedDataCenterIaaS(which: String) {
  private val config = ConfigFactory.load(which)
  val costPerBw: Int = config.getInt(which + ".dataCenter_IaaS.costPerBandWidth")
  val costPerMemory: Int = config.getInt(which + ".dataCenter_IaaS.costPerMemory")
  val costPerStorage: Int = config.getInt(which + ".dataCenter_IaaS.costPerStorage")
  val costPerSecond: Int = config.getInt(which + ".dataCenter_IaaS.cost")

}
