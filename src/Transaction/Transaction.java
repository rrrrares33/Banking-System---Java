package Transaction;

import java.time.LocalDateTime;
import Account.*;
import Users.Banker;

public abstract class Transaction {
    private Account receiver;
    private String date, type;
    private float amount;
    // The person how made the transfer though the system.
    private Banker banker;

    public Transaction(Account receiver, String type, float amount, Banker banker) {
        this.date = LocalDateTime.now().toString();
        this.receiver = receiver;
        this.type = type;
        this.amount = amount;
        this.banker = banker;
    }

    public Account getReceiver() { return this.receiver; }

    public void setReceiver(Account init) { this.receiver = init; }

    public String getDate() { return this.date;}

    public void setDate(String dat) { this.date = dat; }

    public void setAmount(float amount) { this.amount = amount; }

    public float getAmount() { return this.amount; }

    public String getType() { return this.type; }

    public void setType(String typ) { this.type = typ; }

    public void setBanker(Banker banker) { this.banker = banker; }

    public Banker getBanker() { return this.banker; }
}
