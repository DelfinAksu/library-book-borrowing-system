package library.controller;
import library.model.Catalog;
import library.model.Book;

public class ManageBookController {
    private Catalog catalog;

    public ManageBookController(Catalog catalog) {
        this.catalog = catalog;
    }

    public void addBook(Book bookData) {
        catalog.addBook(bookData);
    }

    public void updateBook(Book bookData) {
        catalog.updateBook(bookData);
    }

    public void removeBook(int bookId) {
        catalog.removeBook(bookId);
    }
}
