# Payable APIs with Raiden Network Payment Channels [external service]

Implementation of the `Payable Camera API` component in the overview. This component delivers the service that should be payable with Raiden.  
For demonstration purposes, the `text` service is used, which just returns a String. An `image` component is included as well, simulating the camera and just returning a static image ([the Bauhaus logo](https://en.wikipedia.org/wiki/Bauhaus)) on each call.

![Integration overview](docs/img/paidAPI.png)

# Prerequisites

* Java 8+
* optional: Git

# Complete Demo Setup

For the complete demo setup, you have to have all the components up and running.

This external service has no other external dependencies and runs standalone.  To use it with the other components, it should be served on port 9100.

| Service | Port |
| --- | --- |
| **Camera API** | **9100** |

## For native image creation

* GraalVM (see https://guides.micronaut.io/micronaut-creating-first-graal-app/guide/index.html)

# Setup

* Clone project with `git clone` or download the project as a compressed file

# Run

* Import as Gradle project in IDE and launch Main class   
*or*   
* Build project with `./gradlew run`  
*or*
* Run `./payable-service.sh` after building native-image (macos and linux only only, or WSL/Windows 10+)

# Quick Start (macOS only)
                                                                              
  * Copy macOS-precompiled binary `dist/payable-service` to root directory and run `./payable-service.sh`
# Use

Open link `http://localhost:9100/external/service/text` (or `.../image`) in browser or HTTP clients like [Restlet Client](https://restlet.com/modules/client/), [SoapUI](https://www.soapui.org/) and choose GET HTTP Method.