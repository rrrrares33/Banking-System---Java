package Transaction;

import Account.Account;

public class Tran_Bank_Credit extends Transaction{

    public Tran_Bank_Credit(Account receiver, String type, float amount) {
        super(receiver, type, amount);
    }

    @Override
    public String toString() {
        String text = this.getDate() + "\n";
        text += this.getReceiver().getIBAN() + " has credited from the bank the amount of "
                + this.getAmount() + " " + this.getReceiver().getCurrency() + ".\n";
        return text;
    }
}
