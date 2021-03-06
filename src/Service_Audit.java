import Users.Banker_Singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Service_Audit {

    final DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static Service_Audit single_instance = null;

    public static Service_Audit getInstance()
    {
        if (single_instance == null)
            single_instance = new Service_Audit();
        return single_instance;
    }

    public void sys_accessed() {
        try {
            var write = new FileWriter("data/audit.csv", true);
            write.write("\n" + "System accessed at: " +
                    form.format(LocalDateTime.now()) +
                    ", " + ", " + ", " + "thread_name" + "\n");
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
                    form.format(LocalDateTime.now()) + ',' + Thread.currentThread().getName() + "\n");
            write.close();
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
