package dev.randolph.controller;

import java.util.List;

import dev.randolph.model.Account;
import dev.randolph.model.BalanceTransfer;
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
     * Possible to take query parameters to find accounts within a range.
     * @param ctx The http request/response.
     * @return A 200 response with the accounts in the body and a 404 otherwise.
     */
    public void getAllClientAccounts(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
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
    
    /**
     * Retrieves the account with a specific id from a client id.
     * Takes the client id and account id from the path.
     * @param ctx The http request/response
     * @return A 200 response with the found account, and a 404 otherwise.
     */
    public void getClientAccountById(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Validator<Integer> aid = ctx.pathParamAsClass("aid", Integer.class);
        Account account = as.getClientAccountById(cid.get(), aid.get());
        
        // Checking if account was found
        if (account == null) {
            // Account wasn't found
            ctx.status(404);
        }
        else {
            // Account found
            ctx.status(200);
            ctx.json(account);
        }
    }
    
    /*
     * === PUT / PATCH ===
     */
    
    /**
     * Updates a specific client account.
     * Takes the id of the client and account from the path.
     * Takes the accountType and balance from the body.
     * @param ctx The http request/response
     * @return a 204 response if account was update successfully, and 404 otherwise.
     */
    public void updateClientAccountById(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Validator<Integer> aid = ctx.pathParamAsClass("aid", Integer.class);
        Account account = ctx.bodyAsClass(Account.class);
        account.setClientId(cid.get());
        account.setId(aid.get());
        boolean success = as.updateClientAccountById(account);
        
        // Checking if account was updated
        if (!success) {
            // Failed to update account
            ctx.status(404);
        }
        else {
            // Successfully updated account
            ctx.status(204);
        }
        
    }
    
    /**
     * Updates the balance of a specific client account.
     * The balance is updated as a deposit or withdraw.
     * Takes the id of the client and account from the path.
     * Takes the deposit/withdraw balance from the body.
     * @param ctx The http request/response
     * @return 204 response if balance was updated, 404 if client/account wasn't found, and 422 if there were insufficant funds
     */
    public void updateClientAccountBalance(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Validator<Integer> aid = ctx.pathParamAsClass("aid", Integer.class);
        BalanceTransfer bt = ctx.bodyAsClass(BalanceTransfer.class);
        Account account = new Account(aid.get(), cid.get(), null, bt.getDouble());
        int success = as.updateClientAccountBalance(account);
        
        // Checking if account balance was updated
        if (success == -2) {
            // Service unavailable
            ctx.status(503);
        }
        else if (success == -1) {
            // Couldn't find client/account
            ctx.status(404);
        }
        else if (success == 0) {
            // Insufficient funds
            ctx.status(422);
        }
        else {
            // Successfully updated account balance
            ctx.status(204);
        }
    }
    
    /**
     * Transfers funds between two accounts.
     * Takes the id of the client and accounts from path.
     * Takes amount to transfer from body.
     * @param ctx The http request/response
     * @return 204 response if balance was updated, 404 if client/account wasn't found, and 422 if there were insufficant funds
     */
    public void transferClientAccountFunds(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Validator<Integer> aid = ctx.pathParamAsClass("aid", Integer.class);
        Validator<Integer> tid = ctx.pathParamAsClass("tid", Integer.class);
        BalanceTransfer bt = ctx.bodyAsClass(BalanceTransfer.class);
        Account srcAccount = new Account(aid.get(), cid.get(), null, bt.getDouble());
        int success = as.transferClientAccountFunds(srcAccount, tid.get());
        
        // Checking if account balance was updated
        if (success == -2) {
            // Service unavailable
            ctx.status(503);
        }
        else if (success == -1) {
            // Couldn't find client/account
            ctx.status(404);
        }
        else if (success == 0) {
            // Insufficient funds
            ctx.status(422);
        }
        else {
            // Successfully updated account balance
            ctx.status(204);
        }
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Deletes a client account
     * Takes the id of the client and account form path.
     * @param ctx The http request/response
     * @return A 205 response if account was deleted successfully and 404 otherwise.
     */
    public void deleteClientAccountById(Context ctx) {
        // Init
        Validator<Integer> cid = ctx.pathParamAsClass("cid", Integer.class);
        Validator<Integer> aid = ctx.pathParamAsClass("aid", Integer.class);
        boolean success = as.deleteClientAccountById(cid.get(), aid.get());
        
        // Checking if account was deleted
        if (!success) {
            // Failed to delete account
            ctx.status(404);
        }
        else {
            // Successfully deleted account
            ctx.status(205);
        }
    }

}
