package dev.randolph.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.randolph.model.Account;
import dev.randolph.model.AccountType;
import dev.randolph.model.Client;
import dev.randolph.repo.AccountDAO;
import dev.randolph.repo.ClientDAO;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    
    private AccountService as;
    private List<Client> mockClientData;
    private List<Account> mockAccountData;
      
    // Mock dependencies
    @Mock
    private ClientDAO cdMock;
    @Mock
    private AccountDAO adMock;
    
    @BeforeEach
    public void setupMockDatabaseData() {
        // Init
        as = new AccountService(cdMock, adMock);
        
        // Mock Client data
        mockClientData = new ArrayList<>();
        mockClientData.add(new Client(1, "Alice", "Apple"));
        mockClientData.add(new Client(2, "Bob", "Bacon"));
        mockClientData.add(new Client(3, "Carl", "Cake"));
        
        // Mock Account data
        mockAccountData = new ArrayList<>();
        mockAccountData.add(new Account(1, 1, AccountType.CHECKING, 50));
        mockAccountData.add(new Account(2, 1, AccountType.SAVINGS, 100));
        mockAccountData.add(new Account(3, 2, AccountType.CHECKING, 150));
        mockAccountData.add(new Account(4, 3, AccountType.SAVINGS, 200));
        
        // Universal Mock setup
        // Client
        when(cdMock.getAllClients()).thenReturn(mockClientData);
        
        when(cdMock.getClientById(-1)).thenReturn(null);
        when(cdMock.getClientById(1)).thenReturn(mockClientData.get(0));
        when(cdMock.getClientById(2)).thenReturn(mockClientData.get(1));
        when(cdMock.getClientById(3)).thenReturn(mockClientData.get(2));
        when(cdMock.getClientById(4)).thenReturn(null);
        
        // Account
        when(adMock.getAllClientAccounts(1)).thenReturn(new ArrayList<Account>(
                Arrays.asList(mockAccountData.get(0), mockAccountData.get(1))));
        when(adMock.getAllClientAccounts(2)).thenReturn(new ArrayList<Account>(
                Arrays.asList(mockAccountData.get(2))));
        when(adMock.getAllClientAccounts(3)).thenReturn(new ArrayList<Account>(
                Arrays.asList(mockAccountData.get(3))));
        
        when(adMock.getAllClientAccountsInBalanceRange(1, 30, 90)).thenReturn(new ArrayList<Account>(
                Arrays.asList(mockAccountData.get(0))));
        
        when(adMock.getClientAccountById(1, 1)).thenReturn(mockAccountData.get(0));
        when(adMock.getClientAccountById(1, 2)).thenReturn(mockAccountData.get(1));
        when(adMock.getClientAccountById(2, 3)).thenReturn(mockAccountData.get(2));
        when(adMock.getClientAccountById(3, 4)).thenReturn(mockAccountData.get(3));
        when(adMock.getClientAccountById(-1, 1)).thenReturn(null);
        when(adMock.getClientAccountById(1, -1)).thenReturn(null);
        when(adMock.getClientAccountById(4, 1)).thenReturn(null);
        when(adMock.getClientAccountById(4, 2)).thenReturn(null);
        when(adMock.getClientAccountById(1, 5)).thenReturn(null);
        when(adMock.getClientAccountById(1, 3)).thenReturn(null);
    }
    
    @AfterEach
    public void resetMocks() {
        reset(cdMock);
        reset(adMock);
    }
    
    /*
     * === CREATE ===
     */
    
    @Test
    public void test_createNewAccount() {
        // Init
        int nextId = 5;
        Account inputAccount1 = new Account(0, 1, AccountType.CHECKING, 100);   // Client exists
        Account inputAccount2 = new Account(0, 4, AccountType.CHECKING, 100);   // Client doesn't exist
        Account outputAccount = new Account(nextId, inputAccount1.getClientId(), inputAccount1.getAccountType(), inputAccount1.getBalance());
        
        // Mock setup
        when(adMock.createNewAccount(inputAccount1)).thenReturn(outputAccount);
        
        // Assertions
        Account testAccount = as.createNewAccount(inputAccount1);
        
        // Valid
        assertEquals(nextId, testAccount.getId());
        assertEquals(inputAccount1.getClientId(), testAccount.getClientId());
        assertEquals(inputAccount1.getAccountType(), testAccount.getAccountType());
        assertEquals(inputAccount1.getBalance(), testAccount.getBalance());
        
        // Invalid
        assertEquals(null, as.createNewAccount(null));
        assertEquals(null, as.createNewAccount(inputAccount2)); // Client doesn't exist
    }
    
    /*
     * === READ ===
     */
    
    @Test
    public void test_getAllClientAccounts() {
        // Init
        List<Account> output1 = new ArrayList<Account>(
                Arrays.asList(mockAccountData.get(0), mockAccountData.get(1)));
        List<Account> output2 = new ArrayList<Account>(Arrays.asList(mockAccountData.get(2)));
        List<Account> output3 = new ArrayList<Account>(Arrays.asList(mockAccountData.get(3)));
        List<Account> output4 = new ArrayList<Account>(Arrays.asList(mockAccountData.get(0)));
        
        // Assertions
        // Valid
        assertEquals(output1, as.getAllClientAccounts(1, null, null));
        assertEquals(output2, as.getAllClientAccounts(2, null, null));
        assertEquals(output3, as.getAllClientAccounts(3, null, null));
        assertEquals(output4, as.getAllClientAccounts(1, 30, 90));
        
        // Invalid
        assertEquals(null, as.getAllClientAccounts(-1, null, null));// Invalid client id
        assertEquals(null, as.getAllClientAccounts(4, null, null)); // Client doesn't exist
        assertEquals(null, as.getAllClientAccounts(1, 30, null));   // invalid bounds
        assertEquals(null, as.getAllClientAccounts(1, null, 90));   // invalid bounds
        assertEquals(null, as.getAllClientAccounts(1, 90, 30));     // invalid bounds
    }
    
    @Test
    public void test_getClientAccountById() {
        // Assertions
        // Valid
        assertEquals(mockAccountData.get(0), as.getClientAccountById(1, 1));
        assertEquals(mockAccountData.get(1), as.getClientAccountById(1, 2));
        assertEquals(mockAccountData.get(2), as.getClientAccountById(2, 3));
        assertEquals(mockAccountData.get(3), as.getClientAccountById(3, 4));
        
        // Invalid
        assertEquals(null, as.getClientAccountById(-1, 1)); // Invalid client input
        assertEquals(null, as.getClientAccountById(1, -1)); // Invalid account input
        assertEquals(null, as.getClientAccountById(4, 1));  // Client doesn't exist
        assertEquals(null, as.getClientAccountById(1, 5));  // Account doesn't exist
        assertEquals(null, as.getClientAccountById(1, 3));  // Account not associated with client
    }
    
    /*
     * === UPDATE ===
     */
    
    @Test
    public void test_updateClientAccountById() {
        // Input
        Account inputClient1 = new Account(1, 1, AccountType.CHECKING, 1000);
        Account inputClient2 = new Account(1, 4, AccountType.CHECKING, 1000);   // Client doesn't exist
        Account inputClient3 = new Account(5, 1, AccountType.CHECKING, 1000);   // Account doesn't exist
        Account inputClient4 = new Account(3, 1, AccountType.CHECKING, 1000);   // Client not associated with account
        
        // Mock setup
        when(adMock.updateClientAccountById(inputClient1)).thenReturn(true);
        when(adMock.updateClientAccountById(inputClient2)).thenReturn(false);
        when(adMock.updateClientAccountById(inputClient3)).thenReturn(false);
        when(adMock.updateClientAccountById(inputClient4)).thenReturn(false);
        
        // Assertions
        // Valid
        assertEquals(true, as.updateClientAccountById(inputClient1));
        
        // Invalid
        assertEquals(false, as.updateClientAccountById(inputClient2));
        assertEquals(false, as.updateClientAccountById(inputClient3));
        assertEquals(false, as.updateClientAccountById(inputClient4));
        assertEquals(false, as.updateClientAccountById(null));
    }
    
    @Test
    public void test_updateClientAccountBalance() {
        // Init
        Account inputClient1 = new Account(1, 1, null, 500);    // Deposit
        Account inputClient2 = new Account(1, 1, null, -30);    // Withdraw - Valid
        Account inputClient3 = new Account(1, 1, null, -1000);  // Withdraw - Insufficient funds
        Account inputClient4 = new Account(1, 4, null, 500);    // Client doesn't exist
        Account inputClient5 = new Account(5, 1, null, 500);    // Account doens't exist
        Account inputClient6 = new Account(3, 1, null, 500);    // Account not associated with client
        
        // Mock setup
        // Any update that gets to the DAO should be valid -> otherwise there is a bug in service
        when(adMock.updateClientAccountBalance(any())).thenReturn(true);
        
        // Assertions
        // Valid
        assertEquals(1, as.updateClientAccountBalance(inputClient1));
        assertEquals(1, as.updateClientAccountBalance(inputClient2));
        
        // Invalid
        assertEquals(-1, as.updateClientAccountBalance(null));
        assertEquals(0, as.updateClientAccountBalance(inputClient3));
        assertEquals(-1, as.updateClientAccountBalance(inputClient4));
        assertEquals(-1, as.updateClientAccountBalance(inputClient5));
        assertEquals(-1, as.updateClientAccountBalance(inputClient6));
    }
    
    @Test
    public void test_transferClientAccountFunds() {
        // Init
        Account inputClient1 = new Account(1, 1, null, 30);     // Transfer - valid
        Account inputClient2 = new Account(1, 1, null, 3000);   // Transfer - insufficient funds
        Account inputClient3 = new Account(1, 4, null, 30);     // Client doesn't exist
        Account inputClient4 = new Account(5, 1, null, 30);     // Account doesn't exist
        Account inputClient5 = new Account(3, 1, null, 30);     // Account not associated with client
        
        // Mock setup
        // Any update that gets to the DAO should be valid -> otherwise there is a bug in service
        when(adMock.transferAccountAmount(any(), any())).thenReturn(true);
        
        // Assertions
        // Valid
        assertEquals(1, as.transferClientAccountFunds(inputClient1, 2));
        
        // Invalid
        assertEquals(-1, as.transferClientAccountFunds(null, 5));           // Transfer account doesn't exist
        assertEquals(-1, as.transferClientAccountFunds(inputClient1, -1));  // Invalid transfer id
        assertEquals(-1, as.transferClientAccountFunds(inputClient1, 5));
        assertEquals(0, as.transferClientAccountFunds(inputClient2, 2));
        assertEquals(-1, as.transferClientAccountFunds(inputClient3, 2));
        assertEquals(-1, as.transferClientAccountFunds(inputClient4, 2));
        assertEquals(-1, as.transferClientAccountFunds(inputClient5, 2));
    }
    
    /*
     * === DELETE ===
     */
    
    @Test
    public void test_deleteClientAccountById() {
        // Mock setup
        when(adMock.deleteClientAccountById(1, 2)).thenReturn(true);    // client/account exist
        when(adMock.deleteClientAccountById(4, 2)).thenReturn(false);    // client doensn't exist
        when(adMock.deleteClientAccountById(1, 5)).thenReturn(false);    // account doesn't exist
        when(adMock.deleteClientAccountById(1, 3)).thenReturn(false);    // account isn't associated with client
        
        // Assertions
        // Valid
        assertEquals(true, as.deleteClientAccountById(1, 2));
        assertEquals(false, as.deleteClientAccountById(4, 2));
        assertEquals(false, as.deleteClientAccountById(1, 5));
        assertEquals(false, as.deleteClientAccountById(1, 3));
        assertEquals(false, as.deleteClientAccountById(-1, 2));
        assertEquals(false, as.deleteClientAccountById(1, -2));
    }
}
