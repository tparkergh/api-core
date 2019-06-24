# Drools Engine (Business Rules)
Helps to integrate Drools in backend API and organize rules in simple file structure. 

## Architectural Decisions
* Created to simplify Drools integration into backend APIs
* Used in API Core Legacy Data Access Services to implement Doc Tool rules
* Used in APIs for business validation rules
* Used in ABAC for authorization rules
* Each set of the rules can be stored in separate folder with configuration. auto-discovery file contains list of rules which will be initialized into Knowledge Base. kmodule.xml contains session configuration.

## Usage
* Example of Drools Engine implementation in ABAC module
  * Rules folder https://github.com/ca-cwds/api-core/tree/master/api-core-abac/src/main/resources/client-authorization-rules
  * Configuration https://github.com/ca-cwds/api-core/blob/master/api-core-abac/src/main/java/gov/ca/cwds/authorizer/drools/configuration/ClientResultAuthorizationDroolsConfiguration.java 
  * Authorizer https://github.com/ca-cwds/api-core/blob/master/api-core-abac/src/main/java/gov/ca/cwds/authorizer/ClientResultReadAuthorizer.java  

## Links
 * Drools summary https://github.com/ca-cwds/API/wiki/Drools-for-Business-and-Doctool-Rule-Execution