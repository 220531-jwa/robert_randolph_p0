package dev.randolph.model;

public class Account {
    
    private int id;
    private int clientId;
    private AccountType accountType;
    private double balance;
    
    public Account() {}
    
    public Account(int id, int clientId, AccountType accountType, double balance) {
        this.id = id;
        this.clientId = clientId;
        this.accountType = accountType;
        this.balance = balance;
    }
    
    public Account(Account account) {
        id = account.getId();
        clientId = account.getClientId();
        accountType = account.getAccountType();
        balance = account.getBalance();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", clientId=" + clientId + ", accountType=" + accountType + ", balance=" + balance
                + "]";
    }

}
