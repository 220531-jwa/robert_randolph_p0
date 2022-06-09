package dev.randolph.model;

public class Client {
    
    private int id;
    
    public Client() {}
    
    public Client(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Client [id=" + id + "]";
    }

}
