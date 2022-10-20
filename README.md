# Technical test

### Installation
To install the project, you need to:

* Execute the [init_db.sql](src/main/resources/init_db.sql) file into your PostgreSQL environment.
* Execute the [insert_sessions.sql](src/main/resources/insert_sessions.sql) to update your db for question 2.
* Run the Springboot application [InterviewApplication.java](src/main/java/com/cano/interview/InterviewApplication.java)

### Guides
To use the application, you need to execute a POST request on http://localhost:8080/stats with a JSON body like :

```json
{
  "token" : "c98arf53-ae39-4c9d-af44-c6957ee2f748",
  "customer": "Customer1",
  "content": "channel1",
  "timespan": 30000,
  "p2p": 560065,
  "cdn": 321123,
  "sessionDuration": 120000
}
```