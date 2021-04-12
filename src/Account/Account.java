package Account;

public abstract class Account {
    private String type, currency, IBAN, BIC;
    private float balance;

    Account(String type, String currency, String BIC) {
        IBAN_Factory new_IBAN = new IBAN_Factory();
        this.IBAN = new_IBAN.get_IBAN();
        this.type = type;
        this.currency = currency;
        this.BIC = BIC;
        this.balance = 0;
    }

    Account(String type, String currency, String IBAN, String BIC) {
        this.type = type;
        this.currency = currency;
        this.IBAN = IBAN;
        this.BIC = BIC;
        this.balance = 0;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setCurrency(String Curr) {
        this.currency = Curr;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getIBAN(){
        return this.IBAN;
    }

    public String getBIC(){
        return this.BIC;
    }

    public void setBIC(String BIC){
        this.BIC = BIC;
    }

    public float getBalance() { return this.balance; }

    public void setBalance(float balance) { this.balance = balance; }

    abstract public void withdraw(float amount);

    abstract public void deposit(float amount);
}
