package Bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private String name, prefix, contact, email;
    // I will use this Map to memorize the locations of the bank branches. (city - addresses)
    private Map<String, List<String>> locations;

    public Bank(String name, String prefix, String contact, String email) {
        this.name = name;
        this.prefix = prefix;
        this.contact = contact;
        this.email = email;
        this.locations = new HashMap<>();
    }

    @Override
    public String toString() {
        String text = "\n\n" + this.prefix + "  " + this.name + "\n";
        text += "Contact Info: " + contact + "  " + email + "\n";
        text += "Branches locations: " + "\n";
        for (var x: locations.keySet()){
            text += "---City:" + x + "\n";
            try {
                for (var y: locations.get(x)) {
                    text += y;
                    text += "\n";
                }
                text += "\n";
            }
            catch(NullPointerException e) {
                text += "\n";
            }
            text += "\n";
        }
        text += "\n";
        return text;
    }

    public void setName(String name){
        this.name = name;
    }

    public void prefix(String prefix){
        this.prefix = prefix;
    }

    public void contact(String contact){
        this.contact = contact;
    }

    public void email(String email){
        this.email = email;
    }

    public String getName() { return this.name; }

    public String getPrefix() { return this.prefix; }

    public String getContact() { return this.contact; }

    public String getEmail() { return this.email; }

    public Map getLocations() { return this.locations; }

    public void setLocations(Map locs) { this.locations = locs; }

    public void addCity(String city) {
        if(locations.containsKey(city)){
            System.out.println("This city already exists in our System.");
            System.out.println("Press Any Key To Continue...");
            new java.util.Scanner(System.in).nextLine();
        }
        else{
            locations.put(city, null);
        }
    }

    public void addAdress(String city, String address){
        if(!locations.containsKey(city)) {
            System.out.println("This city does not exist in our System. Consider adding the city first.");
        }
        else{
            if (locations.get(city) != null) {
                List<String> values = locations.get(city);
                values.add(address);
                locations.put(city, values);
            }
            else {
                List<String> values = new ArrayList<>();
                values.add(address);
                locations.put(city, values);
            }
        }
    }
}
