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
Both returning an ID alone and the full details are useful in their own ways. If you decide to print ID alone, it will reduce the amount of data that has to be sent, as well as reducing the amount that the client processes. This is good when you want to have faster processes, however it lacks in data. However, returning full details off the same request in theory would be better. For instance, if there was a building with far more rooms, having to do separate requests to get the ID then the name or capacity would be far slower. I decided on using this as it would be more efficient in the long term to returning the list of rooms.

Task 2.2
The delete request is idempotent. First time you sent a delete request, the room will be removed and you will receive a "204 no content" response to show that it passed. Any attempt afterwards wil show "404 not found" because there are simply no rooms with that ID. Therefore if a client accidentally ends up doing the same delete operation, there will be no issues as only the first one will work. This is what idempotency is meant to do, the same operation can be sent multiple times and it'll only run once without causing errors or data corruption.

Part 3 - Sensor Operations & Linking
Part 3.1
If a client was to send an unsupported format (anything other than an APPLICATION_JSON), JAX-RS would spot it and return a 415 response code. This enforces strict input validation as it'll mean that the client needs to be strictly following the format that needs to be sent.

Part 3.2
The benefit of query parameters is that they can be used to trim down a collection (of rooms and sensors in this scenario). It allows the client to have a look through the list to find the exact object they need. Had I used a method like /sensors/type/(sensorID), a new endpoint would be used for every filter combination, which would slow down the process of looking through the list a lot more. 

Part 4 - Deep Nesting with Sub - Resources
Part 4.1


Part 4.2


Part 5 - Advanced Error Handling, Exception Mapping & Logging
Part 5.1


Part 5.2


Part 5.3


Part 5.4


Part 5.5
