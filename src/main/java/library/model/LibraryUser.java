package library.model;

public class LibraryUser {
    private int userId;
    private String name;
    private String email;
    private String password;

    public LibraryUser(int userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean login(String password) {
        // simple equality check, real systems would hash & validate
        return this.password != null && this.password.equals(password);
    }

    // Getters & setters

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
