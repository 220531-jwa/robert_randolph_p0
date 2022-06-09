package dev.randolph.controller;

import dev.randolph.service.AccountService;
import io.javalin.http.Context;

public class AccountController {
    
    private AccountService accountserivce = new AccountService();
    
    /*
     * === POST ===
     */
    
    public void createNewAccount(Context ctx) {
        
    }
    
    /*
     * === GET ===
     */
    
    public void getAccountsOfClient(Context ctx) {
        
    }
    
    public void getAllAccountsOfClientInAmountRange(Context ctx) {
        
    }
    
    public void getAccountOfClientById(Context ctx) {
        
    }
    
    /*
     * === PUT / PATCH ===
     */
    
    public void setAccountAmount(Context ctx) {
        
    }
    
    public void updateAccountAmount(Context ctx) {
        
    }
    
    public void transferAccountAmount(Context ctx) {
        
    }
    
    /*
     * === DELETE ===
     */
    
    public void deleteAccountOfClientById(Context ctx) {
        
    }

}
