package Utils

import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple

import java.util

class CustomBroker(cloudSim: CloudSim) extends DatacenterBrokerSimple(cloudSim: CloudSim){

  /**
   * Separates the Cloudlets based on their services
   * @param dcIaaS: IaaS DataCenter object
   * @param dcPaaS: PaaS DataCenter object
   * @param dcSaaS: SaaS DataCenter object
   * @param cloudletList: List of Custom Cloudlet objects
   * @return List of List of separated service cloudlets
   */
  def assign(dcIaaS: DatacenterSimple, dcPaaS: DatacenterSimple, dcSaaS: DatacenterSimple, cloudletList: java.util.List[CustomCloudlet]) : util.List[util.List[CustomCloudlet]] = {
    val list: util.List[util.List[CustomCloudlet]] = new util.ArrayList[util.List[CustomCloudlet]]
    val IaaSList: util.List[CustomCloudlet] =  new util.ArrayList[CustomCloudlet]
    val PaaSList: util.List[CustomCloudlet] = new util.ArrayList[CustomCloudlet]
    val SaaSList: util.List[CustomCloudlet] = new util.ArrayList[CustomCloudlet]

    cloudletList.forEach(x => {
      if(x.service == "IaaS"){
        x.assignToDatacenter(dcIaaS)
        IaaSList.add(x)
      }
      else if(x.service == "PaaS"){
        x.assignToDatacenter(dcPaaS)
        PaaSList.add(x)
      }
      else if(x.service == "SaaS"){
        x.assignToDatacenter(dcSaaS)
        SaaSList.add(x)
      }
    })

    list.add(IaaSList)
    list.add(PaaSList)
    list.add(SaaSList)

    list
  }
}
