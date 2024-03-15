package com.dydzik.entity;

public abstract class Client {
    private int id;
    private String name;
    private String type;

    public Client(String type, int id, String name) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Client with " + id + " and name " + name + " | Account Type: " + type;
    }
}
