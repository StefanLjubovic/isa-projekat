package com.backend.model;

import javax.persistence.*;

@Entity
public class SystemProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="system_properties_id", unique=true, nullable=false)
    private Integer id;

    @Column(unique=true, nullable=false)
    private String key;

    @Column(unique=false, nullable=false)
    private String value;

    @Version
    private Integer version;

    public SystemProperty() { }

    public SystemProperty(String key, String value) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "SystemProperties{}";
    }
}
