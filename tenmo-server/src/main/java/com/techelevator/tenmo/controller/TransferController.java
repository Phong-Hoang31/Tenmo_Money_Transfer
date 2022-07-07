package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.UserIdNotFoundException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public String createTransfer(@PathVariable int userId, @RequestBody TransferDTO transferDTO, Principal principal) throws UserIdNotFoundException {

        if (principal.getName().equals(accountDao.getUsername(userId))) {

            return transferDao.createTransfer(principal.getName(), transferDTO.getRecipientUsername(), transferDTO.getTransferAmount());
        } else {
            return null;
            //TODO: make this return forbidden access error instead of null??????????
        }
    }
}
