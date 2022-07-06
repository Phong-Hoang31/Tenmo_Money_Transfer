package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    BigDecimal getBalance(Long userId);

    String getUsername(Long userId);


}
