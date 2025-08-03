import java.util.ArrayList;
import java.util.Scanner;


class Book {
    private static int idCounter = 1;
    private int bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String title, String author) {
        this.bookId = idCounter++;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "[" + bookId + "] " + title + " by " + author + (isAvailable ? " (Available)" : " (Checked Out)");
    }
}

class User {
    private String name;
    private ArrayList<Book> borrowedBooks;

    public User(String name) {
        this.name = name;
        borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void showBorrowingHistory() {
        System.out.println("\nBorrowing History for " + name + ":");
        if (borrowedBooks.isEmpty()) {
            System.out.println("  No books borrowed.");
        } else {
            for (Book book : borrowedBooks) {
                System.out.println("  - " + book);
            }
        }
    }
}

// Library class
class Library {
    private ArrayList<Book> books;
    private ArrayList<User> users;

    public Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(String title, String author) {
        books.add(new Book(title, author));
        System.out.println("Book added successfully.");
    }

    public void editBookByTitle(String title, String newTitle, String newAuthor) {
        Book book = findBookByTitle(title);
        if (book != null) {
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book with title '" + title + "' not found.");
        }
    }

    public void deleteBookByTitle(String title) {
        Book book = findBookByTitle(title);
        if (book != null) {
            books.remove(book);
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book with title '" + title + "' not found.");
        }
    }

    public void showBooks() {
        System.out.println("\nBook Catalog:");
        if (books.isEmpty()) {
            System.out.println("  No books available.");
        } else {
            for (Book book : books) {
                System.out.println("  " + book);
            }
        }
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getBookId() == id) return book;
        }
        return null;
    }

    public Book findBookByTitle(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) return book;
        }
        return null;
    }

    public User getOrCreateUser(String userName) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(userName)) {
                return user;
            }
        }
        User newUser = new User(userName);
        users.add(newUser);
        return newUser;
    }

    public void checkoutBook(String userName, int bookId) {
        Book book = findBookById(bookId);
        if (book == null || !book.isAvailable()) {
            System.out.println("Book not available or not found.");
            return;
        }
        User user = getOrCreateUser(userName);
        user.borrowBook(book);
        book.setAvailable(false);
        System.out.println("Book checked out successfully.");
    }

    public void viewUserHistory(String userName) {
        User user = getOrCreateUser(userName);
        user.showBorrowingHistory();
    }
}


public class Lib{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        while (true) {
            System.out.println("\n==== Library Management System ====");
            System.out.println("1. Librarian - Add Book");
            System.out.println("2. Librarian - Edit Book by Title");
            System.out.println("3. Librarian - Delete Book by Title");
            System.out.println("4. User - View Catalog");
            System.out.println("5. User - Checkout Book by ID");
            System.out.println("6. User - View Borrowing History");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter book author: ");
                    String author = sc.nextLine();
                    library.addBook(title, author);
                    break;

                case 2:
                    System.out.print("Enter title of the book to edit: ");
                    String oldTitle = sc.nextLine();
                    System.out.print("Enter new title: ");
                    String newTitle = sc.nextLine();
                    System.out.print("Enter new author: ");
                    String newAuthor = sc.nextLine();
                    library.editBookByTitle(oldTitle, newTitle, newAuthor);
                    break;

                case 3:
                    System.out.print("Enter title of the book to delete: ");
                    String delTitle = sc.nextLine();
                    library.deleteBookByTitle(delTitle);
                    break;

                case 4:
                    library.showBooks();
                    break;

                case 5:
                    System.out.print("Enter your name: ");
                    String userName = sc.nextLine();
                    library.showBooks();
                    System.out.print("Enter book ID to checkout: ");
                    int bookId = sc.nextInt();
                    library.checkoutBook(userName, bookId);
                    break;

                case 6:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    library.viewUserHistory(name);
                    break;

                case 0:
                    System.out.println("Exiting... Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
