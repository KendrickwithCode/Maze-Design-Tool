import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.io.File;

/**
 * Graphic User Interface Base.
 * This is where all of the components for the GUI Tools and GUI Maze Sit on top off.
 */
public class GUI extends JFrame implements ActionListener, Runnable {

    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");

    private JMenuItem load, save, export,fullScr, exit,logoChange,kidsStart, kidsFinish;


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();



        if(src==load)
        {
            JOptionPane.showMessageDialog(null,"Load from Database.","Load",JOptionPane.INFORMATION_MESSAGE);
        }
        if(src==save)
        {
            JOptionPane.showMessageDialog(null,"Save to Database.","Save",JOptionPane.INFORMATION_MESSAGE);
        }
        if(src==export)
        {
            JOptionPane.showMessageDialog(null,"Export to Jpeg.","Export",JOptionPane.INFORMATION_MESSAGE);
        }
        if(src==fullScr)
        {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        if(src==exit)
        {
            this.dispose();
        }

        if(src==logoChange)
        {
            if(MazeLogoTools.getCurrentMaze() != null) {
                if(MazeLogoTools.getCurrentMaze().getMazeType().equalsIgnoreCase("ADULT")) {
                    Maze currentMaze = MazeLogoTools.getCurrentMaze();
                    imageChange(currentMaze.getLogoBlockIndex(), currentMaze);
                }
            }

        }
        if(src==kidsStart)
        {
            if(MazeLogoTools.getCurrentMaze() != null) {
                if(MazeLogoTools.getCurrentMaze().getMazeType().equalsIgnoreCase("KIDS")) {
                    Maze currentMaze = MazeLogoTools.getCurrentMaze();
                    imageChange(currentMaze.getKidsStartIndex(), currentMaze);
                }
            }
        }
        if(src==kidsFinish)
        {
            if(MazeLogoTools.getCurrentMaze() != null) {
                if(MazeLogoTools.getCurrentMaze().getMazeType().equalsIgnoreCase("KIDS")) {
                    Maze currentMaze = MazeLogoTools.getCurrentMaze();
                    imageChange(currentMaze.getKidsFinishIndex(), currentMaze);
                }
            }
        }
    }

    @Override
    public void run() {

    }

    /**
     * Changes images on the maze via an image selection
     * @param blockIndex index of the block for image to be changed. (must be a logo block or a kids logo block).
     * @param currentMaze the current maze that is being worked on.
     */
    private void imageChange(int blockIndex, Maze currentMaze){
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Image Files (*.png | *.jpg | *.bmp)", "png","jpg","bmp"));

        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File imageFile = fc.getSelectedFile();

            LogoBlock current = (LogoBlock) currentMaze.getMazeMap().get(blockIndex);

            current.setPictureFile(imageFile.getPath());
            maze.renderMaze(true, false);
        }
    }



    /**
     * GUI Constructor. Initializes Swing frame for application
     */
    public GUI(){
        initializeFrame();
    }

    private void initializeFrame(){
        int fileMenuItemWith = 120;
        int editMenuItemWith = 180;
        int menuItemHeight = 20;

        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setResizable(true);

        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        //Set File Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");

        load = menuItemFactory("Load",fileMenuItemWith,menuItemHeight);
        save = menuItemFactory("Save",fileMenuItemWith,menuItemHeight);
        export = menuItemFactory("Export",fileMenuItemWith,menuItemHeight);
        fullScr = menuItemFactory("Go Full Screen",fileMenuItemWith,menuItemHeight);
        exit = menuItemFactory("Exit",fileMenuItemWith,menuItemHeight);

        file.add(load);
        file.add(save);
        file.addSeparator();
        file.add(export);
        file.addSeparator();
        file.add(fullScr);
        file.addSeparator();
        file.add(exit);
        menuBar.add(file);
        this.setJMenuBar(menuBar);

        // Set Edit Toolbar
        JMenu edit = new JMenu("Edit");

        logoChange = menuItemFactory("Change Logo",editMenuItemWith,menuItemHeight);
        kidsStart = menuItemFactory("Change Kids Start Image",editMenuItemWith,menuItemHeight);
        kidsFinish = menuItemFactory("Change Kids Finish Image",editMenuItemWith,menuItemHeight);

        edit.add(logoChange);
        edit.addSeparator();
        edit.add(kidsStart);
        edit.add(kidsFinish);

        menuBar.add(edit);
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
     * Makes menu items.
     * @param name  name of the menu item
     * @param width width of the menu item
     * @param height height of the menu item
     * @return a new drop down menu item.
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
    public void generateNewMaze( int width, int height, String name, boolean generate, String mazeType) throws Exception {
        // Checks if GUI already contains a maze and removes it to be replaced with new maze
        Component[] components = this.getContentPane().getComponents();
        for ( Component comp : components){
            if ( comp instanceof JScrollPane) this.getContentPane().remove(comp);
        }
        // Create new maze and add to GUI using JScrollPane for larger mazes
        maze = new GUI_Maze(new Maze(width, height, name, mazeType), generate);
        this.getContentPane().add(new JScrollPane(maze));
        this.revalidate();
    }

    public void setGrid(boolean toggle){
        maze.renderMaze(toggle, true);
    }

    public boolean getGrid(){
        return maze.getGrid();
    }

}


