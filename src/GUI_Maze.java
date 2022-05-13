import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

/**
 * The editable Maze part of the GUI.
 */
public class GUI_Maze extends JPanel{
    private final int mazeHeight;
    private final int mazeWidth;
    private final int blockSize;
    private final int wallWidth;
    private final Maze maze;
    public MazePanel mazePanel;


    private static boolean mazeGrid = true;
    /**
     * Maze GUI constructor, creates layout for maze blocks and walls and displays them appropriately
     * @param maze Maze object to display
     * @param generate rue to generate maze or false to create blank canvas
     */
    public GUI_Maze(Maze maze,boolean generate) throws Exception {
        // Set Maze
        this.maze = maze;

        //Read current Maze Grid boolean
        mazeGrid = getGrid();

        // Set Maze amount of blocks for maze width and height
        this.mazeHeight = maze.getSize()[1];
        this.mazeWidth = maze.getSize()[0];

        // Set block and wall sizing to suit amount of blocks
        blockSize = mazeBlockSize(mazeHeight, mazeWidth);
        wallWidth = mazeWallWidth(mazeHeight, mazeWidth);

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
        renderMaze(getGrid());
}

    public void renderMaze(boolean grid) throws Exception {
        setGrid(grid);

        // Set maze components common constraints
        GridBagConstraints mazeComponentConstraints = new GridBagConstraints();
        mazeComponentConstraints.weightx = 1;
        mazeComponentConstraints.weighty = 1;

        // Iterate through maze blocks and populate mazePanel
        for ( Block block : maze.getMazeMap() ){

            // Get location of block
            int blockXLocation = (block.getLocation()[0] + 1) * 2 -1;
            int blockYLocation = (block.getLocation()[1] + 1) * 2 -1;
            int[] location = new int[] {blockXLocation, blockYLocation};

            // North wall
            JButton northWallButton = createNorthWallButton(block, mazeComponentConstraints, location);
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

            // Block
            JPanel blockPanel = createBlockPanel(block, mazeComponentConstraints, location);
            // Add block panel to mazePanel
            mazePanel.add(blockPanel, mazeComponentConstraints);
        }
    }

    public boolean getGrid(){
        return mazeGrid;
    }
    public boolean setGrid(boolean toggle){
        mazeGrid = toggle;
        return mazeGrid;
        }

    private JButton createNorthWallButton(Block block, GridBagConstraints constraints, int[] location) {
        // Get north wall button
        JButton northButton = block.getWallNorth().getButton();

        // Set button constraints
        northButton.setPreferredSize(new Dimension(blockSize, wallWidth));
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
        JButton westButton = block.getWallWest().getButton();
        // Set button constraints
                westButton.setPreferredSize(new Dimension(wallWidth, blockSize));
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
        JButton eastButton = block.getWallEast().getButton();
        // Set button constraints
        eastButton.setPreferredSize(new Dimension(wallWidth, blockSize));
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
        JButton southButton = block.getWallSouth().getButton();
        // Set button constraints
        southButton.setPreferredSize(new Dimension(blockSize, wallWidth));
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
     * @param size size to be scaled to in pixels
     * @return  a scaled ImageIcon
     */
    private ImageIcon scaleImage(Image image, int size) {
        BufferedImage bi = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = bi.createGraphics();
        g.drawImage(image, 0, 0, size, size, null);
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

    private void logoBlockRender(Block block, JPanel blockPanel,GridBagConstraints constraints)
    {

        if(block.getClass().getName().equals("LogoBlock")){
            ((LogoBlock) block).setupLogoWalls(maze);

            String FileLocation = ((LogoBlock) block).getPictureFile();
            Image image = new ImageIcon(((LogoBlock) block).getPictureFile()).getImage();
            JLabel imageLabel = new JLabel();

            ImageIcon imageIcon = scaleImage(image,blockSize*2);
            blockPanel.add(imageLabel);
            imageLabel.setIcon(imageIcon);
//            blockPanel.setBackground(new Color(255,243,215));

            //Stretch Panel over 2 Block (THis Panel, Border, Neighbour Panel = 3)
            constraints.gridwidth = 3;
            constraints.gridheight = 3;

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
                public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
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

    private Block mazeTypeSelect(Block block) throws Exception {
        if(Objects.equals(maze.getMazeType(), "KIDS")) {

            if (block.getBlockIndex() == 0) {                     // hack conversion code to make a logo block
                block = new LogoBlock(block.getLocation(), block.getBlockIndex(), maze, "dog", "start");
            }

            int lastBlock = maze.getMazeMap().size() - maze.getSize()[0] - 2;
            if (block.getBlockIndex() == lastBlock)              // hack conversion code to make a logo block
            {
                block = new LogoBlock(block.getLocation(), block.getBlockIndex(), maze, "bone", "end");
            }
        }

//        if(Objects.equals(maze.getMazeType(), "ADULT")) {
//            int middleBlock = maze.getMazeMap().size() / 2;
//            if (block.getBlockIndex() == middleBlock) {                     // hack conversion code to make a logo block
//                block = new LogoBlock(block.getLocation(), block.getBlockIndex(), maze, "mazeCo", "logo");
//            }
//
//        }

        return block;


    }

    private JPanel createBlockPanel(Block block, GridBagConstraints constraints, int[] location) throws Exception {
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

    block = mazeTypeSelect(block);
    logoBlockRender(block,blockPanel,constraints);

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

    private int mazeWallWidth (int mazeHeight, int mazeWidth){
        int amountOfCells = mazeHeight * mazeWidth;
        if(amountOfCells < 100){
            return 10;
        } else if (amountOfCells >= 100 && amountOfCells < 400){
            return 8;
        } else if (amountOfCells >= 400 && amountOfCells < 2500){
            return 6;
        } else {
            return 4;
        }
    }

    private int mazeBlockSize (int mazeHeight, int mazeWidth){
        int amountOfCells = mazeHeight * mazeWidth;
        if(amountOfCells < 100){
            return (600 / mazeHeight) + 10;
        } else if (amountOfCells >= 100 && amountOfCells < 400){
            return (450 / mazeHeight) + 10;
        } else if (amountOfCells >= 400 && amountOfCells < 2500){
            return (300 / mazeHeight)+ 10;
        } else {
            return (100 / mazeHeight) + 10;
        }
    }
}
