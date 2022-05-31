import java.util.ArrayList;

/**
 * Main class for holding all the contents and information of a maze.
 */
public class Maze {

    private int difficulty;
    private String mazeType;
    private boolean solvable;
    private String mazeName;
    private final int[] size;
    private final ArrayList<Block> mazeMap;
    private MazeGenerator mazeHolder;
    private int kidsStartIndex;
    private int kidsFinishIndex;
    private int logoBlockIndex;


    /**
     * Constructs and initialises a new Maze. Resulting maze is blank with only border walls activated.
     * @param sizeX size of the x-axis for the maze
     * @param sizeY size of the y-axis for the maze
     * @param name name of the maze
     */
    public Maze(int sizeX, int sizeY, String name, String mazeType) throws Exception {
        this.size = new int[]{sizeX, sizeY};
        this.mazeMap = new ArrayList<>();
        this.mazeName = name;
        this.solvable = false;
        this.mazeType = mazeType.toUpperCase();
        resetMaze(sizeX,sizeY);
        MazeLogoTools.setCurrentMaze(this);
    }

    /**
     * Activates all the wall objects that sit on the border of the maze. All the block and corresponding wall objects
     * in the maze must have already been created.
     * @param sizeX size of the x-axis of the maze
     * @param sizeY size of the y-axis of the maze
     */
    private void activateBorderWalls(int sizeX, int sizeY){
        int[] current = {0,0};  //used to hold the current position and get the index in mazeMap
        //set the east and west border walls
        for(int y = 0; y < sizeY; y++) {
            current[0] = sizeX - 1;
            current[1] = y;
            mazeMap.get(getIndex(current)).getWallEast().setBorder();
            current[0] = 0;
            mazeMap.get(getIndex(current)).getWallWest().setBorder();
        }
        //set the north and south walls
        for(int x = 0; x < sizeX; x++) {
            current[0] = x;
            current[1] = 0;
            mazeMap.get(getIndex(current)).getWallNorth().setBorder();
            current[1] = sizeY - 1;
            mazeMap.get(getIndex(current)).getWallSouth().setBorder();
        }
    }

    public void setMazeType(String mazeType) {
        this.mazeType = mazeType;
    }

    public String getMazeType() {
        return mazeType;
    }

    /**
     * Auto generates a new maze. Destroys old maze while generating a new one
     * @param algorithm the algorithm used to generate the maze "DFSIterative", "DFSRecursive"
     * @param startPosXY the starting position int[x,y]
     */
    public void generateNewMaze(String algorithm,int[] startPosXY) throws Exception {
        int startIndex = getIndex(startPosXY);
        resetMaze(false);
        MazeGenerator.GenerateMaze(this,startIndex,algorithm);
    }

    /**
     * Overload Auto generates a new maze. Destroys old maze while generating a new one
     */
    public void generateNewMaze() throws Exception {
        generateNewMaze("DFSIterative",new int[]{0,0});
    }


    /**
     * Resets the maze map to new clear blocks with only the outer border walls activated.
     * @param sizeX X-axis size of the maze
     * @param sizeY Y-axis size of the maze
     * @param clearWalls Boolean to set internal maze walls
     */
    public void resetMaze(int sizeX, int sizeY, Boolean clearWalls) throws Exception {
        mazeMap.clear();
        kidsStartIndex = 0;
        kidsFinishIndex = MazeLogoTools.getKidsFinishIndex(this);
//        logoBlockIndex = 0;

//        int[] logoOriginXY = MazeLogoTools.randomLogoPlacerIndex(size);

        int currentIndex=0;
        /*
         *  Iterates through all maze Cells and makes a new empty block
         */
        for (int y =0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
//                if(mazeType.equalsIgnoreCase("ADULT") && x == logoOriginXY[0] && logoOriginXY[1] == y) {
//                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex, this, "logo"));
//                    logoBlockIndex = currentIndex;
//                }
                if(mazeType.equalsIgnoreCase("KIDS") && currentIndex == kidsStartIndex){
                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex,  "start"));
                }
                else if(mazeType.equalsIgnoreCase("KIDS") && currentIndex == kidsFinishIndex){
                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex,  "end"));
                }
                else
                {
                    mazeMap.add(new MazeBlock(new int[]{x,y},currentIndex, clearWalls));
                }
                setMazeWalls(mazeMap.get(currentIndex));
                currentIndex++;
            }

        }
        activateBorderWalls(sizeX,sizeY);
    }


    /**
     * Overload Resets the maze map to new clear blocks with only the outer border walls activated.
     * @param clearWalls boolean for clearing or setting walls.
     */
    public void resetMaze(Boolean clearWalls) throws Exception {
        this.resetMaze(size[0],size[1], clearWalls);
    }

    /**
     * Overload resetMaze - Passes a default true Parameter to resetMaze
     * @param sizeX X-axis size of the maze
     * @param sizeY Y-axis size of the maze
     */
    public void resetMaze(int sizeX, int sizeY) throws Exception {
        this.resetMaze(sizeX,sizeY, true);
    }


    /**
     * Sets all the walls in the maze
     * @param currentBlock current Block from mazeMap Arraylist
     */
    public void setMazeWalls(Block currentBlock)
    {
        // If along the wall edge of y wall belongs to this block else get reference
        if(currentBlock.getLocation()[1] == 0)
        {
            currentBlock.setWallNorth(new MazeWall());
        }else{
            currentBlock.setWallNorth(getNeighbourBlock(currentBlock,"NORTH").getWallSouth());
        }

        // if along the wall edge of x wall belongs to this block else get reference
        if(currentBlock.getLocation()[0] == 0)
        {
            currentBlock.setWallWest(new MazeWall());
        }else
        {
            currentBlock.setWallWest(getNeighbourBlock(currentBlock,"WEST").getWallEast());
        }
    }


    /**
     * Return the index of requested neighbouring block "NORTH", "EAST", "SOUTH" ," WEST"
     * @param referenceBlock origin maze block
     * @param direction neighbouring block direction "NORTH", "EAST", "SOUTH" ," WEST"
     * @return the index or -1 for out of maze map boundary or -2 for direction invalid entry
     */
    public int getNeighbourIndex(Block referenceBlock, String direction){

        int[] newLocation = referenceBlock.getLocation().clone();

        if(outOfBounds(getIndex(newLocation),direction)){return -1;}

        /*
         * Updates newLocation [x,y] according to parameter direction
         */
        switch (direction.toUpperCase()){
            case "NORTH":
                newLocation[1] -= 1;
                break;
            case "SOUTH":
                newLocation[1] += 1;
                break;
            case "WEST":
                newLocation[0] -= 1;
                break;
            case "EAST":
                newLocation[0] += 1;
                break;
            default:
                return -2;
        }

        return getIndex(newLocation);
    }

    /**
     * Check's if neighbour block is out of the mazes boundary
     * @param index array index number
     * @param direction neighbouring block direction "NORTH", "EAST", "SOUTH" ," WEST"
     * @return boolean true if out of bounds or false if not
     */
    public Boolean outOfBounds(int index, String direction )
    {

        switch (direction.toUpperCase()) {
            case "EAST":
                if ((index + 1) % size[0] == 0) {
                    return true;
                }
                break;
            case "WEST":
                if ((index + 1) % size[0] == 1) {
                    return true;
                }
                break;
            case "NORTH":
                if (index / size[0] == 0) {
                    return true;
                }
                break;
            case "SOUTH":
                if ((index / size[0] >= size[1] - 1)) {
                    return true;
                }
                break;
            default:
                return false;
        }
        return false;
    }

    /**
     * Return the index of requested neighbouring block "NORTH", "EAST", "SOUTH" ," WEST"
     * @param referenceBlock referenceBlock origin maze block
     * @param direction neighbouring block direction "NORTH", "EAST", "SOUTH" ," WEST"
     * @return returns the neighbour block
     */
    public Block getNeighbourBlock(Block referenceBlock, String direction){

        return mazeMap.get(getNeighbourIndex(referenceBlock,direction));

    }

    /**
     * Gets the ArrayList index of the asked location
     * @param location [x,y] of the cell you like to know the Arraylist index
     * @return the index of the supplied location2
     */
    public int getIndex(int[] location)
    {
        int x = location[0];
        int y = location[1];



        return y*size[0]+x;
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
     * Returns current maze name
     * @return mazes name
     */
    public String getMazeName() {
        return mazeName;
    }

    /**
     * Returns the ArrayList mazes map
     * @return maze map as an ArrayList
     */
    public ArrayList<Block> getMazeMap() {
        return mazeMap;
    }

    /**
     * Returns current maze size as copy
     * @return current maze size [x,y]
     */
    public int[] getSize() {
        return size.clone();
    }

    /**
     * Sets difficulty level
     * @param difficulty new level for difficulty
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Sets maze solvable
     * @param isSolvable boolean true if maze is solvable else false if maze is not solvable
     */
    public void setSolvable(boolean isSolvable) {
        this.solvable = isSolvable;

    }

    /**
     * Sets the maze name
     * @param mazeName The new name for maze
     */
    public void setMazeName(String mazeName) {
        this.mazeName = mazeName;
    }


    public int getKidsStartIndex() {
        return kidsStartIndex;
    }



    public int getKidsFinishIndex() {
        return kidsFinishIndex;
    }



    public int getLogoBlockIndex() {
        return logoBlockIndex;
    }

    public void setKidsStartIndex(int kidsStartIndex) {
        this.kidsStartIndex = kidsStartIndex;
    }

    public void setKidsFinishIndex(int kidsFinishIndex) {
        this.kidsFinishIndex = kidsFinishIndex;
    }
}

