import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LibraryUI extends JFrame {
    private Library library;
    private JTextField titleField, authorField, yearField, searchField;
    private JComboBox<String> categoryBox;
    private JTable table;
    private DefaultTableModel model;

    public LibraryUI() {
        library = new Library();
        setTitle("Library Digital Archive System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Book"));

        titleField = new JTextField();
        authorField = new JTextField();
        categoryBox = new JComboBox<>(new String[]{"Technology", "Motivation", "Fiction", "Science", "Biography"});
        yearField = new JTextField();

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryBox);
        inputPanel.add(new JLabel("Year:"));
        inputPanel.add(yearField);

        JButton addButton = new JButton("Add Book");
        inputPanel.add(addButton);

        JButton refreshButton = new JButton("Refresh Table");
        inputPanel.add(refreshButton);

        add(inputPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"ID", "Title", "Author", "Category", "Year", "Available"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Search / Actions"));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton issueButton = new JButton("Issue Book");
        JButton returnButton = new JButton("Return Book");
        bottomPanel.add(new JLabel("Search:"));
        bottomPanel.add(searchField);
        bottomPanel.add(searchButton);
        bottomPanel.add(issueButton);
        bottomPanel.add(returnButton);

        add(bottomPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            try {
                int id = library.getNextId();
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String category = (String) categoryBox.getSelectedItem();
                int year = Integer.parseInt(yearField.getText().trim());
                library.addBook(new Book(id, title, author, category, year, true));
                JOptionPane.showMessageDialog(this, "Book Added Successfully!");
                refreshTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your data.");
            }
        });

        refreshButton.addActionListener(e -> refreshTable());

        searchButton.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            Book found = library.searchBook(keyword);
            model.setRowCount(0);
            if (found != null) {
                model.addRow(new Object[]{
                        found.getId(),
                        found.getTitle(),
                        found.getAuthor(),
                        found.getCategory(),
                        found.getYear(),
                        found.isAvailable() ? "Yes" : "No"
                });
            } else {
                JOptionPane.showMessageDialog(this, "No matching book found.");
            }
        });

        issueButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) model.getValueAt(selectedRow, 0);
                library.issueBook(id);
                JOptionPane.showMessageDialog(this, "Book Issued Successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a book from the table first.");
            }
        });

        returnButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int id = (int) model.getValueAt(selectedRow, 0);
                library.returnBook(id);
                JOptionPane.showMessageDialog(this, "Book Returned Successfully!");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Select a book from the table first.");
            }
        });

        refreshTable();
        setVisible(true);
    }

    private void refreshTable() {
        model.setRowCount(0);
        List<Book> books = library.getAllBooks();
        for (Book b : books) {
            model.addRow(new Object[]{
                    b.getId(),
                    b.getTitle(),
                    b.getAuthor(),
                    b.getCategory(),
                    b.getYear(),
                    b.isAvailable() ? "Yes" : "No"
            });
        }
    }
}