package Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Account.*;
import Users.Banker;

public abstract class Transaction {
    private int id;
    private Account receiver;
    private String date, type;
    private float amount;

    final DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Transaction(int id, Account receiver, String type, float amount) {
        this.id = id;
        this.date = form.format(LocalDateTime.now()).toString();
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

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id;}
}
