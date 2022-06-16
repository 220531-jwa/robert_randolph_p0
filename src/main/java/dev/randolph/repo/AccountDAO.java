package dev.randolph.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.randolph.model.Account;
import dev.randolph.model.AccountType;
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
    
    /**
     * Gets an account associated with a given client.
     * @param cid The specific client the account is associated with.
     * @param aid The specific account
     * @return The account if found, and null otherwise.
     */
    public Account getClientAccountById(int cid, int aid) {
        // Init
        String sql = "SELECT * FROM accounts"
                + " WHERE client_id = ? AND id = ?";
        Account account = null;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, aid);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Account found
                account = new Account(rs.getInt("id"),
                        rs.getInt("client_id"),
                        AccountType.valueOf(rs.getString("type")),
                        rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return account;
    }
    
    /*
     * === UPDATE ===
     */
    
    /**
     * Updates the account with the given client and account id with updated information.
     * @param account The account to update
     * @return True if the account was updated successfully, and false otherwise.
     */
    public boolean updateClientAccountById(Account account) {
        // Init
        String sql = "UPDATE accounts"
                + " SET (type, balance) = (?, ?)"
                + " WHERE client_id = ? AND id = ?";
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getAccountType().name());
            ps.setDouble(2, account.getBalance());
            ps.setInt(3, account.getClientId());
            ps.setInt(4, account.getId());
            int changes = ps.executeUpdate();
            
            // going through results
            if (changes != 0) {
                // Successfully updated account
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Updates the account balance in the database.
     * @param account The account information to update
     * @return True of successful and false otherwise.
     */
    public boolean updateClientAccountBalance(Account account) {
        // Init
        String sql = "UPDATE accounts"
                + " SET balance = ?"
                + " WHERE client_id = ? AND id = ?";
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getClientId());
            ps.setInt(3, account.getId());
            int changes = ps.executeUpdate();
            
            // Going through results
            if (changes != 0) {
                // Successfully updated account
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Transfers funds from srcAccount to targetAcc through a transaction.
     * @param srcAcc The source account to change
     * @param targetAcc The target account to change
     * @return True if the transaction was successful, and false otherwise.
     */
    public boolean transferAccountAmount(Account srcAcc, Account targetAcc) {
        // Init
        String sql = "UPDATE accounts"
                + " SET balance = ?"
                + " WHERE client_id = ? AND id = ?";
        Connection conn = cu.getConnection();
        
        // Executing query
        try {
            // Preparing transaction
            conn.setAutoCommit(false);
            
            // Updating src account
            System.out.println("update src");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, srcAcc.getBalance());
            ps.setInt(2, srcAcc.getClientId());
            ps.setInt(3, srcAcc.getId());
            int changes = ps.executeUpdate();
            System.out.println("updated");
            
            // Checking if src was updated
            if (changes != 0) {
                System.out.println("update success");
                // Successfully updated src
                // Updating target account
                System.out.println("updating target");
                ps = conn.prepareStatement(sql);
                ps.setDouble(1, targetAcc.getBalance());
                ps.setInt(2, targetAcc.getClientId());
                ps.setInt(3, targetAcc.getId());
                changes = ps.executeUpdate();
                System.out.println("updated");
                
                // Checking if target was updated
                if (changes != 0) {
                    System.out.println("upaate success");
                    // Successfully updated target => commit
                    conn.commit();
                    return true;
                }
                else {
                    System.out.println("target update failed");
                    // Failed to update target => rollback
                    conn.rollback();
                }
            }
            else {
                System.out.println("src upate failed");
                // Failed to update src => rollback
                conn.rollback();
            }
        } catch (SQLException e) {
            // Attempting to rollback
            System.out.println("mega fail");
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            // Closing connection
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return false;
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Deletes the client account from the database.
     * @param cid The client associated with the account.
     * @param aid The account to delete
     * @return True if the account was successfully deleted, and false otherwise.
     */
    public boolean deleteClientAccountById(int cid, int aid) {
        // Init
        String sql = "DELETE FROM accounts"
                + " WHERE client_id = ? and id = ?";
        boolean success = false;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, aid);
            int changes = ps.executeUpdate();
            
            // Checking if account was deleted
            if (changes != 0) {
                // Deletion successful
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }
}
