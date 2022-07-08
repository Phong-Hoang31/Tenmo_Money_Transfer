package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.exception.TransactionIdNotFoundException;
import com.techelevator.tenmo.exception.UserIdNotFoundException;
import com.techelevator.tenmo.model.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
    @RequestMapping(path = "/transfer", method = RequestMethod.GET)
    public List<String> listOfUsers() throws UserIdNotFoundException {
        return userDao.getAllUsernames();

    }

    @RequestMapping(path = "/transfer", method = RequestMethod.POST)
    public String createTransfer(@RequestBody TransferDTO transferDTO, Principal principal) throws UserIdNotFoundException {
            String senderUsername = principal.getName();
            String recipientUsername = transferDTO.getRecipientUsername();
            BigDecimal transferAmount = transferDTO.getTransferAmount();
            return transferDao.createTransfer(senderUsername, recipientUsername, transferAmount);

            //TODO: make this return forbidden access error instead of null??????????
    }

    @GetMapping(path = "transfer/history")
    public List<Transfer> getAllTransfers (Principal principal) throws RestClientException {
        Integer userId = userDao.findIdByUsername(principal.getName());
        return transferDao.getAllTransfers(userId);
    }

    @GetMapping(path = "transfer/{transferId}")
    public Transfer getTransfer (@PathVariable Integer transferId, Principal principal ) throws TransactionIdNotFoundException {
        Integer userId = userDao.findIdByUsername(principal.getName());
        return transferDao.getTransfer(userId, transferId);
    }

    @PostMapping(path = "transfer/request")
    public String requestTransfer (@RequestBody RequestDTO requestDTO, Principal principal) throws UserIdNotFoundException {
        String requestingUsername = principal.getName();
        String senderUsername = requestDTO.getSendingUsername();
        BigDecimal transferAmount = requestDTO.getTransferAmount();
        return transferDao.requestMoney(requestingUsername, senderUsername, transferAmount);
    }
    @GetMapping(path = "transfer/pending")
    public List<Transfer> getAllPendingTransfers (Principal principal) throws RestClientException {
        Integer userId = userDao.findIdByUsername(principal.getName());
        return transferDao.getPendingTransfers(userId);
    }

    @RequestMapping(path = "/transfer/{transferId}", method = RequestMethod.PUT)
    public String approveTransfer(@PathVariable Integer transferId, @RequestBody ApproveDTO approveDTO, Principal principal) throws UserIdNotFoundException, TransactionIdNotFoundException {
        String status = approveDTO.getStatus();

        return transferDao.approveRequest(transferId, status);

        //TODO: make this return forbidden access error instead of null??????????
    }
}
