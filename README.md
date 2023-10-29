# Clipper-MS
Clipper management system

[![Deployment-CI/CD](https://github.com/MHormes/Clipper-MS/actions/workflows/deployment.yml/badge.svg)](https://github.com/MHormes/Clipper-MS/actions/workflows/deployment.yml)

[![Collection module CI](https://github.com/MHormes/Clipper-MS/actions/workflows/collectionCI.yml/badge.svg?branch=dev)](https://github.com/MHormes/Clipper-MS/actions/workflows/collectionCI.yml)
[![Trading module CI](https://github.com/MHormes/Clipper-MS/actions/workflows/tradingCI.yml/badge.svg)](https://github.com/MHormes/Clipper-MS/actions/workflows/tradingCI.yml)
[![Client CI](https://github.com/MHormes/Clipper-MS/actions/workflows/clientCI.yml/badge.svg)](https://github.com/MHormes/Clipper-MS/actions/workflows/clientCI.yml)


#### Running the system:
In order to run clipper MS, please pull the repository and execute the 'development' run script.
This script will use the maven-jib plugin to build images for the clipper MS and the database, as well as 
start them up.
The plugin usage will be replaced later in development to create a more flexible solution. 

#### Clipper, series and collected clippers
- Each AppUser has a list (collection) of CollectedClippers.

[//]: # (- Collected clipper holds user specific information)

[//]: # (  &#40;date and location bought, notes&#41;.)
- Clippers only get Created, Updated and Deleted by admins
  (creator is saved in clipper as createdBy).
- All users are capable of CRUD their own CollectedClippers
  -> does not affect Clippers themselves.
- Original series follow standard series rules (Name = series name, max number = 4).
  Custom series need guidelines (e.g. [city]-shops, [brand]-lighters, etc.)
  No series should be mapped to default series 'no-series' (currently seriesId = null).
- Users can create lists to group their collected clippers on their profile
  -> user specific.

  
## BE

### Documentation
###### A general structure is defined to ensure proper in-code documentation.
- Swagger-UI is used to generate openAPI documentation for the API.

[//]: # (- Classes and methods are documented using JavaDoc.)

[//]: # (- Method documentation should include a description of the method, the parameters, and the return value.)

### Logging
###### Back end logging is handled using SLF4J. 
- Logic layer methods should log main operations at the INFO level.
- Model conversion is logged at the INFO level.
- Exceptions should be logged at the ERROR level including reference to 'thrower'.


<br/>

## FE




###### Group format:
Id \
UserId \
Name \
Description \
Clippers

