package Account;

public class Saving_Acc extends Account{
    // The benefit of the Savings Account is an interest rate that rises your deposits each month.
    private float interest_rate;
    // In order to withdraw money from a Savings Account you need bank approval.
    private Boolean withdraw_approval;

    public Saving_Acc(String type, String currency, String IBAN, String BIC, float interest_rate) {
        super("Saving", currency, IBAN, BIC);
        this.interest_rate = interest_rate;
        this.withdraw_approval = false;
    }

    public void withdraw(float amount) {
        if (withdraw_approval) {
            if (this.getBalance() > amount){
                System.out.println("The banker should give you " + amount + " " + this.getCurrency());
                this.setBalance(this.getBalance() - amount);
            }
            else
            {
                System.out.println("You do not have enough money in your account.");
            }
        }
        else {
            System.out.println("You do not have permission to withdraw money from this account.");
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
        text += "Interest rate:" + this.interest_rate + "  Withdraw approval:";
        if (this.withdraw_approval)
            text += "Yes\n";
        else
            text += "No\n";
        text += "\n\n";
        return text;
    }
}
