# Payable APIs with Raiden Network Payment Channels [Raiden Node Mock]

Implementation of the `Receiver and Sender Raiden Node` components in the overview. For simplicity, this component mocks the Sender and Receiver Raiden Nodes to avoid the dependency on running Raiden Nodes for the demo.  
If Raiden is running on Sender and Receiver site, this mock is unnecessary. The Raiden Nodes must then be available to the other components on Sender and Receiver site.

![Integration overview](docs/img/paidAPI.png)

# Prerequisites

* Java 8+
* optional: Git

# Complete Demo Setup

For the complete demo setup, you have to have all the components up and running.

This external service has no other external dependencies and runs standalone. To use it with the other components, it should be served on port 9200.

| Service | Port |
| --- | --- |
| **Raiden Mock** | **9200** |

## For native image creation

* GraalVM (see https://guides.micronaut.io/micronaut-creating-first-graal-app/guide/index.html)

# Setup

* Clone project with `git clone` or download the project as a compressed file

# Run

* Import as Gradle project in IDE and launch Main class   
*or*   
* Build project with `./gradlew run`  
*or*
* Run `./raiden-mock.sh` after building native-image (macos and linux only)

# Use

Open link `http://localhost:9200/api/v1/payments/0x0/0x1` in browser or HTTP clients like [Restlet Client](https://restlet.com/modules/client/), [SoapUI](https://www.soapui.org/) and choose GET HTTP Method.