[![Build Status](https://travis-ci.org/adalrsjr1/maverick.svg?branch=master)](https://travis-ci.org/adalrsjr1/maverick)

![Maverick design overview. A microservice-based application  is formed by several microservices, represented by hexagons, distributed across several hosts.](maverick-arch.png)

# Maverick Design

We built Maverick to help Microservice-Based Application (μApp) developers with the challenges of collecting, evaluating, and responding to logged messages that signal an anomaly in a timely manner. 
The figure above presents the architecture of Maverick, which includes a control-loop inspired by the [MAPE-K architecture](http://www-03.ibm.com/autonomic/pdfs/AC%20Blueprint%20White%20Paper%20V7.pdf). Essentially, it checks behavioral 
and quality properties of μApp as they are executing. When a violation is detected, i.e., a property is not satisfied, Maverick takes appropriate adaptation actions associated with the violation. Thus, awry behaviors can be detected at runtime and contention or corrective actions, such rollback the application to a prior state (configuration), can be applied automatically avoiding that (cascading) failures may crash the whole application.

Maverick's design includes four  components that are responsible for monitoring the application, analyzing the monitored events, planning actions in response to some event, and executing these actions. Maverick also has a knowledge base to store adaptation plans and adaptation scripts to deal with properties violations.

Maverick works by receiving events from an μApp and evaluating them against [temporal](https://www.cs.cornell.edu/fbs/publications/RecSafeLive.pdf)  or [quality](https://en.wikipedia.org/wiki/ISO/IEC_9126) properties defined by μApp's domain experts. Events gathered by the sensors are sent to a *Collector*, responsible for maintaining the temporal ordering of the events, transforming them into specific events named *Symptoms* and sending them to *Analyzers*.

Maverick performs log analysis on-the-fly, by evaluating logs against several properties, and adapting the μApp when one or more properties are violated. These properties describe expected behaviors and desired quality properties of the μApp, e.g., "action 'a' must occur between 'c' and 'd'" (behavioral) and "the response time must be less than 5ms" (quality).

To check these properties, Maverick uses lightweight Runtime-Verification techniques, Linear Temporal Logic to express desired behaviors, and predicates to describe quality properties.

An analyzer  (*Processing Unit*) is created for each property. The input of the processing unit is a symptom generated by the Collector. If a property is violated by the symptom, then a *Change Event* is thrown to *Planner* that decides how to handle the violation. A processing unit can be an automata commonly used to evaluate temporal properties or a custom mechanism to evaluate predicates.

In an automata-based processing unit, a violation is detected when at the end of evaluation, the automata is in a non acceptance-state. In processing units with a custom mechanism to evaluate predicates, a violation is detected when a predicate is evaluate as false. In both cases, a domain expert needs to define a parser in the Processing Unit to transform the symptoms, plain text messages, into key-value data structures readable by these Processing Units. Moreover, a developer should define the strategy to evaluate a property, for example, by evaluating symptoms one-by-one, \textit{n} symptoms at time, a set of symptoms in a given period or any other custom strategy.

The Planner stores several Adaptation Policies for the μApp. The Adaptation Policy has a priority that defines the severity of the violation. Thus, policies to handle critical violations, such a microservice crash, are annotated with high severity, which influences the speed with which an adaptation will be applied. The policy also associates a Change plan to a given Change Request. 

A Change Request has data about a property's violation an is used to select an action to handle a violation. When the Planner receives a Change Request from a processing unit, it selects the Adaptation Policies associated with the property violation detected. In the current version of Maverick, the Planner only maps a property violation with one or several Change Plans. When the Planner receives a Change Request, it iterates through Change Plans looking for those that are associated with the violation detected. Those  that have been selected are send to Adaptation Engine, which is responsible for carrying out the adaptation.

A Change Plan contain the name of an adaptation script as well parameters to configure the script's behavior. Each Change Plan is sorted according to its priorities, and one-by-one they are sent to be executed in The Adaptation Engine. 

The Adaptation Engine has a repository of Adaptation Scripts with all actions can be applied on the μApp. 

A script consists of several atomic actions related to the Actuators. Therefore, it is possible to combine actions available on several actuators to create a new adaptation Script. The execution of an Adaptation Script consists of distributing the atomic actions across the actuators that are responsible for applying the change on the μApp.

In addition, the Adaptation Engine maintain an Adaptation Context according Adaptation Scripts are executed. When a script is performed, it can store, replace or retrieve data from this Context. These data are maintained after the execution of a script and might be used by the next one, allowing that complex adaptations be applied by creating a dependency of data among several Adaptation Scripts, or avoiding concurrency issues by paralleling actions on several Actuators.   

Actuators are remote interfaces provided by tools used by a μApp or to manage some aspects of an μApp. Examples of potential actuators are Docker Daemon Interface, Kubernetes Web interface, Software Defined Network Controllers or even an e-mail server used to notify the application engineers.

The policies and adaptation scripts are part of the knowledge base of Maverick.

# Contributing

[Contribution guidelines for this project](CONTRIBUTING.md)
