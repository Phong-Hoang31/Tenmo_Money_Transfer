package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.UserIdNotFoundException;
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
public class TransferController {
    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDao transferDao;

    public TransferController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @RequestMapping(path = "{userId}/accounts/transfer", method = RequestMethod.POST)
    public String createTransfer(@PathVariable Long userId, Principal principal) throws UserIdNotFoundException {
        if (principal.getName().equals(accountDao.getUsername(userId))) {
            Long testId = Long.valueOf(1002);
            return transferDao.createTransfer(accountDao.getUsername(userId),  accountDao.getUsername(testId), new BigDecimal(300.0));
        } else {
            return null;
            //TODO: make this return forbidden access error instead of null??????????
        }
    }
}
