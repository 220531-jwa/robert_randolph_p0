package dev.randolph.repo;

import java.util.List;

import dev.randolph.model.Account;

public class AccountDAO {
    
    /*
     * === CREATE ===
     */
    
    public Account createNewAccount(Account account) {
        return null;
    }
    
    /*
     * === READ ===
     */
    
    public List<Account> getAllAccountsOfClient(int cid) {
        return null;
    }
    
    public Account getClientAccountById(int cid, int aid) {
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
    
    public boolean deleteClientAccountById(int cid, int aid) {
        return false;
    }

}
