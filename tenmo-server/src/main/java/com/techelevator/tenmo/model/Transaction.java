package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private Long transactionId;
    private Long senderAccountId;
    private Long recipientAccountId;
    private BigDecimal amount;
    private LocalDate date;
    private LocalTime time;

    public Transaction() {
    }

    public Transaction(Long transactionId, Long senderAccountId, Long recipientAccountId, BigDecimal amount, LocalDate date, LocalTime time) {
        this.transactionId = transactionId;
        this.senderAccountId = senderAccountId;
        this.recipientAccountId = recipientAccountId;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public Long getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(Long senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public Long getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(Long recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", senderAccountId=" + senderAccountId +
                ", recipientAccountId=" + recipientAccountId +
                ", amount=" + amount +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
