package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
public class UserController {
    private UserDao userDao;
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public UserController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
//        this.transactionDao = transactionDao; TODO: uncomment this when TransactionDao is created
    }

    @RequestMapping(path = "{userId}/accounts/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable Long userId, Principal principal) {
        if (principal.getName().equals(accountDao.getUsername(userId))) {
            return accountDao.getBalance(userId);
        } else {
            return null;
        }
    }
}
