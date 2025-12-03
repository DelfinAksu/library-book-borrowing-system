package library.controller;
import java.util.List;
import library.model.Book;
import library.model.Catalog;
import library.model.LibraryUser;
public class SearchController {
    private Catalog catalog;

    public SearchController(Catalog catalog) {
        this.catalog = catalog;
    }

    public boolean login(LibraryUser user, String password) {
        return user != null && user.login(password);
    }

    public List<Book> searchBooks(String criteria) {
        return catalog.searchBooks(criteria);
    }

    public Book viewBookDetails(int bookId) {
        return catalog.getBookById(bookId);
    }
}
