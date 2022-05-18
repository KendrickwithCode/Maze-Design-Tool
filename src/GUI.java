import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Graphic User Interface Base.
 * This is where all of the components for the GUI Tools and GUI Maze Sit ontop off.
 */
public class GUI extends JFrame implements ActionListener {

    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");
    public JMenuItem save, load, export, exit;
    public JSplitPane splitPane;
    public JLabel dbitems;
    MazeDB mazedata;


    /**
     * GUI Constructor. Initializes Swing frame for application
     */
    public GUI(MazeDB data) throws SQLException {
        this.mazedata = data;
        initializeFrame();
    }

    private void initializeFrame() throws SQLException {

        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setResizable(false);
        setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);

        //Set Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        export = new JMenuItem("Export");
        exit = new JMenuItem("Exit");
        save.addActionListener(this);
        export.addActionListener(this);
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

        dbitems = new JLabel();
        dbitems.setIcon(mazedata.getImage());
        dbitems.setVisible(true);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, borderleft, dbitems);
        splitPane.setDividerSize(10);
        splitPane.setContinuousLayout(false);
        splitPane.setOneTouchExpandable(true);

        Dimension minimumSize = new Dimension(100, 50);
        splitPane.setMinimumSize((minimumSize));
        splitPane.setPreferredSize(new Dimension(400, 400));


        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);
        this.getContentPane().add(splitPane, BorderLayout.LINE_END);
        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        //checks if the export button has been pressed and runs the jpgExport function if it has
        if (src == export) {
            try {
                if (maze != null)
                    System.out.println(jpgExport(maze)); //prints out the file path of the exported jpg
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (src == save) {
            try {
                String type = (String)GUI_Tools.mazeTypeComboBox.getSelectedItem();
                mazedata.addMaze(GUI_Tools.maze_name.getText(), type, GUI_Tools.author_name_text.getText(),
                        GUI_Tools.description_text.getText(), GUI_Tools.width_text.getText(), GUI_Tools.height_text.getText(), maze);
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //JOptionPane.showMessageDialog(null,"Save to Database.","Save",JOptionPane.INFORMATION_MESSAGE);
        }
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

    public void setGrid(boolean toggle) {
        maze.renderMaze(toggle, true);
    }

    public boolean getGrid(){
        return maze.getGrid();
    }

    /**
     * Creates a screenshot of the passed JFrame object and saves it to a temp file
     * adapted from: https://stackoverflow.com/a/10796047
     * @param Maze the GUI_Maze object that is to be exported.
     * @return file path of the exported jpg
     */
    public static String jpgExport(GUI_Maze Maze) {
        Rectangle rec = Maze.getBounds();
        File temp = null;
        BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
        Maze.paint(bufferedImage.getGraphics());

        try {
            // Create temp file
            temp = File.createTempFile("screenshot", ".png");

            // Use the ImageIO API to write the bufferedImage to a temporary file
            ImageIO.write(bufferedImage, "png", temp);

            // Delete temp file when program exits
            temp.deleteOnExit();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return temp.getPath();
    }


}