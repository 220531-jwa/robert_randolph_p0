package dev.randolph.service;

import java.util.List;

import dev.randolph.model.Client;
import dev.randolph.repo.ClientDAO;

public class ClientService {
    
    private ClientDAO clientDao = new ClientDAO();
    
    /*
     * === POST / CREATE ===
     */
    
    public Client createNewClient() {
        return null;
    }
    
    /*
     * === GET / READ ===
     */
    
    public List<Client> getAllClients() {
        return null;
    }
    
    public Client getClientById(int cid) {
        return null;
    }
    
    /*
     * === PUT / UPDATE ===
     */
    
    public Client updateClientById(int cid) {
        return null;
    }
    
    /*
     * === DELETE ===
     */
    
    public boolean deleteClientById(int cid) {
        return false;
    }

}
