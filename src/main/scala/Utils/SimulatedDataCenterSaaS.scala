package Utils

import com.typesafe.config.ConfigFactory

class SimulatedDataCenterSaaS(which: String) {
  private val config = ConfigFactory.load(which)
  val costPerBw: Int = config.getInt(which + ".dataCenter_SaaS.costPerBandWidth")
  val costPerMemory: Int = config.getInt(which + ".dataCenter_SaaS.costPerMemory")
  val costPerStorage: Int = config.getInt(which + ".dataCenter_SaaS.costPerStorage")
  val costPerSecond: Int = config.getInt(which + ".dataCenter_SaaS.cost")

}
