import Account.Account;
import Bank.*;
import Users.*;
import Transaction.*;

import java.util.*;
import java.util.List;
import java.util.Set;

public class Service_Customers {
    private final List<Customer> customers;
    private final Bank Bank;
    private final Set<Transaction> transaction_history;

    public Service_Customers() {
        customers = new ArrayList<>();
        Bank = new Bank("Banca Comerciala Romana", "BCR", "+4072421421", "bcr@contact.ro");
        transaction_history = new HashSet<>();
    }

    public void displayMenu() {
        System.out.println("==============Menu===================");
        System.out.println("0. Close system.");
        System.out.println("1. Create a new customer.");
        System.out.println("2. Delete a customer.");
        System.out.println("3. Show bank details.");
        System.out.println("4. Add a new location or address for a bank branch.");
        System.out.println("5. Add an account to a customer.");
        System.out.println("6. Delete an account from an customer.");
        System.out.println("7. Withdraw money from an account.");
        System.out.println("8. Add money to an account.");
        System.out.println("9. Make a transaction between two customers.");
        System.out.println("10. Make a credit.");
        System.out.println("11. Display all account of a customer.");
        System.out.println("12. Display all customers.");
        System.out.println("13. Display all transactions ever made.");
        System.out.println("=====================================");

        /*
        customers.add(new Customer(0, "Gherasim", "Rares", "5001104080011", "0746018999",
                "rrares33@yahoo.com","Prahova, Sinaia, etc" ,"2000-11-04", 20));
        customers.add(new Customer(1, "Dima", "Matei", "5001219080241", "0244234999",
                "dimasebastian@yahoo.com","Bucuresti, Bucuresti, etc" ,"1985-11-04", 35));
        customers.add(new Customer(2, "Voinea", "Roberta Maria", "6001212298811", "0743523239",
                "voinearoberta@yahoo.com","Ilfov, Popesti-Leordeni, etc" ,"2000-12-12", 20));
        */
    }

    public void interpretOption(Integer option) {
        try {
            switch(option){
                case 1: {
                    newCustomer();
                    break;
                }
                case 2: {
                    deleteCustomer();
                    break;
                }
                case 3: {
                    System.out.println(Bank);
                    System.out.println("Press Any Key To Continue...");
                    new java.util.Scanner(System.in).nextLine();
                    break;
                }
                case 4: {
                    newBranch();
                    break;
                }
                case 5: {
                    addAccoutToCustomer();
                    break;
                }
                case 6: {
                    deleteAccount();
                    break;
                }
                case 7: {
                    withdrawMoney();
                    break;
                }
                case 8: {
                    depositMoneyToAccount();
                    break;
                }
                case 9: {
                    makeATransactionBetweenTwoCust();
                    break;
                }
                case 10: {
                    makeACredit();
                    break;
                }
                case 11: {
                    displayAllAccounts();
                    break;
                }
                case 12: {
                    showAllCustomers();
                    break;
                }
                case 13: {
                    showAllTransactions();
                    break;
                }
                case 14: {
                    //For testing.
                    customers.add(new Customer(0, "Gherasim", "Rares", "5001104080011", "0746018999",
                            "rrares33@yahoo.com","Prahova, Sinaia, etc" ,"2000-11-04", 20));
                    customers.add(new Customer(1, "Dima", "Matei", "5001219080241", "0244234999",
                            "dimasebastian@yahoo.com","Bucuresti, Bucuresti, etc" ,"1985-11-04", 35));
                    customers.add(new Customer(2, "Voinea", "Roberta Maria", "6001212298811", "0743523239",
                            "voinearoberta@yahoo.com","Ilfov, Popesti-Leordeni, etc" ,"2000-12-12", 20));
                    break;
                }
            }
        }
        catch (Exception exp) {
            System.out.println(exp.toString());
        }
    }

    private boolean uniqueCNP(String CNP){
        for(int i = 0; i < customers.size(); i++){
            if ((customers.get(i).getCNP().equals(CNP))){
                System.out.println("====================================");
                return false;
            }
        }
        return true;
    }

    private int searchCNP(String CNP) {
        int pos = -1;
        if (uniqueCNP(CNP)) {
            System.out.println("There is no client with this CNP in our system.");
        }
        else {
            pos = 0;
            while (!(customers.get(pos).getCNP().equals(CNP))) {
                pos = pos + 1;
            }
        }
        return pos;
    }

    private void newCustomer(){

        Collections.sort(customers, (c1, c2) -> {
            return c1.getID() - c2.getID();
        });

        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======New customer===========");
        System.out.println("CNP: ");
        String CNP = input.nextLine().toString();
        boolean uniqueCNPP = this.uniqueCNP(CNP);

        if (uniqueCNPP) {
            System.out.println("Name: ");
            String name = input.nextLine().toString();
            System.out.println("Surname: ");
            String surname = input.nextLine().toString();
            System.out.println("Email: ");
            String email = input.nextLine().toString();
            System.out.println("Phone: ");
            String phone = input.nextLine().toString();
            System.out.println("Birtday(YYYY-MM-DD): ");
            String Birthday = input.nextLine().toString();
            System.out.println("Address: ");
            String address = input.nextLine().toString();
            System.out.println("Age: ");
            String age = input.nextLine().toString();

            Customer newCust;
            if (customers.size() == 0){
                newCust = new Customer(0, name, surname, CNP, phone, email, address, Birthday, Integer.parseInt(age));
            }else {
                newCust = new Customer(customers.get(customers.size() - 1).getID() + 1, name, surname, CNP, phone, email, address, Birthday, Integer.parseInt(age));
            }
            customers.add(newCust);
        }
        else {
            System.out.println("This CNP already exists in our system.");
            System.out.println("Press Any Key To Continue...");
            new java.util.Scanner(System.in).nextLine();
        }
    }

    private void deleteCustomer() {

        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Delete customer=========");
        System.out.println("To delete a client from the system, you will need to provide his CNP to us. \nCNP: ");
        String CNP = input.nextLine().toString();

        if (uniqueCNP(CNP)) {
            System.out.println("There is no client with this CNP in our system.");
        }
        else {
            int pos = 0;
            while (!(customers.get(pos).getCNP().equals(CNP))) {
                System.out.println("+++++++++++++++++++++++++++++++++++");
                pos = pos + 1;
            }
            customers.remove(pos);
            System.out.println("Customer with the given CNP was deleted.");
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void showAllCustomers() {
        List<Customer> Customs = customers;
        System.out.println("\n\n\n\n\n\n\n\n");
        for(int i = 0; i < Customs.size(); i++){
            System.out.println(Customs.get(i));
        }
        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void newBranch() {
        System.out.println("\n\n\n\n\n\n");
        System.out.println("Do you want to add a new city or a new address in a city already registered?");
        System.out.println("1.New city\n2.New address.");
        Scanner input = new Scanner(System.in);
        String optionStr = input.nextLine().toString();

        try {
            Integer option = Integer.parseInt(optionStr);
            if(option != 1 && option != 2) {
                System.out.println("Invalid option entered.");
            }
            else if (option == 1){
                System.out.println("Please enter the city you want to add to the system.");
                String city = input.nextLine().toString();
                Bank.addCity(city);
            }
            else {
                System.out.println("Please enter the city where you want to add the address.");
                String city = input.nextLine().toString();
                System.out.println("Please enter the address.");
                String address = input.nextLine().toString();
                Bank.addAdress(city, address);
            }
        }
        catch (NumberFormatException e){
            System.out.println("Invalid option entered.");
        }
        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void addAccoutToCustomer() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Add account =========");
        System.out.println("To add a new account to the system you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

        int pos = this.searchCNP(CNP);

        if (pos != -1) {
            System.out.println("Which type of account do you want to create? \n 1 - Debit \n 2 - Credit \n 3 - Savings");
            String type = input.nextLine().toString();
            int typeInt = Integer.parseInt(type);
            System.out.println("Which currency do you want your account to be in? (first three letters)");
            String currency = input.nextLine().toString();
            switch (typeInt) {
                case(1): {
                    customers.get(pos).addAccountDebit("Debit", currency);
                    System.out.println("Account created with success.");
                    break;
                }
                case(2): {
                    customers.get(pos).addAccountCredit("Credit", currency,
                            5000);
                    System.out.println("Account created with success.");
                    break;
                }
                case(3): {
                    customers.get(pos).addAccountSavings("Saving", currency,
                            (float) 0.03);
                    System.out.println("Account created with success.");
                    break;
                }
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();

    }

    private void displayAllAccounts() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Display Acc=========");
        System.out.println("To display all accounts of a person you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

        int pos = this.searchCNP(CNP);

        if (pos != -1) {
            System.out.println("\nThis cutomer has " + this.customers.get(pos).getAccNr() + " accounts.");
            for(int i = 0 ; i < customers.get(pos).getAccNr(); i++) {
                System.out.println("==========================");
                System.out.println(customers.get(pos).getAcc(i));
                System.out.println("==========================");
            }
        }

        System.out.println("\n\n\n");
        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void deleteAccount() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Delete account =========");
        System.out.println("To delete an account from the system you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

        int pos = this.searchCNP(CNP);

        if (pos != -1) {
            System.out.println("Which is the IBAN of the account that you want to delete?. \nIBAN: ");
            String IBAN = input.nextLine().toString();
            boolean found = false;
            for (int i = 0 ; i < this.customers.get(pos).getAccNr(); i++) {
                if (this.customers.get(pos).getAcc(i).getIBAN().equals(IBAN)) {
                    found = true;
                    this.customers.get(pos).deleteAcc(i);
                    break;
                }
            }
            if (found) {
                System.out.println("The account with the give IBAN has been deleted!");
            }
            else {
                System.out.println("IBAN is not valid.");
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void depositMoneyToAccount() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Add money to account =========");
        System.out.println("To add money to an account from the system you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

        int pos = this.searchCNP(CNP);

        if (pos != -1) {
            float amount = 0;
            System.out.println("Which sum do you want to add to the account?. \nSUM: ");
            String sum = input.nextLine().toString();
            amount = Float.parseFloat(sum);

            System.out.println("Which is the IBAN of the account that you want to add money to?. \nIBAN: ");
            String IBAN = input.nextLine().toString();

            int posIBAN = this.customers.get(pos).searchIBAN(IBAN);

            if (posIBAN != -1) {
                this.customers.get(pos).getAcc(posIBAN).deposit(amount);
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void withdrawMoney() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Withdraw money=========");
        System.out.println("To withdraw money from an account from the system you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

        int pos = this.searchCNP(CNP);

        if (pos != -1) {
            float amount = 0;
            System.out.println("Which sum do you want to add to withdraw?. \nSUM: ");
            String sum = input.nextLine().toString();
            amount = Float.parseFloat(sum);

            System.out.println("Which is the IBAN of the account that you want to withdraw money from?. \nIBAN: ");
            String IBAN = input.nextLine().toString();

            int posIBAN = this.customers.get(pos).searchIBAN(IBAN);

            if (posIBAN != -1) {
                this.customers.get(pos).getAcc(posIBAN).withdraw(amount);
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void makeATransactionBetweenTwoCust() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Transaction between two customers=========");
        System.out.println("Direct transfers can only be made from Debit accounts.");
        System.out.println("To transfer money between two accounts you need both accounts holders CNPs. \nSender CNP: ");
        String CNP_1 = input.nextLine().toString();
        System.out.println("Receiver CNP: ");
        String CNP_2 = input.nextLine().toString();

        int pos1 = this.searchCNP(CNP_1), pos2 = this.searchCNP(CNP_2);

        if (pos1 == -1 || pos2 == -1)
        {
            System.out.println("One of the CNP you have entered is not in our system. Please try again.");
        }
        else
        {
            System.out.println("We will also need the accounts IBANs. \nSender IBAN: ");
            String IBAN_1 = input.nextLine().toString();
            System.out.println("Receiver IBAN: ");
            String IBAN_2 = input.nextLine().toString();

            Customer sender = this.customers.get(pos1), receiver = this.customers.get(pos2);
            Account acc_sender = sender.getAcc(sender.searchIBAN(IBAN_1));
            Account acc_receiver = receiver.getAcc(receiver.searchIBAN(IBAN_2));

            // Direct transfers can only be made from Debit accounts
            if ((acc_sender.getType().equals(acc_receiver.getType()) && acc_sender.getType().equals("Debit")) ||
                    acc_sender.getCurrency().equals(acc_receiver.getCurrency())) {
                System.out.println("Amount to transfer: ");
                String amount = input.nextLine().toString();

                if (acc_sender.getBalance() > Float.parseFloat(amount)) {
                    Transaction aux = new Tran_Customers(acc_receiver, acc_sender, "Customers", Float.parseFloat(amount));
                    acc_sender.setBalance(acc_sender.getBalance() - Float.parseFloat(amount));
                    acc_receiver.setBalance(acc_receiver.getBalance() + Float.parseFloat(amount));
                    this.transaction_history.add(aux);
                }
                else
                {
                    System.out.println("You do not have enough money in the account.");
                }

            }
            else {
                System.out.println("Accounts are not eligible for transfer.");
            }

        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void makeACredit() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Make a credit=========");
        System.out.println("Direct transfers can only be made from Debit accounts.");
        System.out.println("To request a credit we will need your CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

        int pos = this.searchCNP(CNP);

        if (pos != -1) {
            System.out.println("To request a credit you will need to provide a DEBIT account to us where we" +
                    "will transfer the credit money. \nIBAN: ");
            String IBAN = input.nextLine().toString();

            int pos_acc = this.customers.get(pos).searchIBAN(IBAN);

            if (pos_acc != -1 && this.customers.get(pos).getAcc(pos_acc).getType().equals("Debit")) {
                System.out.println("What amount of money do you need? ( < 50000 ).");
                String amoun = input.nextLine().toString();
                float amount = Float.parseFloat(amoun);
                Transaction aux = new Tran_Bank_Credit(this.customers.get(pos).getAcc(pos_acc), "Credit", amount);

                transaction_history.add(aux);
                this.customers.get(pos).getAcc(pos_acc).setBalance(this.customers.get(pos).getAcc(pos_acc).getBalance() + amount);
                System.out.println("The credit has been processed and the money added to your account.");
            }
            else {
                System.out.println("The account you have entered does not meed the requirements.");
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    private void showAllTransactions() {
        System.out.println("\n\n\nTransaction history:");
        for(Transaction x : transaction_history) {
            System.out.println(("++++++++++++++++++++"));
            System.out.println(x + "++++++++++++++++++++");
        }
        System.out.println("\n\n\nPress Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }
}
