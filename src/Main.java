import Bank.Bank_Singleton;
import Transaction.Transaction_Singleton;
import Users.Banker_Singleton;
import Bank.Bank_Singleton;
import Users.Customer_Singleton;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException{

        System.out.println("\n\n\n\n!!!!!WELCOME TO BANK GHERASIM RARES SYSTEM!!!!!");
        System.out.println("Only Bankers can acces this system and make operations/transactions with customers data.");
        System.out.println("\nTo be able to acces the system you need to be a banker.");
        System.out.println("Are you a banker?");
        System.out.println("-----1)Yes------");
        System.out.println("-----2)No-------");

        Scanner input = new Scanner(System.in);
        Boolean finish = false;

        String bankOrNot = input.nextLine().toString();
        Integer bankOrNotInt = Integer.parseInt(bankOrNot);
        if (bankOrNotInt == 1) {
            Service_Audit.getInstance().sys_accessed();

            System.out.println("Please enter your name:");
            String admin_name = input.nextLine().toString();
            System.out.println("Please enter your surname:");
            String admin_surname = input.nextLine().toString();
            System.out.println("Please enter your password:");
            String admin_pass = input.nextLine().toString();

            //Banker Data Initialization
            Service_Admin admins = new Service_Admin();

            // Get Bankers from database
            Database.getDatabaseInstance().read_bankers();
            admins.setBankers(Database.getDatabaseInstance().getBankers());

            boolean connected = admins.connection(admin_name, admin_surname, admin_pass);
            if (connected) {
                Service_Audit.getInstance().add_command(admin_name, "Connected to the admin system");
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n!!Conexiune realizat?? cu success!!!");

                admins.display_menu();
                String optionStr = input.nextLine().toString();
                Integer option = Integer.parseInt(optionStr);
                Service_Audit.getInstance().getInstance().add_command(admin_name, "Banker administrative side option selected: " + Integer.toString(option));

                while (option != 1 && option != 0) {
                    try {
                        if (option > 4 || option < 1) {
                            System.out.println("\n\n\n\n\n\n\n\n");
                            System.out.println("Invalid option. Try again.");
                            System.out.println("Press Any Key To Continue...");
                            new java.util.Scanner(System.in).nextLine();
                        }  else {
                            admins.interpretOption(option);
                        }
                    } catch (NumberFormatException exc) {
                        System.out.println("\n\n\n\n\n\n\n\n");
                        System.out.println("Invalid option. Try again.");
                        System.out.println("Press Any Key To Continue...");
                        new java.util.Scanner(System.in).nextLine();
                    }

                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    admins.display_menu();
                    optionStr = input.nextLine().toString();
                    option = Integer.parseInt(optionStr);

                    Service_Audit.getInstance().getInstance().add_command(admin_name, "Banker administrative side option selected: " + Integer.toString(option));
                }

                //Saving Banker data (after this point, the data on this part can not be changed anymore without
                //                    restarting the program)
                //Banker_Singleton.getInstance().setBankers(admins.getBankers());
                //Banker_Singleton.getInstance().saveData();
                Database.getDatabaseInstance().setBankers(admins.getBankers());
                Database.getDatabaseInstance().save_bankers();

                if (option != 0) {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    Service_Customers service = new Service_Customers(
                            admins.connectedBanker(admin_name, admin_surname, admin_pass));

                    //Data initialization ==========================================================
                    // Bank_Singleton.getInstance().loadData();
                    Database.getDatabaseInstance().read_bank();
                    service.setBank(Database.getDatabaseInstance().getBank());

                    //Customer_Singleton.getInstance().loadData();
                    Database.getDatabaseInstance().read_customers();
                    service.setCustomers(Database.getDatabaseInstance().getCustomers());


                    //Transaction_Singleton.getInstance().loadData();
                    Database.getDatabaseInstance().read_transactions();
                    service.setTransaction_history(Database.getDatabaseInstance().getTransactions());
                    //===========================================================================

                    while (!finish) {
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        service.displayMenu();
                        System.out.println("Enter your option here: ");
                        optionStr = input.nextLine().toString();
                        Service_Audit.getInstance().getInstance().add_command(admin_name, "Banker customer side option selected: " + Integer.toString(option));
                        try {
                            option = Integer.parseInt(optionStr);
                            if (option > 14 || option < 0) {
                                System.out.println("\n\n\n\n\n\n\n\n");
                                System.out.println("Invalid option. Try again.");
                                System.out.println("Press Any Key To Continue...");
                                new java.util.Scanner(System.in).nextLine();
                            } else if (option == 0) {
                                finish = true;
                            } else {
                                service.interpretOption(option);
                            }
                        } catch (NumberFormatException exc) {
                            System.out.println("\n\n\n\n\n\n\n\n");
                            System.out.println("Invalid option. Try again.");
                            System.out.println("Press Any Key To Continue...");
                            new java.util.Scanner(System.in).nextLine();
                        }
                    }

                    //Data saving========================================================================
                    //Bank_Singleton.getInstance().setBank(service.getBank());
                    //Bank_Singleton.getInstance().saveData();
                    Database.getDatabaseInstance().setBank(service.getBank());
                    Database.getDatabaseInstance().save_bank();

                    //Customer_Singleton.getInstance().setCustomers(service.getCustomers());
                    //Customer_Singleton.getInstance().saveData();
                    Database.getDatabaseInstance().setCustomers(service.getCustomers());
                    Database.getDatabaseInstance().save_customers_and_accounts();

                    //Transaction_Singleton.getInstance().setTransactions(service.getTransaction_history());
                    //Transaction_Singleton.getInstance().saveData();
                    Database.getDatabaseInstance().setTransactions(service.getTransaction_history());
                    Database.getDatabaseInstance().save_transactions();
                    //====================================================================================
                }

                Service_Audit.getInstance().add_command(admin_name, "Banker closed the system.");
            }
            else {
                System.out.println("This banker does not exist in the system.");
                Service_Audit.getInstance().add_command("unknown", "Missed entry attempt.");
            }
        }
        else {
            System.out.println("Please contact a banker before accessing the system!");
            Service_Audit.getInstance().add_command("unknown", "Missed entry attempt.");
        }
    }
}
