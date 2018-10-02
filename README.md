# Home Automation
 
A web app simulating house automation. 

Both front-end and back-end implementations are available.

jQuerry is used to execute network calls.

## Getting Started

git clone https://github.com/VladManolache/jHttpServer.git

### Prerequisites

HomeAutomation-API: 
* [Java 9](https://www.oracle.com/java/java9.html)
* [Maven](https://maven.apache.org/) 
* [jHttpServer](https://github.com/VladManolache/jHttpServer)

UI:
* [Angular6](https://angular.io)

## Features

API
* GET: Returns the list of available devices
* POST: Updates the list of available devices.

UI
* Retrieve and update the list of devices.

## Compatibility

Tested on Chrome and Safari on both mobile and desktop.

## Deploy

mvn clean install

Docker
* docker build -t [tag]:[version] .
* docker push [tag]:[version]

Google cloud
* gcloud app deploy app.yml

## Future Work

* Add authentication 
* Add role-based access 

## License

This project is licensed under the MIT License

