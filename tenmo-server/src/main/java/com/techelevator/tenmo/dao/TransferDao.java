package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface TransferDao {

    // Creates new transfer, returns transfer_id
    String createTransfer(String sendingUser, String recipientUser, BigDecimal transferAmount);

    // Returns the response Pending, Approved or Denied
    String getTransfer(Long transferId);


}
