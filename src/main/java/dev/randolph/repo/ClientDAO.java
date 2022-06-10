package dev.randolph.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.randolph.model.Client;
import dev.randolph.util.ConnectionUtil;

public class ClientDAO {
    
    private ConnectionUtil cu = ConnectionUtil.getConnectionUtil();
    
    /*
     * === CREATE ===
     */
    
    public Client createNewClient(Client client) {
        // Init
        String sql = "INSERT INTO clients VALUES (default) RETURNING *";
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Client successfully added
                client.setId(rs.getInt("id"));
            }
            else {
                // Failed to add client
                client = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return client;
    }
    
    /*
     * === READ ===
     */
    
    public List<Client> getAllClients() {
        // Init
        String sql = "SELECT * FROM clients";
        ArrayList<Client> clients = null;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            ResultSet rs = conn.prepareStatement(sql).executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Found clients
                clients = new ArrayList<Client>();
                do {
                    clients.add(new Client(rs.getInt("id")));
                } while (rs.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return clients;
    }
    
    /**
     * Gets the client with the given ID from the database.
     * @param cid The client id to find.
     * @return The client if found, and null otherwise.
     */
    public Client getClientById(int cid) {
        // Init
        String sql = "SELECT * FROM clients where id = ?";
        Client client = null;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Client found
                client = new Client(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return client;
    }
    
    /*
     * === UPDATE ===
     */
    
    public Client updateClient(int cid) {
        // init
        String sql = "UPDATE clients SET id = ? WHERE id = ? RETURNING *";
        Client client = null;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ps.setInt(2, cid);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Client updated
                client = new Client(rs.getInt("id"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return client;
    }
    
    /*
     * === DELETE ===
     */
    
    public boolean deleteClientById(int cid) {
        // Init
        String sql = "DELETE FROM clients WHERE id = ?";
        boolean success = false;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Deletion successful
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }
    
}
