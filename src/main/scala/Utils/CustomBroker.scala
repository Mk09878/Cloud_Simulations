package Utils
import org.cloudbus.cloudsim.brokers.DatacenterBrokerSimple
import org.cloudbus.cloudsim.core.CloudSim
import org.cloudbus.cloudsim.datacenters.DatacenterSimple
import java.util

import scala.jdk.CollectionConverters._

class CustomBroker(cloudSim: CloudSim) extends DatacenterBrokerSimple(cloudSim: CloudSim){

  def assign(dcIaaS: DatacenterSimple, dcPaaS: DatacenterSimple, dcSaaS: DatacenterSimple, list1: java.util.List[CustomCloudlet], list2: java.util.List[CustomCloudlet], list3: java.util.List[CustomCloudlet]) : util.List[util.List[CustomCloudlet]] = {

    val list: util.List[util.List[CustomCloudlet]] = new util.ArrayList[util.List[CustomCloudlet]]
    val IaaS_list: util.List[CustomCloudlet] = list1
    val PaaS_list: util.List[CustomCloudlet] = list2
    val SaaS_list: util.List[CustomCloudlet] = list3

    IaaS_list.asScala.foreach(x => x.assignToDatacenter(dcIaaS))
    PaaS_list.asScala.foreach(x => x.assignToDatacenter(dcPaaS))
    SaaS_list.asScala.foreach(x => x.assignToDatacenter(dcSaaS))
    /*if(list1.get(0).service == "IaaS"){
      IaaS_list.asScala.foreach(x => x.assignToDatacenter(dcIaaS))
    }
    else if(list1.get(0).service == "PaaS"){
      PaaS_list.asScala.foreach(x => x.assignToDatacenter(dcPaaS))
    }
    else{
      SaaS_list.asScala.foreach(x => x.assignToDatacenter(dcSaaS))
    }

    if(list2.get(0).service == "IaaS"){
      IaaS_list.asScala.foreach(x => x.assignToDatacenter(dcIaaS))
    }
    else if(list2.get(0).service == "PaaS"){
      PaaS_list.asScala.foreach(x => x.assignToDatacenter(dcPaaS))
    }
    else{
      SaaS_list.asScala.foreach(x => x.assignToDatacenter(dcSaaS))
    }

    if(list3.get(0).service == "IaaS"){
      IaaS_list.asScala.foreach(x => x.assignToDatacenter(dcIaaS))
    }
    else if(list3.get(0).service == "PaaS"){
      PaaS_list.asScala.foreach(x => x.assignToDatacenter(dcPaaS))
    }
    else{
      SaaS_list.asScala.foreach(x => x.assignToDatacenter(dcSaaS))
    }*/

    list.add(IaaS_list)
    list.add(PaaS_list)
    list.add(SaaS_list)

    list
  }

  def assign1(dcIaaS: DatacenterSimple, dcPaaS: DatacenterSimple, dcSaaS: DatacenterSimple, cloudletList: java.util.List[CustomCloudlet]) : util.List[util.List[CustomCloudlet]] = {

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
