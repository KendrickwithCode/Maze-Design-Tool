import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Block {

    protected MazeWall wallNorth;
    protected MazeWall wallSouth;
    protected MazeWall wallEast;
    protected MazeWall wallWest;
    protected final int[] location;
    protected final int blockIndex;
    protected boolean visited;

    private final JPanel blockPanel;

    protected ArrayList<String> availableDirections;

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
     * Overload Constructs and initialises new Block
     * @param location int[x,y] x,y location of block on maze
     * @param blockIndex sets the block index for the maze map ArrayList of block.
     */
    public Block(int[] location, int blockIndex)
    {
        this(location,blockIndex, true);
        this.visited = false;
    }

    /**
     * Gets all available directions from objects fields
     * @return ArrayList of available directions to travel
     */
    public ArrayList<String> getAvailableDirections() {
        return availableDirections;
    }

    public void clearAvailableDirections()
    {
        availableDirections.clear();
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
     *  Returns JPanel associated with this block
     * @return
     */
    public JPanel getBlockPanel() {
        return this.blockPanel;
    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 0));

        return panel;
    }

}
