package library.controller;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import library.model.*;

public class ReturnBookController {
    private Catalog catalog;
    private List<BorrowingRecord> borrowingRecords;

    public ReturnBookController(Catalog catalog, List<BorrowingRecord> borrowingRecords) {
        this.catalog = catalog;
        this.borrowingRecords = borrowingRecords;
    }

    public boolean returnBook(LibraryUser user, int bookId) {
        int targetUserId = user.getUserId();

        for (BorrowingRecord r : borrowingRecords) {
            boolean sameUser = r.getUser() != null && r.getUser().getUserId() == targetUserId;
            boolean sameBook = r.getBook() != null && r.getBook().getBookId() == bookId;
            boolean isActive = "Active".equalsIgnoreCase(r.getStatus());

            if (sameUser && sameBook && isActive) {
                r.setStatus("Returned");
                r.setReturnDate(LocalDate.now());
                r.getBook().increaseAvailableCopies();
                return true;
            }
        }
        return false;
    }

    public List<BorrowingRecord> getActiveBorrowings(LibraryUser user) {
        List<BorrowingRecord> result = new ArrayList<>();
        int targetUserId = user.getUserId();

        for (BorrowingRecord r : borrowingRecords) {
            boolean sameUser = r.getUser() != null && r.getUser().getUserId() == targetUserId;
            boolean isActive = "Active".equalsIgnoreCase(r.getStatus());
            if (sameUser && isActive) {
                result.add(r);
            }
        }
        return result;
    }

    public List<BorrowingRecord> getBorrowingHistory(LibraryUser user) {
        List<BorrowingRecord> result = new ArrayList<>();
        int targetUserId = user.getUserId();

        for (BorrowingRecord r : borrowingRecords) {
            boolean sameUser = r.getUser() != null && r.getUser().getUserId() == targetUserId;
            if (sameUser) {
                result.add(r);
            }
        }
        return result;
    }
}
