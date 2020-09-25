package Utils

import com.typesafe.config.ConfigFactory

class SimulatedDataCenterPaaS(which: String) {
  private val config = ConfigFactory.load(which)
  val costPerBw: Int = config.getInt(which + ".dataCenter_PaaS.costPerBandWidth")
  val costPerMemory: Int = config.getInt(which + ".dataCenter_PaaS.costPerMemory")
  val costPerStorage: Int = config.getInt(which + ".dataCenter_PaaS.costPerStorage")
  val costPerSecond: Int = config.getInt(which + ".dataCenter_PaaS.cost")

}
