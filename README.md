# ATA-Learn-And-Be-Curious-Project

Follow the instructions in the course for completing the group LBC project.


## Steps for developing/working locally

**>> Activate local AWS container**
* Start Docker

**>> Install the DynamoDB instance in the Docker container - Do this every time Docker has been shutdown**
* Open a terminal in Intelli J and run:
```
./local-dynamodb.sh
```
**>> Activate local DynamoDB interface**
* Open a terminal in Intelli J and run for Windows:
```
set DYNAMO_ENDPOINT=http://localhost:8000 dynamodb-admin
```
* Open a terminal in Intelli J and run for Mac:
```
DYNAMO_ENDPOINT=http://localhost:8000 dynamodb-admin
```
**>> Builds the application and starts the local web server - Do this every time you make code changes to your project**
* Open a terminal in Intelli J and run:
```
./gradlew :Application:bootRunDev
```
**>> Load the test data**
* Open a terminal in Intelli J and run:
```
./dynamodb_preload.sh
```
* If you want to delete all the entries from your tables. Stop all the commands from above that are running in the background;
and start over.

**>> Just for building**
* Open a terminal in Intelli J and run:
```
./gradlew build
```
**>> For Unit tests and Code Coverage**
* Open a terminal in Intelli J and run:
```
./gradlew jacocoTestReport
```
* Reports will be recorded in:
```
Application/build/reports/jacoco/test/html/index.html
```
* Open this file in IntelliJ by right-clicking and going to:
```
Open In -> Browser -> (Your browser of choice)
```
## Tools for developing/working locally
**>> Local DynamoDB interface page**
```
http://0.0.0.0:8001/
```
**>> Local API interface page**
```
http://localhost:5001/swagger-ui/index.html 
```
**>> Frontend local page**
```
http://localhost:5001/index.html 
```
## GIT workflow

* At the beginning of the day, make sure you are in your branch by running:
```
git checkout <your_branch_name>
```
* Before pushing your changes make sure you are in your branch by running this command and checking that an asterisk is next to it:
```
git branch -a
```
* Push your changes by running:
```
git add .
git commit -m "<your_code_change_description>"
git push
```
* Create a "Pull Request" by clicking on the "New Pull Request" button next to your branch in here:
```
https://github.com/KenzieAcademy-SoftwareEngineering/ata-lbc-project-movienightrsvp/branches
```
* Notify a team member for reviewing the code change, approving it; and merging into main.
* Once the changes have been merged into main, run the following commands for keeping the branches in your desktop in sync with the recent changes:
```
git checkout main
git pull
git checkout <your_branch_name>
git pull
git rebase main
```
* If the rebase cannot be performed due to un-committed changes in your branch, commit them and then retry the rebase.