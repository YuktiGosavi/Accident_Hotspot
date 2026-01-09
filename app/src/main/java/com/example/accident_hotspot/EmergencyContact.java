package com.example.accident_hotspot;

public class EmergencyContact {

    private String name, relation, phone;

    public EmergencyContact(String name, String relation, String phone) {
        this.name = name;
        this.relation = relation;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getRelation() { return relation; }
    public String getPhone() { return phone; }
}
