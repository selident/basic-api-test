# Basic API Tests

### Overview ###

**The structure of Basic API Test**

![image](https://github.com/selident/basic-api-test/blob/master/.github/images/1.png)

Main Features:
- Call API to create Charge Authentication
- Get Response from server and normalize / deserialize the response into a model
- Verify the response values using the deserialized model

Additional Features:
- Using Log4J to create debug log file
- Using TestNG as a runner to have basic HTML test report file
- Using testng.xml file to specify which test package/class/case will be run

Other Integrations:

- Using Github Actions as a CI pipeline to run test (based on Git push action)
- Using Github Actions to create Docker image and run tests inside the docker

### How To Run ###

  * **Run tests on local**
  
    1. Clone repo
    2. Run Maven command to execute tests
        
        ```
        mvn test
        ```
    3. A debug log will be created in main repo folder
    4. A index.html report will be created in `target/surefire-reports` 
    
  * **Build fat-test.jar file to run on other env**

    1. Clone repo
    2. Run Maven command to execute tests

        ```
        mvn clean package -DskipTests
        ```
    3. A test jar file will be created in `target` folder
    4. You can move it to anywhere and run with below command
       
        ```
        java -jar basic-api-test-stage-1.0-SNAPSHOT-fat-tests.jar
        ```

  * **Run with docker**

    1. Clone repo
    2. Run Maven command to execute tests

        ```
        mvn clean package -DskipTests
        ```
    3. A test jar file will be created in `target` folder
    4. Create a docker image which includes above test jar file
       
        ```
        docker build . --file Dockerfile --tag basic-api-test-image
        ```
    5. Run tests inside docker
       
        ```
        docker run basic-api-test-image
        ```

    Note: This flow has been done on Github Actions for this repo.
    You can have a look on [Pull Requests page](https://github.com/selident/basic-api-test/pulls?utf8=%E2%9C%93&q=is%3Apr+is%3Aclosed+).
    
### Details of Implementation ###

  ![image](https://github.com/selident/basic-api-test/blob/master/.github/images/2.png)

  * **Service**

    1. Interface Class
    2. Implementation Class: implement two method for getting responses and normalizing responses
    3. Model Classes: to serve for normalizing data and deserializing JSON response
    
    ![image](https://github.com/selident/basic-api-test/blob/master/.github/images/3.png)
    
  * **Tests**
  
    1. AuthorizationResponseTest: to test assert methods
    2. AuthorizationServiceImplServiceTest: to test API call and normalize methods
    3. TestNGRunner: the runner to run test when using JAR file 
    
    Note: There are some `additional tests` were disabled, because they are failing.
    When writing required tests, we assumed that the current responses from API were correct. 
    
    ![image](https://github.com/selident/basic-api-test/blob/master/.github/images/4.png)
    
  * **Others**
  
    1. Dockerfile: to create docker image
    2. `.github`: to setup Github Actions for running test inside docker or running test directly
    
    ![image](https://github.com/selident/basic-api-test/blob/master/.github/images/5.png)

    3. Report after tests
       
    ![image](https://github.com/selident/basic-api-test/blob/master/.github/images/6.png)
        
    ![image](https://github.com/selident/basic-api-test/blob/master/.github/images/7.png)