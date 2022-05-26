import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.awt.event.MouseEvent;


/**
 *  Astract class used for Maze Block and Logo Blocks in the maze.
 */
public abstract class Block implements IBlock, MouseListener, ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item = new JMenuItem("Place Icon Here");


            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        MazeLogoTools.convertMazeBlockToLogoBlock(Block.this);
                        rerenderIcons();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JMenuItem item2 = new JMenuItem("Remove Icon Here");

            item2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        MazeLogoTools.convertLogoBlockToWallBlock(Block.this);
                        rerenderIcons();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            JMenuItem item3 = new JMenuItem("Change Icon Size");

            item3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    changeIconSize(1,1);
                    rerenderIcons();

//                  new IconSizeWindow();

                }
            });


            menu.add(item);
            menu.add(item2);
            menu.add(item3);
            menu.show(e.getComponent(), e.getX(), e.getY());
        }else
            System.out.println(location[0] + " ," + location[1] + " Idx: " + blockIndex + " Tp: " + this.getBlockType());

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

    private MazeWall wallNorth;
    private MazeWall wallSouth;
    private MazeWall wallEast;
    private MazeWall wallWest;
    private final int[] location;
    private final int blockIndex;
    private boolean visited;

    private JPanel blockPanel;

    private ArrayList<String> availableDirections;    // Stores available directions to traverse from this block



    /**
     * Constructs and initialises new Block
     * @param location int[x,y] x,y location of block on maze
     * @param blockIndex sets the block index for the maze map ArrayList of block.
     * @param clearWalls sets the default value for the walls to be cleared (true / inactive) or set (false / active)
     */
    public Block(int[] location, int blockIndex,Boolean clearWalls)
    {
        this.location = location;
        this.blockIndex = blockIndex;
        wallEast = new MazeWall();
        wallSouth = new MazeWall();
        wallEast.setActive(!clearWalls);
        wallSouth.setActive(!clearWalls);

        this.blockPanel = createPanel();

        availableDirections = new ArrayList<>();
    }

    /**
     * Gets all available directions from objects fields
     * @return ArrayList of available directions to travel
     */
    public ArrayList<String> getAvailableDirections() {
        return availableDirections;
    }

    /**
     * Clears all the available directions array
     */
    public void clearAvailableDirections()
    {
        availableDirections.clear();
    }

    private void rerenderIcons() {
        boolean gridStatus = MazeLogoTools.getCurrentGUIMaze().getGrid();
        MazeLogoTools.getCurrentGUIMaze().renderBlocks();
        MazeLogoTools.getCurrentGUIMaze().renderMaze(gridStatus, true);
    }

    private void changeIconSize(int sizeX, int sizeY){
        MazeLogoTools.convertLogoBlockToWallBlock(Block.this);
        try {
            MazeLogoTools.convertMazeBlockToLogoBlock(Block.this);
            LogoBlock working = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
            working.setLogoSizeX(sizeX);
            working.setLogoSizeY(sizeY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Sets available directions
     * @param availableDirections ArrayList of available directions "NORTH", "EAST", "SOUTH", "WEST"
     */
    public void setAvailableDirections(ArrayList<String> availableDirections) {
        this.availableDirections = availableDirections;
    }

    /**
     * Gets the boolean if the block has been visited
     * @return visited boolean.
     */
    public boolean getVisited() {
        return visited;
    }

    /**
     * Sets the visited state to true or false.
     * @param visited boolean has been visited ture / false.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Returns location of block in maze as a copy of the array
     * @return blocks location int[x,y]
     */
    public int[] getLocation(){
        return location.clone();
    }

    /**
     * Returns Wall object of north facing wall
     * @return north Wall object
     */
    public MazeWall getWallNorth() {
        return wallNorth;
    }

    /**
     * Returns Wall object of south facing wall
     * @return south Wall object
     */
    public MazeWall getWallSouth() {
        return wallSouth;
    }

    /**
     * Returns Wall object of east facing wall
     * @return east Wall object
     */
    public MazeWall getWallEast() {
        return wallEast;
    }

    /**
     * Returns Wall object of west facing wall
     * @return west Wall object
     */
    public MazeWall getWallWest() {
        return wallWest;
    }

    /**
     * Sets Wall object of north facing wall
     * @param wallNorth north facing wall
     */
    public void setWallNorth(MazeWall wallNorth) {
        this.wallNorth = wallNorth;
    }

    /**
     * Sets Wall object of south facing wall
     * @param wallSouth south facing wall
     */
    public void setWallSouth(MazeWall wallSouth) {
        this.wallSouth = wallSouth;
    }

    /**
     * Sets Wall object of east facing wall
     * @param wallEast east facing wall
     */
    public void setWallEast(MazeWall wallEast) {
        this.wallEast = wallEast;
    }

    /**
     * Sets Wall object of west facing wall
     * @param wallWest west facing wall
     */
    public void setWallWest(MazeWall wallWest) {
        this.wallWest = wallWest;
    }

    /**
     * Returns the blocks Arraylist index
     * @return blocks Arraylist index
     */
    public int getBlockIndex() {
        return blockIndex;
    }

    /**
     *  Returns JPanel associated with this block.
     * @return JPanel from this block.
     */
    public JPanel getBlockPanel() {
        return this.blockPanel;
    }

    public void setBlockPanel(JPanel blockPanel) {
        this.blockPanel = blockPanel;
    }


    public Point getBlockPanelLocation() {
        return this.blockPanel.getLocation();
    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
        panel.addMouseListener(this);

        return panel;
    }

}
