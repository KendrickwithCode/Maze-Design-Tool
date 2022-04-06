import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;

public class GUI implements ActionListener {

    private JTextField width_text, height_text;


    //Static puzzle for demo purposes.
    static char[][] puzzle = { { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' },
            { '#', ' ', ' ', ' ', '#', ' ', '#', '#', '#', '#', ' ', 'X', '#' },
            { '#', '#', ' ', '#', '#', ' ', '#', ' ', ' ', '#', ' ', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#' },
            { '#', ' ', '#', ' ', ' ', '#', '#', '#', '#', ' ', '#', '#', '#' },
            { '#', '#', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#' },
            { '#', ' ', ' ', ' ', ' ', '#', '#', ' ', ' ', '#', '#', '#', '#' },
            { '#', '#', ' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', '#' },
            { '#', ' ', '#', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', ' ', '#' },
            { '#', ' ', '#', '#', ' ', '#', '#', ' ', '#', ' ', '#', ' ', '#' },
            { '#', ' ', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', '#', '#', '#' },
            { '#', ' ', '#', '#', '#', ' ', '#', ' ', '#', ' ', ' ', '#', '#' },
            { '#', ' ', ' ', ' ', ' ', ' ', '#', ' ', '#', ' ', ' ', ' ', '#' },
            { '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#' }, };

    /**
     * Adds buttons and labels to the JFrame
     * @param pane JFrame pane
     */
    public void addComponents(Container pane){

        pane.setLayout(new GridBagLayout());
        JPanel panel = new JPanel();
        GridBagConstraints constraint_menu = new GridBagConstraints();
        GridBagConstraints constraint_maze = new GridBagConstraints();
        panel.setBorder(BorderFactory.createTitledBorder("Settings"));

        //Label for Width
        JLabel width = new JLabel("Width: ");
        panel.add(width);
        constraint_menu.fill = GridBagConstraints.VERTICAL;
        constraint_menu.insets = new Insets(10,10,0,0);
        constraint_menu.weightx = 0.5;
        constraint_menu.anchor = GridBagConstraints.NORTHWEST;
        constraint_menu.gridx = 0;
        constraint_menu.gridy = 0;
        pane.add(width, constraint_menu);

        //Label for Height
        JLabel height = new JLabel("Height: ");
        panel.add(height);
        constraint_menu.fill = GridBagConstraints.VERTICAL;
        constraint_menu.insets = new Insets(10,10,0,0);
        constraint_menu.weightx = 0.5;
        constraint_menu.anchor = GridBagConstraints.NORTHWEST;
        constraint_menu.gridx = 0;
        constraint_menu.gridy = 1;
        pane.add(height, constraint_menu);

        //Text field for Width
        width_text = new JTextField("100");
        panel.add(width_text);
        constraint_menu.weightx = 0.5;
        constraint_menu.fill = GridBagConstraints.VERTICAL;
        constraint_menu.anchor = GridBagConstraints.NORTHWEST;
        constraint_menu.gridx = 1;
        constraint_menu.gridy = 0;
        pane.add(width_text, constraint_menu);

        //Text field for Height
        height_text = new JTextField("100");
        panel.add(height_text);
        constraint_menu.weightx = 0.5;
        constraint_menu.fill = GridBagConstraints.VERTICAL;
        constraint_menu.anchor = GridBagConstraints.NORTHWEST;
        constraint_menu.gridx = 1;
        constraint_menu.gridy = 1;
        pane.add(height_text, constraint_menu);

        //Create Button
        JButton button = new JButton("Create");
        panel.add(button);
        constraint_menu.fill = GridBagConstraints.HORIZONTAL;
        constraint_menu.anchor = GridBagConstraints.PAGE_END;
        constraint_menu.weightx = 0.5;
        constraint_menu.gridx = 1;
        constraint_menu.gridy = 2;
        button.addActionListener(this);
        pane.add(button, constraint_menu);

        //Export Button
        button = new JButton("Export");
        panel.add(button);
        constraint_menu.fill = GridBagConstraints.HORIZONTAL;
        constraint_menu.anchor = GridBagConstraints.PAGE_END;
        constraint_menu.weightx = 0.5;
        constraint_menu.gridx = 1;
        constraint_menu.gridy = 3;
        pane.add(button, constraint_menu);

        for (int row = 1; row < puzzle.length; row++) {
            for (int col = 1; col < puzzle[0].length; col++) {
                JLabel label = makeLabel(puzzle[row][col]);
                label.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        if (label.getBackground() == Color.WHITE)
                        {
                            label.setBackground(Color.BLACK);
                        }
                        else{
                            label.setBackground(Color.WHITE);
                        }
                    }
                });
                constraint_maze.gridx = row + 1;
                constraint_maze.gridy = col + 4;
                constraint_maze.gridwidth = 1;
                pane.add(label, constraint_maze);
            }
        }

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

    private JLabel makeLabel(char c) {

        JLabel label= new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(40, 40));
        switch(c) {
            case '#':
                label.setBackground(Color.BLACK);
                break;
            default:
                label.setBackground(Color.WHITE);
                break;

        }
        label.setOpaque(true);
        label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return label;
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
