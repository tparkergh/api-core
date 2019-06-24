# Legacy Data Access (CWS/CMS)
Common storage for all CWS/CMS legacy DB2 entities and Data Access Objects (DTO).  

## Architectural Decisions
* All CARES API uses the same DB2 CWS/CMS database so we can implement data access objects in on place and share it between all API components
* Legacy Data Access contains only Entities and pure DTO. It has no knowledge of business logic
* Hibernate used for data access implementation
* Legacy Data Access used by Legacy Data Access Services as dependency
* Used in CALS API, CANS API, Ferb API, Case Mangement API
 
## Links
 * Legacy Development Guide https://osicagov.sharepoint.com/sites/TechnologyPlatformTeam2/_layouts/15/WopiFrame.aspx?sourcedoc=%7b7D553D6A-0A5B-4FEE-A2AE-CD6A8ED5D6F6%7d&file=Legacy%20Development%20Guide.docx&action=default&IsList=1&ListId=%7b23359B33-C3B6-481F-A33C-027B0A464AB5%7d&ListItemId=26&cid=a9ace45e-6ec8-4f84-b98f-f471971e5485