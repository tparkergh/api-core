# CWDS Core API

The CWDS Core API project provides a common set of classes which are used across CWDS API Modules.

## Development Approach
* API Core is a multi-project Gradle. Please create new sub-modules for new shared library.
* API Core shall include only functionality which potentially can be used by multiple API modules (cals-api, intake-api, cans-api, dora, etc.)
* Everything specific to one API module shall be moved to appropriate module (Neutron, Ferb, Perry, CALS API, etc.)
* API Core shall not be considered as the main code base for any of Technology Platform Teams (TPT) teams. This is shared code base and any change can cause issues in other modules. So changes shall be related to common API infrastructure and code related to CWS/CMS which can be useful to other TPT teams

## Project Structure
* api-core-common - shared classes used by other modules
* api-core-rest - minimally needed classes for every REST API module, dependent on api-core-common
  * Logging and audit
  * Validation (only generic)
  * Dropwizard Configuration (only generic)
  * Error handling
  * Swagger
  * API base classes (delegates, services, filters)
* api-core-cws (deprecated) - classes related to persistence (CWS/CMS, Elasticsearch, NS), dependent on api-core-common and used in Ferb API only
  * Dropwizard Configuration
  * Hibernate dependency
  * DAO, Entities
* api-legacy-data-access - contains entities and DAOs for CWS/CMS DB2 data access
* api-legacy-data-access-services - contains Data Access Services for CWS/CMS DB2 database
* api-core-abac - Attribute Based Access Control authorizers for key entities like Client and Placement Home
* api-core-es - Elasticsearch related helper classes
* drools-engine - Drools integration for API components
* api-core-test-support - testing helpers for functional API testing  
* api-core - parent module dependent on other modules.

## Generating Licenses
The legal folder contains files listing the licenses for application dependencies API Core library contains several subproject each with it's own dependencies. Each subproject must generate license reports, then the individual reports are aggregated into a csv file in the root legal folder.

Each project contains a legal folder that contains a list of licenses. This list is created via a license report plugin. The plug in stores data in the build folder and copies over the csv files.

### Update license report
To generate report run:

```./gradlew  generateAllLicenseFiles libLicenseReport```

generateAllLicenseFiles updates submodules licenses
libLicenseReport creates the report file

## Links
* API development approach. Code reusability. - https://osicagov.sharepoint.com/sites/TechnologyPlatformTeam2/Shared%20Documents/Forms/AllItems.aspx?id=%2Fsites%2FTechnologyPlatformTeam2%2FShared%20Documents%2FArchitecture%2F2018%5F08%5F16%20API%20reusability%2Epptx&parent=%2Fsites%2FTechnologyPlatformTeam2%2FShared%20Documents%2FArchitecture 

# Questions

If you have any questions regarding the contents of this repository, please email the Office of Systems Integration at FOSS@osi.ca.gov.
