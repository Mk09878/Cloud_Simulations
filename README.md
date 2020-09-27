# Homework 1
## Description: Developed Cloud simulators for evaluating executions of applications in cloud datacenters with different characteristics and deployment models.

## Steps to run the project
1. Clone the repository using the following command: 

```git clone https://MihirK3@bitbucket.org/cs441-fall2020/mihir_kelkar_hw1.git```
#### Using command line
1. Navigate to the folder: mihir_kelkar_hw1
2. Run the command ```sbt test``` to run all the test cases
3. Run the command ```sbt run```. Now, you will see a list of all the simulations you can choose to run
4. Choose the one you want to run by entering the simulation's corresponding number

#### Using IntelliJ
1. Open IntelliJ, Click on open project and then select the mihir_kelkar_hw
2. 
## VmAllocation Policies Compared:
1. ***VmAllocationPolicyBestFit***: A VmAllocationPolicy implementation that chooses, as the host for a VM, that one with the most PEs in use. It is therefore a Best Fit policy, allocating each VM into the host with the least available PEs that are enough for the VM.
2. ***VmAllocationPolicyFirstFit***: An First Fit VM allocation policy which finds the first Host having suitable resources to place a given VM.
3. ***VmAllocationPolicySimple***: A VmAllocationPolicy implementation that chooses, as the host for a VM, that one with fewer PEs in use. It is therefore a Worst Fit policy, allocating each VM into the host with most available PEs.
## VmScheduler Policies Compared:
1. ***VmSchedulerSpaceShared***: VmSchedulerSpaceShared is a VMM allocation policy that allocates one or more Pe to a VM, and doesn't allow sharing of PEs. If there is no free PEs to the VM, allocation fails.
2. ***VmSchedulerTimeShared***: VmSchedulerTimeShared is a VMM allocation policy that allocates one or more Pe to a VM, and allows sharing of PEs by multiple VMs.
## Cloudlet Utilization Models Compared:
1. ***UtilizationModelFull***: The UtilizationModelFull class is a simple model, according to which a Cloudlet always utilize all the available CPU capacity.
2. ***UtilizationModelStochastic***: The UtilizationModelStochastic class implements a model, according to which a Cloudlet generates random CPU utilization every time frame.
## Service Models Implemented
1. Infrastructure as a Service (IaaS)
2. Platform as a Service (PaaS)
3. Software as a Service (SaaS)
## Observations
### Mixed Simulations
####Simulation 1

The configuration used in this simulation is:

```
simulation1 {
    host = {
        number = 12
        pesNumber = 8
        ram = 10000
        storage = 100000
        bw = 10000
        mips = 1000
        scheduler = space
    }

    dataCenter = {
        os = Linux
        cost = 5
        costPerMemory = 0.05
        costPerStorage = 0.005
        costPerBandWidth = 0.001
    }
    vm = {
        number = 4
        mips = 1000
        size = 10000
        ram = 1000
        bw = 1000
        pes = 4
    }

    cloudLet = {
        number = 10
        length = 20000
        pesNumber = 2
        utilizationModel = Full
    }
}
```
The results of this simulation are:

```
SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
       2|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
       4|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
       6|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
       8|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
      10|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
      12|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
      14|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
      16|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
      18|SUCCESS| 2|   0|        8| 0|        4|      20000|          4|        0|       200|     200
       1|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
       3|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
       5|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
       7|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
       9|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
      11|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
      13|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
      15|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
      17|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
      19|SUCCESS| 2|   1|        8| 1|        4|      20000|          4|        0|       200|     200
-----------------------------------------------------------------------------------------------------
INFO  Cost for simulation 1 is: 20010.999999999993
```

####Simulation 2

The configuration used in this simulation is:

```
simulation2 {
    host = {
        number = 12
        pesNumber = 8
        ram = 10000
        storage = 100000
        bw = 10000
        mips = 2000
        scheduler = space
    }

    dataCenter = {
        cost = 5
        costPerMemory = 0.05
        costPerStorage = 0.005
        costPerBandWidth = 0.001
    }
    vm = {
        number = 4
        mips = 1000
        size = 10000
        ram = 1000
        bw = 1000
        pes = 4
    }

    cloudLet = {
        number = 10
        length = 10000
        pesNumber = 4
        utilizationModel = Full
    }
}
```

The results of this simulation are:

```
SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       0|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        50|      50
       2|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        50|      50
       4|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        50|      50
       6|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        50|      50
       8|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        50|      50
       1|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        50|      50
       3|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        50|      50
       5|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        50|      50
       7|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        50|      50
       9|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        50|      50
-----------------------------------------------------------------------------------------------------
INFO  Cost for simulation 2 is: 2505.5
```
####Simulation 3

The configuration used in this simulation is:

```
simulation3 {
    host = {
        number = 12
        pesNumber = 8
        ram = 10000
        storage = 100000
        bw = 10000
        mips = 2000
        scheduler = time
    }

    dataCenter = {
        os = Linux
        cost = 5
        costPerMemory = 0.05
        costPerStorage = 0.005
        costPerBandWidth = 0.001
    }
    vm = {
        number = 4
        mips = 1000
        size = 10000
        ram = 1000
        bw = 1000
        pes = 4
    }

    cloudLet = {
        number = 10
        length = 10000
        pesNumber = 4
        utilizationModel = Full
    }
}
```

The results of this simulation are:

```
SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       2|SUCCESS| 2|   2|        8| 2|        4|      10000|          4|        0|        20|      20
       6|SUCCESS| 2|   2|        8| 2|        4|      10000|          4|        0|        20|      20
       3|SUCCESS| 2|   3|        8| 3|        4|      10000|          4|        0|        20|      20
       7|SUCCESS| 2|   3|        8| 3|        4|      10000|          4|        0|        20|      20
       0|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        30|      30
       4|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        30|      30
       8|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|        30|      30
       1|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        30|      30
       5|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        30|      30
       9|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|        30|      30
-----------------------------------------------------------------------------------------------------
INFO  Cost for simulation 3 is: 1308.4399999999998
```

####Simulation 4

The configuration used in this simulation is:

```
simulation4 {
    host = {
        number = 12
        pesNumber = 8
        ram = 10000
        storage = 100000
        bw = 10000
        mips = 2000
        scheduler = time
    }

    dataCenter = {
        os = Linux
        cost = 5
        costPerMemory = 0.05
        costPerStorage = 0.005
        costPerBandWidth = 0.001
    }
    vm = {
        number = 4
        mips = 1000
        size = 10000
        ram = 1000
        bw = 1000
        pes = 4
    }

    cloudLet = {
        number = 10
        length = 10000
        pesNumber = 4
        utilizationModel = Full
    }
}

                             ---- topology.brite ----

Topology: ( 5 Nodes, 8 Edges )
Model (1 - RTWaxman):  5 5 5 1  2  0.15000000596046448 0.20000000298023224 1 1 10.0 1024.0

Nodes: ( 5 )
0	1	3	3	3	-1	RT_NODE
1	0	3	3	3	-1	RT_NODE
2	4	3	3	3	-1	RT_NODE
3	3	1	3	3	-1	RT_NODE
4	3	3	4	4	-1	RT_NODE

Edges: ( 8 )
0	2	0	3.0			1.1	10.0	-1	-1	E_RT	U
1	2	1	4.0			2.1	10.0	-1	-1	E_RT	U
2	3	0	2.8284271247461903	3.9	10.0	-1	-1	E_RT	U
3	3	1	3.605551275463989	4.1	10.0	-1	-1	E_RT	U
4	4	3	2.0			5.0	10.0	-1	-1	E_RT	U
5	4	2	1.0			4.0	10.0	-1	-1	E_RT	U
6	0	4	2.0			3.0	10.0	-1	-1	E_RT	U
7	1	4	3.0			4.1	10.0	-1	-1	E_RT	U
```

The results of this simulation are:

```
SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       2|SUCCESS| 2|   2|        8| 2|        4|      10000|          4|       12|        31|      19
       6|SUCCESS| 2|   2|        8| 2|        4|      10000|          4|       12|        31|      19
       3|SUCCESS| 2|   3|        8| 3|        4|      10000|          4|       12|        31|      19
       7|SUCCESS| 2|   3|        8| 3|        4|      10000|          4|       12|        31|      19
       0|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|       12|        41|      29
       4|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|       12|        41|      29
       8|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|       12|        41|      29
       1|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|       12|        41|      29
       5|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|       12|        41|      29
       9|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|       12|        41|      29
-----------------------------------------------------------------------------------------------------
INFO  Cost for simulation 4 is: 1893.27
```

####Simulation 5

The configuration used in this simulation is:

```
simulation5 {
    host = {
        number = 12
        pesNumber = 8
        ram = 10000
        storage = 100000
        bw = 10000
        mips = 2000
        scheduler = time
    }

    dataCenter = {
        os = Linux
        cost = 5
        costPerMemory = 0.05
        costPerStorage = 0.005
        costPerBandWidth = 0.001
    }
    vm = {
        number = 4
        mips = 1000
        size = 10000
        ram = 1000
        bw = 1000
        pes = 4
    }

    cloudLet = {
        number = 10
        length = 10000
        pesNumber = 4
        utilizationModel = Stochastic
    }
}
```

The results of this simulation are:

```
SIMULATION RESULTS

Cloudlet|Status |DC|Host|Host PEs |VM|VM PEs   |CloudletLen|CloudletPEs|StartTime|FinishTime|ExecTime
      ID|       |ID|  ID|CPU cores|ID|CPU cores|         MI|  CPU cores|  Seconds|   Seconds| Seconds
-----------------------------------------------------------------------------------------------------
       2|SUCCESS| 2|   2|        8| 2|        4|      10000|          4|        0|        77|      77
       6|SUCCESS| 2|   2|        8| 2|        4|      10000|          4|        0|        77|      77
       3|SUCCESS| 2|   2|        8| 3|        4|      10000|          4|        0|        77|      77
       7|SUCCESS| 2|   2|        8| 3|        4|      10000|          4|        0|        77|      77
       0|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|       830|     830
       4|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|       830|     830
       8|SUCCESS| 2|   0|        8| 0|        4|      10000|          4|        0|       830|     830
       1|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|       830|     830
       5|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|       830|     830
       9|SUCCESS| 2|   1|        8| 1|        4|      10000|          4|        0|       830|     830
-----------------------------------------------------------------------------------------------------
INFO  Cost for simulation 5 is: 26459.988985607597
```

###Inference

1. Usages of all the available VmAllocation policies did not have much of an impact towards the total cost of the simulation. This might be related to the demand for resources and the availability of those.
2. We can see the decrease in cost in Simulation 2 as compared to Simulation 1. This is because, Simulation 1 has twice as many cloudets and twice the length of those as Simulation 2.
3. Simulations 3 and 2 have the same parameters except one, the scheduler. Simulation 2 uses a space shared vm scheduler whereas Simulation 3 uses a time shared vm scheduler.
4. We can observe that Simulation 3 is less costly as compared to Simulation 2. This is because, Time shared policy allows sharing of PEs by multiple VMs as opposed to Space shared policy. 
5. In both the simulations 2 and 3, the total number of PEs in all Vms are 4 * 4 = 16 whereas the number of PEs needed by all the cloudlets are 10*4 = 40. So, there is a significant difference and due to the sharing of VMs in the Time shared policy, the cost incurred is less.
6. Since, time shared policy worked better in general, I used it for Simulation 4 and 5.
7. In Simulation 4, brite network topology is used. We can observe that the costs incurred are more in this case which is expected given that it is used to simulate delays.
8. The parameters of Simulation 5 are similar to Simulation 3 except the UtilizationModel of cloudlets. Simulation 5 uses a stochastic utilization model whereas 3 uses a full utilization model. 
9. Simulation 5 incurs higher cost due to the randomness in the model.

