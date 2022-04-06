import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener{

    private JTextField width_text, height_text;
    /**
     * Adds buttons and labels to the JFrame
     * @param pane JFrame pane
     */
    public void addComponents(Container pane){
        pane.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createTitledBorder("Settings"));
        GridBagConstraints position = new GridBagConstraints();

        //Label for Width
        JLabel width = new JLabel("Width: ");
        position.fill = GridBagConstraints.VERTICAL;
        position.insets = new Insets(10,10,0,0);
        position.weightx = 0.5;
        position.anchor = GridBagConstraints.NORTHWEST;
        position.gridx = 0;
        position.gridy = 0;
        pane.add(width, position);

        //Label for Height
        JLabel height = new JLabel("Height: ");
        position.fill = GridBagConstraints.VERTICAL;
        position.insets = new Insets(10,10,0,0);
        position.weightx = 0.5;
        position.anchor = GridBagConstraints.NORTHWEST;
        position.gridx = 0;
        position.gridy = 1;
        pane.add(height, position);

        //Text field for Width
        width_text = new JTextField("100");
        position.weightx = 0.5;
        position.fill = GridBagConstraints.VERTICAL;
        position.anchor = GridBagConstraints.NORTHWEST;
        position.gridx = 1;
        position.gridy = 0;
        pane.add(width_text, position);

        //Text field for Height
        height_text = new JTextField("100");
        position.weightx = 0.5;
        position.fill = GridBagConstraints.VERTICAL;
        position.anchor = GridBagConstraints.NORTHWEST;
        position.gridx = 1;
        position.gridy = 1;
        pane.add(height_text, position);

        //Create Button
        JButton button = new JButton("Create");
        position.fill = GridBagConstraints.HORIZONTAL;
        position.anchor = GridBagConstraints.PAGE_END;
        position.weightx = 0.5;
        position.gridx = 1;
        position.gridy = 2;
        button.addActionListener(this);
        pane.add(button, position);

        //Export Button
        button = new JButton("Export");
        position.fill = GridBagConstraints.HORIZONTAL;
        position.anchor = GridBagConstraints.PAGE_END;
        position.weightx = 0.5;
        position.gridx = 1;
        position.gridy = 3;
        pane.add(button, position);

        //Maze Placeholder
        //button = new JButton("MAZE GOES HERE");
        //position.weightx = 0.5;
        //position.fill = GridBagConstraints.REMAINDER;
       // position.gridx = 4;
        //position.gridwidth = 2;
        //position.gridy = 4;
        //pane.add(button, position);
    }


    /**
     * Displays the User Interface for the Maze Generation program
     * attempts to display native design aspects "Look and Feel".
     */
    public void show_gui() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                 UnsupportedLookAndFeelException |
                 InstantiationException |
                 IllegalAccessException ignored){
        }

        //Create frame
        JFrame jframe = new JFrame("Maze Generator");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Add components to the frame.
        addComponents(jframe.getContentPane());

        //Display frame.
        jframe.pack();
        jframe.setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String width = width_text.getText();
        String height = height_text.getText();
        int width_number = Integer.parseInt(width);
        int height_number = Integer.parseInt(height);
        System.out.println(width_number + " and " + height_number + " are now integers");
    }
}
