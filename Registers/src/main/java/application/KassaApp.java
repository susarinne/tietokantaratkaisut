package application;

import entity.*;
import dao.*;
import java.util.List;

public class KassaApp {
	
	public static void initDB(Dao dao) {
		
		final int NUM_REGISTERS = 5;
		final int NUM_EVENTS = 10;
		
		// initialize registers
		for (int i = 1; i <= NUM_REGISTERS; i++) {
			Register reg = new Register(i, "Kassapiste_" + i);
			dao.addRegister(reg);
		}
		
		// initialize events
		for (int i = 1; i <= NUM_EVENTS; i++) {
			int regnum = (int)(Math.random() * NUM_REGISTERS) + 1;
			// amount between 1.00 - 99.99
			double amount = (int)(100+Math.random() * 9900) / 100.0;
			dao.addEvent(i, regnum, amount);
		}
	}
	
    public static void main(String[] args) {
    	
    	Dao dao = new Dao();
    	initDB(dao);
 
    	// 1a JPQL
    	List<SalesEvent> jpqlResult = dao.retrieveSmallSalesJPQL(35.0);
    	if (jpqlResult != null) {
    		for (SalesEvent s : jpqlResult) {
    			System.out.println(s + " at register " + s.getRegister());
    		}
    	}
    	
    	// 1b JPQL
    	dao.addServiceFeeJPQL(5.00);
    	
    	// 1c JPQL
    	dao.deleteAllSalesEventsJPQL();
    	
    	// 2a Criteria
    	List<SalesEvent> criteriaResult = dao.retrieveSmallSalesCriteria(35.0);
    	if (criteriaResult != null) {
    		for (SalesEvent s : criteriaResult) {
    			System.out.println(s + " at register " + s.getRegister());
    		}
    	}
    	
    	// 2b Criteria
    	dao.addServiceFeeCriteria(5.00);
    	
    	// 2c Criteria
    	dao.deleteAllSalesEventsCriteria();
    	
    }
    
}
