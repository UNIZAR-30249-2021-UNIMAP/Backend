# Backend

## Getting started
1. Build all Gradle Modules.
1. In project root folder open a terminal and run the following command:
    ```
    docker-compose up --build
    ```
   It will set up the different containers.
1. You can check with Eureka _localhost:8761_ that the other two services (Gateway and App-server) are running correctly.
1. Try a connection against the web in _localhost:7000_. You can find _Geoserver_ service at _localhost:8080/geoserver_.
