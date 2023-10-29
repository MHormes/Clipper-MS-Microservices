# Clipper-MS

Clipper management system

[![Deployment-CI/CD](https://github.com/MHormes/Clipper-MS/actions/workflows/deployment.yml/badge.svg)](https://github.com/MHormes/Clipper-MS/actions/workflows/deployment.yml)

[![Collection module CI](https://github.com/MHormes/Clipper-MS/actions/workflows/collectionCI.yml/badge.svg?branch=dev)](https://github.com/MHormes/Clipper-MS/actions/workflows/collectionCI.yml)
[![Trading module CI](https://github.com/MHormes/Clipper-MS/actions/workflows/tradingCI.yml/badge.svg)](https://github.com/MHormes/Clipper-MS/actions/workflows/tradingCI.yml)
[![Client CI](https://github.com/MHormes/Clipper-MS/actions/workflows/clientCI.yml/badge.svg)](https://github.com/MHormes/Clipper-MS/actions/workflows/clientCI.yml)

#### Running the system:

Currently the architecture of the system is being updated in order to run the system in a kubernetes stack. Building images can be done using the configured mvn jib plugin. These images can be used to deploy the kubernetes deployments, services and the igress in order to run the system on a local kubernetes implementation (like k3d).

Once the rebuild of the architecture is proven to work locally, the solution will be moved the Fontys kubernetes stack.

#### Clipper, series and collected clippers

- Each AppUser has a list (collection) of CollectedClippers.

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

### Logging

###### Back end logging is handled using SLF4J.

- Logic layer methods should log main operations at the INFO level.
- Model conversion is logged at the INFO level.
- Exceptions should be logged at the ERROR level including reference to 'thrower'.

<br/>

## FE

###### Group format:

Id 
UserId 
Name 
Description 
Clippers

[//]: #
[//]: #
[//]: #
[//]: #
