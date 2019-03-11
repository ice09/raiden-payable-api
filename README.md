# Payable APIs with Raiden Network Payment Channels [overview]

Umbrella project for all components necessary for the Payable APIs demo setup.  
Read more about the objective of this demo and instructions for running it here: http://blockchainers.org/index.php/2019/02/26/payable-apis-with-raiden-network-payment-channels  

![Integration overview](docs/img/paidAPI.png)

# Prerequisites

* Java 8+
* optional: Git

# Complete Demo Setup

For the complete demo setup, you have to have all the components up and running.

| Service | Port |
| --- | --- |
| [Sender Payment Proxy](raiden-payment-sender-proxy) | 9000 |
| [Raiden Node](raiden-mock) | 9200 | 
| [Ethereum Transaction Signer](ethereum-transaction-signer) | 9300 | 
| [Receiver Payment Proxy](raiden-payment-proxy) | 9500 |
| [Camera API](payable-service) | 9100 |

| Environment Variable | Default Value | Component |
| --- | --- | --- |
| RECEIVER_ADDRESS | 0x627306090abaB3A6e1400e9345bC60c78a8BEf57 | Sender Payment Proxy |
| SENDER_ADDRESS | 0x2284737b7c15c6119589854631c31A7E599A3dB3 | Sender Payment Proxy |
| TOKEN_ADDRESS | 0x0000000000000000000000000000000000000001 | Sender Payment Proxy |
| SIGNER_PRIVATE_KEY | a27fe45bfd9e40ff8ce043f4e8fe6f5dd05c8c6a5ff8494fbe334349500830b5 (see SENDER_ADDRESS) | Ethereum Transaction Signer |

# Use

Call `http://localhost:9000/api/v1/service/delegate` in browser or HTTP clients like [Restlet Client](https://restlet.com/modules/client/), [SoapUI](https://www.soapui.org/) and choose GET HTTP Method.

# Check

Call `http://localhost:9200/api/v1/payments/0x0000000000000000000000000000000000000001/0x627306090abaB3A6e1400e9345bC60c78a8BEf57` in browser or HTTP clients like [Restlet Client](https://restlet.com/modules/client/), [SoapUI](https://www.soapui.org/) and choose GET HTTP Method.  
For each request, the Payment and it's attributes like `identifier`, `sender`, etc. should be listed.
