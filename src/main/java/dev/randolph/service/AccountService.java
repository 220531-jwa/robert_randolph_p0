package dev.randolph.service;

import java.util.List;
import java.util.TreeSet;

import dev.randolph.model.Account;
import dev.randolph.repo.AccountDAO;

public class AccountService {
    
    private AccountDAO accountDao = new AccountDAO();
    
    /*
     * === POST / CREATE ===
     */
    
    public Account createNewAccount() {
        return null;
    }
    
    /*
     * === GET / READ ===
     */
    
    public List<Account> getAllClientAccounts(int cid) {
        return null;
    }
    
    public Account getClientAccountById(int cid, int aid) {
        return null;
    }
    
    /*
     * === PUT / PATCH / UPDATE ===
     */
    
    public Account setAccountAmount() {
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
