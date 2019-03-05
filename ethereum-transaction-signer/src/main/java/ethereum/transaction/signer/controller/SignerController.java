package ethereum.transaction.signer.controller;

import io.micronaut.context.annotation.Value;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Controller("/private")
public class SignerController {

    private Credentials credentials;

    @Value("${SIGNER_PRIVATE_KEY}")
    String signerPrivateKey;

    @Get("/sign")
    public Map<String, String> signIdentifier(@QueryValue String identifier) {
        if (signerPrivateKey == null) {
            throw new IllegalStateException("Environment variable SIGNER_PRIVATE_KEY must be set.");
        } else {
            credentials = Credentials.create(signerPrivateKey);
        }
        // sign
        Sign.SignatureData signature = Sign.signMessage(Numeric.toBytesPadded(new BigInteger(identifier), 32), credentials.getEcKeyPair());
        ByteBuffer sigBuffer = ByteBuffer.allocate(signature.getR().length + signature.getS().length + 1);
        sigBuffer.put(signature.getR());
        sigBuffer.put(signature.getS());
        sigBuffer.put(signature.getV());

        Map<String, String> result = new HashMap<>();
        result.put("signature", Numeric.toHexStringNoPrefix(sigBuffer.array()));
        return result;
    }
}
