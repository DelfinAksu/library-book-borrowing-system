package library.controller;
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
        if (user == null) return false;

        // find active borrowing record
        for (BorrowingRecord record : borrowingRecords) {
            if (record.getUser().equals(user)
                    && record.getBook().getBookId() == bookId
                    && "Active".equals(record.getStatus())) {

                record.markReturned(LocalDate.now());

                Book book = record.getBook();
                book.increaseAvailableCopies();
                return true;
            }
        }
        return false;
    }

    public List<BorrowingRecord> getActiveBorrowings(LibraryUser user) {
        return borrowingRecords.stream()
                .filter(r -> r.getUser().equals(user)
                        && "Active".equals(r.getStatus()))
                .collect(Collectors.toList());
    }

    public List<BorrowingRecord> getBorrowingHistory(LibraryUser user) {
        return borrowingRecords.stream()
                .filter(r -> r.getUser().equals(user))
                .collect(Collectors.toList());
    }
}
