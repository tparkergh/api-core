# Attribute Based Access Control (ABAC)

Mainly contains authorization rules for Legacy objects like Client and Placement home. ABAC allow to implement complex authorization rules based on particular attributes of the object. For example sealed and sensitive logic for Clients in CWS/CMS. ABAC is used in backend APIs like CALS API, FERB API, CANS API.

## Architectural Decisions
* Uses annotation based framework in Perry https://github.com/ca-cwds/perry/tree/master/api-security
* Implementations of Authorizers in API Core https://github.com/ca-cwds/api-core/tree/master/api-core-abac/src/main/java/gov/ca/cwds/authorizer 
* Authorization rules should be consolidated in one place and reused in different parts of solution (Client example)
* Uses Drools for authorization rules https://github.com/ca-cwds/api-core/blob/master/api-core-abac/src/main/resources/client-authorization-rules/client-authorization.drl

## Project Structure
* In resources folder for each type of object created separate folder with authorization Drools rules. 
* Authorized should be implemented for each object to provide input facts to Drools.

## Links
 * API Authorization Library documentation https://github.com/ca-cwds/perry/tree/master/api-security