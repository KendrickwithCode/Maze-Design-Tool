import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import static java.lang.Integer.parseInt;

/**
 * Graphic User Interface Base.
 * This is where all the components for the GUI Tools and GUI Maze Sit on top off.
 */
public class GUI extends JFrame implements ActionListener, Runnable {

    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");
    public JSplitPane splitPane;
    public JList<Object> dbItems;
    public JLabel leftPane, date, edited, lastEdited, dateCreated;
    MazeDB mazeData;
    DBSource mazeDB;
    private JButton reset, delete;
    private JMenuItem save, export, fullScr, windowScr, exit, about;
    private final DefaultListModel<Object> listModel;


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        //checks if the export button has been pressed and runs the jpgExport function if it has
        if (src == export) {
            try {
                if (maze != null) {
                    //Iterate over multiple selections from the list if bulk jpg saving.
                    if (!dbItems.getSelectedValuesList().isEmpty()) {
                        for (Object obj : dbItems.getSelectedValuesList()) {
                            display(mazeData.get(obj));
                            jpgExport(maze.getMazePanel());
                        }
                    }
                    //If an item isn't selected, ignore loop and export anyway if a maze is generated.
                    else {
                        jpgExport(maze.getMazePanel());
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null,
                            "Generate or create a Maze before exporting!","No Maze to export",JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } else if (src == save) {
            if(Maze.MazeTools.getCurrentGUIMaze() != null){
                saveMaze(Maze.MazeTools.getCurrentMaze());
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

        if(src==about)
        {
            new About();
        }

        if(src== reset){
            if(Maze.MazeTools.getCurrentGUIMaze() != null){
                enableCheckboxes(false);
                enableButtons(false);
                clearMaze();
                dbItems.clearSelection();
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
                    mazeDB.deleteEntry(dbItems.getSelectedValue().toString());
                    try {
                        clearMaze();
                        enableCheckboxes(false);
                        enableButtons(false);
                        GUI_Tools.clearStats();
                        listModel.removeElement(dbItems.getSelectedValue());
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
     * @param currentMaze The Maze object to be saved.
     */
    private void saveMaze(Maze currentMaze){
        try {
            if (GUI_Tools.author_name_text.getText() == null || GUI_Tools.author_name_text.getText().length() == 0){
                JOptionPane.showMessageDialog(null,
                        "Please Enter an Author's Name before Saving","Okay",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                currentMaze.setMazeName(GUI_Tools.maze_name.getText());
                currentMaze.setMazeType((String)GUI_Tools.mazeTypeComboBox.getSelectedItem());
                currentMaze.setMazeDescription(GUI_Tools.description_text.getText());
                currentMaze.setAuthorName((GUI_Tools.author_name_text.getText()));
                currentMaze.setWidth(parseInt(GUI_Tools.height_text.getText()));
                currentMaze.setHeight(parseInt(GUI_Tools.height_text.getText()));
                //boolean is true if the entry is new to the db, false if it's an updating entry.
                boolean added = mazeDB.addMaze(currentMaze);
                mazeData.updateList(listModel);
                if (!added){
                    lastEdited.setText(mazeDB.getLastEdited(GUI_Tools.maze_name.getText()));
                }
                //set list selection to new or updated entry
                dbItems.setSelectedIndex(listModel.indexOf(GUI_Tools.maze_name.getText()));
                display(Maze.MazeTools.getCurrentMaze());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
     * @throws Exception thrown from error in database load.
     */
    public void display(Maze maze) throws Exception {
        if (maze != null) {
            clearMaze();
            GUI_Tools.maze_name.setText(maze.getMazeName());
            GUI_Tools.author_name_text.setText(maze.getAuthorName());
            GUI_Tools.description_text.setText(maze.getMazeDescription());
            GUI_Tools.height_text.setText(Integer.toString(maze.getHeight()));
            GUI_Tools.width_text.setText(Integer.toString(maze.getWidth()));
            GUI_Tools.mazeTypeComboBox.getModel().setSelectedItem(maze.getMazeType());
            dateCreated.setText(mazeDB.getDateCreated(maze.getMazeName()));
            lastEdited.setText(mazeDB.getLastEdited(maze.getMazeName()));
            Maze load = mazeDB.getGUIMaze(maze.getMazeName());
            GUI_Maze loadedMaze = new GUI_Maze(load, false);
            Maze.MazeTools.setCurrentGUIMaze(loadedMaze);
            Maze.MazeTools.setCurrentMaze(load);
            enableCheckboxes(true);
            enableButtons(true);
            GUI_Tools.setShowSolution();
            GUI_Tools.setMazeStatsLabels();
            leftPane.add(new JScrollPane(loadedMaze));
            this.revalidate();
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
        int viewMenuItemWith = 120;
        int aboutMenuItemWith = 100;
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


        // Set Edit View
        JMenu view = new JMenu("View");

        fullScr = menuItemFactory("Full Screen",viewMenuItemWith,menuItemHeight);
        windowScr = menuItemFactory("Window",viewMenuItemWith,menuItemHeight);
        view.add(fullScr);
        view.add(windowScr);
        menuBar.add(view);

        JMenu help = new JMenu("Help");
        about = menuItemFactory("About",aboutMenuItemWith,menuItemHeight);
        help.add(about);
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        setIconImage(icon.getImage());

        JPanel borderBottom = createPanel();
        JPanel borderTop = createPanel();
        JPanel borderLeft = createPanel();


        setResizable(false);
        new GUI_Tools(borderLeft, this); //<-- Call GUI_Tools to set menu items on left side

        mazeData.updateList(listModel);
        dbItems = new JList<>(listModel);
        dbItems.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        dbItems.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    display(mazeData.get(dbItems.getSelectedValue()));
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
        dbItems.setVisible(true);

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
        GUI_Tools.setStyle(dbItems);
        GUI_Tools.setStyle(reset);
        GUI_Tools.setStyle(controls);
        GUI_Tools.setStyle(date);
        GUI_Tools.setStyle(edited);

        delete.addActionListener(this);
        reset.addActionListener(this);
        JSplitPane embeddedSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, controls, dbItems);
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

    private JPanel createPanel(){
        JPanel temp = new JPanel();
        temp.setBackground(Color.darkGray);
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
     * @param width Width of maze (in blocks) between 4 and 100 inclusive
     * @param height Height of maze (in blocks) between 4 and 100 inclusive
     * @param name Name of maze
     * @param generate true to generate maze or false to create blank canvas
     * @param mazeType The type of maze to be created.
     * @throws IllegalArgumentException with the message Invalid Dimension when the height or width passed does not fall between 4 and 100
     */
    public void generateNewMaze( int width, int height, String name, boolean generate, String mazeType) throws IllegalArgumentException {
        final int maxDim = 100; //the maximum height or width allowed for the maze
        final int minDim = 4;   //the minimum height or width allowed for the maze
        //check if an appropriate height and width for the maze has been passed
        if(height < minDim || height > maxDim || width < minDim || width > maxDim ){
            throw new IllegalArgumentException("Invalid Dimension");
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

    /**
     * sets the maze grid
     * @param toggle true to render the maze with grid displayed
     */
    public void setGrid(boolean toggle) {
        Maze.MazeTools.getCurrentGUIMaze().renderMaze(toggle, true);
    }

    /**
     * Returns the Maze grid value
     * @return boolean Maze grid value. True if maze grid is enabled.
     */
    public boolean getGrid(){
        return Maze.MazeTools.getCurrentGUIMaze().getGrid();
    }


    /**
     * Creates a screenshot of the passed JPanel object and saves it
     * adapted from: web address = stackoverflow.com/a/10796047
     * @param mazePanel the GUI_Maze object that is to be exported.
     */
    private void jpgExport(JPanel mazePanel) {

        Rectangle rec = mazePanel.getBounds();
        File image;

        BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_BGR);
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
                ImageIO.write(bufferedImage, "jpg", image);

                JOptionPane.showMessageDialog(null,image.getName(),"Exported image of current maze view at:",JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void run() {}
}