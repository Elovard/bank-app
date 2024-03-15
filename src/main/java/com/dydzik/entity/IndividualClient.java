package com.dydzik.entity;

public class IndividualClient extends Client {
    private String personalId;

    public IndividualClient(String type, int id, String name) {
        super(type, id, name);
//        this.personalId = personalId;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
}
