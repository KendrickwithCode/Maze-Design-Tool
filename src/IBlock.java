import java.util.ArrayList;

public interface IBlock {

    /**
     * Return what type of block this block is
     * @return String the type of block this is.
     */
    String getBlockType();

    /**
     * Gets all available directions from objects fields
     * @return ArrayList of available directions to travel
     */
    ArrayList<String> getAvailableDirections();

    /**
     * Clears all the available directions array
     */
    void clearAvailableDirections();

    /**
     * Sets available directions
     * @param availableDirections ArrayList of available directions "NORTH", "EAST", "SOUTH", "WEST"
     */
    void setAvailableDirections(ArrayList<String> availableDirections);
    /**
     * Gets the boolean if the block has been visited
     * @return visited boolean.
     */
    boolean getVisited();

    /**
     * Sets the visited state to true or false.
     * @param visited boolean has been visited ture / false.
     */
    void setVisited(boolean visited);

    /**
     * Returns location of block in maze as a copy of the array
     * @return blocks location int[x,y]
     */
    int[] getLocation();
    /**
     * Returns Wall object of north facing wall
     * @return north Wall object
     */
    MazeWall getWallNorth();

    /**
     * Returns Wall object of south facing wall
     * @return south Wall object
     */
    MazeWall getWallSouth();

    /**
     * Returns Wall object of east facing wall
     * @return east Wall object
     */
    MazeWall getWallEast();
    /**
     * Returns Wall object of west facing wall
     * @return west Wall object
     */
    MazeWall getWallWest();

    /**
     * Sets Wall object of north facing wall
     * @param wallNorth north facing wall
     */
    void setWallNorth(MazeWall wallNorth);

    /**
     * Sets Wall object of south facing wall
     * @param wallSouth south facing wall
     */
    void setWallSouth(MazeWall wallSouth);

    /**
     * Sets Wall object of east facing wall
     * @param wallEast east facing wall
     */
    void setWallEast(MazeWall wallEast);

    /**
     * Sets Wall object of west facing wall
     * @param wallWest west facing wall
     */
    void setWallWest(MazeWall wallWest);

    /**
     * Returns the blocks Arraylist index
     * @return blocks Arraylist index
     */
    int getBlockIndex();
}