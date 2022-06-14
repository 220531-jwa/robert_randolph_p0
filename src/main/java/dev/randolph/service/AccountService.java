package dev.randolph.service;

import java.util.List;

import dev.randolph.model.Account;
import dev.randolph.repo.AccountDAO;
import dev.randolph.repo.ClientDAO;

public class AccountService {
    
    private AccountDAO ad = new AccountDAO();
    private ClientDAO cd = new ClientDAO();
    
    public AccountService() {
        
    }
    
    /*
     * === POST / CREATE ===
     */
    
    /**
     * Creates a new account that will be inserted into the database.
     * Each account will have a unique id.
     * If the client that the account will be associated with doesn't exist returns null.
     * @param account The account to create.
     * @return An account if created successfully, and null otherwise.
     */
    public Account createNewAccount(Account account) {
        // Validating input
        if (account == null) {
            return null;
        }
        
        // Checking if client exists
        if (cd.getClientById(account.getClientId()) == null) {
            // Client not found
            return null;
        }
        
        // Creating account for client
        return ad.createNewAccount(account);
    }
    
    /*
     * === GET / READ ===
     */
    
    /**
     * Gets all the accounts associated with the given client id.
     * If a balance range is given then will find accounts of the client within the given balance range.
     * If the client doesn't exist returns null.
     * @param cid The client to get the accounts from.
     * @param lowerBound The lower bound of the balance range.
     * @param upperBound The upper bound of the balance range.
     * @return The list of accounts associated with the client, and null otherwise.
     */
    public List<Account> getAllClientAccounts(int cid, Integer lowerBound, Integer upperBound) {
        // Validating input
        if (cid < 0 
                || (lowerBound == null && upperBound != null)
                || (lowerBound != null && upperBound == null)) {
            return null;
        }
        
        // Checking if client exists
        if (cd.getClientById(cid) == null) {
            // Client not found
            return null;
        }
        
        // Init
        List<Account> accounts;
        
        // Checking if range was given
        if (lowerBound == null) {
            // Range not given
            accounts = ad.getAllClientAccounts(cid);
        }
        else {
            // Range given
            // Checking if range is valid
            if (lowerBound > upperBound) {
                // Invalid range
                return null;
            }
            
            accounts = ad.getAllClientAccountsInBalanceRange(cid, lowerBound, upperBound);
        }
        
        return accounts;
    }
    
    /**
     * Gets a specific account associated with a client.
     * If the client doesn't exists returns null.
     * @param cid The client to search for.
     * @param aid The account to search for associated with the client.
     * @return The found account, and null otherwise.
     */
    public Account getClientAccountById(int cid, int aid) {
        // Validating input
        if (cid < 0 || aid < 0) {
            return null;
        }
        
        // Checking if client exists
        if (cd.getClientById(cid) == null) {
            // Client not found
            return null;
        }
        
        return ad.getClientAccountById(cid, aid);
    }
    
    /*
     * === PUT / PATCH / UPDATE ===
     */
    
    /**
     * Updates the account in the database associated with a specific client.
     * @param account The account to update along with the updated information.
     * @return True if the account was updated successfully, and false otherwise.
     */
    public boolean updateClientAccountById(Account account) {
        // Validating input
        if (account == null) {
            return false;
        }
        
        return ad.updateClientAccountById(account);
    }
    
    /**
     * Updates the account balance by a given amount.
     * @param account The account to change the balance of.
     * @return -2 if service is unavailable, -1 if client/account doesn't exist, 0 if not enough funds for withdraw, 1 if successful.
     */
    public int updateClientAccountBalance(Account account) {
        // Validating input
        if (account == null) {
            return -1;
        }
        
        // Checking if client and account exist
        Account targetAcc = ad.getClientAccountById(account.getClientId(), account.getId());
        if (targetAcc == null) {
            // Client/Account not found
            return -1;
        }
        
        // Checking if sufficient funds are available
        double updatedBalance = targetAcc.getBalance() + account.getBalance();
        if (updatedBalance < 0) {
            // Not enough funds to withdraw
            return 0;
        }
        
        // Updating balance
        account.setBalance(updatedBalance);
        boolean success = ad.updateClientAccountBalance(account);
        
        // Checking if service was down
        if (!success) {
            // For whatever reason failed to update
            return -2;
        }
        
        // Success
        return 1;
    }
    
    /**
     * Transfers funds between two accounts
     * @param account The source account with the balance to transfer.
     * @param tid The target account to transfer the funds to.
     * @return -2 if service is unavailable, -1 if client/account doesn't exist, 0 if not enough funds for withdraw, 1 if successful.
     */
    public int transferClientAccountFunds(Account account, int tid) {
        // Validating input
        if (account == null || tid < 0) {
            return -1;
        }
        
        // Checking if client and account exist
        Account srcAcc = ad.getClientAccountById(account.getClientId(), account.getId());
        Account targetAcc = ad.getClientAccountById(account.getClientId(), tid);
        if (srcAcc == null || targetAcc == null) {
            // Client/Account not found
            return -1;
        }
        
        // Checking if sufficient funds are available
        double srcUpdatedBalance = srcAcc.getBalance() - account.getBalance();
        double targetUpdatedBalance = targetAcc.getBalance() + account.getBalance();
        if (srcUpdatedBalance < 0) {
            // Not enough funds to transfer
            return 0;
        }
        
        // Updating balance
        srcAcc.setBalance(srcUpdatedBalance);
        targetAcc.setBalance(targetUpdatedBalance);
        boolean success = ad.transferAccountAmount(srcAcc, targetAcc);
        
        // Checking if service was down
        if (!success) {
            // For whatever reason failed to update
            return -2;
        }
        
        // Success
        return 1;
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Deletes the account form the database.
     * @param cid The client the account is associated with
     * @param aid The account id
     * @return True if the account was successfully deleted and false otherwise.
     */
    public boolean deleteClientAccountById(int cid, int aid) {
        // Validating input
        if (cid < 0 || aid < 0) {
            return false;
        }
        
        return ad.deleteClientAccountById(cid, aid);
    }
}
