package raiden.mock.controller;

import io.micronaut.http.annotation.*;
import raiden.mock.service.RaidenMockService;
import raiden.mock.service.dto.Payment;
import raiden.mock.service.dto.PaymentDto;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("/api/v1")
public class RaidenMockController {

    @Inject
    RaidenMockService raidenMockService;

    @Post("/payments/{token_address}/{target_address}")
    public Map<String, BigInteger> payments(@QueryValue String token_address, @QueryValue String target_address, @Body PaymentDto paymentDto) {
        raidenMockService.storePayment(token_address, target_address, paymentDto);
        Map<String, BigInteger> result = new HashMap<>();
        result.put("identifier", paymentDto.getIdentifier());
        result.put("amount", paymentDto.getAmount());
        return result;
    }

    @Get("/payments/{token_address}/{target_address}")
    public List<Payment> payments(@QueryValue String token_address, @QueryValue String target_address) {
        return raidenMockService.findPayments(token_address, target_address);
    }

}