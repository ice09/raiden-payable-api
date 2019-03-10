package raiden.payment.proxy.service;

import org.web3j.crypto.ECDSASignature;
import org.web3j.crypto.Hash;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;
import raiden.payment.proxy.service.dto.Payment;
import raiden.payment.proxy.service.dto.PaymentDto;

import javax.inject.Singleton;
import java.math.BigInteger;
import java.util.*;

@Singleton
public class PaymentService {

    Map<BigInteger, Payment> paymentCorrelations = new HashMap<>();

    BigInteger createIdentifier() {
        return new BigInteger(31, new Random());
    }

    public Payment retrievePaymentProposal(String identifier, String signature) {
        // Ecrecover address from sigature and identifier
        String signer = checkSignature(new BigInteger(identifier), signature);

        // Match and retrieve PaymentProposal
        return matchPaymentProposal(new BigInteger(identifier), signer);
    }

    public BigInteger storePaymentProposal(String token_address, String target_address, PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setSender(paymentDto.getInitiator());
        payment.setReceiver(target_address);
        payment.setAmount(paymentDto.getAmount());
        payment.setIdentifier(createIdentifier());
        payment.setTokenAddress(token_address);
        payment.setModified(paymentDto.getLog_time());
        paymentCorrelations.put(payment.getIdentifier(), payment);
        return payment.getIdentifier();
    }

    public boolean removePaymentProposal(BigInteger identifier) {
        if (paymentCorrelations.remove(identifier) != null) {
            return true;
        }
        return false;
    }

    private Payment matchPaymentProposal(BigInteger identifier, String signer) {
        if (paymentCorrelations.containsKey(identifier)) {
            Payment foundPayment = paymentCorrelations.get(identifier);
            return foundPayment;
        }
        throw new IllegalStateException("Identifier does not exist or Payment is already settled.");
    }

    private String checkSignature(BigInteger identifier, String signature) {
        String expectedAddress = paymentCorrelations.get(identifier).getSender();
        byte[] signatureAsBytes = Numeric.hexStringToByteArray(signature);
        byte[] hashedIdentifier = Hash.sha3(Numeric.toBytesPadded(identifier, 32));
        ECDSASignature esig = new ECDSASignature(Numeric.toBigInt(Arrays.copyOfRange(signatureAsBytes, 0, 32)), Numeric.toBigInt(Arrays.copyOfRange(signatureAsBytes, 32, 64)));
        BigInteger res;
        for (int i=0; i<4; i++) {
            res = Sign.recoverFromSignature(i, esig, hashedIdentifier);
            if (res != null) {
                String addr = Keys.getAddress(res).toLowerCase();
                if (expectedAddress.substring(2).toLowerCase().equals(addr)) {
                    return addr;
                }
            }
        }
        throw new IllegalStateException("Signature and Sender of Payment do not match.");
    }

}
