package com.example.jpa.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custId;
    
    private String firstName;
    private String lastName;
    
    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private BasicProfile basicProfile;
    
    @ManyToMany(mappedBy = "customers")
    private Set<Clinic> clinics;

    public Customer() {}

    public Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

    public Long getCustomerId() {
        return custId;
    }
    
    public void setCustomerId(Long id) {
        this.custId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public BasicProfile getBasicProfile() {
        return basicProfile;
    }
    
    public void setBasicProfile(BasicProfile basicProfile) {
        this.basicProfile = basicProfile;
        
        if (basicProfile != null) {
            basicProfile.setCustomer(this);
        }
    }
    
    public Set<Clinic> getClinics() {
        return clinics;
    }

    public void setClinics(Set<Clinic> clinics) {
        this.clinics = clinics;
    }
    
}
