package dev.randolph.repo;

import java.util.List;

import dev.randolph.model.Account;

public class AccountDAO {
    
    /*
     * === CREATE ===
     */
    
    public Account createNewAccount() {
        return null;
    }
    
    /*
     * === READ ===
     */
    
    public List<Account> getAllAccountsOfClient(int cid) {
        return null;
    }
    
    public List<Account> getAllAccountsOfClientRange(int cid) {
        return null;
    }
    
    public Account getAccountOfClientById(int cid, int aid) {
        return null;
    }
    
    /*
     * === UPDATE ===
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
