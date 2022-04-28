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

        private JButton btnCreate, btnExport, btnLoad, btnSave, btnGenerate;
        private JMenuBar menuBar;
        private JMenu menu;
        public JCheckBox showGrid;
        private JTextField width_text, height_text, maze_name;
        private JLabel width, height, name;
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

                else if (src==btnLoad)
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
                int textFieldSizeWidth = 50;
                int textFieldSizeHeight = 25;

                //Logo image
                ImageIcon companyLogo = new ImageIcon("img/MazeCraft-Emblem-White.png");
                JLabel logoLabel = new JLabel(companyLogo);

                //Maze Name Label and Button
                name = createLabels("Maze Name: ");
                maze_name = createTextFields("Maze",125,textFieldSizeHeight);

                //Maze Name Label and Button
                name = createLabels("Maze Name: ");
                maze_name = createTextFields("Maze",125,textFieldSizeHeight);

                //Width and Height Labels and Buttons
                width = createLabels("Width: ");
                width_text = createTextFields("25",textFieldSizeWidth,textFieldSizeHeight);

                height = createLabels("Height: ");
                height_text = createTextFields("25",textFieldSizeWidth,textFieldSizeHeight);

                //Bottom buttons
                btnCreate = createButtons("Create","Create a new blank maze.");
                btnExport = createButtons("Export","Export current maze to Jpeg.");
                btnLoad = createButtons("Load","Load maze from database.");
                btnSave = createButtons("Save","Save maze to database.");
                btnGenerate = createButtons("Generate","Generate a new maze..");
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
                addToPanel(borderSpot, btnLoad, constraints, 0,5,1,1);
                addToPanel(borderSpot, btnExport, constraints, 1,5,1,1);
                addToPanel(borderSpot, btnSave, constraints, 1,6,1,1);
                addToPanel(borderSpot, showGrid, constraints, 0, 6, 1, 1);


        }

        /**
         * Creates JTextfields components
         * @param defaultValue is the default value of the TextField.
         * @param width sets the width size of the TextField.
         * @param height set the height size of the TextField.
         * @return returns the new created JTextField.
         */
        private JTextField createTextFields(String defaultValue,int width, int height) {
                JTextField textField = new JTextField(defaultValue);
                textField.setPreferredSize(new Dimension(width,height));
                return textField;
        }

        /**
         * Creates JLabels components.
         * @param name is the name of the label.
         * @return returns the new created JLabel.
         */
        private JLabel createLabels(String name) {
                JLabel label = new JLabel();
                label.setForeground(Color.WHITE);
                label.setText(name);
                return label;
        }

        /**
         * Create buttons and returns the new button back with event trigger and set sizes
         * @param name of label on button
         * @return button
         */
        private JButton createButtons(String name,String toolTip)
        {
                Dimension buttonsSize = new Dimension(120,32);
                JButton button = new JButton(name);
                button.setPreferredSize(buttonsSize);
                button.addActionListener(this);
                setStyle(button);
                button.createToolTip();
                button.setToolTipText(toolTip);
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