### a.      Setting Up the Default VPC

- First login
  - ![image-20221127171040924](vpc_setup.assets/image-20221127171040924.png)
- make sure the REGION is set to **N. Virginia
  - ![image-20221127171138588](vpc_setup.assets/image-20221127171138588.png)
-  enter the name **Default VPC**, then click on **Save**
  - ![image-20221127171239899](vpc_setup.assets/image-20221127171239899.png)
- **Setting the Name tags for the default subnets.**
  - ![image-20221127171450583](vpc_setup.assets/image-20221127171450583.png)
- Subnet Association
  - ![image-20221127172321156](vpc_setup.assets/image-20221127172321156.png)
- ACL Association
  - ![image-20221127172401197](vpc_setup.assets/image-20221127172401197.png)
- SG update
  - ![image-20221127172721706](vpc_setup.assets/image-20221127172721706.png)
- Lesson learned
  - we saw in detail how to create an on-demand EC2 instance in this tutorial. Because it is an on-demand server, you can keep it running when in use and ‘Stop’ it when it’s unused to save on your costs.
  - I can provision a Linux or Windows EC2 instance or from any of the available AMIs in AWS Marketplace based on your choice of OS platform.
  - If my application is in production and you have to use it for years to come, you should consider provisioning a reserved instance to drastically save on your CAPEX.
  - Here, we saw how to create a Spot Instance request successfully by determining our bid price.
  - Spot instances are a great way to save on costs for instances which are not application critical. A common example would be to create a fleet of spot instances for a task such as image processing or video encoding. In such cases, you can keep a cluster of instances under a load balancer.
  - If the bid price exceeds the spot price and your instance is terminated from AWS’s side, you can have other instances doing the processing job for you. You can leverage Auto scaling for this scenario. Avoid using Spot instances for business critical applications like databases etc.

### b.      Create an RDS Oracle Database

- Create rds first step
  - ![RDS-Management-Console](vpc_setup.assets/RDS-Management-Console.png)

- RDS creating
  - ![image-20221127173824941](vpc_setup.assets/image-20221127173824941.png)
- RDS created
  - ![image-20221127175532527](vpc_setup.assets/image-20221127175532527.png)
  - 

### c.      Create an Oracle userid “<your initials>-HR"

- Connect to RDS
  - ![image-20221127175703148](vpc_setup.assets/image-20221127175703148.png)
- create user
  - ![image-20221127175814058](vpc_setup.assets/image-20221127175814058.png)

### d.      Load the HR Database Schema

![image-20221127175926157](vpc_setup.assets/image-20221127175926157.png)

###  e.      Connect with the Oracle SQL Developer and load the HR Database schema

### f.       Connect to the HR Database Schema using the Oracle SQL Developer

![image-20221127175955099](vpc_setup.assets/image-20221127175955099.png)

### g.      Use the Oracle Data Modeler to reverse engineer your Oracle Database Schema into and ER Diagram (Logical)

![Logical](vpc_setup.assets/Logical-16695896869412.png)

### h.      Reverse Engineer the ER into a Relational Diagram

![Relational_1](vpc_setup.assets/Relational_1-16695896952804.png)

### i.       All the diagrams must have a LEGENT in the graph

See above diagrams