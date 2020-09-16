package Utils

import com.typesafe.config.ConfigFactory

class SimulatedDataCenter(which : String) {
  private val config = ConfigFactory.load(which)
  val costPerBw: Int = config.getInt(which + ".dataCenter.costPerBandWidth")
  val costPerMemory: Int = config.getInt(which + ".dataCenter.costPerMemory")
  val costPerStorage: Int = config.getInt(which + ".dataCenter.costPerStorage")
  val costPerSecond: Int = config.getInt(which + ".dataCenter.cost")
}
