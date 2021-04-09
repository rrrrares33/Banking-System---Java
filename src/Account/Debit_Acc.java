package Account;

import java.util.Scanner;

public class Debit_Acc extends Account{
    public Debit_Acc(String type, String currency, String IBAN, String BIC) {
        super("Debit", currency, IBAN, BIC);
    }

    public void withdraw(float amount) {
        if (this.getBalance() > amount){
            System.out.println("The banker should give you " + amount + " " + this.getCurrency());
            this.setBalance(this.getBalance() - amount);
        }
        else
        {
            System.out.println("You do not have enough money in your account.");
        }
        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    public void deposit(float amount){
        System.out.println("\n" + amount + this.getCurrency() + "has been added to "+ this.getIBAN() + " account");
    }

    @Override
    public String toString() {
        String text = this.getType() + " " + this.getIBAN() + " " + this.getBIC() + "\n";
        text += this.getBalance() + this.getCurrency() + "\n";
        text += "\n\n";
        return text;
    }
}
