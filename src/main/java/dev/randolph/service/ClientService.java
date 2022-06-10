package dev.randolph.service;

import java.util.List;
import java.util.TreeSet;

import dev.randolph.model.Client;
import dev.randolph.repo.ClientDAO;

public class ClientService {
    
    private ClientDAO clientDao = new ClientDAO();
    
    /*
     * === POST / CREATE ===
     */
    
    /**
     * Creates a new client that will be inserted into the database.
     * Each client will have a unique id.
     * If a client was previously deleted, then that id will be used instead of a new one.
     * @return A client if created successfully, and null otherwise.
     */
    public Client createNewClient() {
        return clientDao.createNewClient(new Client(0));
    }
    
    /*
     * === GET / READ ===
     */
    
    /**
     * Returns a list of clients from the database.
     * @return A list of clients.
     */
    public List<Client> getAllClients() {
        return clientDao.getAllClients();
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
        
        return clientDao.getClientById(cid);
    }
    
    /*
     * === PUT / UPDATE ===
     */
    
    public Client updateClientById(int cid) {
        // Validating input
        if (cid < 0) {
            return null;
        }
        
        // Init
        Client client = clientDao.getClientById(cid);
        
        // Checking if client exists
        if (client != null) {
            // Found client
            // TODO update client -> push update to DB
            clientDao.updateClient(client);
        }
        
        return client;
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
        
        return clientDao.deleteClientById(cid);
    }
}
