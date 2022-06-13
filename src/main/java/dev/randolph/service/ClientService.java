package dev.randolph.service;

import java.util.List;

import dev.randolph.model.Client;
import dev.randolph.repo.ClientDAO;

public class ClientService {
    
    private ClientDAO cd = new ClientDAO();
    
    /*
     * === POST / CREATE ===
     */
    
    /**
     * Creates a new client that will be inserted into the database.
     * Each client will have a unique id.
     * If a client was previously deleted, then that id will be used instead of a new one.
     * @return A client if created successfully, and null otherwise.
     */
    public Client createNewClient(Client client) {
        // Validating input
        if (client == null) {
            return null;
        }
        
        return cd.createNewClient(client);
    }
    
    /*
     * === GET / READ ===
     */
    
    /**
     * Returns a list of clients from the database.
     * @return A list of clients, and null otherwise.
     */
    public List<Client> getAllClients() {
        return cd.getAllClients();
    }
    
    /**
     * Finds a client with the given id.
     * @param cid The client to find.
     * @return The client if found, and null otherwise.
     */
    public Client getClientById(int cid) {
        // Validating input
        if (cid < 0) {
            return null;
        }
        
        return cd.getClientById(cid);
    }
    
    /*
     * === PUT / UPDATE ===
     */
    
    /**
     * Updates a client in the database.
     * @param client The client to update alonge with the updated information.
     * @return The updated client if successful, and null otherwise.
     */
    public boolean updateClientById(Client client) {
        // Validating input
        if (client == null) {
            return false;
        }
        
        return cd.updateClient(client);
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Deletes the client with the given id from the database.
     * @param cid The client to delete.
     * @return True if the client was deleted successfully, and false otherwise.
     */
    public boolean deleteClientById(int cid) {
        // Validating input
        if (cid < 0) {
            return false;
        }
        
        return cd.deleteClientById(cid);
    }
}
