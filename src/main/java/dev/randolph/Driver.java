package dev.randolph;

import io.javalin.Javalin;

public class Driver {
    
    public static void main(String[] args) {
        // Starting server
        Javalin app = Javalin.create();
        app.start();
        
        // End Points
        
        /*
         * === GET ===
         */
        
        // GET all clients
        app.get("/clients", ctx -> {
            
        });
        
        // GET client by Id
        app.get("/clients/{id}", ctx -> {
            
        });
        
        // GET all accounts for client 'cid'
        app.get("/clients/{cid}/accounts", ctx -> {
            
        });
        
        // GET all accounts for client 'cid' with amount between range(num1, num2);
        app.get("/clients/{cid}/accounts???", ctx -> {
            
        });
        
        // GET account 'aid' from client 'cid'
        app.get("/clients/{cid}/accounts/{aid}", ctx -> {
            
        });
        
        /*
         * === POST ===
         */
        
        // POST - create new client
        app.post("/clients", ctx -> {
            
        });
        
        // POST - create new account for client 'cid'
        app.post("/clients/{cid}/accounts", ctx -> {
            
        });
        
        /*
         * === PUT ===
         */
        
        // PUT - update client 'cid'
        app.put("/clients/{cid}", ctx -> {
            
        });
        
        // PUT - update amount of account 'aid' of client 'cid'
        app.put("/clients/{cid}/accounts/{aid}", ctx -> {
            
        });
        
        // === DELETE ===
        
        // DELETE client 'cid'
        app.delete("/clients/{cid}", ctx -> {
            
        });
        
        // DELETE account 'aid' of client 'cid'
        app.delete("clients/{cid}/accounts/{aid}", ctx -> {
            
        });
        
        /*
         * === PATCH ===
         */
        
        // PATCH - withdraw/deposit amount of account 'aid' of client 'cid'
        app.patch("/clients/{cid}/accounts/{aid}", ctx -> {
            
        });
        
        // PATCH - transfer amount of account 'aid' of client 'cid' to account 'taid'
        app.patch("clients/{cid}/accounts/{aid}/transfre/{taid}", ctx -> {
            
        });
    }
}
