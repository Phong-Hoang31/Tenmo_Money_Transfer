package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface AccountDao {

    BigDecimal getBalance(Long userId);

    String getUsername(Long userId);
}
