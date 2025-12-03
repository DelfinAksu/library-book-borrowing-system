package library.app;
import library.controller.*;
import library.model.*;
import java.util.Scanner;
public class OfficerMenu extends StudentMenu {
    private final ManageBookController manage;
    private final Catalog catalog;
    private final Scanner scanner = new Scanner(System.in);

    public OfficerMenu(LibraryUser officer,
                       SearchController search,
                       BorrowBookController borrow,
                       ReturnBookController returns,
                       ManageBookController manage,
                       Catalog catalog) {

        super(officer, search, borrow, returns);

        this.manage = manage;
        this.catalog = catalog;
    }

    @Override
    public void start() {

        boolean running = true;

        while (running) {

            System.out.println("\n--- OFFICER MENU ---");
            System.out.println("1 - Search books");
            System.out.println("2 - View book details");
            System.out.println("3 - Borrow book");
            System.out.println("4 - View active borrowings");
            System.out.println("5 - Return book");
            System.out.println("6 - Add book");
            System.out.println("7 - Update book");
            System.out.println("8 - Remove book");
            System.out.println("0 - Exit");

            System.out.print("Select: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 6 -> addBook();
                case 7 -> updateBook();
                case 8 -> removeBook();
                default -> superChoice(choice); // Student işlemleri
            }
        }
    }

    private void superChoice(int choice) {
        super.start(); // veya switch-case override
    }

    private void addBook() {
        System.out.print("New book id: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Title: ");
        String title = scanner.nextLine();

        Book b = new Book(id, title, "?", "?", 2000, 1, 1);
        manage.addBook(b);
        System.out.println("Book added.");
    }

    private void updateBook() {
        // güncelleme mantığı
    }

    private void removeBook() {
        System.out.print("Book id: ");
        manage.removeBook(Integer.parseInt(scanner.nextLine()));
        System.out.println("Book removed.");
    }
}
