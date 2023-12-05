import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class Frame extends JFrame implements ActionListener, MouseListener {

    private JButton search, submit;
    private JButton logoutButton = new JButton("Confirm");
    private JToggleButton title, author, genre, mostPopular, leastPopular, latest, login;
    private ButtonGroup buttonGroup;
    private JTextField searchBar, nameField, passwordField;
    private JLabel errorMessage = new JLabel("The username or Password is incorrect");
    private boolean loggedIn = false;
    JPanel loginPanel;

    private JScrollPane scrollPane;

    private DetailsWindow detailsWindow;

    private JWindow loginWindow = new JWindow(this);
    private JWindow logoutWindow = new JWindow(this);

    private JList jList;

    private int toggleX = 10;
    private int toggleY = 45;
    private int toggleWidth = 135;
    private int toggleHeight = 25;
    private Customer loggedInAs;
    private Book[] showCasedBooks;

    private Library library;

    Frame() {
        this.setTitle("Library Management System");
        this.setVisible(true);
        this.setSize(900, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        errorMessage.setBounds(70, 180, 300, 20);


        library = Library.getInstance();
        ImageIcon icon = new ImageIcon("src/PNGS/icon.png");
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

        ImageIcon ii = new ImageIcon();      /////////////////// temp
        JLabel jlab = new JLabel(ii);
        jlab.setBounds(300, 300, 500, 500);
        jlab.setVisible(true);
        this.add(jlab);

        // Refresh the frame
        this.revalidate();
        this.repaint();
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

        login = new JToggleButton();
        login.setBounds(810, 490, 85, 85);
        login.setIcon(new ImageIcon("src/icon/Login.png"));
        login.setText("Login");
        setTButtonProperties(login);

    }

    private void searchByTitle(String line) {

        Book[] books = new Book[1];
        books[0] = library.getBook(line);

        if (books[0] == null) {
            return;
        }
        populateScrollPane(books);


    }

    private void clearShowCasedBooks() {
        showCasedBooks = null;
    }

    private void populateScrollPane(Book[] books) {


        DefaultListModel<Book> model = new DefaultListModel<>();

        // Populate the model with book information
        for (Book book : books) {
            model.addElement(book); // Assuming Book has a getTitle() method, replace it with the actual method
        }

        // Check if the JList is already created

        jList = new JList(model);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    // Handle the selection change
                    int selectedIndex = jList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Book selectedBook = model.getElementAt(selectedIndex);
                        // Perform an action based on the selected element
                        System.out.println("Clicked on: " + selectedBook);
                        if (detailsWindow == null) {
                            detailsWindow = new DetailsWindow(selectedBook);
                        } else {
                            detailsWindow.kill();
                            detailsWindow = new DetailsWindow(selectedBook);
                        }
                    }
                }
            }
        });

        scrollPane.setViewportView(jList);

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
            closeLoginWindow();
            System.out.println("Search");

        } else if (title.equals(source)) {
            closeLoginWindow();
            clearShowCasedBooks();
            searchByTitle(searchBar.getText());
            System.out.println(title.isSelected());
            System.out.println(author.isSelected());


        } else if (author.equals(source)) {
            closeLoginWindow();
            clearShowCasedBooks();

        } else if (genre.equals(source)) {
            closeLoginWindow();
            populateScrollPane(library.getSortedByGenreBooks(Genre.Horror));

        } else if (latest.equals(source)) {
            closeLoginWindow();
            populateScrollPane(library.getLatestBooks());
            System.out.println("Latest");

        } else if (mostPopular.equals(source)) {
            closeLoginWindow();
            populateScrollPane(library.getMostPopular());
            System.out.println("MOST POP");
        } else if (leastPopular.equals(source)) {
            closeLoginWindow();
            populateScrollPane(library.getLeastPopular());
        } else if (author.equals(source)) {
            closeLoginWindow();
        } else if (login.equals(source)) {
            if (loggedInAs == null) {

                openLoginWindow();
            } else {
                openLogoutWindow();
            }
        } else if (submit.equals(source)) {
            System.out.println("Works");
            loggedInAs = library.getCustomer(nameField.getText(), passwordField.getText());
            System.out.println(nameField.getText() + " " + passwordField.getText());
            if ((loggedInAs == null)) {
                System.out.println("Its working");
                loginPanel.add(errorMessage);
                loginWindow.repaint();
            } else {
                System.out.println("Successfully logged in");
                this.setTitle("Library Management System: " + loggedInAs.getName());
                login.setText("Logout");
                closeLoginWindow();
            }
        } else if (logoutButton.equals(source)) {
            System.out.println("works");
            login.setText("Login");
            loggedInAs = null;
            this.setTitle("Library Management System");
            logoutWindow.setVisible(false);

        }
    }

    public void closeLoginWindow() {
        if (loginWindow.isVisible()) {
            loginWindow.setVisible(false);
            loginWindow=new JWindow(this);
            loginPanel=new JPanel();
        }


    }

    public void openLogoutWindow() {
        logoutWindow.setBounds(200, 200, 400, 200);
        JPanel panel = new JPanel();
        panel.setLayout(null); // Use null layout for manual component placement
        JLabel confirmationText = new JLabel("Do you want to Log Out?");
        confirmationText.setBounds(40, 20, 300, 50);

        logoutButton.setBounds(100, 100, 100, 50);
        panel.add(confirmationText);
        panel.add(logoutButton);
        logoutButton.addActionListener(this);
        logoutWindow.add(panel);
        logoutWindow.setVisible(true);

    }

    public void openLoginWindow() {

        loginWindow.setBounds(200, 200, 400, 200);

        // Create text fields for name and password
        nameField = new JTextField();
        passwordField = new JPasswordField();
        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignore the event if the character is not a digit
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // No action needed for keyPressed
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // No action needed for keyReleased
            }
        });
        // Create a JPanel for the login window content
        loginPanel = new JPanel();
        loginPanel.setLayout(null); // Use null layout for manual component placement

        // Add components to the login window content
        JLabel titleLabel = new JLabel("Login Window");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBounds(150, 10, 100, 20); // Adjust the bounds as needed
        loginPanel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 50, 20); // Adjust the bounds as needed
        loginPanel.add(nameLabel);
        nameField.setBounds(120, 50, 200, 20); // Adjust the bounds as needed
        loginPanel.add(nameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 80, 70, 20); // Adjust the bounds as needed
        loginPanel.add(passwordLabel);
        passwordField.setBounds(120, 80, 200, 20); // Adjust the bounds as needed
        loginPanel.add(passwordField);

        submit = new JButton("Submit");
        submit.setBounds(140, 120, 100, 50);
        loginPanel.add(submit);
        submit.addActionListener(this);
        // Explicitly request focus for the text fields
        // Customize the login window content as needed
        // ...
        nameField.addMouseListener(this);
        passwordField.addMouseListener(this);
        // Set a border for the login panel to create a boundary
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        // Add the login panel to the content pane of the login window
        loginWindow.getContentPane().add(loginPanel);
        // Explicitly request focus for the text fields
        nameField.requestFocusInWindow();
        passwordField.requestFocusInWindow();

        // Set the visibility of the login window to true
        loginWindow.setVisible(true);

    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
