package dev.randolph.util;

import java.util.ArrayList;
import java.util.List;

import dev.randolph.model.Account;
import dev.randolph.model.Client;

public class MockDB {
    
    public static List<Client> clients = new ArrayList<Client>();
    public static List<Account> accounts = new ArrayList<Account>();
    
    static {
        // Init
        for (int i = 0; i < 5; i++) {
            clients.add(new Client(i));
            accounts.add(new Account(i, i, 3.5*i));
        }
    }
}
