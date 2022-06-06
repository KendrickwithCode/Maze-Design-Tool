import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
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
    public JList<Object> dbitems;
    public JLabel leftPane, date, edited, lastEdited, dateCreated;
    MazeDB mazeData;
    DBSource mazeDB;
    private JButton reset, delete;
    private JMenuItem save, export, fullScr, windowScr, exit;
    private final DefaultListModel<Object> listModel;


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        //checks if the export button has been pressed and runs the jpgExport function if it has
        if (src == export) {
            try {
                if (maze != null)
                    //Iterate over multiple selections from the list if bulk jpg saving.
                    for(Object obj : dbitems.getSelectedValuesList()){
                        display(mazeData.get(obj));
                        jpgExport(maze.getMazePanel());
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (src == save) {
            if(Maze.MazeTools.getCurrentGUIMaze() != null){
                saveMaze();
            }
            else{
                JOptionPane.showMessageDialog(null,
                        "Generate a Maze before Saving!","Okay",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if(src==fullScr)
        {
            this.setExtendedState(MAXIMIZED_BOTH);
            splitPane.setDividerLocation(4000);
        }
        if(src==windowScr)
        {
            setWindowSize();
            splitPane.setDividerLocation(1250);
        }
        if(src==exit)
        {
            this.dispose();
        }

        if(src== reset){
            if(Maze.MazeTools.getCurrentGUIMaze() != null){
                enableCheckboxes(false);
                enableButtons(false);
                clearMaze();
                dbitems.clearSelection();
                GUI_Tools.clearStats();
                this.repaint();
                this.revalidate();
            }
        }
        if(src==delete){
            if(Maze.MazeTools.getCurrentGUIMaze() != null){
                int delete = JOptionPane.showConfirmDialog
                        (null, "Are you sure you want to delete?", "WARNING", JOptionPane.YES_NO_OPTION);
                if(delete == JOptionPane.YES_OPTION){
                    mazeDB.deleteEntry(dbitems.getSelectedValue().toString());
                    try {
                        clearMaze();
                        enableCheckboxes(false);
                        enableButtons(false);
                        GUI_Tools.clearStats();
                        listModel.removeElement(dbitems.getSelectedValue());
                        mazeData.updateList(listModel);
                        Maze.MazeTools.deleteMazeObj();
                        this.repaint();
                        this.revalidate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Save or Update Maze details (depending on if the Maze Name already exists in the DB) to the Database.
     */
    private void saveMaze(){
        try {
            if (GUI_Tools.author_name_text.getText() == null || GUI_Tools.author_name_text.getText().length() == 0){
                JOptionPane.showMessageDialog(null,
                        "Please Enter an Author's Name before Saving","Okay",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                String type = (String)GUI_Tools.mazeTypeComboBox.getSelectedItem();
                //boolean is true if the entry is new to the db, false if it's an updating entry.
                boolean added = mazeDB.addMaze(GUI_Tools.maze_name.getText(), type, GUI_Tools.author_name_text.getText(),
                        GUI_Tools.description_text.getText(), GUI_Tools.width_text.getText(), GUI_Tools.height_text.getText());
                mazeData.updateList(listModel);
                if (!added){
                    lastEdited.setText(mazeDB.getLastEdited(GUI_Tools.maze_name.getText()));
                }
                //set list selection to new or updated entry
                dbitems.setSelectedIndex(listModel.indexOf(GUI_Tools.maze_name.getText()));
            }
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
        //JOptionPane.showMessageDialog(null,"Save to Database.","Save",JOptionPane.INFORMATION_MESSAGE);
    }
    /**
     * Adds a listener to the name list
     */
    private void addNameListListener( MouseListener e) {
        dbitems.addMouseListener(e);
    }

    /**
     * Enable Show Grid and Show Solution checkboxes
     * @param toggle True to activate checkboxes, False to grey them out.
     */
    public void enableCheckboxes(boolean toggle){
        GUI_Tools.showGrid.setEnabled(toggle);
        GUI_Tools.showSolution.setEnabled(toggle);
    }

    /**
     * Enable Delete Selection and Reset Selections buttons.
     * @param toggle True to activate buttons, False to grey them out.
     */
    public void enableButtons(boolean toggle){
        delete.setEnabled(toggle);
        reset.setEnabled(toggle);
    }

    /**
     * Display the Maze from the Database to the GUI including all details about the Maze in text fields.
     * @param maze The Maze object to be displayed
     * @throws Exception Write something here
     */
    public void display(Maze maze) throws Exception {
        if (maze != null) {
            clearMaze();
            GUI_Tools.maze_name.setText(maze.getMazeName());
            GUI_Tools.author_name_text.setText(maze.getAuthorName());
            GUI_Tools.description_text.setText(maze.getMazeDescription());
            GUI_Tools.height_text.setText(Integer.toString(maze.getHeight()));
            GUI_Tools.width_text.setText(Integer.toString(maze.getWidth()));
            dateCreated.setText(mazeDB.getDateCreated(maze.getMazeName()));
            lastEdited.setText(mazeDB.getLastEdited(maze.getMazeName()));
            Maze load = mazeDB.getGUIMaze(maze.getMazeName());
            GUI_Maze loadedMaze = new GUI_Maze(load, false);
            Maze.MazeTools.setCurrentGUIMaze(loadedMaze);
            Maze.MazeTools.setCurrentMaze(load);
            GUI_Tools.setShowSolution();
            GUI_Tools.setMazeStatsLabels();
            setMaze(loadedMaze);
            leftPane.add(new JScrollPane(loadedMaze));
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
        this.mazeData = data;
        listModel = new DefaultListModel<>();
        mazeDB = new DBSource();
        initializeFrame();
    }

    private void initializeFrame() throws SQLException {
        int fileMenuItemWith = 120;
        int editMenuItemWith = 180;
        int viewMenuItemWith = 120;
        int menuItemHeight = 20;

        leftPane = new JLabel();
        setTitle("MazeCraft");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setWindowSize();
        leftPane.setLayout(new BorderLayout());


        //Set File Toolbar
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        save = new JMenuItem("Save");
        export = new JMenuItem("Export");
        exit = new JMenuItem("Exit");
        save.addActionListener(this);
        export.addActionListener(this);
        save = menuItemFactory("Save",fileMenuItemWith,menuItemHeight);
        export = menuItemFactory("Export to Jpg",fileMenuItemWith,menuItemHeight);
        exit = menuItemFactory("Exit",fileMenuItemWith,menuItemHeight);

        file.add(save);
        file.addSeparator();
        file.add(export);
        file.addSeparator();
        file.add(exit);
        menuBar.add(file);
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

        JPanel borderBottom = createPanel(Color.DARK_GRAY);
        JPanel borderTop = createPanel(Color.DARK_GRAY);
        JPanel borderLeft = createPanel(Color.DARK_GRAY);
        JPanel borderRight = createPanel(Color.DARK_GRAY);


        setResizable(false);
        new GUI_Tools(borderLeft, this); //<-- Call GUI_Tools to set menu items on left side

        mazeData.updateList(listModel);
        dbitems = new JList<>(listModel);
        dbitems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        addNameListListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    enableButtons(true);
                    enableCheckboxes(true);
                    display(mazeData.get(dbitems.getSelectedValue()));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        dbitems.setVisible(true);

        JPanel controls = new JPanel();
        date = new JLabel("Date Created");
        dateCreated = new JLabel("");
        edited = new JLabel("Last Edited");
        lastEdited = new JLabel("");
        reset = new JButton("Reset Selections");
        delete = new JButton("Delete Selection");
        reset.setEnabled(false);
        delete.setEnabled(false);
        controls.add(date);
        controls.add(dateCreated);
        controls.add(edited);
        controls.add(lastEdited);
        controls.add(reset);
        controls.add(delete);
        controls.setLayout(new GridLayout(6, 0));
        GUI_Tools.setStyle(delete);
        GUI_Tools.setStyle(dateCreated);
        GUI_Tools.setStyle(lastEdited);
        GUI_Tools.setStyle(dbitems);
        GUI_Tools.setStyle(reset);
        GUI_Tools.setStyle(controls);
        GUI_Tools.setStyle(date);
        GUI_Tools.setStyle(edited);

        delete.addActionListener(this);
        reset.addActionListener(this);
        JSplitPane embeddedSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controls, dbitems);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, embeddedSplit);

        //Makes the split pane unmovable
        embeddedSplit.setEnabled(false);
        splitPane.setEnabled(false);

        embeddedSplit.setDividerLocation(250);
        splitPane.setDividerLocation(1250);
        splitPane.setOneTouchExpandable(false);
        splitPane.setContinuousLayout(true);

        leftPane.add(borderTop, BorderLayout.PAGE_START);
        leftPane.add(borderLeft, BorderLayout.LINE_START);
        leftPane.add(borderBottom, BorderLayout.PAGE_END);
        //leftPane.add(borderRight, BorderLayout.LINE_END);
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
     * Clears current Maze and details in text boxes.
     */
    public void clearMaze(){
        // Checks if GUI already contains a maze and removes it to be replaced with new maze
        GUI_Tools.maze_name.setText("Maze");
        GUI_Tools.height_text.setText("25");
        GUI_Tools.width_text.setText("25");
        GUI_Tools.author_name_text.setText("");
        GUI_Tools.description_text.setText("");
        Component[] components = leftPane.getComponents();
        for ( Component comp : components){
            if ( comp instanceof JScrollPane) {
                leftPane.remove(comp);
            }
        }
    }

    /**
     * Creates new maze and displays it in the GUI
     * @param width Width of maze (in blocks)
     * @param height Height of maze (in blocks)
     * @param name Name of maze
     * @param generate true to generate maze or false to create blank canvas
     * @throws Exception with the message Invalid Dimension when the height or width passed does not fall between 4 and 100
     */
    public void generateNewMaze( int width, int height, String name, boolean generate, String mazeType) throws Exception {
        final int maxDim = 100; //the maximum height or width allowed for the maze
        final int minDim = 4;   //the minimum height or width allowed for the maze
        //check if an appropriate height and width for the maze has been passed
        if(height < minDim || height > maxDim || width < minDim || width > maxDim ){
            throw new Exception("Invalid Dimension");
        }
        // Checks if GUI already contains a maze and removes it to be replaced with new maze
        Component[] components = leftPane.getComponents();
        for ( Component comp : components){
            if ( comp instanceof JScrollPane) {
                leftPane.remove(comp);
            }
        }
        // Create new maze and add to GUI using JScrollPane for larger mazes
        maze = new GUI_Maze(new Maze(width, height, name, mazeType,
                GUI_Tools.description_text.getText(), GUI_Tools.author_name_text.getText()), generate);
        leftPane.add(new JScrollPane(maze));
        this.revalidate();
    }

    public void setGrid(boolean toggle) {
        maze.renderMaze(toggle, true);
    }

    public boolean getGrid(){
        return maze.getGrid();
    }


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
        fc.setSelectedFile(new File(GUI_Tools.maze_name.getText()));
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
}