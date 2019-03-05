package raiden.payment.proxy.service.dto;

import java.math.BigInteger;
import java.util.Date;

public class Payment {

    private BigInteger amount;
    private String sender;
    private String receiver;
    private BigInteger identifier;
    private Date modified;
    private String tokenAddress;

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public BigInteger getIdentifier() {
        return identifier;
    }

    public void setIdentifier(BigInteger identifier) {
        this.identifier = identifier;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public void setTokenAddress(String tokenAddress) {
        this.tokenAddress = tokenAddress;
    }

    public String getTokenAddress() {
        return tokenAddress;
    }
}