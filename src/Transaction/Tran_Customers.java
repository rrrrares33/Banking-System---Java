package Transaction;

import Account.*;
import Users.Banker;

public class Tran_Customers extends Transaction {
    private Account sender;

    public Tran_Customers(Account receiver, Account sender, String type, float amount, Banker banker) {
        super(receiver, type, amount, banker);
        this.sender = sender;
    }

    public Account getSender() {
                return this.sender;
    }

    public void setSender(Account send) { this.sender = send; }

    @Override
    public String toString() {
        String text = this.getDate() + "\n";
        text += "Banker: " + this.getBanker().getFirstName() + " " + this.getBanker().getSecondName() + "\n";
        text += sender.getIBAN() + " sent to " + this.getReceiver().getIBAN()
                + " the amount of " + this.getAmount() + " " + sender.getCurrency() + ".\n";
        return text;
    }
}
