package raiden.payment.proxy.controller;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import raiden.payment.proxy.service.dto.Payment;
import raiden.payment.proxy.service.dto.PaymentDto;
import raiden.payment.proxy.service.PaymentService;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Controller("/api/v1")
public class PaidApiController {

    @Inject
    PaymentService paymentService;

    @Client("${raiden.node}/api/v1/payments")
    @Inject
    HttpClient httpRaidenClient;

    @Client("${external.service}")
    @Inject
    HttpClient httpClient;

    @Post("/paymentProposal/{token_address}/{target_address}")
    public PaymentDto paymentProposal(@QueryValue String token_address, @QueryValue String target_address, @Body PaymentDto paymentDto) {
        paymentDto.setLog_time(new Date());
        paymentDto.setEvent(PaymentDto.eventDescription);
        BigInteger createdIdentifier = paymentService.storePaymentProposal(token_address, target_address, paymentDto);
        paymentDto.setIdentifier(createdIdentifier);
        return paymentDto;
    }

    @Post("/service/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String serviceProxyCallText(@QueryValue String identifier, @QueryValue String signature) {
        try {
            // Check that there has been exactly one PaymentProposal (add amount check here)
            if (checkPaidAndRemovePaymentProposal(identifier, signature)) {

                // Call external service
                return httpClient.toBlocking().retrieve(HttpRequest.GET("/external/service/text"), String.class);
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        throw new IllegalStateException("Pay the bill first!");
    }

    @Post("/service/image")
    @Produces(MediaType.ALL)
    public byte[] serviceProxyCallImage(@QueryValue String identifier, @QueryValue String signature) {
        try {
            // Check that there has been exactly one PaymentProposal (add amount check here)
            if (checkPaidAndRemovePaymentProposal(identifier, signature)) {

                // Call external service
                return httpClient.toBlocking().retrieve(HttpRequest.GET("/external/service/image"), byte[].class);
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        throw new IllegalStateException("Pay the bill first!");
    }

    private boolean checkPaidAndRemovePaymentProposal(String identifier, String signature) {
        // Ecrecover address from sigature and identifier
        Payment proposedPayment = paymentService.retrievePaymentProposal(identifier, signature);
        boolean isPaid = isPaymentMatch(proposedPayment);
        if (isPaid) {
            // Remove matched PaymentProposal
            return paymentService.removePaymentProposal(new BigInteger(identifier));
        }
        return false;
    }

    private boolean isPaymentMatch(Payment payment) {
        List<Payment> payments = httpRaidenClient.toBlocking().retrieve(HttpRequest.GET("/" + payment.getTokenAddress() + "/" + payment.getReceiver()), Argument.of(List.class, Payment.class));
        return payments.stream().filter(it -> payment.getTokenAddress().equals(it.getTokenAddress()) && payment.getReceiver().equals(it.getReceiver()) && payment.getIdentifier().equals(it.getIdentifier())).count() == 1;
    }


}