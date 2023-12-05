import javax.swing.*;
import java.awt.*;

public class BorrowWindow {
    JFrame frame;
    JPanel textBoxPanel;
    public BorrowWindow() {

        initializeFrame();
        initializeTextBoxPanel();

        frame.getContentPane().add(textBoxPanel);
        frame.setLocation(890,0);
        frame.setSize(350, 200);
        frame.setVisible(true);
    }

    private void initializeFrame(){
        frame = new JFrame("BlockChain");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon icon = new ImageIcon("src/PNGS/diamonds.png");
        frame.setIconImage(icon.getImage());
    }

    private void initializeTextBoxPanel(){
        // Create a panel with GridLayout
        textBoxPanel = new JPanel((new GridLayout(3, 2, 5, 5)));

        // Add text bars to the textBoxPanel
        JLabel passwordLabel = new JLabel("Signature:");
        JTextField passwordField = new JTextField(20);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField(20);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);

        textBoxPanel.add(passwordLabel);
        textBoxPanel.add(passwordField);
        textBoxPanel.add(phoneLabel);
        textBoxPanel.add(phoneField);
        textBoxPanel.add(emailLabel);
        textBoxPanel.add(emailField);


    }
    public void kill(){
        frame.dispose();
    }

}
