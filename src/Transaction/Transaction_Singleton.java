package Transaction;

import Account.Account;
import Account.Credit_Acc;
import Account.Debit_Acc;
import Account.Saving_Acc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transaction_Singleton {

    private static Transaction_Singleton single_instance = null;

    private Set<Transaction> transactions;

    public static Transaction_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Transaction_Singleton();
        return single_instance;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }


    public void loadData(){
        //Check if there is any line at all in the file
        try(var in = new BufferedReader(new FileReader("data/transaction_history.csv"))) {

            String line = in.readLine();
            transactions = new HashSet<>();

            if (line == null) {
                return;
            }

            Integer numberOfTransactionsMade = Integer.parseInt(line);

            if (numberOfTransactionsMade > 0)
            {
                for(int i = 0; i < numberOfTransactionsMade; i++)
                {
                    line = in.readLine();
                    List<String> fieldsTransData = Arrays.asList(line.split(","));
                    line = in.readLine();
                    List<String> fieldsReceiver = Arrays.asList(line.split(","));

                    Account receiver = new Debit_Acc("Debit", fieldsReceiver.get(4));
                    receiver.setIBAN(fieldsReceiver.get(1));
                    receiver.setBIC(fieldsReceiver.get(2));
                    receiver.setBalance(Float.parseFloat(fieldsReceiver.get(3)));

                    if (fieldsTransData.get(1).equals("Customers")) {
                        line = in.readLine();
                        List<String> fieldsSender = Arrays.asList(line.split(","));

                        Account sender = new Debit_Acc("Debit", fieldsSender.get(4));
                        sender.setIBAN(fieldsSender.get(1));
                        sender.setBIC(fieldsSender.get(2));
                        sender.setBalance(Float.parseFloat(fieldsSender.get(3)));

                        Transaction aux = new Tran_Customers(Integer.parseInt(fieldsTransData.get(0)),
                                                            receiver, sender,
                                                            fieldsTransData.get(1),
                                                            Float.parseFloat(fieldsTransData.get(2)));
                        aux.setDate(fieldsTransData.get(3));

                        transactions.add(aux);
                    }

                    if (fieldsTransData.get(1).equals("Credit")) {

                        Transaction aux = new Tran_Bank_Credit(Integer.parseInt(fieldsTransData.get(0)),
                                receiver,
                                fieldsTransData.get(1),
                                Float.parseFloat(fieldsTransData.get(2)));
                        aux.setDate(fieldsTransData.get(3));

                        transactions.add(aux);
                    }
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
            var write = new FileWriter("data/transaction_history.csv");

            write.write(transactions.size() + "\n");

            for(var x : transactions) {
                write.write(x.getId() + "," + x.getType() + "," + x.getAmount() + "," + x.getDate() + "\n");

                if (x.getReceiver() instanceof Credit_Acc) {
                    write.write(x.getReceiver().getType()
                            + "," + x.getReceiver().getIBAN()
                            + "," + x.getReceiver().getBIC()
                            + "," + x.getReceiver().getBalance()
                            + "," + x.getReceiver().getCurrency()
                            + "," + ((Credit_Acc) x.getReceiver()).getCredit()
                            + "," + ((Credit_Acc) x.getReceiver()).getMaxCredit()
                            + "\n");
                }
                else if (x.getReceiver() instanceof Debit_Acc) {
                    write.write(x.getReceiver().getType()
                            + "," + x.getReceiver().getIBAN()
                            + "," + x.getReceiver().getBIC()
                            + "," + x.getReceiver().getBalance()
                            + "," + x.getReceiver().getCurrency()
                            + "\n");
                }
                else {
                    write.write(x.getReceiver().getType()
                            + "," + x.getReceiver().getIBAN()
                            + "," + x.getReceiver().getBIC()
                            + "," + x.getReceiver().getBalance()
                            + "," + x.getReceiver().getCurrency()
                            + "," + ((Saving_Acc) x.getReceiver()).getInterest_rate()
                            + "," + ((Saving_Acc) x.getReceiver()).getWithdraw_approval()
                            + "\n");
                }

                if (x instanceof Tran_Customers) {
                    Account aux = ((Tran_Customers) x).getSender();

                    if (aux instanceof Credit_Acc) {
                        write.write(aux.getType()
                                + "," + aux.getIBAN()
                                + "," + aux.getBIC()
                                + "," + aux.getBalance()
                                + "," + aux.getCurrency()
                                + "," + ((Credit_Acc) aux).getCredit()
                                + "," + ((Credit_Acc) aux).getMaxCredit()
                                + "\n");
                    }
                    else if (aux instanceof Debit_Acc) {
                        write.write(aux.getType()
                                + "," + aux.getIBAN()
                                + "," + aux.getBIC()
                                + "," + aux.getBalance()
                                + "," + aux.getCurrency()
                                + "\n");
                    }
                    else {
                        write.write(aux.getType()
                                + "," + aux.getIBAN()
                                + "," + aux.getBIC()
                                + "," + aux.getBalance()
                                + "," + aux.getCurrency()
                                + "," + ((Saving_Acc) aux).getInterest_rate()
                                + "," + ((Saving_Acc) aux).getWithdraw_approval()
                                + "\n");
                    }
                }
            }

            write.close();
        }catch (Exception e){
            System.out.println(e.toString());
        }
    }
}