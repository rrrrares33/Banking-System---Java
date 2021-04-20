package Users;

import Users.Person;

public class Banker extends Person {
    private String password;

    public Banker(Integer userId, String name, String surname, String CNP, String phone_nr, String email, String address, String birthday, Integer age)
    {
        this.setID(userId);
        this.setFirstName(name);
        this.setSecondName(surname);
        this.setCNP(CNP);
        this.setPhone_nr(phone_nr);
        this.setEmail(email);
        this.setAddress(address);
        this.setBirthday(birthday);
        this.setAge(age);
    }

    @Override
    public String toString() {
        String text = "";
        text += "\n";
        text += this.getSecondName() + " " + this.getFirstName() + "\n";
        text += "PHONE_NUMBER:" + this.getPhone_nr() + "\n";
        return text;
    }

    public String getPassword() { return this.password; }

    public void setPassword(String pass) { this.password = pass; }
}
