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
        
    }
    
    /*
     * === PUT ===
     */
    
    public void updateClientById(Context ctx) {
        
    }
    
    /*
     * === DELETE ===
     */
    
    public void deleteClientById(Context cts) {
        
    }

}
