package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.UserIdNotFoundException;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping
@PreAuthorize("isAuthenticated()")
public class UserController {
    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDao transferDao;

    public UserController(UserDao userDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
//        this.transferDao = transferDao; TODO: uncomment this when TransactionDao is created
    }

    @RequestMapping(path = "{userId}/accounts/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(@PathVariable int userId, Principal principal) throws UserIdNotFoundException {
        if (principal.getName().equals(accountDao.getUsername(userId))) {
            return accountDao.getBalance(userId);
        } else {
            return null;
            //TODO: make this return forbidden access error instead of null??????????
        }
    }


}
