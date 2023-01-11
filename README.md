# Read Me First
The following was discovered as part of building this project:

* The JVM level was changed from '11' to '8', review the [JDK Version Range](https://github.com/spring-projects/spring-framework/wiki/Spring-Framework-Versions#jdk-version-range) on the wiki for more details.
* The original package name 'com.javachallange.spring-boot-crud' is invalid and this project uses 'com.javachallange.springbootcrud' instead.

#About 
This project focuses on creating Rest APIS for Patients and Doctors.

*I have focused on Providing spring security for the CRUD Api's to protect from unauthorized access.
*I have handled exception handling globally using ControllerAdvice.
*Unit tests included for DoctorsController. Due to my workloads and time constraints, I have added exception handling and unit tests for Doctor CRUD flow alone. 

Without authentication users cannot access the Api's.
Upon authentication JWT token will be sent, Which we have to set in bearer part of authorization header to access CRUD API's.

***************************************************
             Points to remember
***************************************************
-Role based Authorization has been implemented, i.e. Only Admin has rights to save and delete the entries, Other users can only access GET API's. 	
-By default upon application start up one admin role is created with 
  username:admin123
  password:admin@12345
-For newly created users by default the roles will be user role.  

# Getting Started

Setting Up Project:

git clone https://github.com/anjaliarasu/spring-boot-crud-docker.git

mvn clean package -DskipTests

docker-compose up


Endpoints and payload:
Sno| HTTP Verbs | Endpoints | Action |
   | --- | --- | --- |
1. | POST | /users | To add users |
2. | POST | /authenticate | To Authenticate user and get Token upon successful authentication |
3. | GET | /doctors | To retrieve all doctors on the platform |
4. | GET | /doctors/{id} | To retrieve doctors with id from the platform |
5. | POST | /doctors | To save doctors to the platform |
6. | PUT | /doctors | To update a doctor with payload |
7. | DELETE | /doctors/{id} | To delete a doctor with id |
8. | GET | /patients | To retrieve all patients on the platform |
9. | GET | /patients/{id} | To retrieve patients with id from the platform |
10.| POST | /patients | To save patients to the platform |
11.| PUT | /patients | To update a patient with payload |
12.| DELETE | /patients/{id} | To delete a patient with id |

1.| POST | /users | To add users |
Endpoint: http://localhost:8080/users
Payload:
{
    "userName":"Anjali",
    "userFirstName":"Anjalikumari",
    "userLastName":"T",
    "userPassword":"1234"
}
Response:
{
    "userName": "Anjali",
    "userFirstName": "Anjalikumari",
    "userLastName": "T",
    "userPassword": "$2a$10$W2pz6jAr0Ghc9v30SF.UK.8YDLjF7yW0pilwxu3kIZ0tfIJaDfEgC",
    "roles": [
        {
            "roleName": "User",
            "roleDescription": "Default role for newly created record"
        }
    ]
}

2.| POST | /authenticate |

Endpoint:http://localhost:8080/authenticate

Payload:
{
    "userName":"Anjali",
    "userPassword":"1234"
}

Response:
{
    "user": {
        "userName": "Anjali",
        "userFirstName": "Anjalikumari",
        "userLastName": "T",
        "userPassword": "$2a$10$wHGwhnxC4pyLcNHGzM1uq.iAOb86wh60J9XR6Ndl8Ebi4tEo7Nl.K",
        "roles": [
            {
                "roleName": "User",
                "roleDescription": "Default role for newly created record"
            }
        ]
    },
    "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBbmphbGkiLCJleHAiOjE2NzI5NTk3OTUsImlhdCI6MTY3Mjk0MTc5NX0.8zoAJu0WO4N5OtJy21LY9k_YdlbDk0e1v5l6ESoo3IqH6PEnDtt2i5PMpyOmBP-EQmZiAn3G1c4LwZR_E5UkBQ"
}

This token should be used in authorization bearer part of further requests for crud apis

[3]. Endpoint :http://localhost:8080/doctors
If we access [3] without token
{
    "timestamp": "2023-01-05T18:23:11.830+00:00",
    "status": 401,
    "error": "Unauthorized",
    "path": "/doctors"
}

if accessed with token

[4].Endpoint :http://localhost:8080/doctors/1

[5]. Endpoint:http://localhost:8080/doctors

Payload: {
    "name":"Dr. Asha",
    "emailAddress":"asha@gmail.com",
    "city":"coimbatore",
    "department":"ortho",
    "phoneNumber":9897645324
}

if we post a data with existing email or phone number it shows DataIntegrityViolationException exception

if payload: {
    "name":"Dr. Aarthi",
    "emailAddress":"asha@gmail.com",
    "city":"coimbatore",
    "department":"ortho",
    "phoneNumber":9897645324
}

Response:
{
    "timestamp": 1673444294596,
    "status": "406",
    "error": "Validation Error",
    "exception": "org.springframework.dao.DataIntegrityViolationException",
    "message": "Doctor with mail address asha@gmail.com already registered",
    "messageType": "ERROR",
    "path": "/doctors",
    "identifier": null
}

similarly, below are few scenarios which throws validation errors and exception 
payload: 
{
    "name":"Dr. Dhanush",
    "emailAddress":"dhanushnm@gmail.com",
    "city":"coimbatore",
    "department":"ortho",
    "phoneNumber":9895345324
}
Response:
{
    "timestamp": 1672922809011,
    "status": "406",
    "error": "Validation Error",
    "exception": "org.springframework.dao.DataIntegrityViolationException",
    "message": "Doctor with phone number 9895345324 already present",
    "messageType": "ERROR",
    "path": "/doctors",
    "identifier": null
}
payload: 
{
    "name":"Dr. Dhanush",
    "emailAddress":"dhanushnm@gmail.com",
    "city":"coimbatore",
    "department":null,
    "phoneNumber":8765432098
}
Response:
{
    "timestamp": 1672925012304,
    "status": "400",
    "error": "Validation Argument Error",
    "exception": "org.springframework.web.bind.MethodArgumentNotValidException",
    "message": "Department cannot be null",
    "messageType": "ERROR",
    "path": "/doctors",
    "identifier": null
}


[6]. Put : http://localhost:8080/doctors

payload:{
    "id":2,
    "name":"Dr. Aarthi",
    "emailAddress":"asha@gmail.com",
    "city":"coimbatore",
    "department":"ortho",
    "phoneNumber":9997645324
}

[7].http://localhost:8080/doctors/1

[8].Get: http://localhost:8080/patients

[9].Post: http://localhost:8080/patients
Payload:
{
   "name":"Anand",
   "emailAddress":"anand1@gmail.com",
   "phoneNumber":"9895625321",
   "city":"Chennai",
   "type":"Inpatient",
   "doctor":{"id":2}
}
response:
{
"id":1,
   "name":"Anand",
   "emailAddress":"anand1@gmail.com",
   "phoneNumber":"9895625321",
   "city":"Chennai",
   "type":"Inpatient",
   "doctor":{"id":2}
}

[10]. Patients-Get by id
http://localhost:8080/patients/1

[11].PUT http://localhost:8080/patients
Payload:{
"id":1,
   "name":"Anand N",
   "emailAddress":"anand1@gmail.com",
   "phoneNumber":"9895625321",
   "city":"Chennai",
   "type":"Inpatient",
   "doctor":{"id":2}
}

[12].DELETE http://localhost:8080/patients/1


Some scenarios when user role tries for post or delete request,

try [5] with user login token

following exception will be got 

{
    "timestamp": 1672942829520,
    "status": "500",
    "error": "Internal Server Error",
    "exception": "org.springframework.security.access.AccessDeniedException",
    "message": "Access is denied",
    "messageType": "ERROR",
    "path": "/doctors",
    "identifier": null
}

