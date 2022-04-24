import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Constructs and initialises Menu items for the GUI
 */
public class GUI_Tools extends JFrame implements ActionListener, Runnable {

        private JButton btnCreate, btnExport, btnImport, btnSave, btnGenerate;
        public JCheckBox showGrid;
        private JTextField width_text;
        private JTextField height_text;
        private JLabel name;
        private GUI mainGui;

        @Override
        public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();

                if (src==btnCreate)
                {
                        mainGui.generateNewMaze(Integer.parseInt(width_text.getText()),
                                Integer.parseInt(height_text.getText()), name.getText(), false);
                }

                else if (src==btnExport)
                {
                        JOptionPane.showMessageDialog(this,"Export","Export",JOptionPane.INFORMATION_MESSAGE);
                }

                else if (src==btnImport)
                {
                        JOptionPane.showMessageDialog(this,"Import","Import",JOptionPane.INFORMATION_MESSAGE);
                }

                else if (src==btnSave)
                {
                        JOptionPane.showMessageDialog(this,"Save","Save",JOptionPane.INFORMATION_MESSAGE);
                }

                else if (src==btnGenerate)
                {
                        mainGui.generateNewMaze(Integer.parseInt(width_text.getText()),
                              Integer.parseInt(height_text.getText()), name.getText(), true);
                }

        }

        @Override
        public void run() {}

        /**
         * Constructs and initialises Menu items for the GUI
         * @param borderComponent Determines the location of the menu with BorderLayout.
         *                        Example - A panel situated on BorderLayout.LINE_START will
         *                        put the menu items on the left side.
         *
         */
        public GUI_Tools(JPanel borderComponent, GUI mainGUI){
                toolsMenu(borderComponent);
                this.mainGui = mainGUI;
        }

        /**
         * Add the menu options for the Maze
         * Is called automatically from constructor
         */
        private void toolsMenu(JPanel borderSpot){
                GridBagLayout layout = new GridBagLayout();
                borderSpot.setLayout(layout);

                //Logo image
                ImageIcon companyLogo = new ImageIcon("img/MazeCraft-Emblem-White.png");
                JLabel logoLabel = new JLabel(companyLogo);

                //Maze Name Label and Button
                name = new JLabel("Maze Name: ");
                name.setForeground(Color.WHITE);
                JTextField maze_name = new JTextField("Maze");
                maze_name.setPreferredSize(new Dimension(50, 25));

                //Width and Height Labels and Buttons
                JLabel width = new JLabel("Width: ");
                width.setForeground(Color.WHITE);
                width_text = new JTextField("25");
                width_text.setPreferredSize(new Dimension(50, 25));
                JLabel height = new JLabel("Height: ");
                height.setForeground(Color.WHITE);
                height_text = new JTextField("25");
                height_text.setPreferredSize(new Dimension(50, 25));

                //Bottom buttons
                btnCreate = createButtons("Create!");
                btnExport = createButtons("Export");
                btnImport = createButtons("Import");
                btnSave = createButtons("Save as...");
                btnGenerate = createButtons("Generate");
                showGrid = new JCheckBox("Show Grid", true);
                showGrid.addActionListener(this);

                setStyle(name);
                setStyle(height);
                setStyle(width);
                setStyle(showGrid);

                GridBagConstraints constraints = new GridBagConstraints();
                constraints.fill = GridBagConstraints.NONE;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.insets = new Insets(20,20,20,20);
                addToPanel(borderSpot, logoLabel, constraints, 0,0,2,1);
                addToPanel(borderSpot, name, constraints, 0, 1, 1, 1);
                addToPanel(borderSpot, maze_name, constraints, 1, 1, 1, 1);
                addToPanel(borderSpot, width, constraints, 0,2,1,1);
                addToPanel(borderSpot, width_text, constraints, 1,2,1,1);
                addToPanel(borderSpot, height, constraints, 0,3,1,1);
                addToPanel(borderSpot, height_text, constraints, 1,3,1,1);
                addToPanel(borderSpot, btnCreate, constraints, 0,4,1,1);
                addToPanel(borderSpot, btnGenerate, constraints, 1,4,1,1);
                addToPanel(borderSpot, btnImport, constraints, 0,5,1,1);
                addToPanel(borderSpot, btnExport, constraints, 1,5,1,1);
                addToPanel(borderSpot, btnSave, constraints, 1,6,1,1);
                addToPanel(borderSpot, showGrid, constraints, 0, 6, 1, 1);


        }


        /**
         * Create buttons and returns the new button back with event trigger and set sizes
         * @param name of label on button
         * @return button
         */
        private JButton createButtons(String name)
        {
                Dimension buttonsSize = new Dimension(120,32);
                JButton button = new JButton(name);
                button.setPreferredSize(buttonsSize);
                button.addActionListener(this);
                setStyle(button);
                return button;
        }

        /**
         *
         * A convenience method to add a component to given grid bag
         * layout locations. Code due to Cay Horstmann.
         *
         * @param component the component to add
         * @param constraints the grid bag constraints to use
         * @param x the x grid position
         * @param y the y grid position
         * @param w the grid width of the component
         * @param h the grid height of the component
         */
        private void addToPanel(JPanel panel,Component component,
                                GridBagConstraints constraints,
                                int x, int y, int w, int h) {
                constraints.gridx = x;
                constraints.gridy = y;
                constraints.gridwidth = w;
                constraints.gridheight = h;
                panel.add(component, constraints);
        }

        /**
         * Sets font style to Sans Serif, bold and size 16
         * @param item The component to be set to this style.
         * @return Component with font style set.
         */
        private Component setStyle(Component item){
                item.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                return item;
        }
}