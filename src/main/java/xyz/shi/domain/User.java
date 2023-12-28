package xyz.shi.domain;

public class User {
    private int id;
    private String name;
    private String password;
    public int getId() {
        return id;
    }
    public  User () {

    }
    public User(int id,String name, String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password=" + password +
                '}';
    }

}