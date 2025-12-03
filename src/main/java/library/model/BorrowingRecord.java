package library.model;
import java.time.LocalDate;

public class BorrowingRecord {
    private int recordId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String status; // "Active", "Returned"

    private LibraryUser user;
    private Book book;

    public BorrowingRecord(int recordId, LibraryUser user, Book book,
                           LocalDate borrowDate) {
        this.recordId = recordId;
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.status = "Active";
    }

    public void markReturned(LocalDate date) {
        this.returnDate = date;
        this.status = "Returned";
    }

    // Getters & setters

    public int getRecordId() { return recordId; }
    public void setRecordId(int recordId) { this.recordId = recordId; }

    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LibraryUser getUser() { return user; }
    public void setUser(LibraryUser user) { this.user = user; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
