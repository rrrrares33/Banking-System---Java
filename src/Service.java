import Bank.*;
import Customer.*;

import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class Service {
    private final List<Customer> customers = new ArrayList<>();
    private final Bank Bank = new Bank("Banca Comerciala Romana", "BCR", "+4072421421", "bcr@contact.ro");

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
        System.out.println("10. Make a transaction between a customer and the bank.");
        System.out.println("11. Display all account of a customer.");
        System.out.println("12. Display all customers.");
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
                    // deleteAccount();
                    break;
                }
                case 7: break;
                case 8: break;
                case 9: break;
                case 10: break;
                case 11: {
                    displayAllAccounts();
                    break;
                }
                case 12: {
                    showAllCustomers();
                    break;
                }
                case 13: {
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

    public boolean uniqueCNP(String CNP){
        for(int i = 0; i < customers.size(); i++){
            if ((customers.get(i).getCNP().equals(CNP))){
                System.out.println("====================================");
                return false;
            }
        }
        return true;
    }

    public void newCustomer(){

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

    public void deleteCustomer() {

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

    public void showAllCustomers() {
        List<Customer> Customs = customers;
        System.out.println("\n\n\n\n\n\n\n\n");
        for(int i = 0; i < Customs.size(); i++){
            System.out.println(Customs.get(i));
        }
        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    public void newBranch() {
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

    public void addAccoutToCustomer() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Add account =========");
        System.out.println("To add a new account to the system you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

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

        if (pos != -1) {
            System.out.println("Which type of account do you want to create? \n 1 - Debit \n 2 - Credit \n 3 - Savings");
            String type = input.nextLine().toString();
            int typeInt = Integer.parseInt(type);
            System.out.println("Which currency do you want your account to be in? (first three letters)");
            String currency = input.nextLine().toString();
            switch (typeInt) {
                case(1): {
                    customers.get(pos).addAccountDebit("Debit", currency,
                            "DEBROBCR" + customers.get(pos).getCNP(),
                            "DRNCB" + customers.get(pos).getID() + customers.get(pos).getAge());
                    System.out.println("Account created with success.");
                    break;
                }
                case(2): {
                    customers.get(pos).addAccountCredit("Credit", currency,
                            "CRBROBCR" + customers.get(pos).getCNP(),
                            "CRNCB" + customers.get(pos).getID() + customers.get(pos).getAge(),
                            5000);
                    System.out.println("Account created with success.");
                    break;
                }
                case(3): {
                    customers.get(pos).addAccountSavings("Saving", currency,
                            "SVBROBCR" + customers.get(pos).getCNP(),
                            "SRNCB" + customers.get(pos).getID() + customers.get(pos).getAge(),
                            (float) 0.03);
                    System.out.println("Account created with success.");
                    break;
                }
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();

    }

    public void displayAllAccounts() {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Display Acc=========");
        System.out.println("To display all accounts of a person you first need to provide the holder CNP. \nCNP: ");
        String CNP = input.nextLine().toString();

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

        if (pos != -1) {
            for(int i = 0 ; i < customers.get(pos).getAccNr(); i++) {
                System.out.println(customers.get(pos).getAcc(i));
            }
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }
}
