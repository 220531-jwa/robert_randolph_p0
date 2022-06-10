package dev.randolph.controller;

import java.util.List;

import dev.randolph.model.Client;
import dev.randolph.service.ClientService;
import io.javalin.http.Context;

public class ClientController {
    
    private ClientService clientService = new ClientService();
    
    /*
     * === POST ===
     */
    
    public void createNewClient(Context ctx) {
        // Init
        Client client = clientService.createNewClient();
        
        // Checking if new client was created successfully
        if (client == null) {
            // Failed to create new client
            ctx.status(503);
        }
        else {
            // Successfully created new client
            ctx.status(201);
            ctx.json(client);
        }
    }
    
    /*
     * === GET ===
     */
    
    public void getAllClients(Context ctx) {
        // Init
        List<Client> clients = clientService.getAllClients();
        
        // Checking if any clients where found
        if (clients == null) {
            // No clients found
            ctx.status(200);
        }
        else {
            // Clients found
            ctx.status(200);
            ctx.json(clientService.getAllClients());
        }
        
    }
    
    public void getClientById(Context ctx) {
        // Init
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        Client client = clientService.getClientById(cid);
        
        // Checking if client was found
        if (client == null) {
            // No client found
            ctx.status(404);
        }
        else {
            // Client found
            ctx.status(200);
            ctx.json(client);
        }
    }
    
    /*
     * === PUT ===
     */
    
    public void updateClientById(Context ctx) {
        // Init
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        Client client = clientService.updateClientById(cid);
        
        // Checking if client was updated
        if (client == null) {
            // Failed to update client
            ctx.status(404);
        }
        else {
            // Successfully updated client
            ctx.status(200);
            ctx.json(client);
        }
    }
    
    /*
     * === DELETE ===
     */
    
    public void deleteClientById(Context ctx) {
        // Init
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        boolean success = clientService.deleteClientById(cid);
        
        // Checking if client was updated
        if (!success) {
            // Failed to update client
            ctx.status(404);
        }
        else {
            // Successfully updated client
            ctx.status(205);
        }
    }

}
