package model;

import jakarta.persistence.*;
import converter.*;

@Entity
@Table(name = "account")
public class Account {
    
    @Id
    @Column(name = "account_number")
    private int number;
    
    @Convert(converter = BalanceConverter.class)
    private double balance;
    
    @Version
    private int version;

    public Account() {}

    public Account(int number, double balance) {
        this.number = number;
        this.balance = balance;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

}
