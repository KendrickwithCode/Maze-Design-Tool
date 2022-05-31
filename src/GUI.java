import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Graphic User Interface Base.
 * This is where all the components for the GUI Tools and GUI Maze Sit on top off.
 */
public class GUI extends JFrame implements ActionListener, Runnable {

    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");
    public JSplitPane splitPane;
    public JList dbitems;
    public JLabel leftpane;
    MazeDB mazedata;
    DBSource mazeDB;
    private JButton clear;
    private JMenuItem load, save, export,fullScr, windowScr, exit,logoChange,kidsStart, kidsFinish;
    private DefaultListModel listModel;


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
                if (GUI_Tools.author_name_text.getText() == null || GUI_Tools.author_name_text.getText().length() == 0){
                    JOptionPane.showMessageDialog(null,
                            "Please Enter an Author's Name before Saving","Okay",JOptionPane.INFORMATION_MESSAGE);
                }
                else{
                    String type = (String)GUI_Tools.mazeTypeComboBox.getSelectedItem();
                    mazeDB.addMaze(GUI_Tools.maze_name.getText(), type, GUI_Tools.author_name_text.getText(),
                            GUI_Tools.description_text.getText(), GUI_Tools.width_text.getText(), GUI_Tools.height_text.getText(), maze);
                    mazedata.updateList(listModel);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //JOptionPane.showMessageDialog(null,"Save to Database.","Save",JOptionPane.INFORMATION_MESSAGE);
        }
        else if (src == load){
//            try {
//                clearMaze();
//                Maze load = mazedata.getMaze();
//                GUI_Maze loadedMaze = new GUI_Maze(load, false);
//                GUI_Tools.maze_name.setText(load.getMazeName());
//                GUI_Tools.author_name_text.setText(load.getAuthorName());
//                GUI_Tools.description_text.setText(load.getMazeDescription());
//                GUI_Tools.height_text.setText(load.getHeight());
//                GUI_Tools.width_text.setText(load.getWidth());
//                // Add getters for description, etc.
//               // GUI_Tools.author_name_text.setText(load.getAuthorName());
//                this.getContentPane().add(new JScrollPane(loadedMaze));
//                this.revalidate();
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
        }
        if(src==fullScr)
        {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        if(src==windowScr)
        {
            setWindowSize();
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
        if(src==clear){
            if(MazeLogoTools.getCurrentGUIMaze() != null){
                clearMaze();
                dbitems.clearSelection();
                this.repaint();
                this.revalidate();
            }
        }
    }

    /**
     * Adds a listener to the name list
     */
    private void addNameListListener(ListSelectionListener listener) {
        dbitems.addListSelectionListener(listener);
    }



    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different name is selected from the list.
     */
    private class NameListListener implements ListSelectionListener {

        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (dbitems.getSelectedValue() != null) {
                try {
                    display(mazedata.get(dbitems.getSelectedValue()));
                    GUI_Tools.setShowSolution();
                    GUI_Tools.setMazeStatsLabels();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void display(Maze maze) throws Exception {
        if (maze != null) {
            clearMaze();
            GUI_Tools.maze_name.setText(maze.getMazeName());
            GUI_Tools.author_name_text.setText(maze.getAuthorName());
            GUI_Tools.description_text.setText(maze.getMazeDescription());
            GUI_Tools.height_text.setText(Integer.toString(maze.getHeight()));
            GUI_Tools.width_text.setText(Integer.toString(maze.getWidth()));
            Maze load = mazeDB.getGUIMaze(maze.getMazeName());
            //MazeLogoTools.setCurrentMaze(maze);
            GUI_Maze loadedMaze = new GUI_Maze(load, false);
            setMaze(loadedMaze);
            //MazeLogoTools.setCurrentGUIMaze(loadedMaze);
            leftpane.add(new JScrollPane(loadedMaze));
            this.revalidate();
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
    private void imageChange(int blockIndex, Maze currentMaze) {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Image Files (*.png | *.jpg | *.bmp)", "png", "jpg", "bmp"));

        int returnVal = fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File imageFile = fc.getSelectedFile();

            LogoBlock current = (LogoBlock) currentMaze.getMazeMap().get(blockIndex);

            current.setPictureFile(imageFile.getPath());

            maze.renderBlocks();
        }
    }


    /**
     * GUI Constructor. Initializes Swing frame for application
     */
    public GUI(MazeDB data) throws SQLException {
        this.mazedata = data;
        listModel = new DefaultListModel();
        mazeDB = new DBSource();
        initializeFrame();
    }

    private void initializeFrame() throws SQLException {
        int fileMenuItemWith = 120;
        int editMenuItemWith = 180;
        int viewMenuItemWith = 120;
        int menuItemHeight = 20;

        leftpane = new JLabel();
        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowSize();
        leftpane.setLayout(new BorderLayout());


        //Set File Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        load = new JMenuItem("Load");
        save = new JMenuItem("Save");
        export = new JMenuItem("Export");
        exit = new JMenuItem("Exit");
        save.addActionListener(this);
        export.addActionListener(this);
        load.addActionListener(this);
        load = menuItemFactory("Load",fileMenuItemWith,menuItemHeight);
        save = menuItemFactory("Save",fileMenuItemWith,menuItemHeight);
        export = menuItemFactory("Export to Jpg",fileMenuItemWith,menuItemHeight);
        exit = menuItemFactory("Exit",fileMenuItemWith,menuItemHeight);

        file.add(load);
        file.add(save);
        file.addSeparator();
        file.add(export);
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


        // Set Edit View
        JMenu view = new JMenu("View");

        fullScr = menuItemFactory("Full Screen",viewMenuItemWith,menuItemHeight);
        windowScr = menuItemFactory("Window",viewMenuItemWith,menuItemHeight);
        view.add(fullScr);
        view.add(windowScr);

        menuBar.add(view);
        this.setJMenuBar(menuBar);

        setIconImage(icon.getImage());

        JPanel borderbottom = createPanel(Color.DARK_GRAY);
        JPanel bordertop = createPanel(Color.DARK_GRAY);
        JPanel borderleft = createPanel(Color.DARK_GRAY);
        JPanel borderight = createPanel(Color.DARK_GRAY);


        setResizable(false);
        new GUI_Tools(borderleft, this); //<-- Call GUI_Tools to set menu items on left side

        mazedata.updateList(listModel);
        dbitems = new JList(listModel);
        addNameListListener(new NameListListener());
        dbitems.setVisible(true);

        JPanel controls = new JPanel(new BorderLayout());
        clear = new JButton("Clear");
        JLabel date = new JLabel("Date Created", SwingConstants.CENTER);
        JLabel edited = new JLabel("Last Edited", SwingConstants.CENTER);
        edited.setBorder(new EmptyBorder(10, 0, 0, 0));
        date.setBorder(new EmptyBorder(10, 0, 0, 0));
        //clear.setBorder(new EmptyBorder(10, 0, 10, 0));
        controls.add(clear, BorderLayout.SOUTH);
        controls.add(date, BorderLayout.NORTH);
        controls.add(edited, BorderLayout.CENTER);
        GUI_Tools.setStyle(dbitems);
        GUI_Tools.setStyle(clear);
        GUI_Tools.setStyle(controls);
        GUI_Tools.setStyle(date);
        GUI_Tools.setStyle(edited);

        clear.addActionListener(this);
        JSplitPane embed = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controls, dbitems);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftpane, embed);

        //Makes the split pane unmovable
        embed.setEnabled(true);
        embed.setDividerLocation(200);
        splitPane.setEnabled(false);

        splitPane.setDividerLocation(1250);
        splitPane.setOneTouchExpandable(false);
        splitPane.setContinuousLayout(true);

        leftpane.add(bordertop, BorderLayout.PAGE_START);
        leftpane.add(borderleft, BorderLayout.LINE_START);
        leftpane.add(borderbottom, BorderLayout.PAGE_END);
        //leftpane.add(borderight, BorderLayout.LINE_END);
        add(splitPane);
        setVisible(true);
    }



    private void setWindowSize(){
        setSize(1500, 1000);
        setResizable(false);
        this.setLocationRelativeTo(null);
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
     * Clears current Maze on screen.
     */
    public void clearMaze(){
        // Checks if GUI already contains a maze and removes it to be replaced with new maze
        GUI_Tools.maze_name.setText("Maze");
        GUI_Tools.height_text.setText("25");
        GUI_Tools.width_text.setText("25");
        GUI_Tools.author_name_text.setText("");
        GUI_Tools.description_text.setText("");
        Component[] components = leftpane.getComponents();
        for ( Component comp : components){
            if ( comp instanceof JScrollPane) leftpane.remove(comp);
        }
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
        Component[] components = leftpane.getComponents();
        for ( Component comp : components){
            if ( comp instanceof JScrollPane) leftpane.remove(comp);
        }
        // Create new maze and add to GUI using JScrollPane for larger mazes
        maze = new GUI_Maze(new Maze(width, height, name, mazeType,
                GUI_Tools.description_text.getText(), GUI_Tools.author_name_text.getText()), generate);
        leftpane.add(new JScrollPane(maze));
        this.revalidate();
    }

    public void setGrid(boolean toggle) throws NamingException {
        maze.renderMaze(toggle, true);
    }

    public boolean getGrid(){
        return maze.getGrid();
    }


//    final JFileChooser fc = new JFileChooser();
//        fc.setFileFilter(new FileNameExtensionFilter("Image Files (*.png | *.jpg | *.bmp)", "png", "jpg", "bmp"));


    /**
     * Creates a screenshot of the passed JPanel object and saves it
     * adapted from: https://stackoverflow.com/a/10796047
     * @param mazePanel the GUI_Maze object that is to be exported.
     */
    private void jpgExport(JPanel mazePanel) {

        Rectangle rec = mazePanel.getBounds();
        File image;

        BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
        mazePanel.paint(bufferedImage.getGraphics());
        //Jfile chooser code
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter(".jpg", "jpg"));
        fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showSaveDialog(this);

        try {
            if(returnVal == JFileChooser.APPROVE_OPTION){
                // Create file if successful
                image = fc.getSelectedFile();

                if(!image.getName().endsWith(".jpg"))
                {
                    image = new File(image.getParent(),image.getName() + ".jpg");
                }

                // Use the ImageIO API to write the bufferedImage to the selected file
                ImageIO.write(bufferedImage, "png", image);

                JOptionPane.showMessageDialog(null,image.getName(),"Exported image of current maze view at:",JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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