import Account.*;
import Bank.*;
import Transaction.*;
import Users.*;

import javax.swing.text.html.parser.Parser;
import java.sql.*;
import java.util.*;

public class Database implements AutoCloseable{
    private static Database db = null;
    private final Connection connection;

    private List<Banker> bankers;
    private Bank bank;
    private List<Customer> customers;
    private Set<Transaction> transactions;

    public Database() throws SQLException {
        String  url = "jdbc:mysql://localhost:3306/bankingappdb?autoReconnect=true&useSSL=false";
        String user = "root";
        String password = "rootpassword";
        connection = DriverManager.getConnection(url, user, password);
        create_tables_and_insert_info();
    }

    public static Database getDatabaseInstance() {
        if (db == null) {
            try {
                db = new Database();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return db;
    }

    // initialize tables in the database.
    private void create_tables_and_insert_info() throws SQLException {
        ResultSet tables = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"});

        List<String> tables_names = new ArrayList<>();

        // read table names from db and count them.
        Integer counter = 0;
        while (tables.next()) {
            counter += 1;
            tables_names.add(tables.getString("TABLE_NAME").toLowerCase());
        }

        if (counter != 6) {
            List<String> list_of_tables_names = new ArrayList<>();
            list_of_tables_names.add("bankers");
            list_of_tables_names.add("banks");
            list_of_tables_names.add("customers");
            list_of_tables_names.add("transaction_history");

            for (var x : list_of_tables_names) {
                Boolean contains = tables_names.contains(x);
                if (!contains) {
                    if (x.equals("bankers")) {
                        // Creating the table
                        try {
                            connection.createStatement().execute(
                                    "CREATE TABLE bankers ( " +
                                            "id varchar(30) primary key, " +
                                            "name varchar(30) not null, " +
                                            "surname varchar(30), " +
                                            "cnp varchar(25), " +
                                            "phonenr varchar(13), " +
                                            "email varchar(30), " +
                                            "address varchar(50), " +
                                            "birthday varchar(20), " +
                                            "age varchar(5), " +
                                            "password varchar(30));"
                            );
                        } catch (SQLSyntaxErrorException e) {
                            System.out.println(e.toString());
                        }

                        List<Banker> list_bankers = new ArrayList<>();
                        Banker_Singleton.getInstance().loadData();
                        list_bankers = Banker_Singleton.getInstance().getBankers();

                        // Adding bankers from CSV.
                        for (var aux : list_bankers) {
                            try {
                                connection.createStatement().execute(
                                        "INSERT INTO bankers " +
                                                "VALUES(" +
                                                '"' + aux.getID().toString() + '"' + "," +
                                                '"' + aux.getFirstName().toString() + '"' + "," +
                                                '"' + aux.getSecondName().toString() + '"' + "," +
                                                '"' + aux.getCNP().toString() + '"' + "," +
                                                '"' + aux.getPhone_nr().toString() + '"' + "," +
                                                '"' + aux.getEmail().toString() + '"' + "," +
                                                '"' + aux.getAddress().toString() + '"' + "," +
                                                '"' + aux.getBirthday().toString() + '"' + "," +
                                                '"' + aux.getAge().toString() + '"' + "," +
                                                '"' + aux.getPassword().toString() + '"' + ");"
                                );
                            } catch (SQLSyntaxErrorException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (x.equals("banks")) {
                        try {
                            connection.createStatement().execute(
                                    "CREATE TABLE banks (name varchar(30), prefix varchar(4), contact varchar(12), " +
                                            "email varchar(20), city varchar(10), address varchar(30) primary key)"
                            );
                        } catch (SQLSyntaxErrorException e) {
                            System.out.println(e.toString());
                        }

                        Bank_Singleton.getInstance().loadData();
                        Bank bank = Bank_Singleton.getInstance().getBank();

                        for (var aux : bank.getLocations().keySet()) {
                            for (var loc : (List<String>) bank.getLocations().get(aux)) {
                                try {
                                    connection.createStatement().execute(
                                            "INSERT INTO banks VALUES (" +
                                                    '"' + bank.getName() + '"' + "," +
                                                    '"' + bank.getPrefix() + '"' + "," +
                                                    '"' + bank.getContact() + '"' + "," +
                                                    '"' + bank.getEmail() + '"' + "," +
                                                    '"' + aux + '"' + "," +
                                                    '"' + loc + '"' +
                                                    ");"
                                    );
                                } catch (SQLSyntaxErrorException e) {
                                    System.out.println(e.toString());
                                }
                            }
                        }
                    }
                    if (x.equals("customers")) {
                        try {
                            connection.createStatement().execute(
                                    "CREATE TABLE customers (id varchar(30) primary key, " +
                                            "name varchar(40), " +
                                            "surname varchar(40), " +
                                            "cnp varchar(25), " +
                                            "phonenr varchar(20), " +
                                            "email varchar(30), " +
                                            "address varchar(50), " +
                                            "birthday varchar(10), " +
                                            "age varchar(3)," +
                                            "debit_card varchar(50)," +
                                            "credit_card varchar(50)," +
                                            "savings_card varchar(50))"
                            );

                            connection.createStatement().execute(
                                    "CREATE TABLE accounts (IBAN varchar(50) primary key," +
                                            "type varchar(10)," +
                                            "currecy varchar(4)," +
                                            "bic varchar(10)," +
                                            "balance varchar(20)," +
                                            "credit varchar(20)," +
                                            "max_credit varchar(20)," +
                                            "saving_interest_rate varchar(10)," +
                                            "withdraw_approval varchar(5)" +
                                            ")"
                            );
                        } catch (SQLSyntaxErrorException e) {
                            System.out.println(e.toString());
                        }

                        List<Customer> list_customers = new ArrayList<>();
                        Customer_Singleton.getInstance().loadData();
                        list_customers = Customer_Singleton.getInstance().getCustomers();

                        // Adding customers from CSV.
                        for (var aux : list_customers) {

                            Map<String, String> hm = new HashMap<String, String>();
                            for (var aux1 : aux.getAllAcc()) {
                                hm.put(aux1.getType(), aux1.getIBAN());
                            }

                            try {
                                // First I will add a cutomer
                                connection.createStatement().execute(
                                        "INSERT INTO customers " +
                                                "VALUES(" +
                                                '"' + aux.getID().toString() + '"' + "," +
                                                '"' + aux.getFirstName().toString() + '"' + "," +
                                                '"' + aux.getSecondName().toString() + '"' + "," +
                                                '"' + aux.getCNP().toString() + '"' + "," +
                                                '"' + aux.getPhone_nr().toString() + '"' + "," +
                                                '"' + aux.getEmail().toString() + '"' + "," +
                                                '"' + aux.getAddress().toString() + '"' + "," +
                                                '"' + aux.getBirthday().toString() + '"' + "," +
                                                '"' + aux.getAge().toString() + '"' + "," +
                                                '"' + hm.get("Debit") + '"' + "," +
                                                '"' + hm.get("Credit") + '"' + "," +
                                                '"' + hm.get("Saving") + '"' + ')'
                                );
                                // And then his/hers accounts.
                                for (var aux1 : aux.getAllAcc()) {
                                    if (aux1.getType().equals("Debit")) {
                                        connection.createStatement().execute(
                                                "INSERT INTO accounts " +
                                                        "VALUES (" +
                                                        '"' + aux1.getIBAN() + '"' + ',' +
                                                        '"' + aux1.getType() + '"' + ',' +
                                                        '"' + aux1.getCurrency() + '"' + ',' +
                                                        '"' + aux1.getBIC() + '"' + ',' +
                                                        '"' + aux1.getBalance() + '"' + ',' +
                                                        '"' + null + '"' + ',' +
                                                        '"' + null + '"' + ',' +
                                                        '"' + null + '"' + ',' +
                                                        '"' + null + '"' + ')'
                                        );
                                    }
                                    if (aux1.getType().equals("Credit")) {
                                        connection.createStatement().execute(
                                                "INSERT INTO accounts " +
                                                        "VALUES (" +
                                                        '"' + aux1.getIBAN() + '"' + ',' +
                                                        '"' + aux1.getType() + '"' + ',' +
                                                        '"' + aux1.getCurrency() + '"' + ',' +
                                                        '"' + aux1.getBIC() + '"' + ',' +
                                                        '"' + aux1.getBalance() + '"' + ',' +
                                                        '"' + ((Credit_Acc) aux1).getCredit() + '"' + ',' +
                                                        '"' + ((Credit_Acc) aux1).getMaxCredit() + '"' + ',' +
                                                        '"' + null + '"' + ',' +
                                                        '"' + null + '"' + ')'
                                        );
                                    }
                                    if (aux1.getType().equals("Saving")) {
                                        connection.createStatement().execute(
                                                "INSERT INTO accounts " +
                                                        "VALUES (" +
                                                        '"' + aux1.getIBAN() + '"' + ',' +
                                                        '"' + aux1.getType() + '"' + ',' +
                                                        '"' + aux1.getCurrency() + '"' + ',' +
                                                        '"' + aux1.getBIC() + '"' + ',' +
                                                        '"' + aux1.getBalance() + '"' + ',' +
                                                        '"' + null + '"' + ',' +
                                                        '"' + null + '"' + ',' +
                                                        '"' + ((Saving_Acc) aux1).getInterest_rate() + '"' + ',' +
                                                        '"' + ((Saving_Acc) aux1).getWithdraw_approval() + '"' + ')'
                                        );
                                    }
                                }
                            } catch (SQLSyntaxErrorException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                    }
                    if (x.equals("transaction_history")) {
                            // Sender is going to be BANK for credits.
                            try {
                                connection.createStatement().execute(
                                        "CREATE TABLE transaction_history (id varchar(12) primary key," +
                                                "type varchar(20), " +
                                                "ammount varchar(6)," +
                                                "date_of_trans varchar(20)," +
                                                "receiver varchar(50)," +
                                                "sender varchar(50))"
                                );
                            } catch (SQLSyntaxErrorException e) {
                                System.out.println(e.toString());
                            }

                            Transaction_Singleton.getInstance().loadData();
                            Set<Transaction> transactions =  Transaction_Singleton.getInstance().getTransactions();

                            for (var aux: transactions) {
                                if(aux.getType().equals("Customers")) {
                                    connection.createStatement().execute(
                                            "INSERT INTO transaction_history " +
                                                    "VALUES (" +
                                                    '"' + aux.getId() + '"' + ',' +
                                                    '"' + aux.getType() + '"' + ',' +
                                                    '"' + aux.getAmount() + '"' + ',' +
                                                    '"' + aux.getDate() + '"' + ',' +
                                                    '"' + aux.getReceiver().getIBAN() + '"' + ',' +
                                                    '"' + ((Tran_Customers) aux).getSender().getIBAN() + '"' +
                                                    ')'
                                    );
                                }
                                else {
                                    connection.createStatement().execute(
                                            "INSERT INTO transaction_history " +
                                                    "VALUES (" +
                                                    '"' + aux.getId() + '"' + ',' +
                                                    '"' + aux.getType() + '"' + ',' +
                                                    '"' + aux.getAmount() + '"' + ',' +
                                                    '"' + aux.getDate() + '"' + ',' +
                                                    '"' + aux.getReceiver().getIBAN() + '"' + ',' +
                                                    '"' + null + '"' +
                                                    ')'
                                    );
                                }
                            }
                     }
                }
            }
        }
    }

    // READ
    public void read_bankers() throws SQLException {
        this.bankers = new ArrayList();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM BANKERS");
        while (resultSet.next()) {
            Banker aux = new Banker(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2), resultSet.getString(3),
                    resultSet.getString(4), resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8), Integer.parseInt(resultSet.getString(9)));
            aux.setPassword(resultSet.getString(10));
            this.bankers.add(aux);
        }
    }

    public void read_bank() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM banks");
        String name ="", prefix="", contact="", email="";
        Map<String, List<String>> locations = new HashMap<>();
        while (resultSet.next()) {
            name = resultSet.getString(1);
            prefix = resultSet.getString(2);
            contact = resultSet.getString(3);
            email = resultSet.getString(4);
            if (locations.containsKey(resultSet.getString(5))) {
                List<String> aux = locations.get(resultSet.getString(5));
                aux.add(resultSet.getString(6));
                locations.remove(resultSet.getString(5));
                locations.put(resultSet.getString(5), aux);
            }
            else {
                List<String> aux = new ArrayList();
                aux.add(resultSet.getString(6));
                locations.remove(resultSet.getString(5));
                locations.put(resultSet.getString(5), aux);
            }
        }
        this.bank = new Bank(name, prefix, contact, email);
        this.bank.setLocations(locations);
    }

    public void read_customers() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM CUSTOMERS");
        this.customers = new ArrayList();

        while(resultSet.next()) {
            Customer customer = new Customer(Integer.parseInt(resultSet.getString(1)), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),
                    resultSet.getString(6), resultSet.getString(7), resultSet.getString(8),
                    Integer.parseInt(resultSet.getString(9)));

            String debit_IBAN = '"' + resultSet.getString(10) + '"';
            String credit_IBAN = '"' + resultSet.getString(11) + '"';
            String saving_IBAN = '"' + resultSet.getString(12) + '"';
            List<Account> accounts= new ArrayList();

            if (debit_IBAN != null) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT * FROM ACCOUNTS WHERE IBAN = " + debit_IBAN);
                Debit_Acc debit_acc = null;
                while(resultSet1.next()) {
                    debit_acc = new Debit_Acc(resultSet1.getString(2), resultSet1.getString(3));
                    debit_acc.setIBAN(debit_IBAN);
                    debit_acc.setBIC(resultSet1.getString(4));
                    debit_acc.setBalance(Float.parseFloat(resultSet1.getString(5)));
                }
                accounts.add(debit_acc);
            }
            if (credit_IBAN != null) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT * FROM ACCOUNTS WHERE IBAN = " + credit_IBAN);
                Credit_Acc credit_acc = null;
                while (resultSet1.next()) {
                    credit_acc = new Credit_Acc(resultSet1.getString(2), resultSet1.getString(3),
                            Float.parseFloat(resultSet1.getString(7)));
                    credit_acc.setIBAN(credit_IBAN);
                    credit_acc.setBIC(resultSet1.getString(4));
                    credit_acc.setBalance(Float.parseFloat(resultSet1.getString(5)));
                    credit_acc.setCredit(Float.parseFloat(resultSet1.getString(6)));
                }
                accounts.add(credit_acc);
            }
            if (saving_IBAN != null) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("Select * from accounts where IBAN = " + saving_IBAN);
                Saving_Acc saving_acc = null;
                while (resultSet1.next()) {
                    saving_acc = new Saving_Acc(resultSet1.getString(2), resultSet1.getString(3),
                            Float.parseFloat(resultSet1.getString(8)));
                    saving_acc.setIBAN(saving_IBAN);
                    saving_acc.setBIC(resultSet1.getString(4));
                    saving_acc.setBalance(Float.parseFloat(resultSet1.getString(5)));
                    saving_acc.setWithdraw_approval(Boolean.parseBoolean(resultSet1.getString(9)));
                }
                accounts.add(saving_acc);
            }

            customer.setAccounts(accounts);

            this.customers.add(customer);
        }

    }

    public void read_transactions() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM TRANSACTION_HISTORY");
        this.transactions = new HashSet<>();

        while (resultSet.next()) {
            if (resultSet.getString(2).equals("Customers")) {
                Debit_Acc sender = new Debit_Acc("Debit", "RON");
                Debit_Acc receiver = new Debit_Acc("Debit", "RON");

                for (var x : this.customers) {
                    if (x.getAllAcc().size() != 0) {
                        for (var y : x.getAllAcc()) {
                            if (y != null) {
                                if (y.getIBAN().equals(resultSet.getString(6))) {
                                    sender = new Debit_Acc("Debit", y.getCurrency());
                                    sender.setIBAN(y.getIBAN());
                                    sender.setBIC(y.getBIC());
                                }
                                if (y.getIBAN().equals(resultSet.getString(5))) {
                                    receiver = new Debit_Acc("Debit", y.getCurrency());
                                    receiver.setIBAN(y.getIBAN());
                                    receiver.setBIC(y.getBIC());
                                }
                            }
                        }
                    }

                }

                Tran_Customers tran_customers = new Tran_Customers(Integer.parseInt(resultSet.getString(1)), receiver, sender,
                        "Customers", Float.parseFloat(resultSet.getString(3)));
                tran_customers.setDate(resultSet.getString(4));
                tran_customers.setReceiver(receiver);
                tran_customers.setSender(sender);
                transactions.add(tran_customers);
            }
            else {
                Credit_Acc receiver = new Credit_Acc("Credit", "RON", 0);

                for (var x : this.customers) {
                    if (x.getAllAcc().size() != 0) {
                        for (var y : x.getAllAcc()) {
                            if (y != null) {
                                if (y.getIBAN().equals(resultSet.getString(5))) {
                                    receiver = new Credit_Acc("Credit", y.getCurrency(), ((Credit_Acc)y).getMaxCredit());
                                    receiver.setIBAN(y.getIBAN());
                                    receiver.setBIC(y.getBIC());
                                }
                            }
                        }
                    }
                }

                Tran_Bank_Credit tranBankCredit = new Tran_Bank_Credit(Integer.parseInt(resultSet.getString(1)), receiver,
                        "Credit", Float.parseFloat(resultSet.getString(3)));
                tranBankCredit.setDate(resultSet.getString(4));
                tranBankCredit.setReceiver(receiver);
                transactions.add(tranBankCredit);
            }
        }

    }
    // ============================================

    // SAVE (at the closing of the application)
    // First I add the new bankers and then I check if there is any banker that was deleted during the running of the app
    // If there was, I will delete them.
    public void save_bankers() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM BANKERS;");

        List<String> cnp_in_tabel = new ArrayList();
        while(resultSet.next()) {
            cnp_in_tabel.add(resultSet.getString(4));
        }

        List<String> cnp_in_tabel_and_app = new ArrayList();
        for(var aux : this.bankers) {
            if(!cnp_in_tabel.contains(aux.getCNP())) {
                connection.createStatement().execute(
                        "INSERT INTO BANKERS " +
                            "VALUES (" +
                                '"' + aux.getID().toString() + '"' + "," +
                                '"' + aux.getFirstName().toString() + '"' + "," +
                                '"' + aux.getSecondName().toString() + '"' + "," +
                                '"' + aux.getCNP().toString() + '"' + "," +
                                '"' + aux.getPhone_nr().toString() + '"' + "," +
                                '"' + aux.getEmail().toString() + '"' + "," +
                                '"' + aux.getAddress().toString() + '"' + "," +
                                '"' + aux.getBirthday().toString() + '"' + "," +
                                '"' + aux.getAge().toString() + '"' + "," +
                                '"' + aux.getPassword().toString() + '"' + ");"
                );
                cnp_in_tabel_and_app.add(aux.getCNP());

            }
            else {
                cnp_in_tabel_and_app.add(aux.getCNP());
            }
        }

        ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT * FROM BANKERS;");
        while(resultSet1.next()) {
            if (!cnp_in_tabel_and_app.contains(resultSet1.getString(4))) {
                connection.createStatement().execute(
                        "DELETE FROM BANKERS " +
                                "WHERE cnp = " +
                                '"' + resultSet1.getString(4) + '"'
                );
            }
        }
    }

    public void save_bank() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM BANKS;");

        Map<String, List<String>> locations = new HashMap();
        while(resultSet.next()) {
            if (locations.containsKey(resultSet.getString(5))){
                locations.get(resultSet.getString(5)).add(resultSet.getString(6));
            }
            else {
                List<String> addresses = new ArrayList();
                addresses.add(resultSet.getString(6));
                locations.put(resultSet.getString(5), addresses);
            }
        }

        // If a value appears in the app but not in the tables, I will add it to the table.
        Map<String, List<String>> locations_in_table_and_app = new HashMap();
        for (var aux : bank.getLocations().keySet()) {
            for (var loc : (List<String>) bank.getLocations().get(aux)) {
                Boolean does_contain = false;
                if (locations.containsKey(aux)){

                    for(var xyz : locations.get(aux)) {
                        if (xyz.equals(loc)) {
                            does_contain = true;
                            break;
                        }
                    }
                }

                if (!does_contain) {
                    try {
                        connection.createStatement().execute(
                                "INSERT INTO banks VALUES (" +
                                        '"' + bank.getName() + '"' + "," +
                                        '"' + bank.getPrefix() + '"' + "," +
                                        '"' + bank.getContact() + '"' + "," +
                                        '"' + bank.getEmail() + '"' + "," +
                                        '"' + aux + '"' + "," +
                                        '"' + loc + '"' +
                                        ");"
                        );
                    } catch (SQLSyntaxErrorException e) {
                        System.out.println(e.toString());
                    }

                    if(locations_in_table_and_app.containsKey(aux)) {
                        locations_in_table_and_app.get(aux).add(loc);
                    }
                    else {
                        List<String> addresses = new ArrayList();
                        addresses.add(loc);
                        locations_in_table_and_app.put((String)aux, addresses);
                    }
                }
                else {
                    if(locations_in_table_and_app.containsKey(aux)) {
                        locations_in_table_and_app.get(aux).add(loc);
                    }
                    else {
                        List<String> addresses = new ArrayList();
                        addresses.add(loc);
                        locations_in_table_and_app.put((String) aux, addresses);
                    }
                }
            }
        }


        // If there are fields that appear in the table, but not in the app, remove them:
        for (var aux : locations_in_table_and_app.keySet()) {
            for (var loc : (List<String>) locations_in_table_and_app.get(aux)) {
                Boolean does_contain = true;
                if (locations_in_table_and_app.keySet().contains(aux)){
                    if (!locations_in_table_and_app.get(aux).contains(loc)) {
                        does_contain = false;
                    }
                }
                else {
                    does_contain = false;
                }

                if (!does_contain) {
                    try{
                        connection.createStatement().execute(
                                "DELETE FROM BANKS " +
                                        "WHERE city = " + '"' + aux + '"' +  " and address =" +'"' + loc + '"' + ';'
                        );
                    }
                    catch (SQLException E){
                        System.out.println(E.getErrorCode());
                    }
                }
            }
        }

    }

    public void save_customers_and_accounts() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM CUSTOMERS;");

        List<String> cnp_in_tabel = new ArrayList();
        while(resultSet.next()) {
            cnp_in_tabel.add(resultSet.getString(4));
        }

        // First I add all of the new customers to the db
        List<String> cnp_in_tabel_and_app = new ArrayList();
        for(var aux : this.customers) {
            if(!cnp_in_tabel.contains(aux.getCNP())) {

                Map<String, String> hm = new HashMap<String, String>();
                for (var aux1 : aux.getAllAcc()) {
                    hm.put(aux1.getType(), aux1.getIBAN());
                }

                // Adding the customer
                connection.createStatement().execute(
                        "INSERT INTO CUSTOMERS " +
                                "VALUES (" +
                                '"' + aux.getID().toString() + '"' + "," +
                                '"' + aux.getFirstName().toString() + '"' + "," +
                                '"' + aux.getSecondName().toString() + '"' + "," +
                                '"' + aux.getCNP().toString() + '"' + "," +
                                '"' + aux.getPhone_nr().toString() + '"' + "," +
                                '"' + aux.getEmail().toString() + '"' + "," +
                                '"' + aux.getAddress().toString() + '"' + "," +
                                '"' + aux.getBirthday().toString() + '"' + "," +
                                '"' + aux.getAge().toString() + '"' + "," +
                                '"' + hm.get("Debit") + '"' + "," +
                                '"' + hm.get("Credit") + '"' + "," +
                                '"' + hm.get("Saving") + '"' + ");"
                );
                // And his/hers accounts
                for (var aux1 : aux.getAllAcc()) {
                    if (aux1.getType().equals("Debit")) {
                        connection.createStatement().execute(
                                "INSERT INTO accounts " +
                                        "VALUES (" +
                                        '"' + aux1.getIBAN() + '"' + ',' +
                                        '"' + aux1.getType() + '"' + ',' +
                                        '"' + aux1.getCurrency() + '"' + ',' +
                                        '"' + aux1.getBIC() + '"' + ',' +
                                        '"' + aux1.getBalance() + '"' + ',' +
                                        '"' + null + '"' + ',' +
                                        '"' + null + '"' + ',' +
                                        '"' + null + '"' + ',' +
                                        '"' + null + '"' + ')'
                        );
                    }
                    if (aux1.getType().equals("Credit")) {
                        connection.createStatement().execute(
                                "INSERT INTO accounts " +
                                        "VALUES (" +
                                        '"' + aux1.getIBAN() + '"' + ',' +
                                        '"' + aux1.getType() + '"' + ',' +
                                        '"' + aux1.getCurrency() + '"' + ',' +
                                        '"' + aux1.getBIC() + '"' + ',' +
                                        '"' + aux1.getBalance() + '"' + ',' +
                                        '"' + ((Credit_Acc) aux1).getCredit() + '"' + ',' +
                                        '"' + ((Credit_Acc) aux1).getMaxCredit() + '"' + ',' +
                                        '"' + null + '"' + ',' +
                                        '"' + null + '"' + ')'
                        );
                    }
                    if (aux1.getType().equals("Saving")) {
                        connection.createStatement().execute(
                                "INSERT INTO accounts " +
                                        "VALUES (" +
                                        '"' + aux1.getIBAN() + '"' + ',' +
                                        '"' + aux1.getType() + '"' + ',' +
                                        '"' + aux1.getCurrency() + '"' + ',' +
                                        '"' + aux1.getBIC() + '"' + ',' +
                                        '"' + aux1.getBalance() + '"' + ',' +
                                        '"' + null + '"' + ',' +
                                        '"' + null + '"' + ',' +
                                        '"' + ((Saving_Acc) aux1).getInterest_rate() + '"' + ',' +
                                        '"' + ((Saving_Acc) aux1).getWithdraw_approval() + '"' + ')'
                        );
                    }
                }

                cnp_in_tabel_and_app.add(aux.getCNP());
            }
            else {
                cnp_in_tabel_and_app.add(aux.getCNP());
            }
        }

        // Here I delete all of the customers and accounts that were deleted during app runtime.
        for(var aux : cnp_in_tabel) {
            if(!cnp_in_tabel_and_app.contains(aux)) {
                ResultSet resultSet1 = connection.createStatement().executeQuery("" +
                        "SELECT debit_card, credit_card, savings_card " +
                        "FROM customers " +
                        "WHERE CNP = " + '"'+ aux + '"');

                connection.createStatement().execute("" +
                        "DELETE FROM CUSTOMERS " +
                        "WHERE CNP = " + '"'+ aux + '"');

                /*
                for(int i = 1 ; i < 4; i++) {

                    if (!"null".equals(resultSet1.getObject(i).toString())) {

                        connection.createStatement().execute("" +
                                "DELETE FROM accounts " +
                                "WHERE IBAN = "+ '"'+ resultSet1.getString(i) + '"');
                    }
                }
                */
            }
        }
    }

    public void save_transactions() throws SQLException {
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM transaction_history;");

        List<Integer> id_in_tabel = new ArrayList();
        while(resultSet.next()) {
            id_in_tabel.add(Integer.parseInt(resultSet.getString(1)));
        }

        for (var aux : this.transactions) {
            if(!id_in_tabel.contains(aux.getId())) {
                System.out.println("Da");
                    if(aux.getType().equals("Customers")) {
                        connection.createStatement().execute(
                                "INSERT INTO transaction_history " +
                                        "VALUES (" +
                                        '"' + aux.getId() + '"' + ',' +
                                        '"' + aux.getType() + '"' + ',' +
                                        '"' + aux.getAmount() + '"' + ',' +
                                        '"' + aux.getDate() + '"' + ',' +
                                        '"' + aux.getReceiver().getIBAN() + '"' + ',' +
                                        '"' + ((Tran_Customers) aux).getSender().getIBAN() + '"' +
                                        ')'
                        );
                    }
                    else {
                        connection.createStatement().execute(
                                "INSERT INTO transaction_history " +
                                        "VALUES (" +
                                        '"' + aux.getId() + '"' + ',' +
                                        '"' + aux.getType() + '"' + ',' +
                                        '"' + aux.getAmount() + '"' + ',' +
                                        '"' + aux.getDate() + '"' + ',' +
                                        '"' + aux.getReceiver().getIBAN() + '"' + ',' +
                                        '"' + null + '"' +
                                        ')'
                        );
                }
            }
        }
    }
    // =============================================

    // GETTERS AND SETTERS
    public List<Banker> getBankers() { return this.bankers;}
    public void setBankers(List<Banker> x) {this.bankers = x;};
    public Bank getBank() {return this.bank;}
    public void setBank(Bank x) {this.bank = x;}
    public List<Customer> getCustomers() { return this.customers;}
    public void setCustomers(List<Customer> x) { this.customers = x;}
    public Set<Transaction> getTransactions() {return this.transactions;}
    public void setTransactions(Set<Transaction> x) {this.transactions = x;}
    //======================================================================

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
