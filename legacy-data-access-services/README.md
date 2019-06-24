# Legacy Data Access Services (CWS/CMS)
Common storage for all CWS/CMS Data Access Services.  

## Architectural Decisions
* All CARES API uses the same DB2 CWS/CMS database and should implement the same DocTool rules related to data consistency. It make sense to implement all Data Access Service in one place and share among all API components. Such approach reduces overall development time due to efficient reusability model.
* Data Access Services incorporate DocTool rules implemented with Drools
* Each Data Access Service can work with multiple DAOs and other Data Access Services
* Used in CALS API, CANS API, FERB API, Case Management API
 
## Links
 * Legacy Development Guide https://osicagov.sharepoint.com/sites/TechnologyPlatformTeam2/_layouts/15/WopiFrame.aspx?sourcedoc=%7b7D553D6A-0A5B-4FEE-A2AE-CD6A8ED5D6F6%7d&file=Legacy%20Development%20Guide.docx&action=default&IsList=1&ListId=%7b23359B33-C3B6-481F-A33C-027B0A464AB5%7d&ListItemId=26&cid=a9ace45e-6ec8-4f84-b98f-f471971e5485