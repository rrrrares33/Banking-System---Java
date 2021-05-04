import Bank.Bank;
import Users.Banker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Service_Admin {
    private List<Banker> bankers;

    public Service_Admin() {
        this.bankers = new ArrayList<>();
    }

    public void setBankers(List<Banker> bankers) { this.bankers = bankers; }

    public List<Banker> getBankers() { return this.bankers; }

    public void display_menu() {
        System.out.println("==============Menu===================");
        System.out.println("0. Close system and save changes.");
        System.out.println("1. Enter customers system.");
        System.out.println("2. Create a new banker");
        System.out.println("3. Delete a banker.");
        System.out.println("4. Display all bankers.");
        System.out.println("=====================================");
    }

    public void interpretOption(Integer option) {
        try {
            switch (option) {
                case 1: {
                    break;
                }
                case 2: {
                    String bankPass = "12345678";
                    addNewBanker(bankPass);
                    break;
                }
                case 3: {
                    String bankPass = "87654321";
                    deleteABanker(bankPass);
                    break;
                }
                case 4: {
                    displayAllBankers();
                    break;
                }
            }
        } catch (Exception exp) {
            System.out.println(exp.toString());
        }
    }

    public boolean connection(String name, String surname, String password) {
        boolean connected = false;
        for (int i = 0 ; i < bankers.size(); i++) {
            if (name.compareTo(bankers.get(i).getFirstName()) == 0 && surname.compareTo(bankers.get(i).getSecondName()) == 0
             && password.compareTo(bankers.get(i).getPassword()) == 0) {
                connected = true;
            }
        }
        return connected;
    }

    public Banker connectedBanker(String name, String surname, String password) {
        for (int i = 0 ; i < bankers.size(); i++) {
            if (name.compareTo(bankers.get(i).getFirstName()) == 0 && surname.compareTo(bankers.get(i).getSecondName()) == 0
                    && password.compareTo(bankers.get(i).getPassword()) == 0) {
                return bankers.get(i);
            }
        }
        return null;
    }

    public void addNewBanker(String systemPassword) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("To be able to add a new banker you need to enter the system password:");
        Scanner input = new Scanner(System.in);
        String sysPass = input.nextLine().toString();

        if (sysPass.compareTo(systemPassword) == 0) {

            Collections.sort(bankers, (c1, c2) -> {
                return c1.getID() - c2.getID();
            });

            System.out.println("\n\n\n\n=======New banker===========");
            System.out.println("CNP: ");
            String CNP = input.nextLine().toString();
            System.out.println("Name:");
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
            System.out.println("Password: ");
            String password = input.nextLine().toString();


            Banker newBanker = new Banker(bankers.get(bankers.size() - 1).getID(), name, surname, CNP, phone, email, address, Birthday, Integer.parseInt(age));
            newBanker.setPassword(password);

            bankers.add(newBanker);
        } else {
            System.out.println("The password you have entered is wrong.");
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }

    public void deleteABanker(String systemPassword) {
        System.out.println("\n\n\n\n\n\n\n\n");
        Scanner input = new Scanner(System.in);
        System.out.println("\n\n\n\n=======Delete Admin=========");
        System.out.println("To delete an admin from the system, you will need to provide the system password first.\n");
        String sysPass = input.nextLine().toString();

        if (sysPass.compareTo(systemPassword) == 0) {
            int pos = -1;

            System.out.println("Please enter the admin CNP:");
            String CNP = input.nextLine().toString();
            System.out.println("Please enter the admin password:");
            String passowrd = input.nextLine().toString();

            for (int i = 0 ; i < bankers.size(); i++) {
                if (bankers.get(i).getCNP().compareTo(CNP) == 0 &&
                        bankers.get(i).getPassword().compareTo(passowrd) == 0) {
                    pos = i;
                    break;
                }
            }

            if (pos != -1) {
                System.out.println("The banker has been succesfully deleted!");
                bankers.remove(pos);
            }
            else {
                System.out.println("There was a problem and we could not delete the banker. (maybe CNP or Pass were wrong");
            }
        }
        else {
            System.out.println("System password is wrong. Try again.");
        }


        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();

    }

    public void displayAllBankers() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("=============================");
        for(int i=0 ; i < bankers.size(); i++) {
            System.out.println("Name: " + bankers.get(i).getFirstName());
            System.out.println("Surnameame: " + bankers.get(i).getSecondName());
            System.out.println("=============================");
        }

        System.out.println("Press Any Key To Continue...");
        new java.util.Scanner(System.in).nextLine();
    }
}
