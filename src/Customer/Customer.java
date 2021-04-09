package Customer;

import Account.*;

import java.util.*;

public class Customer {
    private Integer id, age;
    private String name, surname, CNP, phone_nr, email, address;
    private String birthday;
    // Tip - Account
    private List<Account> accounts = new ArrayList<>();

    public Customer(Integer userId, String name, String surname, String CNP, String phone_nr, String email, String address, String birthday, Integer age) {
        this.id = userId;
        this.name = name;
        this.surname = surname;
        this.CNP = CNP;
        this.phone_nr = phone_nr;
        this.email = email;
        this.address = address;
        this.birthday = birthday;
        this.age = age;
    }

    public String getFirstName() {
        return this.name;
    }

    public void setFirstName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return this.surname;
    }

    public void setSecondName(String surname) {
        this.surname = surname;
    }

    public Integer getID() {
        return this.id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCNP() {
        return this.CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getPhone_nr() {
        return this.phone_nr;
    }

    public void setPhone_nr(String nr) {
        this.phone_nr = nr;
    }

    public String getEmail() {
        return this.email;
    }

    public void getEmail(String mail) {
        this.email = mail;
    }

    public String getAddress() {
        return this.address;
    }

    public void getAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void getBirthday(String date) {
        this.birthday = date;
    }

    @Override
    public String toString() {
        String text = "";
        text += "\n\n";
        text += "ID: " + this.id + "\n";
        text += this.name + " " + this.surname + "\n";
        text += "AGE: " + this.age + " " + "BIRTHDAY:" + this.birthday + "\n";
        text += "CNP: " + this.CNP + "\n";
        text += "PHONE_NUMBER:" + this.phone_nr + " " + "\nEMAIL: " + this.email + "\n";
        text += "ADDRESS: " + this.address + "\n";
        return text;
    }

    public void addAccountDebit(String type, String currency, String IBAN, String BIC) {
        if (type == "Debit") {
            Account aux = new Debit_Acc(type, currency, IBAN, BIC);
            accounts.add(aux);
        }
    }

    public void addAccountCredit(String type, String currency, String IBAN, String BIC, float maxCredit) {
        if (type == "Credit") {
            Account aux = new Credit_Acc(type, currency, IBAN, BIC, maxCredit);
            accounts.add(aux);
        }
    }

    public void addAccountSavings(String type, String currency, String IBAN, String BIC, float interest_rate) {
        if (type == "Saving") {
            Account aux = new Saving_Acc(type, currency, IBAN, BIC, interest_rate);
            accounts.add(aux);
        }
    }

    public int getAccNr() { return accounts.size(); }

    public Account getAcc(int i) { return accounts.get(i); }
}
