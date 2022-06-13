package dev.randolph.controller;

import java.util.List;
import java.util.Map;

import dev.randolph.model.Account;
import dev.randolph.service.AccountService;
import io.javalin.core.validation.Validator;
import io.javalin.http.Context;

public class AccountController {
    
    private AccountService as = new AccountService();
    
    /*
     * === POST ===
     */
    
    /**
     * Creates a new account to add to the database.
     * Account must be associated with a client.
     * Takes the clientId from path, accountType, and amount from request body.
     * @param ctx The http request/response
     * @return a 201 response with the account in the body if successful, and 404 otherwise.
     */
    public void createNewAccount(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Account account = ctx.bodyAsClass(Account.class);
        account.setClientId(cid.get());
        account = as.createNewAccount(account);
        
        // Checking if new account was created successfully
        if (account == null) {
            // Failed to create account
            ctx.status(404);
        }
        else {
            // Successfully created new account
            ctx.status(201);
            ctx.json(account);
        }
    }
    
    /*
     * === GET ===
     */
    
    /**
     * Retrieves all the accounts associated with a specific client.
     * Takes the client id from the path.
     * @param ctx The http request/response.
     * @return A 200 response with the accounts in the body.
     */
    public void getAllClientAccounts(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Map<String, List<String>> queryParams = ctx.queryParamMap();
        
        List<Account> accounts;
        Integer upperBound, lowerBound;
        
        // Attempting to get query params
        try {
            upperBound = Integer.parseInt(ctx.queryParam("amountLessThan"));
            lowerBound = Integer.parseInt(ctx.queryParam("amountGreaterThan"));
        } catch (Exception e) {
            // Didn't get valid params
            upperBound = lowerBound = null;
        }
        
        // Getting accounts
        accounts = as.getAllClientAccounts(cid.get(), lowerBound, upperBound);
        
        // Checking if any accounts where found
        if (accounts == null) {
            // No accounts found
            ctx.status(404);
        }
        else {
            // Accounts found
            ctx.status(200);
            ctx.json(accounts);
        }
    }
    
    public void getClientAccountById(Context ctx) {
    }
    
    /*
     * === PUT / PATCH ===
     */
    
    public void updateClientAccountById(Context ctx) {
    }
    
    public void updateClientAccountBalance(Context ctx) {
        
    }
    
    public void transferClientAccountFunds(Context ctx) {
        
    }
    
    /*
     * === DELETE ===
     */
    
    public void deleteClientAccountById(Context ctx) {
        
    }

}
