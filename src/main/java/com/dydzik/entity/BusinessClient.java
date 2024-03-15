package com.dydzik.entity;

public class BusinessClient extends Client {
    private String vatId;

    public BusinessClient(String type, int id, String name) {
        super(type, id, name);
//        this.vatId = vatId;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }
}
