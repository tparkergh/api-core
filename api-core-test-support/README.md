# Test Support Library
Library helps to implement functional API tests for different authentications modes. 

## Architectural Decisions
* We need to test APIs end-to-end with authentication and authorization rules 
* Better to have one library which helps integrate with different Perry modes and can be used for functional testing in all APIs  
* Library provides test integration with Perry Dev mode for integration testing in PreInt and Perry Mock for unit testing
* Used in CALS API, Case Management API, CANS API for fixture based functional testing
* Provides ability to write tests which can be used as unit and as integration. Reduce time for writing tests
 
## Key concept
* Library has implementations for each type of authentication like: 
  * CognitoTokenProvider - production Perry mode
  * PerryV2DevModeTokenProvider - development Perry mode (JSON)

## Links
 * Backend Testing Approach https://osicagov.sharepoint.com/sites/TechnologyPlatformTeam2/Shared%20Documents/Forms/AllItems.aspx?id=%2Fsites%2FTechnologyPlatformTeam2%2FShared%20Documents%2FArchitecture%2F2018%5F10%5F03%20Backend%20Testing%20approach%2Epptx&parent=%2Fsites%2FTechnologyPlatformTeam2%2FShared%20Documents%2FArchitecture