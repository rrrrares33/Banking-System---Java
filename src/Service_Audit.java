import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Service_Audit {

    final DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Service_Audit() {
        try {
            var write = new FileWriter("data/audit.csv", true);
            write.write("\n" + "System accessed at: " +
                    form.format(LocalDateTime.now()) + "\n");
            write.close();
        }
        catch (Exception e) {
            System.out.println("Exception");
        }
    }

    public void add_command(String banker, String action){
        try {
            var write = new FileWriter("data/audit.csv", true);
            write.write(banker + "," + action + "," +
                    form.format(LocalDateTime.now()) + "\n");
            write.close();
        }
        catch (Exception e) {
            System.out.println("Exception");
        }
    }
}
