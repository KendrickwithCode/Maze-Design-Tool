import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Display software and developer information
 */
public class About extends javax.swing.JFrame implements ActionListener  {
String text = """
        Version 1.0

        MazeCraft is a program made for the CAB302 unit for the Queensland University of Technology.

        This software has been developed by:
        Daniel Lopez
        Jacob Hollister
        Jason Kendrick
        Zave Bradshaw""";

Image img = Toolkit.getDefaultToolkit().getImage("img/About.png");

    /**
     * Display software and developer information
     */
    public About(){
    this.setContentPane(new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(img, 0, 0, null);
        }
    });
    // Window Properties
    ImageIcon icon = new ImageIcon("img/TopIcon.png");
    setIconImage(icon.getImage());
    setTitle("About");

    //Text      Code Based from https://www.tutorialspoint.com/how-to-set-default-background-color-for-jtextpane-in-java
    JTextPane aboutText = new JTextPane();
    StyledDocument documentStyle = aboutText.getStyledDocument();
    SimpleAttributeSet textAttribute = new SimpleAttributeSet();
    StyleConstants.setAlignment(textAttribute, StyleConstants.ALIGN_LEFT);
    StyleConstants.setBackground(textAttribute,Color.darkGray);
    StyleConstants.setForeground(textAttribute,Color.white);
    aboutText.setBackground(Color.darkGray);
    aboutText.setCharacterAttributes(textAttribute,true);
    documentStyle.setParagraphAttributes(0, documentStyle.getLength(), textAttribute , false);

    aboutText.setBounds(100,80, 300, 160);
    aboutText.setText(text);
    aboutText.setEditable(false);


    //Ok button
    JButton okButton = new JButton("OK");
    okButton.setBounds(330,240,70,30);
    okButton.addActionListener(this);

    this.add(aboutText);
    this.add(okButton);

    this.setSize(440, 320);
    this.setLayout(null);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
