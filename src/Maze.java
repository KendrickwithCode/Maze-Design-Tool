import java.util.ArrayList;


public class Maze {

    private int difficulty;
    private final boolean solvable;
    private String mazeName;
    private final int[] size;
    private final ArrayList<Block> mazeMap;

    /**
     * Constructs and initialises a new Maze
     * @param sizeX size of the x-axis for the maze
     * @param sizeY size of the y-axis for the maze
     * @param name name of the maze
     */
    public Maze(int sizeX, int sizeY, String name) {
        this.size = new int[]{sizeX, sizeY};
        this.mazeMap = new ArrayList<>();
        this.mazeName = name;
        this.solvable = false;
        resetMaze(sizeX,sizeY);
    }

    /**
     * Resets the maze map to new clear blocks.
     * @param sizeX X-axis size of the maze
     * @param sizeY Y-axis size of the maze
     */
    public void resetMaze(int sizeX, int sizeY)
    {
        mazeMap.clear();

        /*
         *  Iterates through all maze Cells and makes a new empty block
         */
        for (int y =0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                mazeMap.add(new Block(new int[]{x,y}));
            }
        }
    }

    /**
     * Return the index of requested neighbouring block "UP", "DOWN", "LEFT", "RIGHT"
     * @param referenceBlock origin maze block
     * @param direction neighbouring block direction "UP", "DOWN", "LEFT", "RIGHT"
     * @return the index or -1 for out of Arraylist or -2 for direction invalid entry
     */
    public int getNeighbourIndex(Block referenceBlock, String direction){

        int[] newLocation = referenceBlock.getLocation();

        /*
         * Updates newLocation [x,y] according to parameter direction
         */
        switch (direction.toUpperCase()){
            case "UP":
                newLocation[1] -= 1;
                break;
            case "DOWN":
                newLocation[1] += 1;

                break;
            case "LEFT":
                newLocation[0] -= 1;
                break;
            case "RIGHT":
                newLocation[0] += 1;
                break;
            default:
                return -2;
        }
        return getIndex(newLocation);
    }

    /**
     * Return the index of requested neighbouring block "UP", "DOWN", "LEFT", "RIGHT"
     * @param referenceBlock referenceBlock origin maze block
     * @param direction neighbouring block direction "UP", "DOWN", "LEFT", "RIGHT"
     * @return returns the neighbour block
     */
    public Block getNeighbourBlock(Block referenceBlock, String direction){

        int newLocationIndex = getIndex(referenceBlock.getLocation());
        if (newLocationIndex >= 0 && newLocationIndex < mazeMap.size())
            return mazeMap.get(getNeighbourIndex(referenceBlock,direction));
        else
            return null;
    }

    /**
     * Gets the ArrayList index of the asked location
     * @param location [x,y] of the cell you like to know the Arraylist index
     * @return the index of the supplied location
     */
    public int getIndex(int[] location)
    {
        int x = location[0];
        int y = location[1];

        return y*size[0]+x;
    }

    /**
     * Renders the current maze
     */
    public void RenderMaze()
    {
    }

    /**
     * Auto generates a new maze
     */
    public void GenerateMaze()
    {
    }

    /**
     * Returns current difficulty level
     * @return difficulty level
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns a boolean value of if the maze is currently solvable
     * @return if maze is solvable ture or false
     */
    public boolean getSolvable() {
        return solvable;
    }

    /**
     *Gets current maze name
     * @return mazes name
     */
    public String getMazeName() {
        return mazeName;
    }

    /**
     *
     * @return mazeMap
     */
    public ArrayList<Block> getMazeMap() {
        return mazeMap;
    }

    /**
     * Gets current maze size
     * @return current maze size [x,y]
     */
    public int[] getSize() {
        return size;
    }

    /**
     *  sets difficulty level
     * @param difficulty new level for difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     *  Sets the maze name
     * @param mazeName The new name for maze
     */
    public void setMazeName(String mazeName) {
        this.mazeName = mazeName;
    }

    /**
     * Sets new size of maze
     * @param sizeX sets new X axis size of maze
     * @param sizeY sets new Y axis size of maze
     */
    public void setSize(int sizeX, int sizeY) {
        this.size[0] = sizeX;
        this.size[1] = sizeY;
    }
}

