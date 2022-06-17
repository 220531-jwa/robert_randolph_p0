package dev.randolph.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.randolph.model.Client;
import dev.randolph.repo.ClientDAO;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTests {
    
    private ClientService cs;
    private List<Client> mockClientData;
      
    // Mock dependencies
    @Mock
    private ClientDAO cdMock;
    
    @BeforeEach
    public void setupMockDatabaseData() {
        // Init
        cs = new ClientService(cdMock);
        
        // Mock Client data
        mockClientData = new ArrayList<>();
        mockClientData.add(new Client(1, "Alice", "Apple"));
        mockClientData.add(new Client(2, "Bob", "Bacon"));
        mockClientData.add(new Client(3, "Carl", "Cake"));
        
        // Universal Mock setup
        when(cdMock.getAllClients()).thenReturn(mockClientData);
        
        when(cdMock.getClientById(1)).thenReturn(mockClientData.get(0));
        when(cdMock.getClientById(2)).thenReturn(mockClientData.get(1));
        when(cdMock.getClientById(3)).thenReturn(mockClientData.get(2));
    }
    
    @AfterEach
    public void resetMocks() {
        reset(cdMock);
    }
    
    /*
     * === CREATE ===
     */
    
    @Test
    public void test_createNewClient() {
        // Init
        int nextId = 4;                                         // Next unique client id
        Client inputClient = new Client(0, "David", "Drink");   // Client to add to database
        // Client that is returned from database after being created
        Client outputClient = new Client(nextId, inputClient.getFirstName(), inputClient.getLastName());
        
        // Mock setup
        when(cdMock.createNewClient(inputClient)).thenReturn(outputClient);
        
        // Assertions
        Client testClient = cs.createNewClient(inputClient);
        
        // Valid
        assertEquals(nextId, testClient.getId());
        assertEquals(inputClient.getFirstName(), testClient.getFirstName());
        assertEquals(inputClient.getLastName(), testClient.getLastName());
        
        // Invalid input
        assertEquals(null, cs.createNewClient(null));
    }
    
    /*
     * === READ ===
     */
    
    @Test
    public void test_getAllClients() {
        // Assertions
        assertEquals(mockClientData, cs.getAllClients());
    }
    
    @Test
    public void test_getClientById() {
        // Assertions
        // Valid
        assertEquals(mockClientData.get(0), cs.getClientById(1));
        assertEquals(mockClientData.get(1), cs.getClientById(2));
        assertEquals(mockClientData.get(2), cs.getClientById(3));
        
        // Invalid
        assertEquals(null, cs.getClientById(-1));
        assertEquals(null, cs.getClientById(4));
    }
    
    /*
     * === UPDATE ===
     */
    
    @Test
    public void test_updateClientById() {
        // Init
        Client inputClient1 = new Client(1, "Alan", "Apricot"); // Update client with new first/last name
        Client inputClient2 = new Client(4, "Alan", "Apricot"); // Client doesn't exist
        
        // Mock setup
        when(cdMock.updateClientById(inputClient1)).thenReturn(true);
        when(cdMock.updateClientById(inputClient2)).thenReturn(false);
        
        // Assertions
        // Valid
        assertEquals(true, cs.updateClientById(inputClient1));
        
        // Invalid
        assertEquals(false, cs.updateClientById(inputClient2));
        assertEquals(false, cs.updateClientById(null));
    }
    
    /*
     * === DELETE ===
     */
    
    @Test
    public void test_deleteClientById() {
        // Mock setup
        when(cdMock.deleteClientById(1)).thenReturn(true);      // Client exists
        when(cdMock.deleteClientById(4)).thenReturn(false);     // Client doesn't exist
        
        // Assertions
        // Valid
        assertEquals(true, cs.deleteClientById(1));
        
        // Invalid
        assertEquals(false, cs.deleteClientById(-1));
        assertEquals(false, cs.deleteClientById(4));
    }
}
