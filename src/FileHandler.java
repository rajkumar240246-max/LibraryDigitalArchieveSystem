import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "books.csv";

    public static List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) file.createNewFile();

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    int id = Integer.parseInt(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    String category = parts[3];
                    int year = Integer.parseInt(parts[4]);
                    boolean available = Boolean.parseBoolean(parts[5]);
                    books.add(new Book(id, title, author, category, year, available));
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveBooks(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Book b : books) {
                bw.write(b.toString());
                bw.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}