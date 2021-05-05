package Transaction;

import Account.*;
import Users.Banker;

public class Tran_Customers extends Transaction {
    private Account sender;

    public Tran_Customers(int id, Account receiver, Account sender, String type, float amount) {
        super(id, receiver, type, amount);
        this.sender = sender;
    }

    public Account getSender() {
                return this.sender;
    }

    public void setSender(Account send) { this.sender = send; }

    @Override
    public String toString() {
        String text = this.getDate() + "\n";
        text += sender.getIBAN() + " sent to " + this.getReceiver().getIBAN()
                + " the amount of " + this.getAmount() + " " + sender.getCurrency() + ".\n";
        return text;
    }
}
