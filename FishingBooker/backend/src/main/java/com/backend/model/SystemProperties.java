package com.backend.model;

import javax.persistence.*;

@Entity
public class SystemProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="system_properties_id", unique=true, nullable=false)
    private Integer id;

    @Column(unique=true, nullable=false)
    private String key;

    @Column(unique=false, nullable=false)
    private String value;

    public SystemProperties() { }

    public SystemProperties(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SystemProperties{}";
    }
}
