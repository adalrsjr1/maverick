### Processing Unit

To create a new Processing Unit

1. Create a new Project
2. Add to pom.xml:
  1. For all processing units
      ~~~
      <groupId>gfads.cin.ufpe.maverick.analyzer</groupId>
      <artifactId>maverick-analyzer</artifactId>
      ~~~
      
  2. If it is a temporal processing unit, it is also necessary add
      ~~~
      <groupId>gfads.cin.ufpe.maverick.analyzer</groupId>
      <artifactId>maverick-analyzer-temporal</artifactId>
      ~~~


3. In a quality processing unit, implement the method `process()` in class <Name>Property.java extending Property.java
  * A ProcessingUnit must have only one Property;
  * A property always receive a MavericSymptom;
  * When a violation happens a MaverickChangeRequest must be sent to Planner by using:
  ~~~
  	Property.sendChangeRequest(changeRequest)
  ~~~
    
   In a temporal processing unit, implement the method check() in class Checker<Name>.java extending TrasitionChecker.java
   * A TemporalProcessingUnit must have only one Checker;
   * A Processing Unit always receive a MavericSymtom;
