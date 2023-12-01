import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Frame extends JFrame implements ActionListener {

    private JButton search;
    private JToggleButton title, author, genre, mostPopular, leastPopular, latest;
    private ButtonGroup buttonGroup;
    private JTextField searchBar;
    private JScrollPane scrollPane;
    private JList jList;

    private int toggleX = 10;
    private int toggleY = 45;
    private int toggleWidth = 135;
    private int toggleHeight = 25;

    private Library library;

    Frame() {
        this.setTitle("Library Management System");
        this.setVisible(true);
        this.setSize(900, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        library = Library.getInstance();
        ImageIcon icon = new ImageIcon("src/icon/icon.png");
        this.setIconImage(icon.getImage());
//        this.getContentPane().setBackground(new Color(184, 149, 96));

        addElements();
    }

    private void addElements() {
        addButtons();

        searchBar = new JTextField();
        searchBar.setBounds(10, 10, 720, 30);
        searchBar.setVisible(true);
        this.add(searchBar);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 80, 800, 500);
        scrollPane.setVisible(true);
        this.add(scrollPane);

        ImageIcon ii = new ImageIcon("src/icon/icon.png");      /////////////////// temp
        JLabel jlab = new JLabel(ii);
        jlab.setBounds(300, 300, 500, 500);
        jlab.setVisible(true);

    }

    private void addButtons() {
        search = new JButton();
        search.setBounds(750, 10, 120, 30);
        search.setText("Search");
        search.setFocusable(false);
        search.addActionListener(this);
        this.add(search);

        buttonGroup = new ButtonGroup();

        title = new JToggleButton();
        title.setBounds(toggleX, toggleY, toggleWidth, toggleHeight);
        title.setText("Title");
        setTButtonProperties(title);

        author = new JToggleButton();
        author.setBounds(toggleX + title.getX() + title.getWidth(), toggleY, toggleWidth, toggleHeight);
        author.setText("Author");
        setTButtonProperties(author);

        genre = new JToggleButton();
        genre.setBounds(toggleX + author.getX() + author.getWidth(), toggleY, toggleWidth, toggleHeight);
        genre.setText("Genre");
        setTButtonProperties(genre);

        latest = new JToggleButton();
        latest.setBounds(toggleX + genre.getX() + genre.getWidth(), toggleY, toggleWidth, toggleHeight);
        latest.setText("Latest");
        setTButtonProperties(latest);

        mostPopular = new JToggleButton();
        mostPopular.setBounds(toggleX + latest.getX() + latest.getWidth(), toggleY, toggleWidth, toggleHeight);
        mostPopular.setText("Most Popular");
        setTButtonProperties(mostPopular);

        leastPopular = new JToggleButton();
        leastPopular.setBounds(toggleX + mostPopular.getX() + mostPopular.getWidth(), toggleY, toggleWidth, toggleHeight);
        leastPopular.setText("Least Popular");
        setTButtonProperties(leastPopular);

    }

    private void search(String line) {
        System.out.println(line);
        Book book = library.getBook(line);


    }

    private void populateScrollPane(Book[] books) {
        String[] data = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

        DefaultListModel<String> model = new DefaultListModel<>();
        for (int i = 0; i < data.length; i++) {
            model.addElement(data[i]);
        }

        jList = new JList(model);
        scrollPane = new JScrollPane(jList);
        scrollPane.setViewportView(jList);

        System.out.println("POPULATING");
    }

    private void setTButtonProperties(JToggleButton jtb) {
        jtb.setVisible(true);
        jtb.setFocusable(false);
        jtb.addActionListener(this);
        this.add(jtb);
        buttonGroup.add(jtb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        library = Library.getInstance();

        if (search.equals(source)) {
            System.out.println("Search");

        } else if (title.equals(source)) {
            if (library.getBook(searchBar.getText()) != null) {
                Book[] book = { library.getBook(searchBar.getText()) };
                populateScrollPane(book);
            } else {

            }

        } else if (author.equals(source)) {


        } else if (genre.equals(source)) {

            System.out.println("Genre");

        } else if (latest.equals(source)) {
            populateScrollPane(library.getLatestBooks());
            System.out.println("Latest");

        } else if (mostPopular.equals(source)) {
            populateScrollPane(library.getMostPopular());
            System.out.println("MOST POP");
        }
    }
}
