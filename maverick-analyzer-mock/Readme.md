### Processing Unit

To create a custom Processing Unit:

1. Make a copy of analyzer project, changing the name
   ~~~
    e.g.: maverick-analyzer-mock -> maverick-analyzer-MyProcessingUnit
   ~~~

2. Update the name of this new project in its in pom.xml
   ~~~
    <project>
    ...
        <artifactId>maverick-analyzer-mock</artifactId> -> <artifactId>maverick-analyzer-MyProcessingUnit</artifactId>
    ...
        <name>maverick-analyzer-mock</name> -> <name>maverick-analyzer-MyProcessingUnit</name> 
    ... 
    </project>
   ~~~

3. Implement a class like MockProperty.java (Mock is the property name)
  + A ProcessingUnit must have only one Property;
  + A property always receive a MavericSymptom;
  + When a violation happens a MaverickChangeRequest must be sent to planner;
  + Ideally, every property should be implemented in a new project and then added in the ProcessingUnit (copy) as a dependency.
    
