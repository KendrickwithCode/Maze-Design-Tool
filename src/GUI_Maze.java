import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * The editable Maze part of the GUI.
 */
public class GUI_Maze extends JPanel{
    private final int mazeHeight;
    private final int mazeWidth;
    private final int blockSize;
    private final int wallThickness;
    private final Maze maze;

    /**
     * The associated GUI mazePanel object which shows solution statistics
     */
    public static MazePanel mazePanel;

    private static boolean mazeGrid = true;
    //variables to control the size of walls in pixels with respect to blocks at different maze sizes
        //width in pixels when max dim is 100, should not be set to more than 15:
        private final static int largeMazeWallSize = 4;
        //width in pixels when max dim is 6, should not be set to more than 63:
        private final static int smallMazeWallSize = 10;

    /**
     * Maze GUI constructor, creates layout for maze blocks and walls and displays them appropriately
     * @param maze Maze object to display
     * @param generate true to generate maze or false to create blank canvas
     */
    public GUI_Maze(Maze maze,boolean generate) {
        // Set Maze
        this.maze = maze;
        Maze.MazeTools.setCurrentGUIMaze(this);

        for(Block block : maze.getMazeMap()){
            block.getWallSouth().addListeners();
            block.getWallNorth().addListeners();
            block.getWallEast().addListeners();
            block.getWallWest().addListeners();
        }

        //Read current Maze Grid boolean
        mazeGrid = getGrid();

        // Set Maze amount of blocks for maze width and height
        this.mazeHeight = maze.getSize()[1];
        this.mazeWidth = maze.getSize()[0];

        // Set block and wall sizing to suit amount of blocks
        blockSize = mazeBlockSize();
        wallThickness = mazeWallWidth();

        if (generate)
        {
            //maze.generateNewMaze("DFSRecursive",new int[] {0,0});
            maze.generateNewMaze("DFSIterative",new int[]{0,0});
        }

        // Set maze padding and layout
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        setBorder(padding);
        setLayout(new GridBagLayout());

        // Create and set maze JPanel to be placed in center of area
        mazePanel = createMazePanel();

        //Render Maze to GUI
        renderMaze(getGrid(),false);
}

    /**
     * returns the mazePanel
     * @return the mazePanel
     */
    public MazePanel getMazePanel() {
        return mazePanel;
    }

    private GridBagConstraints setupGridConstraints()
    {
        // Set maze components common constraints
        GridBagConstraints mazeComponentConstraints = new GridBagConstraints();
        mazeComponentConstraints.weightx = 1;
        mazeComponentConstraints.weighty = 1;
        return mazeComponentConstraints;
    }

    /**
     * Renders the maze
     * @param grid True if the maze is to be rendered with grid showing
     * @param refresh True if the maze is to rendered on the GUI
     */
    public void renderMaze(boolean grid,boolean refresh){
        setGrid(grid);
        //LoadingBar bar = new LoadingBar();
        GridBagConstraints mazeComponentConstraints = setupGridConstraints();

        // Iterate through maze blocks and populate mazePanel
        for ( Block block : maze.getMazeMap() ){
            // Get location of block
            int blockXLocation = (block.getLocation()[0] + 1) * 2 -1;
            int blockYLocation = (block.getLocation()[1] + 1) * 2 -1;
            int[] location = new int[] {blockXLocation, blockYLocation};

            // Setup Walls
            setupWalls(block, mazeComponentConstraints,location,grid);

            if(!refresh)
            {
                renderPanel(block,mazeComponentConstraints,location);
            }
        }
    }

    private void setupWalls(Block block, GridBagConstraints mazeComponentConstraints, int[] location, boolean grid)
    {
        // North wall
        JButton northWallButton = createNorthWallButton(
                block, mazeComponentConstraints, location);
        //Set Grid based on "Show Grid" button.
        if (grid){
            northWallButton.setBorderPainted(true);
            northWallButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        } else { northWallButton.setBorderPainted(false);}
        // Add button to mazePanel with constraints
        mazePanel.add(northWallButton, mazeComponentConstraints);

        // West wall
        JButton westWallButton = createWestWallButton(block, mazeComponentConstraints, location);
        //Set Grid based on "Show Grid" button.
        if (grid){
            westWallButton.setBorderPainted(true);
            westWallButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        } else { westWallButton.setBorderPainted(false);}
        // Add button to mazePanel with constraints
        mazePanel.add(westWallButton, mazeComponentConstraints);

        // East wall
        JButton eastWallButton = createEastWallButton(block, mazeComponentConstraints, location);
        //Set Grid based on "Show Grid" button.
        if (grid){
            eastWallButton.setBorderPainted(true);
            eastWallButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        } else { eastWallButton.setBorderPainted(false);}
        // Add button to mazePanel with constraints
        mazePanel.add(eastWallButton, mazeComponentConstraints);

        // South wall
        JButton southWallButton = createSouthWallButton(block, mazeComponentConstraints, location);
        //Set Grid based on "Show Grid" button.
        if (grid){
            southWallButton.setBorderPainted(true);
            southWallButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        } else { southWallButton.setBorderPainted(false);}
        // Add button to mazePanel with constraints
        mazePanel.add(southWallButton, mazeComponentConstraints);
    }

    /**
     * renders the maze blocks
     */
    public void renderBlocks() {

        // Set maze components common constraints
        GridBagConstraints mazeComponentConstraints = setupGridConstraints();

        // Iterate through maze blocks and populate mazePanel
        for ( Block currentBlock : maze.getMazeMap() ){

            // Get location of block
            int blockXLocation = (currentBlock.getLocation()[0] + 1) * 2 -1;
            int blockYLocation = (currentBlock.getLocation()[1] + 1) * 2 -1;
            int[] location = new int[] {blockXLocation, blockYLocation};

            renderPanel(currentBlock,mazeComponentConstraints,location);

        }
    }


    private void renderPanel(Block block, GridBagConstraints mazeComponentConstraints, int[] location)
    {
        // Block
        JPanel blockPanel = createBlockPanel(block, mazeComponentConstraints, location);
        // Add block panel to mazePanel
        mazePanel.add(blockPanel, mazeComponentConstraints);
    }

    /**
     * Gets the mazeGrid value
     * @return boolean mazeGrid value. True if grid is enabled and false if not.
     */
    public boolean getGrid(){
        return mazeGrid;
    }

    private void setGrid(boolean toggle){
        mazeGrid = toggle;
        }

    private JButton createNorthWallButton(Block block, GridBagConstraints constraints, int[] location) {
        // Get north wall button
        JButton northButton = block.getWallNorth();
        // Set button constraints
        northButton.setPreferredSize(new Dimension(blockSize, wallThickness));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = location[0] - 1;
        constraints.gridy = location[1] - 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        return northButton;
    }

    private JButton createWestWallButton(Block block, GridBagConstraints constraints, int[] location){
        // Get West wall button
        JButton westButton = block.getWallWest();
        // Set button constraints

        westButton.setPreferredSize(new Dimension(wallThickness, blockSize));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = location[0] - 1;
        constraints.gridy = location[1] - 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 3;

        return westButton;
    }

    private JButton createEastWallButton(Block block, GridBagConstraints constraints, int[] location){
        // Get east wall button
        JButton eastButton = block.getWallEast();
        // Set button constraints
        eastButton.setPreferredSize(new Dimension(wallThickness, blockSize));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.gridx = location[0]  + 1;
        constraints.gridy = location[1]  - 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 3;

        return eastButton;
    }

    private JButton createSouthWallButton(Block block, GridBagConstraints constraints, int[] location){
        // Get south wall button
        JButton southButton = block.getWallSouth();
        // Set button constraints
        southButton.setPreferredSize(new Dimension(blockSize, wallThickness));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridx = location[0] - 1;
        constraints.gridy = location[1] + 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        return southButton;
    }

    /**
     * Scales Image and returns as ImageIcon
     * @param image image to be scales as an image
     * @param sizeX sizeX to be scaled to in pixels
     * @param sizeY sizeY to be scaled to in pixels
     * @return  a scaled ImageIcon
     */
    private ImageIcon scaleImage(Image image, int sizeX, int sizeY) {
        BufferedImage bi = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, sizeX, sizeY, null);
        return new ImageIcon(bi);
    }

    /**
     * Paints 2d object
     * @param g input object for graphics
     * @param image image location as a string.
     */
    private void paintObject(Graphics g, String image){
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image i = tk.getImage(image);
        g.drawImage(i,100,100,this);
    }

    /**
     * Render the logoBlock to a JPanel in the maze.
     * @param block current block that is being worked on
     * @param blockPanel gui block panel
     * @param constraints gui constraints
     */
    private void logoBlockRender(Block block, JPanel blockPanel,GridBagConstraints constraints,int sizeX,int sizeY)
    {
        Map<Integer,Integer> scales = new HashMap<>();
        scales.put(1,1);
        scales.put(2,3);
        scales.put(3,5);
        scales.put(4,7);
        scales.put(5,9);
        scales.put(6,11);
        scales.put(7,13);
        scales.put(8,15);
        scales.put(9,17);
        scales.put(10,19);

        int scaleX = blockSize * sizeX;
        int scaleY = blockSize * sizeY;

        if(block.getBlockType().equals("LogoBlock")){

            String FileLocation = ((LogoBlock) block).getPictureFile();
            Image image = new ImageIcon(((LogoBlock) block).getPictureFile()).getImage();
            JLabel imageLabel = new JLabel();

            ImageIcon imageIcon = scaleImage(image,scaleX,scaleY);
            blockPanel.add(imageLabel);
            imageLabel.setIcon(imageIcon);

            //Stretch Panel over 2 Block (THis Panel, Border, Neighbour Panel = 3)
            constraints.gridwidth = scales.get(sizeX);
            constraints.gridheight = scales.get(sizeY);

            Graphics g = new Graphics() {
                @Override
                public Graphics create() {
                    return null;
                }

                @Override
                public void translate(int x, int y) {

                }

                @Override
                public Color getColor() {
                    return null;
                }

                @Override
                public void setColor(Color c) {

                }

                @Override
                public void setPaintMode() {

                }

                @Override
                public void setXORMode(Color c1) {

                }

                @Override
                public Font getFont() {
                    return null;
                }

                @Override
                public void setFont(Font font) {

                }

                @Override
                public FontMetrics getFontMetrics(Font f) {
                    return null;
                }

                @Override
                public Rectangle getClipBounds() {
                    return null;
                }

                @Override
                public void clipRect(int x, int y, int width, int height) {

                }

                @Override
                public void setClip(int x, int y, int width, int height) {

                }

                @Override
                public Shape getClip() {
                    return null;
                }

                @Override
                public void setClip(Shape clip) {

                }

                @Override
                public void copyArea(int x, int y, int width, int height, int dx, int dy) {

                }

                @Override
                public void drawLine(int x1, int y1, int x2, int y2) {

                }

                @Override
                public void fillRect(int x, int y, int width, int height) {

                }

                @Override
                public void clearRect(int x, int y, int width, int height) {

                }

                @Override
                public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

                }

                @Override
                public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

                }

                @Override
                public void drawOval(int x, int y, int width, int height) {

                }

                @Override
                public void fillOval(int x, int y, int width, int height) {

                }

                @Override
                public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

                }

                @Override
                public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

                }

                @Override
                public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {

                }

                @Override
                public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {

                }

                @Override
                public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {

                }

                @Override
                public void drawString(String str, int x, int y) {

                }

                @Override
                public void drawString(AttributedCharacterIterator iterator, int x, int y) {

                }

                @Override
                public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
                    return false;
                }

                @Override
                public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
                    return false;
                }

                @Override
                public boolean drawImage(Image img, int x, int y, Color color, ImageObserver observer) {
                    return false;
                }

                @Override
                public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
                    return false;
                }

                @Override
                public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
                    return false;
                }

                @Override
                public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
                    return false;
                }

                @Override
                public void dispose() {

                }
            };
            paintObject(g,FileLocation);
        }
    }


    private JPanel createBlockPanel(Block block, GridBagConstraints constraints, int[] location) {
    // Get block panel
    JPanel blockPanel = block.getBlockPanel();
    // Set block panel sizing
    blockPanel.setPreferredSize(new Dimension(blockSize, blockSize));
    blockPanel.setMaximumSize(new Dimension(blockSize, blockSize));
    blockPanel.setMinimumSize(new Dimension(blockSize, blockSize));

    // Set blockPanel constraints
    constraints.fill = GridBagConstraints.BOTH;
    constraints.anchor = GridBagConstraints.CENTER;
    constraints.gridwidth = 1;
    constraints.gridheight = 1;
    constraints.gridx = location[0];
    constraints.gridy = location[1];

    //    block = mazeTypeSelect(block);
    int sizeX = 3;
    int sizeY = 3;

    // clear panel
    blockPanel.removeAll();
    if(Objects.equals(block.getBlockType(), "LogoBlock"))
    {
        LogoBlock workingBlock = (LogoBlock) block;
        sizeX = workingBlock.getLogoSizeX();
        sizeY = workingBlock.getLogoSizeY();
//        System.out.println("Size Change :" + " Block : X" + location[0] + ",Y" + location[1]+" Size: " + sizeX);

    }
    logoBlockRender(block,blockPanel,constraints,sizeX,sizeY);

    return blockPanel;
    }


    private MazePanel createMazePanel() {
        MazePanel panel = new MazePanel(maze);
        GridBagLayout mazeLayout = new GridBagLayout();
        panel.setLayout(mazeLayout);

        // Set mazePanel Constraints and dimensions
        GridBagConstraints mazePanelConstraints = new GridBagConstraints();
        mazePanelConstraints.anchor = GridBagConstraints.CENTER;
        mazePanelConstraints.weightx = 1;
        mazePanelConstraints.weighty = 1;
        panel.setMaximumSize(new Dimension(mazeWidth * 50, mazeHeight * 50));

        // Add mazePanel to parent JPanel component
        add(panel, mazePanelConstraints);
        return panel;
    }

    /**
     * Returns the maze wall pixel width in pixels according to either mazeHeight or mazeWidth. The greater of the two is inputted to a reciprocal function which linearly scales the overall width of the maze with the number of cells in the maze. The function is scaled such that a maze with a max dimension of 6 cells results in a maze pixel width of about 380 and a maze with a max dimension of 100 cells results in about a 1600 pixel wall width.
     * @return the pixel width of the maze walls
     */
    private int mazeWallWidth (){
        final int maxDim = Math.max(mazeHeight,mazeWidth);
        final int d1 = 6;       //value 1 max dimension
        final int s1 = smallMazeWallSize*d1;      //value 1 corresponding desired length in pixels
        final int d2 = 100;     //value 2 max dimension
        final int s2 = largeMazeWallSize*d2;     //value 2 corresponding desired length in pixels
        return ((s2 - s1 + (-s2*d1+s1*d2)/maxDim)/(d2-d1));
    }

    /**
     * Returns the maze block width in pixels according to either mazeHeight or mazeWidth. The greater of the two is inputted to a reciprocal function which linearly scales the overall width of the maze with the number of cells in the maze. The function is scaled such that a maze with a max dimension of 6 cells results in a maze pixel width of about 380 and a maze with a max dimension of 100 cells results in about a 1600 pixel wall width.
     * @return the pixel width of the maze walls
     */
    private int mazeBlockSize (){
        final int maxDim = Math.max(mazeHeight,mazeWidth);
        final int d1 = 6;       //value 1 max dimension
        final int s1 = (64-smallMazeWallSize)*d1;     //value 1 corresponding desired length in pixels
        final int d2 = 100;     //value 2 max dimension
        final int s2 = (16-largeMazeWallSize)*d2;    //value 2 corresponding desired length in pixels
        return ((s2 - s1 + (-s2*d1+s1*d2)/maxDim)/(d2-d1));
    }
}
