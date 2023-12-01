
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class DetailsWindow{
    Book book;
    JFrame frame;

    BorrowWindow borrowWindow;

    JPanel mainPanel;

    ReviewsWindow reviewsWindow;
    JPanel scrollPanel;
    JButton borrowButton;
    JButton reviewButton;

    int x_size = 300;
    int y_size = 400;

    ImageIcon Fullheart = new ImageIcon(new ImageIcon("src/PNGS/FullHeart.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
    ImageIcon Halfheart = new ImageIcon(new ImageIcon("src/PNGS/HalfHeart.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

    public DetailsWindow(Book book) {

        this.book = book;


        // Initializing all Panels
        initializeFrame();
        initializeScrollPanel();
        initializeButtonPanel();
        initializeReviewPanel();



        //adding mainPanel to frame
        frame.getContentPane().add(mainPanel);


    }

    private void initializeFrame(){
        // Frame Settings
        ImageIcon icon = new ImageIcon("src/PNGS/book.png");
        frame = new JFrame("Details");

        // This code is not necessary, but it makes it so that reviews and borrow windows close when the details window is closed
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(borrowWindow != null){
                    borrowWindow.kill();
                }
                if(reviewsWindow != null){
                    reviewsWindow.kill();
                }

                super.windowClosing(e);
            }
        });
        frame.setIconImage(icon.getImage());
        frame.setLocation(950,200);
        frame.setSize(x_size, y_size);
        frame.setVisible(true);

        // Creating Main panel that has border layout
        // All other panels with be added to the mainPanel
        mainPanel = new JPanel(new BorderLayout());

    }


    private void initializeScrollPanel(){

        // Text for the book details
        String[] labelTexts =
                {
                        "  Title: " +book.getTitle()+ " " ,
                        "  Author: "+book.getAuthor()+ " ",
                        "  Genre: "+book.getGenre()+ " ",
                        "  Published At: "+ book.getPubDate()+ " ",
                        "  Published By: "+book.getPublisher()+ " ",
                        "  ISBN: "+book.getISBN()+ " "
                };




        // Using grid layout
         scrollPanel = new JPanel(new GridLayout(6,1));
        scrollPanel.setBackground(Color.BLACK);

        // adding labels to the scrollPanel
        for(String label: labelTexts){
            JLabel labels = new JLabel();
            // Use whatever font
            Font labelFont = new Font("Arial", Font.BOLD, 14);
            labels.setFont(labelFont);
            labels.setForeground(Color.WHITE);

            labels.setText(label);
            scrollPanel.add(labels);

        }
        // creating and adding scroll pane
        JScrollPane scrollPane = new JScrollPane(scrollPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }




    // Button Panel has review and Buy Button
    private void initializeButtonPanel(){

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);

        initializeButtons();

        buttonPanel.add(borrowButton);
        buttonPanel.add(reviewButton);
        int borderSize = 15;
        buttonPanel.setBorder(new EmptyBorder(borderSize, 0, borderSize, 0));
        mainPanel.add(buttonPanel,BorderLayout.SOUTH);

    }
    private void initializeButtons(){
        borrowButton = new JButton("Borrow");

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(borrowWindow ==null){
                    borrowWindow = new BorrowWindow();
                }

            }
        });

        reviewButton = new JButton("Review");
        reviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(reviewsWindow == null){
                    reviewsWindow = new ReviewsWindow(book);
                }
            }
        });


    }

    private void initializeReviewPanel(){
        int borderSize = 15;
        JPanel reviewPanel = new JPanel();
        reviewPanel.setBackground(Color.RED);
        reviewPanel.setBorder(new EmptyBorder(borderSize, 0, borderSize, 0));
        int fullHearts = determineFullHearts();

        for(int i =0;i<fullHearts;i++){
            JLabel j = new JLabel(Fullheart);
            reviewPanel.add(j);
        }
        if(determineHalfHearts()){
            JLabel j = new JLabel(Halfheart);
            reviewPanel.add(j);
        }

        // Adding to mainPanel
        mainPanel.add(reviewPanel, BorderLayout.NORTH);
    }


    // Hearts method determine how many hearts to represent the rating
    private int determineFullHearts(){
        double rating = book.getReviewRating();
        return (int) rating;
    }
    private boolean determineHalfHearts(){
        double rating = book.getReviewRating() - determineFullHearts();
        return rating > 0.5;
    }
    public void kill(){
        if(borrowWindow != null)
        {
            borrowWindow.kill();
        }
        frame.dispose();
    }

}
