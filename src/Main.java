import java.util.*;

public class Main {
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        Service service = new Service();
        Boolean finish = false;

        while(!finish) {
            service.displayMenu();
            System.out.println("Enter your option here: ");
            String optionStr = input.nextLine().toString();
            try {
                Integer option = Integer.parseInt(optionStr);
                if (option > 14 ||  option < 0) {
                    System.out.println("\n\n\n\n\n\n\n\n");
                    System.out.println("Invalid option. Try again.");
                    System.out.println("Press Any Key To Continue...");
                    new java.util.Scanner(System.in).nextLine();
                }
                else if (option == 0){
                    finish = true;
                }
                else {
                    service.interpretOption(option);
                }
            }
            catch(NumberFormatException exc){
                System.out.println("\n\n\n\n\n\n\n\n");
                System.out.println("Invalid option. Try again.");
                System.out.println("Press Any Key To Continue...");
                new java.util.Scanner(System.in).nextLine();
            }
        }
    }
}
