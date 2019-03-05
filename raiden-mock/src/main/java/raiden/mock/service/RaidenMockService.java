package raiden.mock.service;

import raiden.mock.service.dto.Payment;
import raiden.mock.service.dto.PaymentDto;

import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class RaidenMockService {

    private Map<BigInteger, Payment> payments = new HashMap<>();

    public BigInteger storePayment(String token_address, String target_address, PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setSender(paymentDto.getInitiator());
        payment.setReceiver(target_address);
        payment.setAmount(paymentDto.getAmount());
        payment.setIdentifier(paymentDto.getIdentifier());
        payment.setTokenAddress(token_address);
        payment.setModified(paymentDto.getLog_time());
        payments.put(payment.getIdentifier(), payment);
        return payment.getIdentifier();
    }

    public List<Payment> findPayments(String token_address, String target_address) {
        return payments.values().stream().filter(pc -> isPaymentMatch(token_address, target_address, pc)).collect(Collectors.toList());
    }

    private boolean isPaymentMatch(String token_address, String target_address, Payment value) {
        return token_address.equals(value.getTokenAddress()) && target_address.equals(value.getReceiver());
    }

}
