package dev.randolph.service;

import java.util.List;
import java.util.TreeSet;

import dev.randolph.model.Client;
import dev.randolph.repo.ClientDAO;

public class ClientService {
    
    private ClientDAO clientDao = new ClientDAO();
    private TreeSet<Integer> gapIds = new TreeSet<Integer>();
    private int nextId = 0;
    
    /**
     * Will determine which ids are available for use.
     */
    public ClientService() {
        // Init
        List<Client> clients = clientDao.getAllClients();
        TreeSet<Integer> usedIds = new TreeSet<Integer>();
        
        // Find which client ids are used
        for (Client client: clients) {
            usedIds.add(client.getId());
        }
        
        // Finding gaps between 0 - largestId
        if (usedIds.size() - 1 != usedIds.last()) {
            // There are gaps
            for (int i = 0; i < usedIds.last(); i++) {
                if (!usedIds.contains(i)) {
                    gapIds.add(i);
                }
            }
        }
        
        // Next Id
        nextId = usedIds.last() + 1;
    }
    
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
        // Init
        int id = getNextId();
        Client client = clientDao.createNewClient(new Client(id)); // Adding new client to DB
        
        // Checking if new client was added successfully
        if (client == null) {
            // Failed to add new client
            gapIds.add(id);
        }
        
        return client;
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
            clientDao.updateClientById(client);
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
        
        // Init
        boolean success = clientDao.deleteClientById(cid);
        
        // Checking if client was deleted successfully
        if (success) {
            // Successful
            gapIds.add(cid);
        }
        
        return success;
    }
    
    /*
     * === Utility ===
     */
    
    /**
     * Returns an available client ID that can be used.
     * If there was a deletion then that ID is now available and will be returned instead.
     * Otherwise will return a new id.
     * @return The client ID to use.
     */
    private int getNextId() {
        // Checking if there are gaps
        if (gapIds.isEmpty()) {
            // No gaps
            return nextId++;
        }
        else {
            // Gap
            return gapIds.pollFirst();
        }
    }

}
