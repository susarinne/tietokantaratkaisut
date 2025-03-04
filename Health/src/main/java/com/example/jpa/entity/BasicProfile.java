package com.example.jpa.entity;

import jakarta.persistence.*;

@Entity
public class BasicProfile {
	
    @Id
    private Long custId;

    private int birthyear;
    private double weight;
    private double height;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "custId")
    private Customer customer;

    public BasicProfile() {}

    public BasicProfile(int birthyear, double weight, double height) {
		this.birthyear = birthyear;
		this.weight = weight;
		this.height = height;
	}
    
    public Long getCustomerId() {
        return custId;
    }
    
    public void setCustomerId(Long id) {
        this.custId = id;
    }

    public int getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(int birthyear) {
        this.birthyear = birthyear;
    }
    
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
}
