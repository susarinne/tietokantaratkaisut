package com.example.jpa.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ContractCustomer extends Customer {
	
    private String contractId;
    private LocalDate startDate;
    private LocalDate endDate;
    
    public ContractCustomer() {}

	public String getContractId() {
        return contractId;
    }

    public void setContractId(String id) {
        this.contractId = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
}
