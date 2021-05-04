package Users;

import Account.*;

import java.util.*;

public class Customer extends Person{
    // Tip - Account
    private List<Account> accounts = new ArrayList<>();

    public Customer(Integer userId, String name, String surname, String CNP, String phone_nr, String email, String address, String birthday, Integer age) {
        this.setID(userId);
        this.setFirstName(name);
        this.setSecondName(surname);
        this.setCNP(CNP);
        this.setPhone_nr(phone_nr);
        this.setEmail(email);
        this.setAddress(address);
        this.setBirthday(birthday);
        this.setAge(age);
    }

    @Override
    public String toString() {
        String text = "";
        text += "\n\n";
        text += "ID: " + this.getID() + "\n";
        text += this.getSecondName() + " " + this.getFirstName() + "\n";
        text += "AGE: " + this.getAge() + " " + "BIRTHDAY:" + this.getBirthday() + "\n";
        text += "CNP: " + this.getCNP() + "\n";
        text += "PHONE_NUMBER:" + this.getPhone_nr() + " " + "\nEMAIL: " + this.getEmail() + "\n";
        text += "ADDRESS: " + this.getAddress() + "\n";
        return text;
    }

    public void addAccountDebit(String type, String currency) {
        if (type == "Debit") {
            Account aux = new Debit_Acc(type, currency);
            this.accounts.add(aux);
        }
    }

    public void addAccountCredit(String type, String currency, float maxCredit) {
        if (type == "Credit") {
            Account aux = new Credit_Acc(type, currency, maxCredit);
            this.accounts.add(aux);
        }
    }

    public void addAccountSavings(String type, String currency, float interest_rate) {
        if (type == "Saving") {
            Account aux = new Saving_Acc(type, currency, interest_rate);
            this.accounts.add(aux);
        }
    }

    public int getAccNr() { return this.accounts.size(); }

    public Account getAcc(int i) { return this.accounts.get(i); }

    public List<Account> getAllAcc() { return this.accounts; }

    public void setAccounts(List<Account> acc) {this.accounts = acc; }

    public void deleteAcc(int i) {
        this.accounts.remove(i);
    }

    public int searchIBAN(String IBAN) {
        for(int i = 0 ; i < this.accounts.size(); i++) {
            if (this.accounts.get(i).getIBAN().equals(IBAN)){
                return i;
            }
        }
        System.out.println("This IBAN does not exist in our system.");
        return -1;
    }
}
