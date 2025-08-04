import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class BookScraperGUI extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;

    public BookScraperGUI() {
        setTitle("Book Scraper");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton scrapeButton = new JButton("Scrape Books");
        scrapeButton.addActionListener(e -> scrapeBooks());

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Title", "Price", "Availability"});

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrapeButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void scrapeBooks() {
        try {
            String url = "https://books.toscrape.com/";
            Document doc = Jsoup.connect(url).get();
            Elements products = doc.select("article.product_pod");

            tableModel.setRowCount(0); // Clear previous rows

            for (Element product : products) {
                String title = product.select("h3 a").attr("title");
                String price = product.select(".price_color").text();
                String availability = product.select(".availability").text().trim();

                tableModel.addRow(new Object[]{title, price, availability});
            }

            JOptionPane.showMessageDialog(this, "Scraping Completed!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookScraperGUI().setVisible(true));
    }
}
