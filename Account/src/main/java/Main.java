import dao.AccountDao;
import model.Account;

public class Main {
	
    public static void main(String[] args) {

    	// Initialize account DAO
	    AccountDao dao = new AccountDao();
	    
	    // Create account objects
	    Account account1 = new Account(39457394, 700.00);
	    Account account2 = new Account(29485673, 800.00);
	    
	    // Persist
	    dao.save(account1);
	    dao.save(account2);
	
	    // Retrieve persisted objects from database
		Account sourceAccount = dao.find(account1.getNumber());
	    Account destinationAccount = dao.find(account2.getNumber());
	    
	    // Save original balances before transfer for comparison
	    double sourceOgBalance = sourceAccount.getBalance();
	    double destinationOgBalance = destinationAccount.getBalance();
	    
	    // Define transfer amount
	    double transferAmount = 200.00;
	    
	    // Make transfer
	    dao.transfer(sourceAccount.getNumber(), destinationAccount.getNumber(), transferAmount);
	    
	    // Fetch updated account data
        Account updatedSourceAccount = dao.find(account1.getNumber());
        Account updatedDestinationAccount = dao.find(account2.getNumber());
	    
	    // Echo the resulting balance on the console
	    // Verify that the conversion works correctly also in this direction (from database)
	    System.out.println("Account " + account1.getNumber() + " – before: " + sourceOgBalance + " – after: "+ updatedSourceAccount.getBalance());
	    System.out.println("Account " + account2.getNumber() + " – before: " + destinationOgBalance + " – after: "+ updatedDestinationAccount.getBalance());

	    if (updatedSourceAccount.getBalance() + transferAmount == sourceOgBalance &&
	    	updatedDestinationAccount.getBalance() - transferAmount == destinationOgBalance) {
	        System.out.println("Conversion successful");
	    } else {
	        System.out.println("Conversion failed");
	    }
    }
    
}
