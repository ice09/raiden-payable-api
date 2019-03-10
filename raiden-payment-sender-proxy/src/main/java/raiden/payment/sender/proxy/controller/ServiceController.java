package raiden.payment.sender.proxy.controller;

import io.micronaut.context.annotation.Value;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import raiden.payment.sender.proxy.controller.dto.PaymentDto;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Map;

@Controller("/api/v1")
public class ServiceController {

    @Value("${receiver.address}")
    String receiverAddress; // "0x627306090abaB3A6e1400e9345bC60c78a8BEf57";

    /* This sender address must be the Raiden address. For the showcase, it MUST be the address which belongs to the private key in PrivateClientController */
    @Value("${sender.address}")
    String senderAddress;// "0x2284737b7c15c6119589854631c31A7E599A3dB3";

    @Value("${token.address}")
    String tokenAddress;

    @Client("${raiden.node}/api/v1/payments")
    @Inject
    HttpClient httpRaidenClient;

    @Client("${raiden.receiver}/api/v1")
    @Inject
    HttpClient httpPaymentProxy;

    @Client("${sender.signer}/private")
    @Inject
    HttpClient httpPrivateClient;

    @Get("/service/delegate/image")
    @Produces(MediaType.ALL)
    public byte[] serviceCallImage() {
        CheckPayment checkPayment = new CheckPayment().invoke();
        PaymentDto in = checkPayment.getIn();
        PaymentDto res = checkPayment.getRes();
        Map<String, String> signature = checkPayment.getSignature();

        // Call Service proxy with signed identifier to correlate payment and service request
        // If signed identifier and payment identifier match, the service is executed
        return httpPaymentProxy.toBlocking().retrieve(HttpRequest.POST("/service/image?identifier=" + res.getIdentifier() + "&signature=" + signature.get("signature"), in), byte[].class);
    }

    @Get("/service/delegate/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String serviceCallText() {
        CheckPayment checkPayment = new CheckPayment().invoke();
        PaymentDto in = checkPayment.getIn();
        PaymentDto res = checkPayment.getRes();
        Map<String, String> signature = checkPayment.getSignature();

        // Call Service proxy with signed identifier to correlate payment and service request
        // If signed identifier and payment identifier match, the service is executed
        return httpPaymentProxy.toBlocking().retrieve(HttpRequest.POST("/service/text?identifier=" + res.getIdentifier() + "&signature=" + signature.get("signature"), in), String.class);
    }

    private void checkConfiguration() {
        if ((receiverAddress == null) || (senderAddress == null) || (tokenAddress == null)) {
            throw new IllegalStateException("Required environment variables are not set.");
        }
    }

    private class CheckPayment {
        private PaymentDto in;
        private PaymentDto res;
        private Map<String, String> signature;

        public PaymentDto getIn() {
            return in;
        }

        public PaymentDto getRes() {
            return res;
        }

        public Map<String, String> getSignature() {
            return signature;
        }

        public CheckPayment invoke() {
            checkConfiguration();

            // Create data transfer object for payment facade
            in = new PaymentDto();
            in.setAmount(BigInteger.valueOf(100));
            in.setInitiator(senderAddress);

            // Create PaymentProposal and receive identifier to use in payment to correlate payment and service request
            res = httpPaymentProxy.toBlocking().retrieve(HttpRequest.POST("/paymentProposal/" + tokenAddress + "/" + receiverAddress, in), PaymentDto.class);

            // Send Payment with identifier
            Map<String, BigInteger> result = httpRaidenClient.toBlocking().retrieve(HttpRequest.POST("/" + tokenAddress + "/" + receiverAddress, res), Argument.of(Map.class, String.class, BigInteger.class));

            // Sign identifier with private key
            signature = httpPrivateClient.toBlocking().retrieve(HttpRequest.GET("/sign?identifier=" + result.get("identifier").toString()), Argument.of(Map.class, String.class, String.class));
            return this;
        }
    }
}
