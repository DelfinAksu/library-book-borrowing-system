package library.app;
import library.controller.*;
import library.model.*;
import library.persistence.BorrowingRecordStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // ================================
        // 1) SYSTEM SETUP
        // ================================
        Catalog catalog = new Catalog();
        List<BorrowingRecord> borrowingRecords = BorrowingRecordStorage.loadRecords();

        SearchController searchController = new SearchController(catalog);
        BorrowBookController borrowController = new BorrowBookController(catalog, borrowingRecords);
        ReturnBookController returnController = new ReturnBookController(catalog, borrowingRecords);
        ManageBookController manageController = new ManageBookController(catalog);

        // Örnek kullanıcılar
        LibraryUser student = new LibraryUser(1, "Alice", "alice@mail.com", "1234");
        LibraryOfficer officer = new LibraryOfficer(2, "Mr. John", "john@mail.com", "admin", 1001);

        // Örnek kitaplar
        manageController.addBook(new Book(101, "Data Structures", "Mark Lewis", "CS", 2010, 3, 3));
        manageController.addBook(new Book(102, "Operating Systems", "Silberschatz", "CS", 2014, 2, 2));
        manageController.addBook(new Book(103, "Database Systems", "Elmasri", "CS", 2016, 1, 1));

        System.out.println("=== Library Book Borrowing System ===");
        System.out.println("Sample users:");
        System.out.println("  Student -> id: 1, password: 1234");
        System.out.println("  Officer -> id: 2, password: admin");
        System.out.println();

        // ================================
        // 2) LOGIN
        // ================================
        LibraryUser currentUser = null;
        boolean isOfficer = false;

        while (currentUser == null) {
            System.out.print("Enter user id (1 = student, 2 = officer): ");
            int id = readInt();
            System.out.print("Enter password: ");
            String pw = scanner.nextLine();

            if (id == student.getUserId()) {
                if (searchController.login(student, pw)) {
                    currentUser = student;
                    isOfficer = false;
                    System.out.println("Login successful as STUDENT.\n");
                } else {
                    System.out.println("Invalid password.\n");
                }
            } else if (id == officer.getUserId()) {
                if (searchController.login(officer, pw)) {
                    currentUser = officer;
                    isOfficer = true;
                    System.out.println("Login successful as OFFICER.\n");
                } else {
                    System.out.println("Invalid password.\n");
                }
            } else {
                System.out.println("Unknown user id.\n");
            }
        }

        // ================================
        // 3) MAIN MENU
        // ================================
        boolean running = true;
        while (running) {
            printMenu(isOfficer);
            System.out.print("Select option: ");
            int choice = readInt();
            System.out.println();

            switch (choice) {
                case 0 -> {
                    BorrowingRecordStorage.saveRecords(borrowingRecords);
                    System.out.println("Exiting system. Goodbye!");
                    running = false;
                }
                case 1 -> { // Search books
                    System.out.print("Enter search keyword (title/author/category): ");
                    String criteria = scanner.nextLine();
                    var results = searchController.searchBooks(criteria);
                    if (results.isEmpty()) {
                        System.out.println("No books found.\n");
                    } else {
                        System.out.println("Search results:");
                        for (Book b : results) {
                            printBook(b);
                        }
                        System.out.println();
                    }
                }
                case 2 -> { // View book details
                    System.out.print("Enter book id: ");
                    int bookId = readInt();
                    Book b = searchController.viewBookDetails(bookId);
                    if (b == null) {
                        System.out.println("Book not found.\n");
                    } else {
                        System.out.println("Book details:");
                        printBook(b);
                        System.out.println();
                    }
                }
                case 3 -> { // Borrow book
                    System.out.print("Enter book id to borrow: ");
                    int bookId = readInt();
                    boolean ok = borrowController.borrowBook(currentUser, bookId);
                    if (ok) {
                        System.out.println("Book borrowed successfully.\n");
                        BorrowingRecordStorage.saveRecords(borrowingRecords);
                    } else {
                        System.out.println("Borrow failed (book not found or not available).\n");
                    }
                }
                case 4 -> { // View active borrowings (current user)
                    System.out.println("Active borrowings for " + currentUser.getName() + ":");
                    var active = returnController.getActiveBorrowings(currentUser);
                    if (active.isEmpty()) {
                        System.out.println("No active borrowings.\n");
                    } else {
                        for (BorrowingRecord r : active) {
                            System.out.println("Record " + r.getRecordId()
                                    + " | Book: " + r.getBook().getTitle()
                                    + " | Status: " + r.getStatus());
                        }
                        System.out.println();
                    }
                }
                case 5 -> { // Return book
                    System.out.print("Enter book id to return: ");
                    int bookId = readInt();
                    boolean ok = returnController.returnBook(currentUser, bookId);
                    if (ok) {
                        System.out.println("Book returned successfully.\n");
                    } else {
                        System.out.println("Return failed (no active borrowing found).\n");
                    }
                }
                case 6 -> { // Add book (officer only)
                    if (!isOfficer) {
                        System.out.println("Only officers can manage books.\n");
                        break;
                    }
                    System.out.print("New book id: ");
                    int id = readInt();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Publication year: ");
                    int year = readInt();
                    System.out.print("Total copies: ");
                    int total = readInt();

                    Book newBook = new Book(id, title, author, category, year, total, total);
                    manageController.addBook(newBook);
                    System.out.println("Book added to catalog.\n");
                }
                case 7 -> { // Update book (officer only)
                    if (!isOfficer) {
                        System.out.println("Only officers can manage books.\n");
                        break;
                    }
                    System.out.print("Book id to update: ");
                    int id = readInt();
                    Book b = catalog.getBookById(id);
                    if (b == null) {
                        System.out.println("Book not found.\n");
                        break;
                    }
                    System.out.println("Current data:");
                    printBook(b);

                    System.out.print("New title (empty = keep): ");
                    String newTitle = scanner.nextLine();
                    if (!newTitle.isBlank()) b.setTitle(newTitle);

                    System.out.print("New total copies (-1 = keep): ");
                    int newTotal = readIntAllowNegative();
                    if (newTotal >= 0) {
                        b.setTotalCopies(newTotal);
                        if (b.getAvailableCopies() > newTotal) {
                            b.setAvailableCopies(newTotal);
                        }
                    }

                    manageController.updateBook(b);
                    System.out.println("Book updated.\n");
                }
                case 8 -> { // Remove book
                    if (!isOfficer) {
                        System.out.println("Only officers can manage books.\n");
                        break;
                    }
                    System.out.print("Book id to remove: ");
                    int id = readInt();
                    manageController.removeBook(id);
                    System.out.println("If the book existed, it has been removed.\n");
                }
                case 9 -> { // View user transaction history (OFFICER)
                    if (!isOfficer) {
                        System.out.println("Only officers can view user history.\n");
                        break;
                    }

                    System.out.print("Enter user id to view history: ");
                    int uid = readInt();

                    LibraryUser targetUser = null;
                    if (uid == student.getUserId()) {
                        targetUser = student;
                    } else if (uid == officer.getUserId()) {
                        targetUser = officer;
                    }

                    if (targetUser == null) {
                        System.out.println("User not found.\n");
                        break;
                    }

                    var history = returnController.getBorrowingHistory(targetUser);

                    if (history.isEmpty()) {
                        System.out.println("No transactions found for user: " + targetUser.getName() + "\n");
                    } else {
                        System.out.println("Transaction history for " + targetUser.getName() + ":");
                        for (BorrowingRecord r : history) {
                            System.out.println("Record " + r.getRecordId()
                                    + " | Book: " + r.getBook().getTitle()
                                    + " | Status: " + r.getStatus()
                                    + " | Borrowed: " + r.getBorrowDate()
                                    + " | Returned: " + r.getReturnDate());
                        }
                        System.out.println();
                    }
                }
                default -> System.out.println("Invalid option.\n");
            }
        }
    }

    // ================================
    // HELPER METHODS
    // ================================
    private static void printMenu(boolean isOfficer) {
        System.out.println("----- MENU -----");
        System.out.println("1 - Search books");
        System.out.println("2 - View book details");
        System.out.println("3 - Borrow book");
        System.out.println("4 - View my active borrowings");
        System.out.println("5 - Return book");
        if (isOfficer) {
            System.out.println("6 - Add book");
            System.out.println("7 - Update book");
            System.out.println("8 - Remove book");
            System.out.println("9 - View user transaction history");
        }
        System.out.println("0 - Exit");
    }

    private static void printBook(Book b) {
        System.out.println("[" + b.getBookId() + "] "
                + b.getTitle() + " | " + b.getAuthor()
                + " | " + b.getCategory()
                + " | year: " + b.getPublicationYear()
                + " | total: " + b.getTotalCopies()
                + " | available: " + b.getAvailableCopies());
    }

    private static int readInt() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }

    // updateBook’ta -1 girilebilsin diye
    private static int readIntAllowNegative() {
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }
}
