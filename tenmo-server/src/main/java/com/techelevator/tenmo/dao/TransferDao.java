package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {

    // Creates new
    String createTransfer(String sendingUser, String recipientUser, BigDecimal transferAmount);

    // Returns the response valid or invalid
    String getTransfer(Long transferId);


}
