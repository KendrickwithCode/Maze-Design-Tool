import javax.swing.*;
import java.awt.*;

/**
 * Graphic User Interface Base.
 * This is where all of the components for the GUI Tools and GUI Maze Sit ontop off.
 */
public class GUI extends JFrame{

    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");


    /**
     * GUI Constructor. Initializes Swing frame for application
     */
    public GUI(){
        initializeFrame();
    }

    private void initializeFrame(){

        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setResizable(false);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        //Set Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem export = new JMenuItem("Export");
        JMenuItem exit = new JMenuItem("Exit");
        file.add(load);
        file.add(export);
        file.add(save);
        file.add(exit);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        setIconImage(icon.getImage());

        JPanel borderbottom = createPanel(Color.DARK_GRAY);
        JPanel bordertop = createPanel(Color.DARK_GRAY);
        JPanel borderleft = createPanel(Color.DARK_GRAY);
        JPanel borderight = createPanel(Color.DARK_GRAY);

        setResizable(false);
        GUI_Tools menu = new GUI_Tools(borderleft, this); //<-- Call GUI_Tools to set menu items on left side

        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);

        setVisible(true);
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


