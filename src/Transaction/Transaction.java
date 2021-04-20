package Transaction;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import Account.*;
import Users.Banker;

public abstract class Transaction {
    private Account receiver;
    private String date, type;
    private float amount;

    public Transaction(Account receiver, String type, float amount) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        this.date = LocalDateTime.now().toString();
        this.receiver = receiver;
        this.type = type;
        this.amount = amount;
    }

    public Account getReceiver() { return this.receiver; }

    public void setReceiver(Account init) { this.receiver = init; }

    public String getDate() { return this.date;}

    public void setDate(String dat) { this.date = dat; }

    public void setAmount(float amount) { this.amount = amount; }

    public float getAmount() { return this.amount; }

    public String getType() { return this.type; }

    public void setType(String typ) { this.type = typ; }
}
