import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Graphic User Interface Base.
 * This is where all of the components for the GUI Tools and GUI Maze Sit on top off.
 */
public class GUI extends JFrame implements ActionListener, Runnable {

    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");
    private JMenuItem load, save, export, exit;
    private GUI_Tools menu;
    MazeDB mazedata;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src==load)
        {
            JOptionPane.showMessageDialog(null,"Load from Database.","Load",JOptionPane.INFORMATION_MESSAGE);
        }
        if(src==save)
        {
            try {
                mazedata.addMaze(menu.maze_name.getText(), menu.author_name_text.getText(),
                        menu.description_text.getText(), menu.width_text.getText(), menu.height_text.getText());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            //JOptionPane.showMessageDialog(null,"Save to Database.","Save",JOptionPane.INFORMATION_MESSAGE);
        }
        if(src==export)
        {
            JOptionPane.showMessageDialog(null,"Export to Jpeg.","Export",JOptionPane.INFORMATION_MESSAGE);
        }
        if(src==exit)
        {
            this.dispose();
        }

    }

    @Override
    public void run() {

    }

    /**
     * GUI Constructor. Initializes Swing frame for application. Creates Connection to DB.
     */
    public GUI(){
        mazedata = new MazeDB();
        initializeFrame();
    }

    private void initializeFrame(){
        int menuItemWith = 120;
        int menuItemHeight = 20;

        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setResizable(false);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        //Set Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        load = menuItemFactory("Load",menuItemWith,menuItemHeight);
        save = menuItemFactory("Save",menuItemWith,menuItemHeight);
        export = menuItemFactory("Export",menuItemWith,menuItemHeight);
        exit = menuItemFactory("Exit",menuItemWith,menuItemHeight);

        JSeparator separator1 = new JSeparator();
        JSeparator separator2 = new JSeparator();

        file.add(load);
        file.add(save);
        file.add(separator1);
        file.add(export);
        file.add(separator2);
        file.add(exit);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        setIconImage(icon.getImage());

        JPanel borderbottom = createPanel(Color.DARK_GRAY);
        JPanel bordertop = createPanel(Color.DARK_GRAY);
        JPanel borderleft = createPanel(Color.DARK_GRAY);
        JPanel borderight = createPanel(Color.DARK_GRAY);

        setResizable(false);
        menu = new GUI_Tools(borderleft, this); //<-- Call GUI_Tools to set menu items on left side

        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);

        setVisible(true);
    }

    /**
     * Makes menu items.
     * @param name  name of the menu item
     * @param width width of the menu item
     * @param height height of the menu item
     * @return
     */
    private JMenuItem menuItemFactory(String name, int width, int height)
    {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setPreferredSize(new Dimension(width,height));
        menuItem.addActionListener(this);
        return menuItem;
    }

    /**
     * Returns current GUI_Maze
     * @return current GUI_maze
     */
    public GUI_Maze getMaze() {
        return maze;
    }

    /**
     * Sets current maze GUI
     * @param maze current GUI_Maze
     */
    public void setMaze(GUI_Maze maze) {
        this.maze = maze;
    }

    private JPanel createPanel(Color c){
        JPanel temp = new JPanel();
        temp.setBackground(c);
        return temp;
        }

    /**
     * Creates new maze and displays it in the GUI
     * @param width Width of maze (in blocks)
     * @param height Height of maze (in blocks)
     * @param name Name of maze
     * @param generate true to generate maze or false to create blank canvas
     */
    public void generateNewMaze( int width, int height, String name, boolean generate){
        // Checks if GUI already contains a maze and removes it to be replaced with new maze
        Component[] components = this.getContentPane().getComponents();
        for ( Component comp : components){
            if ( comp instanceof JScrollPane) this.getContentPane().remove(comp);
        }
        // Create new maze and add to GUI using JScrollPane for larger mazes
        maze = new GUI_Maze(new Maze(width, height, name), generate);
        this.getContentPane().add(new JScrollPane(maze));
        this.revalidate();
    }

    public void setGrid(boolean toggle){
        maze.renderMaze(toggle);
    }

   public boolean getGrid(){
       return maze.getGrid();
    }

}


