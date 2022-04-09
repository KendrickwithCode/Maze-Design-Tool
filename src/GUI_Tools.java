import javax.swing.*;
import java.awt.*;

/**
 * Constructs and initialises Menu items for the GUI
 */
public class GUI_Tools extends JFrame{

        private JButton btnCreate, btnExport, btnImport, btnSave;
        private JTextField width_text, height_text, maze_name;
        private ImageIcon companyLogo;
        private JLabel width, height, name;

        /**
         * Constructs and initialises Menu items for the GUI
         * @param borderComponent Determines the location of the menu with BorderLayout.
         *                        Example - A panel situated on BorderLayout.LINE_START will
         *                        put the menu items on the left side.
         *
         */
        public GUI_Tools(JPanel borderComponent){
                toolsMenu(borderComponent);
        }

        /**
         * Add the menu options for the Maze
         * Is called automatically from constructor
         */
        private void toolsMenu(JPanel borderSpot){
                GridBagLayout layout = new GridBagLayout();
                borderSpot.setLayout(layout);

                //Logo image
                companyLogo = new ImageIcon("img/MazeCraft-Emblem-White.png");
                JLabel logoLabel = new JLabel(companyLogo);

                //Maze Name Label and Button
                name = new JLabel("Maze Name: ");
                name.setForeground(Color.WHITE);
                maze_name = new JTextField("Maze");
                maze_name.setPreferredSize(new Dimension(50, 25));

                //Width and Height Labels and Buttons
                width = new JLabel("Width: ");
                width.setForeground(Color.WHITE);
                width_text = new JTextField("100");
                width_text.setPreferredSize(new Dimension(50, 25));
                height = new JLabel("Height: ");
                height.setForeground(Color.WHITE);
                height_text = new JTextField("100");
                height_text.setPreferredSize(new Dimension(50, 25));

                //Bottom buttons
                btnCreate = new JButton("Create!");
                btnExport = new JButton("Export");
                btnImport = new JButton("Import");
                btnSave = new JButton("Save as...");

                setStyle(name);
                setStyle(height);
                setStyle(width);
                setStyle(btnExport);
                setStyle(btnCreate);
                setStyle(btnImport);
                setStyle(btnSave);

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
                addToPanel(borderSpot, btnExport, constraints, 1,4,1,1);
                addToPanel(borderSpot, btnImport, constraints, 0,5,1,1);
                addToPanel(borderSpot, btnSave, constraints, 1,5,1,1);
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
         * @param temp The component to be set to this style.
         * @return Component with font style set.
         */
        private Component setStyle(Component temp){
                temp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                return temp;
        }
}