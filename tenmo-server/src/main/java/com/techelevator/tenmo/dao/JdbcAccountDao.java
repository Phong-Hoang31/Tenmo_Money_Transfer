package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}

    @Override
    public String getUsername(Long userId){
        String sql = "SELECT username FROM tenmo_user WHERE user_id = ?";
        String username = jdbcTemplate.queryForObject(sql, String.class, userId);
        return username;
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        BigDecimal balance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userId);
        return balance;
    }

    private Account mapRowToAccount(SqlRowSet rs){
        Account account = new Account();
        account.setAccountId(rs.getLong("account_id"));
        account.setUserId(rs.getLong("user_id"));
        account.setBalance(rs.getBigDecimal("balance"));
        return account;
    }
}
