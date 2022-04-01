import java.util.ArrayList;

public class Maze {

    private int difficulty;
    private boolean solvable;
    private String mazeName;
    private final int[] size;
    private ArrayList<String> mazeMap;              //<<---------          Change Type to Block when class is made

    /**
     * Constructs and initialises a new Maze
     * @param sizeX size of the x-axis for the maze
     * @param sizeY size of the y-axis for the maze
     */
    public Maze(int sizeX, int sizeY) {
        this.size = new int[]{sizeX, sizeY};
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
    public boolean isSolvable() {
        return solvable;
    }

    /**
     *Get's current maze name
     * @return mazes name
     */
    public String getMazeName() {
        return mazeName;
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

