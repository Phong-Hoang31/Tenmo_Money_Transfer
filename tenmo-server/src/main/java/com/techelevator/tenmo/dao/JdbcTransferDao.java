package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String createTransfer(String sendingUser, String recipientUser, BigDecimal transferAmount) {

        User user1 = new User();
        User user2 = new User();

        Account account1 = new Account();
        Account account2 = new Account();

        user1.setUsername(sendingUser);
        user2.setUsername(recipientUser);

        account1.setUserId(user1.getUserId());
        account2.setUserId(user2.getUserId());

        // create transfer
        String sql = "INSERT INTO transfer (sender_account_id, recipient_account_id, transfer_amount, date, time, status) " +
                "VALUES ((SELECT account_id as ais FROM tenmo_user as tu " +
                "JOIN account as a ON tu.user_id = a.user_id " +
                "WHERE username = ?), " +
                "(SELECT account_id as air FROM tenmo_user as tu " +
                "JOIN account as a ON tu.user_id = a.user_id " +
                "WHERE username = ?), ?, CURRENT_DATE, CURRENT_TIME, 'Approved') RETURNING status;";

        System.out.println("account balance = " + account1.getBalance());
        if(account1.getBalance().compareTo(transferAmount) == -1) {
            return "Not enough funds";
        }
        else {
            String status = jdbcTemplate.queryForObject(sql, String.class, sendingUser, recipientUser, transferAmount);
            return status;
        }
    }

    @Override
    public String getTransfer(Long transferId) {

        return "";
    }
}
