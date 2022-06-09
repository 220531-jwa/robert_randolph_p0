package dev.randolph.model;

public class Account {
    
    private int id;
    private int clientId;
    private double amount;
    
    public Account() {}
    
    public Account(int id, int clientId, double amount) {
        this.id = id;
        this.clientId = clientId;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", clientId=" + clientId + ", amount=" + amount + "]";
    }

}
