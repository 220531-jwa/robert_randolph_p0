package dev.randolph.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.randolph.model.Account;
import dev.randolph.util.ConnectionUtil;

public class AccountDAO {
    
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    
    /*
     * === CREATE ===
     */
    
    public Account createNewAccount(Account account) {
        // Init
        String sql = "INSERT INTO accounts VALUES"
                + " (default, ?, ?, ?)"
                + " RETURNING *";
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, account.getClientId());
            ps.setString(2, account.getAccountType().name());
            ps.setDouble(3, account.getBalance());
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Successfully created account
                account.setId(rs.getInt("id"));
            }
            else {
                // Failed to create account
                account = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return account;
    }
    
    /*
     * === READ ===
     */
    
    /**
     * Gets all the accounts associated with the given client id.
     * @param cid The client to find the accounts of.
     * @return A list of accounts, or null if non were retrieved.
     */
    public List<Account> getAllClientAccounts(int cid) {
        // Init
        String sql = "SELECT * FROM accounts"
                + " WHERE client_id = ? ORDER BY id";
        ArrayList<Account> accounts = null;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Found accounts
                accounts = new ArrayList<Account>();
                do {
                    accounts.add(new Account(rs.getInt("id"),
                            rs.getInt("client_id"),
                            AccountType.valueOf(rs.getString("type")),
                            rs.getDouble("balance")));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accounts;
    }
    
    /**
     * Gets all the accounts associated with the given client id within a given balance range.
     * @param cid The client to find the accounts of.
     * @param lowerBound The lower bound of the range.
     * @param upperBound The upper bound of the range.
     * @return A list of accounts, or null if non were retrieved.
     */
    public List<Account> getAllClientAccountsInBalanceRange(int cid, int lowerBound, int upperBound) {
        // Init
        String sql = "SELECT * FROM accounts"
                + " WHERE client_id = ? AND balance > ? AND balance < ?"
                + " ORDER BY id";
        ArrayList<Account> accounts = null;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, lowerBound);
            ps.setInt(3, upperBound);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Found accounts
                accounts = new ArrayList<Account>();
                do {
                    accounts.add(new Account(rs.getInt("id"),
                            rs.getInt("client_id"),
                            AccountType.valueOf(rs.getString("type")),
                            rs.getDouble("balance")));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return accounts;
    }
    
    public Account getClientAccountById(int cid, int aid) {
        return null;
    }
    
    /*
     * === UPDATE ===
     */
    
    public Account updateClientAccountById(Account account) {
        return null;
    }
    
    public Account updateAccountAmount() {
        return null;
    }
    
    public Account transferAccountAmount() {
        return null;
    }
    
    /*
     * === DELETE ===
     */
    
    public boolean deleteClientAccountById(int cid, int aid) {
        return false;
    }
}
