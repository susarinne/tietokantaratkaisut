package com.example.jpa;

import com.example.jpa.entity.*;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("HealthPU");
        EntityManager em = emf.createEntityManager();
        
        try {
	        em.getTransaction().begin();
	        
	        // Create customers
            Customer customer1 = new Customer();
            customer1.setFirstName("Matti");
            customer1.setLastName("Meikäläinen");
            
            Customer customer2 = new Customer();
            customer2.setFirstName("Terttu");
            customer2.setLastName("Teikäläinen");         
            
            // Create profiles
            BasicProfile profile1 = new BasicProfile();
            profile1.setBirthyear(1993);
            profile1.setWeight(72.0);
            profile1.setHeight(180.0);
            
            BasicProfile profile2 = new BasicProfile();
            profile2.setBirthyear(2000);
            profile2.setWeight(90.0);
            profile2.setHeight(188.0);

            em.persist(customer1);
            em.persist(customer2);
            
            // Set relations between profiles and customers
            profile1.setCustomer(customer1);
            customer1.setBasicProfile(profile1);
            
            profile2.setCustomer(customer2);
            customer2.setBasicProfile(profile2);

            // Create clinic
            Clinic clinic = new Clinic();
            clinic.setLocation("Hymykylä");

            // Create ContractCustomer (extends Customer)
            ContractCustomer contractCustomer = new ContractCustomer();
            contractCustomer.setFirstName("Taavi");
            contractCustomer.setLastName("Teikäläinen");
            contractCustomer.setContractId("ABC-2025-123L");
            contractCustomer.setStartDate(LocalDate.now());
            contractCustomer.setEndDate(LocalDate.now().plusYears(5));
            
            em.persist(contractCustomer);

            // Set relations between customers and clinic
            Set<Customer> customers = new HashSet<>();
            customers.add(customer1);
            customers.add(customer2);
            customers.add(contractCustomer);
            clinic.setCustomers(customers);

            Set<Clinic> clinics = new HashSet<>();
            clinics.add(clinic);
            customer1.setClinics(clinics);
            customer2.setClinics(clinics);

            em.persist(clinic);

            em.getTransaction().commit();
            logger.info("Customers, BasicProfiles, Clinic, and ContractCustomer persisted successfully");
        } catch (Exception e) {
        	logger.error("Failed to execute", e);
	    } finally {
	        em.close();
	        emf.close();
	    }
    }
    
}
