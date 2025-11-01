import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private String category;
    private int year;
    private boolean available;

    public Book(int id, String title, String author, String category, int year, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.year = year;
        this.available = available;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }
    public int getYear() { return year; }
    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + category + "," + year + "," + available;
    }
}