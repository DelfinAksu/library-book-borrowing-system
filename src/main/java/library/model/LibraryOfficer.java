package library.model;

public class LibraryOfficer extends LibraryUser {
    private int staffId;

    public LibraryOfficer(int userId, String name, String email,
                          String password, int staffId) {
        super(userId, name, email, password);
        this.staffId = staffId;
    }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
}
