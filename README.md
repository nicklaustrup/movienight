# ATA-Learn-And-Be-Curious-Project

Follow the instructions in the course for completing the group LBC project.


Project Steps
==========

* (Activate local AWS container)
  Start Docker

* (Install the DynamoDB instance in the Docker container - Do this every time Docker has been shutdown)
  ./local-dynamodb.sh

* (Activate local DynamoDB interface)
  DYNAMO_ENDPOINT=http://localhost:8000/ dynamodb-admin

* (Builds the application and starts the local web server - Do this every time you make code changes to your project)
  ./gradlew :Application:bootRunDev

* Just for building
  ./gradlew build

* (For Unit tests and coverage) A reminder on how to find the code coverage reports
  In this project, we will be viewing code coverage reports to measure how much of our code is covered by tests. There are several ways you can view these reports -

You can run ./gradlew jacocoTestReport
Reports will be recorded in build/reports/jacoco/test/html/index.html in the main package. Open this file with IntelliJ by right-clicking and going to Open In -> Browser -> Build-in Preview.

Tools
====

(Local DynamoDB interface page)
http://0.0.0.0:8001/

(Local API interface page)
http://localhost:5001/swagger-ui/index.html

(Frontend local page)
http://localhost:5001/index.html