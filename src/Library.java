import java.util.*;

public class Library {
    private List<Book> books;

    public Library() {
        books = FileHandler.loadBooks();
    }

    public void addBook(Book book) {
        books.add(book);
        FileHandler.saveBooks(books);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book searchBook(String keyword) {
        for (Book b : books) {
            if (b.getTitle().equalsIgnoreCase(keyword) || b.getAuthor().equalsIgnoreCase(keyword)) {
                return b;
            }
        }
        return null;
    }

    public List<Book> filterByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getCategory().equalsIgnoreCase(category)) result.add(b);
        }
        return result;
    }

    public void issueBook(int id) {
        for (Book b : books) {
            if (b.getId() == id && b.isAvailable()) {
                b.setAvailable(false);
                FileHandler.saveBooks(books);
                break;
            }
        }
    }

    public void returnBook(int id) {
        for (Book b : books) {
            if (b.getId() == id && !b.isAvailable()) {
                b.setAvailable(true);
                FileHandler.saveBooks(books);
                break;
            }
        }
    }

    public int getNextId() {
        if (books.isEmpty()) return 1;
        return books.get(books.size() - 1).getId() + 1;
    }
}