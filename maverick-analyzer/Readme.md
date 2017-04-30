### Processing Unit

To create a new Processing Unit

1. Execute the following script into the project (folder) maverick-analyzer-mock

~~~
$ manage-analyzer.sh <create> <name>
~~~

This script will create a new project named

~~~
maverick-analyzer-<name>
~~~ 

2. Implement a class like MockProperty.java (Mock is the property name)
  * A ProcessingUnit must have only one Property;
  * A property always receive a MavericSymptom;
  * When a violation happens a MaverickChangeRequest must be sent to Planner;
  * Ideally, every property should be implemented in a new project and then added into 
    a  ProcessingUnit as a dependency.
    
3. to delete a Processing Unit project, execute the script in folder maverick-analyzer-mock 

~~~
create-new-analyzer.sh <remove> <name>
~~~
