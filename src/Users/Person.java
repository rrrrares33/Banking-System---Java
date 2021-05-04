package Users;

public abstract class Person implements Human{
    private Integer id, age;
    private String name, surname, CNP, phone_nr, email, address;
    private String birthday;

    public String getFirstName() {
        return this.name;
    }

    public void setFirstName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return this.surname;
    }

    public void setSecondName(String surname) {
        this.surname = surname;
    }

    public Integer getID() {
        return this.id;
    }

    public void setID(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCNP() {
        return this.CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getPhone_nr() {
        return this.phone_nr;
    }

    public void setPhone_nr(String nr) {
        this.phone_nr = nr;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String date) {
        this.birthday = date;
    }
}
