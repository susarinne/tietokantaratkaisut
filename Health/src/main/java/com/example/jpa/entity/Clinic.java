package com.example.jpa.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Clinic {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clinicId;
    
    private String location;
    
    @ManyToMany
    @JoinTable(
        name = "clinic_customer",
        joinColumns = @JoinColumn(name = "clinic_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    public Clinic() {}

    public Clinic(String location) {
		this.location = location;
	}

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

}
