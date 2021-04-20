import java.util.*;

public class Main {
    public static void main(String[] args)
    {
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
            String name = input.nextLine().toString();
            System.out.println("Please enter your surname:");
            String surname = input.nextLine().toString();
            System.out.println("Please enter your password:");
            String password = input.nextLine().toString();

            Service_Admin admins = new Service_Admin();

            boolean connected = admins.connection(name, surname, password);

            if (connected) {
                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n!!Conexiune realizată cu success!!!");
                admins.display_menu();
                String optionStr = input.nextLine().toString();
                Integer option = Integer.parseInt(optionStr);

                while (option != 1) {
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
                }

                System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

                while (!finish) {
                    Service_Customers service = new Service_Customers();
                    service.displayMenu();
                    System.out.println("Enter your option here: ");
                    optionStr = input.nextLine().toString();
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
            }
            else {
                System.out.println("This banker does not exist in the system.");
            }
        }
        else {
            System.out.println("Please contact a banker before accessing the system!");
        }
    }
}
