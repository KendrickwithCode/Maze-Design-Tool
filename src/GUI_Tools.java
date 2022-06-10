import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

/**
 * Constructs and initialises Menu items for the GUI
 */
public class GUI_Tools extends JFrame implements ActionListener, Runnable {

        private JButton btnCreate, btnGenerate;
        public static JCheckBox showGrid, showSolution;
        public static JTextField width_text;
        public static JTextField height_text;
        public static JTextField maze_name;
        public static JTextField author_name_text;
        public static JTextArea  description_text;
        static JLabel solvableBool;
        static JLabel percentageTravelled;
        static JLabel deadEndCount;
        public static JComboBox<String> mazeTypeComboBox;
        private static GUI mainGui;
        private String mazeType;

        @Override
        public void actionPerformed(ActionEvent e) {
                Object src = e.getSource();

                if (src == btnCreate || src == btnGenerate) {
                        try {
                                //generateNewMaze executed first. Will throw exception if invalid input is in the fields and not execute the rest in the try block
                                mainGui.generateNewMaze(Integer.parseInt(width_text.getText()),
                                        Integer.parseInt(height_text.getText()), maze_name.getText(), src != btnCreate, mazeType);
                                mainGui.enableCheckboxes(true);
                                setShowSolution();
                                setMazeStatsLabels();
                        } catch (IllegalArgumentException ex) {
                                JOptionPane.showMessageDialog(null,
                                        "You must enter an integer between 4 and 100 inclusive for both width and height to generate a maze.",
                                        "Invalid Dimension", JOptionPane.INFORMATION_MESSAGE);
                        }
                }

                else if (src == showGrid)
                {
                        if (mainGui.getGrid()){
                                try {
                                        mainGui.setGrid(false);
                                } catch (Exception ex) {
                                        ex.printStackTrace();
                                }
                        }
                        else {
                                try {
                                        mainGui.setGrid(true);
                                } catch (Exception ex) {
                                        ex.printStackTrace();
                                }
                        }
                        Maze.MazeTools.getCurrentGUIMaze().revalidate();
                        Maze.MazeTools.getCurrentGUIMaze().repaint();
                }
                else if (src == showSolution)
                {
                        setShowSolution();
                }

                else if (src == mazeTypeComboBox)
                {
                        mazeType = Objects.requireNonNull(mazeTypeComboBox.getSelectedItem()).toString();
                        if(Maze.MazeTools.getCurrentMaze() != null) {
                                Maze.MazeTools.getCurrentMaze().setMazeType(mazeType);
                        }
                }

                if(Maze.MazeTools.getCurrentMaze() != null) {
                        if (Maze.MazeTools.getCurrentMaze().getSolvable()) {
                                solvableBool.setBackground(Color.GREEN);
                                solvableBool.setText("True");
                        } else {
                                solvableBool.setBackground(Color.RED);
                                solvableBool.setText("False");
                        }
                }
        }

        /**
         * Clears Solvable, Solution Travels, Dead Ends text on the bottom left
         * As well as Date Created and Last Edited text on the top right.
         */
        public static void clearStats(){
                percentageTravelled.setText("0%");
                deadEndCount.setText("0%");
                solvableBool.setText("");
                mainGui.lastEdited.setText("");
                mainGui.dateCreated.setText("");
        }

        /**
         * Sets Show Solution label.
         */
        public static void setShowSolution() {
                GUI_Maze.mazePanel.setRenderSolution(showSolution.isSelected());
        }

        /**
         * Sets the maze stats labels in the GUI
         */
        public static void setMazeStatsLabels() {
                GUI_Maze.mazePanel.setSolvableLabel(solvableBool);
                GUI_Maze.mazePanel.setPercentageDeadEndLabel(deadEndCount);
                GUI_Maze.mazePanel.setPercentageTravelledLabel(percentageTravelled);
        }

        /**
         * Constructs and initialises Menu items for the GUI
         * @param borderComponent Determines the location of the menu with BorderLayout.
         *                        Example - A panel situated on BorderLayout.LINE_START will
         *                        put the menu items on the left side.
         *
         */
        public GUI_Tools(JPanel borderComponent, GUI mainGUI){
                toolsMenu(borderComponent);
                mainGui = mainGUI;
                this.mazeType = "Adult";
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

                //Maze Type Selector
                JLabel mazeType_text = createLabels("Maze Type: ");
                String[] mazeOptions = {"Adult","Kids"};
//                mazeTypeComboBox = new JComboBox<>(mazeOptions);
                mazeTypeComboBox = createComboBox(mazeOptions,textFieldSizeWidth,textFieldSizeHeight);
//                mazeTypeComboBox.setBounds(80,50,140,20);


                //Maze Name Label and Button
                JLabel name = createLabels("Maze Name: ");
                maze_name = createTextFields("Maze",125,textFieldSizeHeight);
                JLabel author_name = createLabels("Author Name: ");
                author_name_text = createTextFields("", 125,textFieldSizeHeight);

                JLabel description = createLabels("Maze Description: ");
                description_text = new JTextArea();
                description_text.setLineWrap(true);
                description_text.setWrapStyleWord(true);
                description_text.setEditable(true);
                description_text.setCaretColor(Color.WHITE);
                JScrollPane description_pane = new JScrollPane(description_text);
                description_pane.setPreferredSize(new Dimension(250,150));
                description_pane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));


                //Width and Height Labels and Buttons
                JLabel width = createLabels("Width: ");
                width_text = createTextFields("25",textFieldSizeWidth,textFieldSizeHeight);

                JLabel height = createLabels("Height: ");
                height_text = createTextFields("25",textFieldSizeWidth,textFieldSizeHeight);

                //Bottom buttons
                btnCreate = createButtons("Create","Create a new blank maze.");
                btnGenerate = createButtons("Generate","Generate a new filled maze.");
                //Show Grid check box
                showGrid = new JCheckBox("Show Grid", true);
                showGrid.addActionListener(this);
                showGrid.setEnabled(false);
                //Show Grid check box
                showSolution = new JCheckBox("Show Solution", false);
                showSolution.addActionListener(this);
                showSolution.setEnabled(false);

                JLabel solvable = createLabels("Solvable:");
                solvable.setHorizontalAlignment(SwingConstants.RIGHT);
                solvableBool = createLabels("");
                solvableBool.setHorizontalAlignment(SwingConstants.LEFT);
                JLabel travelled = createLabels("Solution Travels:");
                travelled.setHorizontalAlignment(SwingConstants.RIGHT);
                percentageTravelled = createLabels("0%");
                percentageTravelled.setHorizontalAlignment(SwingConstants.LEFT);
                JLabel deadEnds = createLabels("Dead Ends:");
                deadEnds.setHorizontalAlignment(SwingConstants.RIGHT);
                deadEndCount = createLabels("0%");
                deadEndCount.setHorizontalAlignment(SwingConstants.LEFT);

                setStyle(mazeType_text);
                setStyle(mazeTypeComboBox);
                setStyle(name);
                setStyle(height);
                setStyle(width);
                setStyle(showGrid);
                setStyle(showSolution);
                setStyle(author_name);
                setStyle(description);
                setStyle(showGrid);
                setStyle(maze_name);
                setStyle(author_name_text);
                setStyle(description_text);
                setStyle(width_text);
                setStyle(height_text);
                setStyle(solvable);
                setStyle(travelled);
                setStyle(deadEnds);
                setStyle(solvableBool);
                setStyle(percentageTravelled);
                setStyle(deadEndCount);

                GridBagConstraints constraints = new GridBagConstraints();
                constraints.fill = GridBagConstraints.NONE;
                constraints.anchor = GridBagConstraints.CENTER;
                constraints.insets = new Insets(10,20,13,20);

                //For description text, need to anchor the text box to the top of the grid.
                GridBagConstraints descriptionConstraints = new GridBagConstraints();
                descriptionConstraints .anchor = GridBagConstraints.PAGE_START;

                addToPanel(borderSpot, logoLabel, constraints, 0,0,2);
                addToPanel(borderSpot, mazeType_text, constraints , 0 , 1 ,1);
                addToPanel(borderSpot, mazeTypeComboBox, constraints , 1 , 1 ,1);
                addToPanel(borderSpot, name, constraints, 0, 2, 1);
                addToPanel(borderSpot, maze_name, constraints, 1, 2, 1);
                addToPanel(borderSpot, author_name, constraints, 0, 3, 1);
                addToPanel(borderSpot, author_name_text, constraints, 1, 3, 1);
                addToPanel(borderSpot, description, constraints, 0, 4, 2);
                addToPanel(borderSpot, description_pane, descriptionConstraints , 0, 5, 2);
                addToPanel(borderSpot, width, constraints, 0,6,1);
                addToPanel(borderSpot, width_text, constraints, 1,6,1);
                addToPanel(borderSpot, height, constraints, 0,7,1);
                addToPanel(borderSpot, height_text, constraints, 1,7,1);
                addToPanel(borderSpot, btnGenerate, constraints, 0,8,2);
                addToPanel(borderSpot, btnCreate, constraints, 0,9,2);

                constraints.insets = new Insets(20,20,5,20);
                addToPanel(borderSpot, showGrid, constraints, 0, 10, 2);

                constraints.insets = new Insets(5,20,20,20);
                addToPanel(borderSpot, showSolution, constraints, 0, 11, 2);

                constraints.insets = new Insets(5,35,5,20);
                constraints.fill = GridBagConstraints.HORIZONTAL;
                addToPanel(borderSpot, solvable, constraints, 0, 12, 1);
                addToPanel(borderSpot, travelled, constraints, 0, 13, 1);
                addToPanel(borderSpot, deadEnds, constraints, 0, 14, 1);
                addToPanel(borderSpot, deadEndCount, constraints, 1, 14, 1);
                addToPanel(borderSpot, solvableBool, constraints, 1, 12, 1);
                addToPanel(borderSpot, percentageTravelled, constraints, 1, 13, 1);
        }

        private JComboBox<String> createComboBox(String[] options,int width, int height) {
                JComboBox<String> combo = new JComboBox<>(options);
                combo.setPreferredSize(new Dimension(width + 80,height));
                combo.setUI(ColorArrowUI.createUI(combo));
                combo.addActionListener(this);
                return combo;
        }

        /**
         * Creates JTextField components
         * @param defaultValue is the default value of the TextField.
         * @param width sets the width size of the TextField.
         * @param height set the height size of the TextField.
         * @return returns the new created JTextField.
         */
        private JTextField createTextFields(String defaultValue,int width, int height) {
                JTextField textField = new JTextField(defaultValue);
                textField.setCaretColor(Color.WHITE);
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
         * A convenience method to add a component to given grid bag
         * layout locations. Code due to Cay Horstmann.
         *
         * @param component   the component to add
         * @param constraints the grid bag constraints to use
         * @param x           the x grid position
         * @param y           the y grid position
         * @param w           the grid width of the component
         */
        private void addToPanel(JPanel panel,Component component,
                                GridBagConstraints constraints,
                                int x, int y, int w) {
                constraints.gridx = x;
                constraints.gridy = y;
                constraints.gridwidth = w;
                constraints.gridheight = 1;
                panel.add(component, constraints);
        }

        /**
         * Sets font style to Sans Serif, bold and size 16
         *
         * @param item The component to be set to this style.
         */
        public static void setStyle(Component item){
                item.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                item.setBackground(Color.DARK_GRAY);
                item.setForeground(Color.WHITE);
        }

        @Override
        public void run() {}
}