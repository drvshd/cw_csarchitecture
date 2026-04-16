"Smart Campus" Sensor & Room Management API

Overview of this API design - 


What is needed to run - 


Instructions on how to run - 


Curl commands for showing successful interactions - 


REPORT

Part 1 - Service Architecture & Setup
Task 1.1
By default, JAX-RS creates a new instance of a resource class for every individual incoming request. This means that the data stored in an instance variable cannot persist between requests, which means that once the request is gone, the object is also gone along with all the data in it. In order for mine to work, I created a DataStore class which holds static fields, meaning that it belongs to the class instead of the individual instance. This was structred in this format so that all the requests can use the same maps. I also decided to use ConcurrentHashMap instead of a regular one as the server can handle multiple requests at once, which may corrupt data if two requests are done at the same time.

Task 1.2
HATEOAS stands for Hypermedia as the engine of application state. The idea is that the client will be given hypermedia, when it connects to the API, so that it understands what it can do. For example, my DiscoveryResource java file includes links for the rooms and sensors directly in the response. A client will benefit as they will be able to navigate easily by following these links. It would make it extremely similar to hyperlinks on a website. When comparing to static documentation, this method is more beneficial because if the server was to change a URL, clients that were to follow the links provided wouldn't face issues.

Part 2 - Room Management
Task 2.1


Task 2.2


Part 3 - Sensor Operations & Linking
Part 3.1


Part 3.2


Part 4 - Deep Nesting with Sub - Resources
Part 4.1


Part 4.2


Part 5 - Advanced Error Handling, Exception Mapping & Logging
Part 5.1


Part 5.2


Part 5.3


Part 5.4


Part 5.5
