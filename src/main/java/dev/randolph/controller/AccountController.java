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
    
    public void getAllClientAccounts(Context ctx) {
        // TODO: Also handles the amount ranges below
    }
    
    public void getClientAccountById(Context ctx) {
        
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
    
    public void deleteClientAccountById(Context ctx) {
        
    }

}
