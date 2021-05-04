package Users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Bank_Singleton {

    private static Bank_Singleton single_instance = null;

    private List<Banker> bankers = new ArrayList<>();

    public static Bank_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Bank_Singleton();
        return single_instance;
    }

    public void setBankers(List<Banker> bankers) {
        this.bankers = bankers;
    }

    public List<Banker> getBankers() {
        return bankers;
    }

    private static List<String[]> getCols(String nameOfFile){

        List<String[]> dataCols = new ArrayList<>();

        //Check if there is any line at all in the file
        try(var in = new BufferedReader(new FileReader(nameOfFile))) {

            String line = in.readLine() ;
            //While we still have lines to read from the file.
            while(line != null ){
                String[] fields = line.split(",");
                dataCols.add(fields);
                line = in.readLine();
            }

        }
        catch (Exception e)
        {
            System.out.println("!There are no bankers saved in the system!");
        }

        return dataCols;
    }

    public void loadData() {
        try{
            List <String[]> lines = Bank_Singleton.getCols("data/bankers.csv");
            for(var line : lines){
                var newBanker = new Banker(
                        Integer.parseInt(line[0]),
                        line[1], //name
                        line[2], //surname
                        line[3], // CNP
                        line[4], // phone nr
                        line[5], // email
                        line[6], // address
                        line[7], // birthday
                        Integer.parseInt(line[8]) // age
                );
                newBanker.setPassword(line[9]);
                bankers.add(newBanker);
            }
        }catch (Exception e){
            System.out.println("Exception encountered.");
        }

    }

    public void saveData(){
        try{
            var write = new FileWriter("data/bankers.csv");
            for(int i = 0; i < bankers.size(); i++){
                write.write(Integer.toString(bankers.get(i).getID()) +
                        ',' + bankers.get(i).getFirstName() +
                        ',' + bankers.get(i).getSecondName() +
                        ',' + bankers.get(i).getCNP() +
                        ',' + bankers.get(i).getPhone_nr() +
                        ',' + bankers.get(i).getEmail() +
                        ',' + bankers.get(i).getAddress() +
                        ',' + bankers.get(i).getBirthday() +
                        ',' + bankers.get(i).getAge() +
                        ',' + bankers.get(i).getPassword() +
                        '\n');
            }
            write.close();
        }catch (Exception e){
            System.out.println("Exception encountered.");
        }
    }
}
