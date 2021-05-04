package Users;

import Account.Account;
import Account.Credit_Acc;
import Account.Debit_Acc;
import Account.Saving_Acc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Customer_Singleton {

    private static Customer_Singleton single_instance = null;

    private List<Customer> customers;

    public static Customer_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Customer_Singleton();
        return single_instance;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void loadData(){
        //Check if there is any line at all in the file
        try(var in = new BufferedReader(new FileReader("data/customers.csv"))) {

            String line = in.readLine();
            customers = new ArrayList<>();
            Integer numberOfCustomers = Integer.parseInt(line);
            if (numberOfCustomers == 0) {
                // If there are no clients, I will stop the loading here.
                return;
            }
            if (numberOfCustomers > 0) {
                // If there is at least one client in the system
                for(int i = 0; i < numberOfCustomers; i++) {
                    System.out.println("DAAAAAAAAAAAAAAAAAAAAAAA");
                    line = in.readLine();
                    List<String> fields = Arrays.asList(line.split(","));
                    Customer aux = new Customer(Integer.parseInt(fields.get(0)),
                            fields.get(1), fields.get(2), fields.get(3),
                            fields.get(4), fields.get(5), fields.get(6),
                            fields.get(7), Integer.parseInt(fields.get(8)));

                    Integer numberOfAccountsOfTheUser = Integer.parseInt(fields.get(9));

                    if (numberOfAccountsOfTheUser > 0){
                        List<Account> accounts = new ArrayList<Account>();
                        for (int j = 0; j < numberOfAccountsOfTheUser; j++) {
                            line = in.readLine();
                            fields = Arrays.asList(line.split(","));

                            if (fields.get(0).equals("Debit")) {
                                Account auxAcc = new Debit_Acc("Debit", fields.get(4));
                                auxAcc.setIBAN(fields.get(1));
                                auxAcc.setBIC(fields.get(2));
                                auxAcc.setBalance(Float.parseFloat(fields.get(3)));
                                accounts.add(auxAcc);
                            }
                            else if (fields.get(0).equals("Credit")) {
                                Account auxAcc = new Credit_Acc("Credit", fields.get(4), Float.parseFloat(fields.get(6)));
                                auxAcc.setIBAN(fields.get(1));
                                auxAcc.setBIC(fields.get(2));
                                auxAcc.setBalance(Float.parseFloat(fields.get(3)));
                                ((Credit_Acc) auxAcc).setCredit(Float.parseFloat(fields.get(5)));
                                accounts.add(auxAcc);
                            }
                            else {
                                Account auxAcc = new Saving_Acc("Saving", fields.get(4), Float.parseFloat(fields.get(5)));
                                auxAcc.setIBAN(fields.get(1));
                                auxAcc.setBIC(fields.get(2));
                                auxAcc.setBalance(Float.parseFloat(fields.get(3)));
                                ((Saving_Acc) auxAcc).setWithdraw_approval(Boolean.parseBoolean(fields.get(6)));
                                accounts.add(auxAcc);
                            }
                        }
                        aux.setAccounts(accounts);
                    }

                    this.customers.add(aux);
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("!There are no banks saved in the system!");
        }
    }

    public void saveData(){
        try{
            var write = new FileWriter("data/customers.csv");

            write.write(customers.size() + "\n");
            for(int i = 0; i < customers.size(); i++){
                write.write(Integer.toString(customers.get(i).getID()) +
                        ',' + customers.get(i).getFirstName() +
                        ',' + customers.get(i).getSecondName() +
                        ',' + customers.get(i).getCNP() +
                        ',' + customers.get(i).getPhone_nr() +
                        ',' + customers.get(i).getEmail() +
                        ',' + customers.get(i).getAddress() +
                        ',' + customers.get(i).getBirthday() +
                        ',' + customers.get(i).getAge() +
                        ',' + customers.get(i).getAllAcc().size() +
                        '\n');

                for(int j = 0; j < customers.get(i).getAllAcc().size(); j++) {
                    if (customers.get(i).getAllAcc().get(j) instanceof Credit_Acc) {
                        write.write(customers.get(i).getAllAcc().get(j).getType()
                                + "," + customers.get(i).getAllAcc().get(j).getIBAN()
                                + "," + customers.get(i).getAllAcc().get(j).getBIC()
                                + "," + customers.get(i).getAllAcc().get(j).getBalance()
                                + "," + customers.get(i).getAllAcc().get(j).getCurrency()
                                + "," + ((Credit_Acc) customers.get(i).getAllAcc().get(j)).getCredit()
                                + "," + ((Credit_Acc) customers.get(i).getAllAcc().get(j)).getMaxCredit()
                                + "\n");
                    }
                    else if (customers.get(i).getAllAcc().get(j) instanceof Debit_Acc) {
                        write.write(customers.get(i).getAllAcc().get(j).getType()
                                + "," + customers.get(i).getAllAcc().get(j).getIBAN()
                                + "," + customers.get(i).getAllAcc().get(j).getBIC()
                                + "," + customers.get(i).getAllAcc().get(j).getBalance()
                                + "," + customers.get(i).getAllAcc().get(j).getCurrency()
                                + "\n");
                    }
                    else {
                        write.write(customers.get(i).getAllAcc().get(j).getType()
                            + "," + customers.get(i).getAllAcc().get(j).getIBAN()
                            + "," + customers.get(i).getAllAcc().get(j).getBIC()
                            + "," + customers.get(i).getAllAcc().get(j).getBalance()
                            + "," + customers.get(i).getAllAcc().get(j).getCurrency()
                            + "," + ((Saving_Acc) customers.get(i).getAllAcc().get(j)).getInterest_rate()
                            + "," + ((Saving_Acc) customers.get(i).getAllAcc().get(j)).getWithdraw_approval()
                            + "\n");
                    }
                }
            }

            write.close();
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}