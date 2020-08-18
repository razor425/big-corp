# big-corp

Basic API to wrap and merge employees, offices and departments endpoints.

Offices and departments sources are currently stored in a hardcoded json file in resources, but its implementations are ready to be upgraded into a full fledged rest api.
Employees are wrapped from a REST API

This API provides a functionality called "expand" which allows to join two different Json structures into one, provided they have an id to bind them.

Currently, there are 4 dependencies that are allowed in our structures:

  * Employee    -> Manager(Employee)
  * Employee    -> Office
  * Employee    -> Department
  * Department  -> Superdepartment

All controller's details are provided in Swagger at ${host:port}/swagger-ui.html once the server is up.
