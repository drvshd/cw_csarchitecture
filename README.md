"Smart Campus" Sensor & Room Management API

Overview of this API design - 
This is a RESTful JAX-RS API for managing rooms and sensors in a smart campus. It allows the user to use CRUD (create, read, update and delete) for the rooms and sensors, along with access to sensor readings through sub-resource. The API has HATEOAS based discovery endpoint, filtering for sensors, custom exceptions for errors a user may run into, and request/response logging through a JAX-RS filter. All data given is stored using a DataStore class which is backed by ConcurrentHashMaps, ensuring the requests are handled safely.

What is needed to run - 
NetBeans IDE
Java 8 or higher
Apache Tomcat 9.x
Maven (already in NetBeans)  
The following Maven dependencies that are handled automatically via pom.xml:
    jersey-container-servlet 2.32
    jersey-hk2 2.32
    jersey-media-json-jackson 2.32
Postman (testing of API)


Instructions on how to run - 
1. Clone or download and open on NetBeans.
2. Right click the project and press on "Clean and Build" to build the WAR file and install all dependencies.
3. Install Apache Tomcat and make sure to add as server on NetBeans. Once added, make sure it is runnning.
4. Right click the project and run it. It will automatically open in our default browser, on Tomcat.
5. The correct site should be: http://localhost:8080/cw_csarchitecture/api/v1

Curl commands for showing successful interactions - 
1. Discovery 
curl -X GET http://localhost:8080/cw_csarchitecture/api/v1/
2. Create a room
curl -X POST http://localhost:8080/cw_csarchitecture-1.0/api/v1/rooms 
  -H "Content-Type: application/json" 
  -d '{"id": "room1", "name": "Name of Class", "capacity": 123}' 
(variables are changable)
3. Get all rooms
curl -X GET http://localhost:8080/cw_csarchitecture/api/v1/rooms
4. Get a room by ID
curl -X GET http://localhost:8080/cw_csarchitecture-1.0-SNAPSHOT/api/v1/rooms/room1
6. Create a sensor (room must already exist)
curl -X POST http://localhost:8080/cw_csarchitecture-1.0-SNAPSHOT/api/v1/sensors \
  -H "Content-Type: application/json" \
  -d '{"id": "sensor1", "type": "temperature", "status": "ACTIVE", "currentValue": 0.0, "roomId": "room1"}'
7. Get all sensors
curl -X GET http://localhost:8080/cw_csarchitecture-1.0-SNAPSHOT/api/v1/sensors



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
Going by the sub resource locator patter means that every action is seperated into a class. By seperating the classes, it allows me to change one of the files without the risk of the other getting errors. Although for a small API it may not seem like it is worth it, however for a scaling API it would be more beneficial as the time passes.

Part 5 - Advanced Error Handling, Exception Mapping & Logging
Part 5.2
HTTP 422 focuses more on the idea of what's inside the JSON file that was sent to the server. If the content of the JSON file references something that is not stored in the server, the server will not be able to process the request. Therefore, a 422 status code would be returned. HTTP 404 focuses on the URLs, whether the URL entered is correct or not. If a client tried to enter a URL that simply did not exist in the server, it will give a 404. HTTP 404 focuses more on web pages whereas HTTP 422 deals with the data given. 

Part 5.4
A stack trace can be used by developers to pinpoint what part of the code messed up and it returns the sort of error. It gives exact Java file class names, the methods and the lines that went wrong. It also exposes the framework and the version that is being run, allowing an attacker to search up exploits for that specific version. Once they find exploits that could be used on the API, they could possibly have access to the entirety of the code, leaving all the code in bad hands.

Part 5.5
If you were to place Logger lines in every single method, it would have to also be in every class resource class. This is extremely inefficient as it will mean that you have to change every piece of code containing Logger in every resource class. Using JAX-RS filters will benefit the developer as it will allow them to create a filter once in one file and the requests will always pass through it regardless of the different classes. Readability will also improve, as there will be no scattered Logger lines throughout the classes. 