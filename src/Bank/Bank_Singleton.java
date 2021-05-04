package Bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Bank_Singleton {

    private static Bank_Singleton single_instance = null;

    private Bank bank;

    public static Bank_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Bank_Singleton();
        return single_instance;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void loadData(){

        List<String[]> dataCols = new ArrayList<>();

        //Check if there is any line at all in the file
        try(var in = new BufferedReader(new FileReader("data/banks.csv"))) {

            String line = in.readLine() ;

            if (line == null) {
                bank = new Bank("Banca Comerciala Romana", "BCR", "+4072421421", "bcr@contact.ro");
                return;
            }

            String[] firstLine = line.split(",");
            bank = new Bank(firstLine[0], firstLine[1], firstLine[2], firstLine[3]);

            line = in.readLine() ;
            //While we still have lines to read from the file. (cities and locations in this case)
            Map<String, List<String>> locs = new HashMap<>();
            while(line != null ){
                List<String> fields = Arrays.asList(line.split(","));

                List<String> addresses = new ArrayList<>();

                for(int i = 1; i < fields.size(); i++) {
                    addresses.add(fields.get(i));
                }

                locs.put(fields.get(0), addresses);
                line = in.readLine();
            }

            bank.setLocations(locs);
        }
        catch (Exception e)
        {
            System.out.println("!There are no banks saved in the system!");
        }
    }

    public void saveData(){
        try{
            var write = new FileWriter("data/banks.csv");
            write.write(bank.getName() + "," + bank.getPrefix() + "," + bank.getContact()
                    + "," + bank.getEmail() +"\n");

            for(var city : bank.getLocations().keySet()){
                write.write((String) city);
                for(var loc : (List<String>) bank.getLocations().get(city)) {
                    write.write("," + loc);
                }
                write.write("\n");
            }

            write.close();
        }catch (Exception e){
            System.out.println(bank.getLocations().keySet());
            System.out.println(e.toString());
        }
    }
}
