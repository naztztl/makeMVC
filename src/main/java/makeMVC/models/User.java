package makeMVC.models;

public class User {
    public Integer id;
    public String username;
    public String password;
    public UserRole role;

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
