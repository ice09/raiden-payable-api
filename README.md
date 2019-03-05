# Payable APIs with Raiden Network Payment Channels [ethereum transaction signer]

Implementation of the `Private Wallet` component in the overview. This component signs an identifier, which is needed for correlating payments to service requests, with the sender's private key.  
This component is for testing purposes only and should not be used with Mainnet private key.

![Integration overview](docs/img/paidAPI.png)

# Prerequisites

* Java 8+
* optional: Git

# Complete Demo Setup

For the complete demo setup, you have to have all the components up and running.

This external service has no other external dependencies and runs standalone.  To use it with the other components, it should be served on port 9300.


| Service | Port |
| --- | --- |
| **Sender Transaction Signer** | **9300** | 

| Environment Variable | Default Value |
| --- | --- |
| **SIGNER_PRIVATE_KEY** | **a27fe45bfd9e40ff8ce043f4e8fe6f5dd05c8c6a5ff8494fbe334349500830b5** |

## For native image creation

* GraalVM (see https://guides.micronaut.io/micronaut-creating-first-graal-app/guide/index.html)

# Setup

* Clone project with `git clone` or download the project as a compressed file
* Set environment variables. Presets are in `application.yml` for Micronaut environment and `ethereum-transaction-signer.sh` for native-image usage.

# Run

* Import as Gradle project in IDE and launch Main class   
*or*   
* Build project with `./gradlew run`  
*or*
* Run `./ethereum-transaction-signer.sh` after building native-image (macos and linux only)

# Use

Open link `http://localhost:9300/private/sign?identifier=1` in browser or HTTP clients like [Restlet Client](https://restlet.com/modules/client/), [SoapUI](https://www.soapui.org/) and choose GET HTTP Method.