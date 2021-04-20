package Transaction;

import Account.Account;
import Users.Banker;

public class Tran_Bank_Credit extends Transaction{

    public Tran_Bank_Credit(Account receiver, String type, float amount, Banker banker) {
        super(receiver, type, amount, banker);
    }

    @Override
    public String toString() {
        String text = this.getDate() + "\n";
        text += "Banker: " + this.getBanker().getFirstName() + " " + this.getBanker().getSecondName() + "\n";
        text += this.getReceiver().getIBAN() + " has credited from the bank the amount of "
                + this.getAmount() + " " + this.getReceiver().getCurrency() + ".\n";
        return text;
    }
}
