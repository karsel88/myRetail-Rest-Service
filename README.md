## MyRetail Rest Service

MyRetail RESTful service used to retrieve product and price details by ID.

It uses Embedded MongoDb NoSQL data source to store the product price details.

## Steps to run it locally
1. Clone it from github repository
2. mvn clean install to download and build all dependency libraries. 
3. Run the application

Note: Since it uses embedded mongoDB, i have some test data preloaded through commandLineRunner during the server bootup.

##  Other information

**Source code:**
https://github.com/karsel88/myRetail-Rest-Service

**LocalHost:**
http://localhost:8080/myRetail/api/products/13860428