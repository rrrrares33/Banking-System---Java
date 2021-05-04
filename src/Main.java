import Bank.Bank_Singleton;
import Users.Banker_Singleton;
import Bank.Bank_Singleton;
import Users.Customer_Singleton;

import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Service_Audit audit = new Service_Audit();

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

            System.out.println("Please enter your name:");
            String admin_name = input.nextLine().toString();
            System.out.println("Please enter your surname:");
            String admin_surname = input.nextLine().toString();
            System.out.println("Please enter your password:");
            String admin_pass = input.nextLine().toString();

            //Banker Data Initialization
            Service_Admin admins = new Service_Admin();
            Banker_Singleton.getInstance().loadData();
            admins.setBankers(Banker_Singleton.getInstance().getBankers());

            boolean connected = admins.connection(admin_name, admin_surname, admin_pass);
            if (connected) {
                audit.add_command(admin_name, "Connected to the admin system");
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n!!Conexiune realizată cu success!!!");

                admins.display_menu();
                String optionStr = input.nextLine().toString();
                Integer option = Integer.parseInt(optionStr);
                audit.add_command(admin_name, "Banker administrative side option selected: " + Integer.toString(option));

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

                    audit.add_command(admin_name, "Banker administrative side option selected: " + Integer.toString(option));
                }

                //Saving Banker data (after this point, the data on this part can not be changed anymore without
                //                    restarting the program)
                Banker_Singleton.getInstance().setBankers(admins.getBankers());
                Banker_Singleton.getInstance().saveData();

                if (option != 0) {
                    System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                    Service_Customers service = new Service_Customers(
                            admins.connectedBanker(admin_name, admin_surname, admin_pass));

                    //Data initialization ==========================================================
                    Bank_Singleton.getInstance().loadData();
                    service.setBank(Bank_Singleton.getInstance().getBank());
                    Customer_Singleton.getInstance().loadData();
                    service.setCustomers(Customer_Singleton.getInstance().getCustomers());

                    while (!finish) {
                        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
                        service.displayMenu();
                        System.out.println("Enter your option here: ");
                        optionStr = input.nextLine().toString();
                        audit.add_command(admin_name, "Banker customer side option selected: " + Integer.toString(option));
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

                    //Data saving=============================================================================
                    Bank_Singleton.getInstance().setBank(service.getBank());
                    Bank_Singleton.getInstance().saveData();
                    Customer_Singleton.getInstance().setCustomers(service.getCustomers());
                    Customer_Singleton.getInstance().saveData();
                }

                audit.add_command(admin_name, "Banker closed the system.");
            }
            else {
                System.out.println("This banker does not exist in the system.");
                audit.add_command("unknown", "Missed entry attempt.");
            }
        }
        else {
            System.out.println("Please contact a banker before accessing the system!");
            audit.add_command("unknown", "Missed entry attempt.");
        }
    }
}
