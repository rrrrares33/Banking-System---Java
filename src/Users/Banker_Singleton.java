package Users;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Banker_Singleton {

    private static Banker_Singleton single_instance = null;

    private List<Banker> bankers = new ArrayList<>();

    public static Banker_Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Banker_Singleton();
        return single_instance;
    }

    public void setBankers(List<Banker> bankers) {
        this.bankers = bankers;
    }

    public List<Banker> getBankers() {
        return bankers;
    }

    public void loadData(){
        //Check if there is any line at all in the file
        try(var in = new BufferedReader(new FileReader("data/bankers.csv"))) {

            String line = in.readLine();
            bankers = new ArrayList<>();

            if (line == null) {
                // If there are no clients, I will stop the loading here.
                return;
            }

            while(line != null) {
                    List<String> fields = Arrays.asList(line.split(","));

                    Banker aux = new Banker(Integer.parseInt(fields.get(0)),
                            fields.get(1), fields.get(2), fields.get(3),
                            fields.get(4), fields.get(5), fields.get(6),
                            fields.get(7), Integer.parseInt(fields.get(8)));

                    aux.setPassword(fields.get(9));

                    this.bankers.add(aux);

                    line = in.readLine();
            }
        }
        catch (Exception e)
        {
            System.out.println("!There are no banks saved in the system!");
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
