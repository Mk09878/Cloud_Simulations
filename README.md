# Homework 1

## Description: Developed Cloud simulators for evaluating executions of applications in cloud datacenters with different characteristics and deployment models.

## Steps to run the project
1. Clone the repository using the following command: 

```git clone https://MihirK3@bitbucket.org/cs441-fall2020/mihir_kelkar_hw1.git```
#### Using command line
1. Navigate to the folder: mihir_kelkar_hw1
2. Run the command ```sbt clean compile test``` to run all the test cases
3. Run the command ```sbt clean compile run```. Now, you will see a list of all the simulations you can choose to run
4. Choose the one you want to run by entering the simulation's corresponding number

#### Using IntelliJ
1. Open IntelliJ, Click on open project and then select mihir_kelkar_hw1
2. If no run configuration appears by default, click on the green arrow on the left of the ```object simulationx extends App``` for whichever simulation you want to run

## VmAllocation Policies Compared:
1. ***VmAllocationPolicyBestFit***: A VmAllocationPolicy implementation that chooses, as the host for a VM, that one with the most PEs in use. It is therefore a Best Fit policy, allocating each VM into the host with the least available PEs that are enough for the VM.
2. ***VmAllocationPolicyFirstFit***: An First Fit VM allocation policy which finds the first Host having suitable resources to place a given VM.
3. ***VmAllocationPolicySimple***: A VmAllocationPolicy implementation that chooses, as the host for a VM, that one with fewer PEs in use. It is therefore a Worst Fit policy, allocating each VM into the host with most available PEs.
4. ***VmAllocationPolicyRoundRobin***: Finds the next Host having suitable resources to place a given VM in a circular way. That means when it selects a suitable Host to place a VM, it moves to the next suitable Host when a new VM has to be placed. This is a high time-efficient policy with a best-case complexity O(1) and a worst-case complexity O(N), where N is the number of Hosts.

## VmScheduler Policies Compared:
1. ***VmSchedulerSpaceShared***: VmSchedulerSpaceShared is a VMM allocation policy that allocates one or more Pe to a VM, and doesn't allow sharing of PEs. If there is no free PEs to the VM, allocation fails.
2. ***VmSchedulerTimeShared***: VmSchedulerTimeShared is a VMM allocation policy that allocates one or more Pe to a VM, and allows sharing of PEs by multiple VMs.

## Cloudlet Utilization Models Compared:
1. ***UtilizationModelFull***: The UtilizationModelFull class is a simple model, according to which a Cloudlet always utilize all the available CPU capacity.
2. ***UtilizationModelStochastic***: The UtilizationModelStochastic class implements a model, according to which a Cloudlet generates random CPU utilization every time frame.

## Service Models
1. Infrastructure as a Service (IaaS)
2. Platform as a Service (PaaS)
3. Software as a Service (SaaS)

Three DataCenters are created representing each of these models.

The user is considered to be giving inputs from the command line.

1. For the IaaS model, the VM specifications (with some restrictions) and the operating system are taken from the user.
2. For the PaaS model, the programming language and the DataStore are taken from the user.
3. The default implementation is considered as the SaaS model since, the user cannot change parameters when the program is run.

Additional information about which service the cloudlet belongs to is included in the Custom Cloudlet class.
Once the cloudlets are created, the broker assigns the cloudlets to their respective datacenters.

## Observations

### Mixed Simulations

#### Simulation 1

The configuration used in this simulation is:

```
simulation1 {
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
        number = 20
        length = 20000
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

#### Simulation 2

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
#### Simulation 3

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

#### Simulation 4

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

#### Simulation 5

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

### Services Simulation

#### Simulation 1

The configuration used in this simulation is:

```
models_simulation1 {
    host = {
        number = 4
        pesNumber = 4
        ram = 10000
        storage = 100000
        bw = 10000
        mips = 1000
        scheduler = time
    }

    dataCenter_IaaS = {
        cost = 10
        costPerMemory = 0.1
        costPerStorage = 0.008
        costPerBandWidth = 0.005
    }

    dataCenter_PaaS = {
        os = Linux
        vmm = VMware
        cost = 8
        costPerMemory = 0.08
        costPerStorage = 0.006
        costPerBandWidth = 0.003
    }

    dataCenter_SaaS = {
        os = Linux
        vmm = VMware
        cost = 5
        costPerMemory = 0.05
        costPerStorage = 0.005
        costPerBandWidth = 0.001
    }

    vm = {
        number = 4
        mips = 500
        size = 10000
        ram = 10000
        bw = 10000
        pes = 4
    }

    cloudLet = {
        number = 10
        length = 20000
        pesNumber = 32
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
       6|SUCCESS| 3|   2|        4| 6|        4|      20000|         32|        0|       640|     640
      18|SUCCESS| 3|   2|        4| 6|        4|      20000|         32|        0|       640|     640
       7|SUCCESS| 3|   3|        4| 7|        4|      20000|         32|        0|       640|     640
      19|SUCCESS| 3|   3|        4| 7|        4|      20000|         32|        0|       640|     640
       8|SUCCESS| 4|   0|        4| 8|        4|      20000|         32|        0|       640|     640
      20|SUCCESS| 4|   0|        4| 8|        4|      20000|         32|        0|       640|     640
       9|SUCCESS| 4|   1|        4| 9|        4|      20000|         32|        0|       640|     640
      21|SUCCESS| 4|   1|        4| 9|        4|      20000|         32|        0|       640|     640
      10|SUCCESS| 4|   2|        4|10|        4|      20000|         32|        0|       640|     640
      22|SUCCESS| 4|   2|        4|10|        4|      20000|         32|        0|       640|     640
      11|SUCCESS| 4|   3|        4|11|        4|      20000|         32|        0|       640|     640
      23|SUCCESS| 4|   3|        4|11|        4|      20000|         32|        0|       640|     640
       0|SUCCESS| 2|   0|        4| 0|        4|      20000|         32|        0|       960|     960
      12|SUCCESS| 2|   0|        4| 0|        4|      20000|         32|        0|       960|     960
      24|SUCCESS| 2|   0|        4| 0|        4|      20000|         32|        0|       960|     960
       1|SUCCESS| 2|   1|        4| 1|        4|      20000|         32|        0|       960|     960
      13|SUCCESS| 2|   1|        4| 1|        4|      20000|         32|        0|       960|     960
      25|SUCCESS| 2|   1|        4| 1|        4|      20000|         32|        0|       960|     960
       2|SUCCESS| 2|   2|        4| 2|        4|      20000|         32|        0|       960|     960
      14|SUCCESS| 2|   2|        4| 2|        4|      20000|         32|        0|       960|     960
      26|SUCCESS| 2|   2|        4| 2|        4|      20000|         32|        0|       960|     960
       3|SUCCESS| 2|   3|        4| 3|        4|      20000|         32|        0|       960|     960
      15|SUCCESS| 2|   3|        4| 3|        4|      20000|         32|        0|       960|     960
      27|SUCCESS| 2|   3|        4| 3|        4|      20000|         32|        0|       960|     960
       4|SUCCESS| 3|   0|        4| 4|        4|      20000|         32|        0|       960|     960
      16|SUCCESS| 3|   0|        4| 4|        4|      20000|         32|        0|       960|     960
      28|SUCCESS| 3|   0|        4| 4|        4|      20000|         32|        0|       960|     960
       5|SUCCESS| 3|   1|        4| 5|        4|      20000|         32|        0|       960|     960
      17|SUCCESS| 3|   1|        4| 5|        4|      20000|         32|        0|       960|     960
      29|SUCCESS| 3|   1|        4| 5|        4|      20000|         32|        0|       960|     960
-----------------------------------------------------------------------------------------------------
INFO  Cost for IaaS model is: 83240.08
INFO  Cost for PaaS model is: 66592.06400000001
INFO  Cost for SaaS model is: 41620.04000000001
```
### Inference

#### Mixed Simulations
1. Usages of all the available VmAllocation policies did not have much of an impact towards the total cost of the simulation. This might be related to the demand for resources and the availability of those. So, I used the round robin policy due to its efficient time complexity.
2. We can see the decrease in cost in Simulation 2 as compared to Simulation 1. This is because, Simulation 1 has twice as many cloudets and twice the length of those as Simulation 2.
3. Simulations 3 and 2 have the same parameters except one, the scheduler. Simulation 2 uses a space shared vm scheduler whereas Simulation 3 uses a time shared vm scheduler.
4. We can observe that Simulation 3 is less costly as compared to Simulation 2. This is because, Time shared policy allows sharing of PEs by multiple VMs as opposed to Space shared policy. 
5. In both the simulations 2 and 3, the total number of PEs in all Vms are 4 * 4 = 16 whereas the number of PEs needed by all the cloudlets are 10*4 = 40. So, there is a significant difference and due to the sharing of VMs in the Time shared policy, the cost incurred is less.
6. So, in such cases of difference, Time shared policy tends to work better.
7. Since, Time shared policy worked better in general, I used it for Simulation 4 and 5.
8. In Simulation 4, brite network topology is used. We can observe that the costs incurred are more in this case which is expected given that it is used to simulate delays.
9. The parameters of Simulation 5 are similar to Simulation 3 except the UtilizationModel of cloudlets. Simulation 5 uses a stochastic utilization model whereas 3 uses a full utilization model. 
10. Simulation 5 incurs higher cost due to the randomness in the model. So, Full UtilizationModel would work better in general unless some excellent seed value is provided to the Stochastic Model.

#### Services Simulation
1. As expected from the pricing criteria defined, the IaaS service costs the highest followed by the PaaS and SaaS service.
2. This trend conforms with the real world since, greater flexibility of models leads to greater costs. 
