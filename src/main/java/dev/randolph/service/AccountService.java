package dev.randolph.service;

import java.util.List;

import dev.randolph.model.Account;
import dev.randolph.repo.AccountDAO;

public class AccountService {
    
    AccountDAO accountDao = new AccountDAO();
    
    /*
     * === POST / CREATE ===
     */
    
    public Account createNewAccount() {
        return null;
    }
    
    /*
     * === GET / READ ===
     */
    
    public List<Account> getAccountsOfClient(int cid) {
        return null;
    }
    
    public List<Account> getAllAccountsOfClientInAmountRange(int cid) {
        return null;
    }
    
    public Account getAccountOfClientById(int cid, int aid) {
        return null;
    }
    
    /*
     * === PUT / UPDATE ===
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
    
    public boolean deleteAccountOfClientById(int cid, int aid) {
        return false;
    }
    
}
