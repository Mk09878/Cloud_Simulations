package Utils

import com.typesafe.config.ConfigFactory

class SimulatedCloudLet(which : String) {
  private val config = ConfigFactory.load(which)
  val number: Int = config.getInt(which + ".cloudLet.number")
  val length: Int = config.getInt(which + ".cloudLet.length")
  val pesNumber: Int = config.getInt(which + ".cloudLet.pesNumber")
  val utilizationModel: String = config.getString(which + ".cloudLet.utilizationModel")
}
