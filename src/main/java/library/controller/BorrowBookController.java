package library.controller;
import java.time.LocalDate;
import java.util.List;

import library.model.Book;
import library.model.Catalog;
import library.model.LibraryUser;
import library.model.BorrowingRecord;

public class BorrowBookController {
    private Catalog catalog;
    private List<BorrowingRecord> borrowingRecords;

    public BorrowBookController(Catalog catalog, List<BorrowingRecord> borrowingRecords) {
        this.catalog = catalog;
        this.borrowingRecords = borrowingRecords;
    }

    public boolean borrowBook(LibraryUser user, int bookId) {
        if (user == null) return false;

        Book book = catalog.getBookById(bookId);
        if (book == null || !book.isAvailable()) {
            return false;
        }

        // decrease availability and create record
        book.decreaseAvailableCopies();
        BorrowingRecord record = new BorrowingRecord(
                generateRecordId(),
                user,
                book,
                LocalDate.now()
        );
        borrowingRecords.add(record);

        return true;
    }

    private int generateRecordId() {
        return borrowingRecords.size() + 1; // simple incremental id
    }
}
