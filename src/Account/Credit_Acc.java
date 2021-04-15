package Account;

public class Credit_Acc extends Account{
    private float maxCredit;
    private float Credit;

    public Credit_Acc(String type, String currency, float maxCredit) {
        super("Credit", currency);
        this.maxCredit = maxCredit;
        this.Credit = 0;
    }

    public void withdraw(float amount) {
        if (this.getBalance() > amount){
            System.out.println("The banker should give you " + amount + " " + this.getCurrency());
            this.setBalance(this.getBalance() - amount);
        }
        else
        {
            if (this.Credit + amount <= this.maxCredit ) {
                System.out.println("The banker should give you " + amount + " " + this.getCurrency());
                this.Credit += amount;
                System.out.println("!!!! Watch out !!!! You are on Credit with " + this.Credit + " amount. " +
                        "You need to pay it until the end of next month.");
            }
            else
            {
                System.out.println("You can not make a credit this big. " +
                        "You can credit at most" + (this.maxCredit - this.Credit) + " .");
            }
        }
    }

    public void deposit(float amount){
        System.out.println("\n" + amount + this.getCurrency() + " has been added to "+ this.getIBAN() + " account");
        if (this.Credit > 0) {
            if (this.Credit > amount) {
                this.Credit -= amount;
            }
            else {
                amount -= this.Credit;
                this.Credit = 0;
                this.setBalance(this.getBalance() + amount);
            }
        }
        else {
            this.setBalance(this.getBalance() + amount);
        }
    }

    @Override
    public String toString() {
        String text = this.getType() + " " + this.getIBAN() + " " + this.getBIC() + "\n";
        text += this.getBalance() + this.getCurrency() + "\n";
        text += "Credit made:" + this.Credit + "  Max Credit:" + this.maxCredit;
        return text;
    }
}
