import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class GUI extends JFrame{

    public JPanel panel;
    private JPanel borderleft, borderight, bordertop, borderbottom;
    private JScrollPane scrollPane;
    private GUI_Tools menu;
    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");


    public GUI(){
        initializeFrame();
    }

    private void initializeFrame(){

        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        //Set Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem saveAs = new JMenuItem("Save As...");
        file.add(saveAs);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        setIconImage(icon.getImage());

        borderbottom = createPanel(Color.DARK_GRAY);
        bordertop = createPanel(Color.DARK_GRAY);
        borderleft = createPanel(Color.DARK_GRAY);
        borderight = createPanel(Color.DARK_GRAY);

        setResizable(false);
        menu = new GUI_Tools(borderleft,this); //<-- Call GUI_Tools to set menu items on left side


        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);



        setVisible(true);
    }

    public GUI_Maze getMaze() {
        return maze;
    }

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

}


