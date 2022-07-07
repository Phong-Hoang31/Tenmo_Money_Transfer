package com.techelevator.tenmo.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String createTransfer(String sendingUser, String recipientUser, BigDecimal transferAmount) {

        // create transfer
        String sql = "INSERT INTO tran (username, password_hash) VALUES (?, ?) RETURNING user_id";

        return "";
    }

    @Override
    public String getTransfer(Long transferId) {

        return "";
    }
}
