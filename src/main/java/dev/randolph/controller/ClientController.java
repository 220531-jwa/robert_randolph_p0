package dev.randolph.controller;

import java.util.List;

import dev.randolph.model.Client;
import dev.randolph.service.ClientService;
import io.javalin.http.Context;

public class ClientController {
    
    private ClientService cs = new ClientService();
    
    /*
     * === POST ===
     */
    
    /**
     * Creates a new client to add to the database.
     * Takes the firstName, and lastName from the request body.
     * @param ctx The http request/response.
     * @return A 201 response with the client in the body if successful. 503 otherwise.
     */
    public void createNewClient(Context ctx) {
        // Init
        Client client = ctx.bodyAsClass(Client.class);
        client = cs.createNewClient(client);
        
        // Checking if new client was created successfully
        if (client == null) {
            // Failed to create new client
            // Service unavailable
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
    
    /**
     * Retrieves all the clients in the database.
     * @param ctx The http request/response.
     * @return A 200 response with the clients in the body, and 404 otherwise
     */
    public void getAllClients(Context ctx) {
        // Init
        List<Client> clients = cs.getAllClients();
        
        // Checking if any clients where found
        if (clients == null) {
            // No clients found
            ctx.status(404);
        }
        else {
            // Clients found
            ctx.status(200);
            ctx.json(cs.getAllClients());
        }
        
    }
    
    /**
     * Gets a client by a specific id.
     * Takes the id of the client.
     * @param ctx the http request/response.
     * @return A 200 response with the client in the body if successful, and 404 otherwise.
     */
    public void getClientById(Context ctx) {
        // Init
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        Client client = cs.getClientById(cid);
        
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
    
    /**
     * Updates a client by a specific id.
     * Takes the id of the client.
     * Takes the firstName and lastName from the body.
     * @param ctx the http request/response.
     * @return A 204 response if client was updated successfully, and 404 otherwise.
     */
    public void updateClientById(Context ctx) {
        // Init
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        Client client = ctx.bodyAsClass(Client.class);
        client.setId(cid);
        boolean success = cs.updateClientById(client);
        
        // Checking if client was updated
        if (!success) {
            // Failed to update client
            ctx.status(404);
        }
        else {
            // Successfully updated client
            ctx.status(204);
        }
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Deletes a client by a specifc id.
     * Takes the id of the client.
     * @param ctx the http request/response.
     * @return A 205 response if client was deleted successfully, and 404 otherwise.
     */
    public void deleteClientById(Context ctx) {
        // Init
        int cid = Integer.parseInt(ctx.pathParam("cid"));
        boolean success = cs.deleteClientById(cid);
        
        // Checking if client was deleted
        if (!success) {
            // Failed to delete client
            ctx.status(404);
        }
        else {
            // Successfully deleted client
            ctx.status(205);
        }
    }

}
