package library.app;
import library.controller.*;
import library.model.*;
import java.util.Scanner;

public class StudentMenu {
    private final SearchController search;
    private final BorrowBookController borrow;
    private final ReturnBookController returns;
    private final LibraryUser user;
    private final Scanner scanner = new Scanner(System.in);

    public StudentMenu(LibraryUser user,
                       SearchController search,
                       BorrowBookController borrow,
                       ReturnBookController returns) {
        this.user = user;
        this.search = search;
        this.borrow = borrow;
        this.returns = returns;
    }

    public void start() {

        boolean running = true;

        while (running) {
            System.out.println("\n--- STUDENT MENU ---");
            System.out.println("1 - Search books");
            System.out.println("2 - View book details");
            System.out.println("3 - Borrow book");
            System.out.println("4 - View active borrowings");
            System.out.println("5 - Return book");
            System.out.println("0 - Exit");

            System.out.print("Select: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> handleSearch();
                case 2 -> handleViewDetails();
                case 3 -> handleBorrow();
                case 4 -> handleViewBorrowings();
                case 5 -> handleReturn();
                case 0 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void handleSearch() {
        System.out.print("Keyword: ");
        var results = search.searchBooks(scanner.nextLine());
        results.forEach(b -> System.out.println(" - " + b.getTitle()));
    }

    private void handleViewDetails() {
        System.out.print("Book id: ");
        Book b = search.viewBookDetails(Integer.parseInt(scanner.nextLine()));
        System.out.println(b != null ? b : "Not found.");
    }

    private void handleBorrow() {
        System.out.print("Book id to borrow: ");
        boolean ok = borrow.borrowBook(user, Integer.parseInt(scanner.nextLine()));
        System.out.println(ok ? "Success." : "Failed.");
    }

    private void handleViewBorrowings() {
        var list = returns.getActiveBorrowings(user);
        list.forEach(r -> System.out.println("Record " + r.getRecordId() + " | " + r.getBook().getTitle()));
    }

    private void handleReturn() {
        System.out.print("Book id to return: ");
        boolean ok = returns.returnBook(user, Integer.parseInt(scanner.nextLine()));
        System.out.println(ok ? "Returned." : "Not found.");
    }
}
