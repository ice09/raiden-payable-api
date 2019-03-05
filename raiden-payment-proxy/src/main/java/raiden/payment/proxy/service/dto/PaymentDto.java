package raiden.payment.proxy.service.dto;

import java.math.BigInteger;
import java.util.Date;

public class PaymentDto {

    public static String eventDescription = "EventPaymentSentSuccess";

    private String initiator;
    private BigInteger identifier;
    private Date log_time;
    private String event;
    private BigInteger amount;

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public BigInteger getIdentifier() {
        return identifier;
    }

    public void setIdentifier(BigInteger identifier) {
        this.identifier = identifier;
    }

    public Date getLog_time() {
        return log_time;
    }

    public void setLog_time(Date log_time) {
        this.log_time = log_time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

}
