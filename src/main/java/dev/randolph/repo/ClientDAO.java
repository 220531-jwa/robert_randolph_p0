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
    
    /**
     * Inserts a new client into the database.
     * @param client The client to enter into the database. (Ignores ID)
     * @return The client that was added to the database.
     */
    public Client createNewClient(Client client) {
        // Init
        String sql = "INSERT INTO clients VALUES"
                + " (default, ?, ?)"
                + " RETURNING *";
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ResultSet rs = ps.executeQuery();
            
            // Going through results
            if (rs.next()) {
                // Successfully created client
                client.setId(rs.getInt("id"));
            }
            else {
                // Failed to create client
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
    
    /**
     * Gets all the clients in the database.
     * @return A list of clients, or null if none was retrieved.
     */
    public List<Client> getAllClients() {
        // Init
        String sql = "SELECT * FROM clients ORDER BY id";
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
                    clients.add(new Client(rs.getInt("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name")));
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
        String sql = "SELECT * FROM clients"
                + " WHERE id = ?";
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
                client = new Client(rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return client;
    }
    
    /*
     * === UPDATE ===
     */
    
    /**
     * Updates the client with the given client id, along with updated information.
     * @param client The client to update
     * @return True if the client was updated successfully, and false otherwise.
     */
    public boolean updateClient(Client client) {
        // Init
        String sql = "UPDATE clients"
                + " SET (first_name, last_name) = (?, ?)"
                + " WHERE id = ?";
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, client.getFirstName());
            ps.setString(2, client.getLastName());
            ps.setInt(3, client.getId());
            int changes = ps.executeUpdate();
            
            // Going through results
            if (changes != 0) {
                // Successfully updated client
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /*
     * === DELETE ===
     */
    
    /**
     * Deletes the client with the given client id.
     * @param cid The client to delete.
     * @return True if the client was successfully deleted, and false otherwise.
     */
    public boolean deleteClientById(int cid) {
        // Init
        String sql = "DELETE FROM clients"
                + " WHERE id = ?";
        boolean success = false;
        
        // Executing query
        try (Connection conn = cu.getConnection()) {
            // Getting results
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cid);
            int changes = ps.executeUpdate();
            
            // Going through results
            if (changes != 0) {
                // Deletion successful
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return success;
    }
    
}
