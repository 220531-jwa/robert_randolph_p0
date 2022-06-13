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
            accounts = ad.getAllClientAccountsInBalanceRange(cid, lowerBound, upperBound);
        }
        
        return accounts;
    }
    
    public Account getClientAccountById(int cid, int aid) {
        return null;
    }
    
    /*
     * === PUT / PATCH / UPDATE ===
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
