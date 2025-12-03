package library.model;

public class Book {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private int publicationYear;
    private int totalCopies;
    private int availableCopies;

    public Book(int bookId, String title, String author,
                String category, int publicationYear,
                int totalCopies, int availableCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    public void increaseAvailableCopies() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public void decreaseAvailableCopies() {
        if (availableCopies > 0) {
            availableCopies--;
        }
    }

    // Getters & setters

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }

    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}
