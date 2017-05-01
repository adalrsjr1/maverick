### Processing Unit

To create a new Processing Unit

1. Execute the following script into the project maverick-analyzer-tutorial

~~~
$ manage-analyzer.sh <create> <name of the new project  property's name>
~~~

This script will generate a new project named

~~~
maverick-analyzer-<name>
~~~ 

2. Implement the method process in class <Name>Property.java 
  * A ProcessingUnit must have only one Property;
  * A property always receive a MavericSymptom;
  * When a violation happens a MaverickChangeRequest must be sent to Planner by using:
  ~~~
  	Property.sendChangeRequest(changeRequest)
  ~~~
    
3. to delete a Processing Unit project, execute the script in folder maverick-analyzer-mock 

~~~
create-new-analyzer.sh <remove> <name of property>
~~~
