package library.model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class Catalog {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    public void updateBook(Book updatedBook) {
        if (updatedBook == null) return;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookId() == updatedBook.getBookId()) {
                books.set(i, updatedBook);
                return;
            }
        }
    }

    public void removeBook(int bookId) {
        books.removeIf(b -> b.getBookId() == bookId);
    }

    public List<Book> searchBooks(String criteria) {
        if (criteria == null || criteria.isBlank()) {
            return new ArrayList<>(books);
        }
        String lower = criteria.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(lower)
                        || b.getAuthor().toLowerCase().contains(lower)
                        || b.getCategory().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public Book getBookById(int bookId) {
        return books.stream()
                .filter(b -> b.getBookId() == bookId)
                .findFirst()
                .orElse(null);
    }

    public List<Book> getBooks() {
        return books;
    }
}
