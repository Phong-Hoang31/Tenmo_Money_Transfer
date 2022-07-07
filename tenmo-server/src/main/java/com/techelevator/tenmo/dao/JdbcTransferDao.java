package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    private JdbcAccountDao jdbcAccountDao;
    private JdbcUserDao jdbcUserDao;

    public JdbcTransferDao(DataSource dataSource, JdbcAccountDao jdbcAccountDao, JdbcUserDao jdbcUserDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcAccountDao = jdbcAccountDao;
        this.jdbcUserDao = jdbcUserDao;
    }

    @Override
    public String createTransfer(String sendingUser, String recipientUser, BigDecimal transferAmount) {
        boolean senderHasEnoughMoney = false;
        boolean senderIsNotRecipient = false;
        boolean transferAmountIsGreaterThanZero = false;

//        System.out.println("transferAmount = "+ transferAmount);
        BigDecimal senderBalance = jdbcAccountDao.getBalance(jdbcUserDao.findIdByUsername(sendingUser));

        if(senderBalance.compareTo(transferAmount) != -1) {
            senderHasEnoughMoney = true;
            System.out.println("senderHasEnoughMoney");
        }

        if(!sendingUser.equals(recipientUser)) {
            senderIsNotRecipient = true;
            System.out.println("senderIsNotRecipient");
        }

        if(transferAmount.compareTo(BigDecimal.ZERO) == 1) {
            transferAmountIsGreaterThanZero = true;
            System.out.println("transferAmountIsGreaterThanZero");
        }

        // create transfer
        String sql = "INSERT INTO transfer (sender_account_id, recipient_account_id, transfer_amount, date, time, status) " +
                "VALUES ((SELECT account_id as ais FROM tenmo_user as tu " +
                "JOIN account as a ON tu.user_id = a.user_id " +
                "WHERE username = ?), " +
                "(SELECT account_id as air FROM tenmo_user as tu " +
                "JOIN account as a ON tu.user_id = a.user_id " +
                "WHERE username = ?), ?, CURRENT_DATE, CURRENT_TIME, 'Approved') RETURNING status;";

//        System.out.println("account balance = " + account1.getBalance());
        if (!senderHasEnoughMoney && senderIsNotRecipient && transferAmountIsGreaterThanZero) {
            return "Invalid transfer";
        } else {

            adjustSenderBalance(sendingUser, transferAmount);
            adjustRecipientBalance(recipientUser, transferAmount);

            String status = jdbcTemplate.queryForObject(sql, String.class, sendingUser, recipientUser, transferAmount);
            return status;
        }
    }

    private void adjustSenderBalance(String sendingUser, BigDecimal withdrawAmount) {
        String sql = "UPDATE account " +
                "SET " +
                "balance = ? " +
                "WHERE user_id = ?;";

        jdbcTemplate.queryForRowSet(sql, jdbcAccountDao.getBalance(jdbcUserDao.findIdByUsername(sendingUser)).subtract(withdrawAmount),jdbcUserDao.findIdByUsername(sendingUser));
    }

    private void adjustRecipientBalance(String recipientUser, BigDecimal depositAmount) {
        String sql = "UPDATE account " +
                "SET " +
                "balance = ? " +
                "WHERE user_id = ?;";

        jdbcTemplate.queryForRowSet(sql, jdbcAccountDao.getBalance(jdbcUserDao.findIdByUsername(recipientUser)).subtract(depositAmount), jdbcUserDao.findIdByUsername(recipientUser));
    }


    @Override
    public String getTransfer(int transferId) {

        return "";
    }
}
