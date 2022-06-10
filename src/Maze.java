import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Main class for holding all the contents and information of a maze. The maze is a two-dimensional array of Block objects arranged in a grid pattern.
 */
public class Maze implements Serializable {

    @Serial
    private static final long serialVersionUID = 4L;
    /**
     * The name of the maze as specified in the Maze name field on the GUI
     */
    public String mazeName;

    /**
     * A string representing the maze type. Must be equal to either "kids" or "adult"
     */
    private String mazeType;
    private String mazeDescription;
    private int mazeHeight;
    private int mazeWidth;
    private String authorName;
    private boolean solvable;
    private final int[] size;
    private final ArrayList<Block> mazeMap;
    private int kidsStartIndex;
    private int kidsFinishIndex;

    /**
     * Maze tools class
     */
    public static class MazeTools {

        private static Maze currentMaze;
        private static GUI_Maze currentGUIMaze;

        /**
         * Resets walls back to previous wall state.
         * @param working block to reset walls to previous state.
         */
        public static void resetWalls(LogoBlock working){
            int sizeX = working.getLogoSizeX();
            int sizeY = working.getLogoSizeY();

            int currentY = working.getLocation()[1];
            int currentX = working.getLocation()[0];
            int endY = currentY+sizeY-1;
            int endX = currentX+sizeX-1;

            int indexEast = currentMaze.getIndex(new int[]{endX,currentY});
            resetWallsEastWest(endY,working.getBlockIndex(),true);
            resetWallsEastWest(endY,indexEast,false);

            int indexSth = currentMaze.getIndex(new int[]{currentX,endY});
            resetWallsNorthSouth(endX,working.getBlockIndex(),true);
            resetWallsNorthSouth(endX,indexSth,false);
        }

        /**
         * Resets East or West walls to previous state.
         * @param endY end off cell Y-axis.
         * @param currentIndexY current index
         * @param westWall west wall if true to reset or false will do east wall.
         */
        private static void resetWallsEastWest(int endY, int currentIndexY, boolean westWall)
        {
            Block workingBlock = currentMaze.getMazeMap().get(currentIndexY);
            int nextIndex = currentMaze.getNeighbourIndex(workingBlock,"south");
            int currentY = workingBlock.getLocation()[1];
            if(westWall) {
                workingBlock.getWallWest().resetWall();

                if(workingBlock.getLocation()[0] != 0)
                    workingBlock.getWallWest().setBorder(false);
            }
            else {
                workingBlock.getWallEast().resetWall();
                if(workingBlock.getLocation()[0]+1 != currentMaze.getWidth())
                    workingBlock.getWallEast().setBorder(false);
            }
            if (endY == currentY)                       //base Case
                return;
            resetWallsEastWest(endY,nextIndex,westWall);     //Recurse
        }

        /**
         * Resets North or South walls to previous state.
         * @param endX end off cell X-axis.
         * @param currentIndexX current index
         * @param northWall north wall if true to reset or false will do south wall.
         */
        private static void resetWallsNorthSouth(int endX, int currentIndexX, boolean northWall)
        {
            Block workingBlock = currentMaze.getMazeMap().get(currentIndexX);
            int nextIndex = currentMaze.getNeighbourIndex(workingBlock,"east");
            int currentX = workingBlock.getLocation()[0];

            if(northWall) {
                workingBlock.getWallNorth().resetWall();
                if(workingBlock.getLocation()[1] != 0)
                    workingBlock.getWallNorth().setBorder(false);
            }
            else {
                workingBlock.getWallSouth().resetWall();
                if(workingBlock.getLocation()[1]+1 != currentMaze.getHeight())
                    workingBlock.getWallSouth().setBorder(false);
            }

            if (endX == currentX)                       //base Case
                return;

            resetWallsNorthSouth(endX,nextIndex,northWall);               //Recurse
        }

        /**
         * Configure the logo block walls and cells for the Adult maze type.
         * @param logoBlock block to be the origin point for a logo block.
         * @param sizeX size in cells in length along x-axis.
         * @param sizeY size in cells in length along y-axis.
         */
        public static void setupAdultLogoBlocks(Block logoBlock,int sizeX, int sizeY)
        {
            int currentY = logoBlock.getLocation()[1];
            int currentX = logoBlock.getLocation()[0];
            int endY = currentY+sizeY-1;
            int endX = currentX+sizeX-1;

            int indexEast = currentMaze.getIndex(new int[]{endX,currentY});
            setWallsEastWest(endY,logoBlock.getBlockIndex(),true);
            setWallsEastWest(endY,indexEast,false);

            int indexSth = currentMaze.getIndex(new int[]{currentX,endY});
            setWallsNorthSouth(endX,logoBlock.getBlockIndex(),true);
            setWallsNorthSouth(endX,indexSth,false);
        }


        /**
         * Sets East or West walls active.
         * @param endY end off cell Y-axis.
         * @param currentIndexY current index
         * @param westWall set west wall active if true else east wall will be set to active
         */
        private static void setWallsEastWest(int endY, int currentIndexY, boolean westWall)
        {
            Block workingBlock = currentMaze.getMazeMap().get(currentIndexY);
            int nextIndex = currentMaze.getNeighbourIndex(workingBlock,"south");
            int currentY = workingBlock.getLocation()[1];
            if(westWall) {
                workingBlock.getWallWest().setActive(true, false);
                workingBlock.getWallWest().setBorder(true);
            }
            else {
                workingBlock.getWallEast().setActive(true, false);
                workingBlock.getWallEast().setBorder(true);
            }

            if (endY == currentY)                       //base Case
                return;
            setWallsEastWest(endY,nextIndex,westWall);     //Recurse
        }

        /**
         * Sets North or South walls active.
         * @param endX end off cell X-axis.
         * @param currentIndexX current index
         * @param northWall set north wall active if true else south wall will be set to active
         */
        private static void setWallsNorthSouth(int endX, int currentIndexX, boolean northWall)
        {

            Block workingBlock = currentMaze.getMazeMap().get(currentIndexX);
            int nextIndex = currentMaze.getNeighbourIndex(workingBlock,"east");
            int currentX = workingBlock.getLocation()[0];

            if(northWall) {
                workingBlock.getWallNorth().setActive(true, false);
                workingBlock.getWallNorth().setBorder(true);
            }
            else {
                workingBlock.getWallSouth().setActive(true, false);
                workingBlock.getWallSouth().setBorder(true);
            }

            if (endX == currentX)                       //base Case
                return;

            setWallsNorthSouth(endX,nextIndex,northWall);               //Recurse
        }

        /**
         * Sets up the kids finish index for any size maze and returns the index.
         * @param currentMaze the current maze where the index is going to be used on.
         * @return index for placing a kids finish logo.
         */
        private static int getKidsFinishIndex(Maze currentMaze){
            int posX = currentMaze.getSize()[0]-2;
            int posY = currentMaze.getSize()[1]-2;

            return currentMaze.getIndex(new int[]{posX,posY});
        }

        /**
         * returns current maze object
         * @return current maze object
         */
        public static Maze getCurrentMaze() {
            return currentMaze;
        }

        /**
         * Sets current maze object
         * @param currentMaze Maze Object to set as current maze
         */
        public static void setCurrentMaze(Maze currentMaze) {
            MazeTools.currentMaze = currentMaze;
        }

        /**
         * Sets current GUIMaze object
         * @param currentGUIMaze GUIMaze object to set current GUIMaze
         */
        public static void setCurrentGUIMaze(GUI_Maze currentGUIMaze) {
            MazeTools.currentGUIMaze = currentGUIMaze;
        }

        /**
         * return current GUIMaze
         * @return Current GUIMaze object
         */
        public static GUI_Maze getCurrentGUIMaze() {
            return currentGUIMaze;
        }

        /**
         * Clears current Maze and GUIMaze object
         */
        public static void deleteMazeObj(){
            currentMaze = null;
            currentGUIMaze = null;
        }

        /**
         * converts a Maze block to a Logo block.
         * @param inputBLock block to be converted.
         * @param logoType logo type to use (logo, start, finish).
         */
        public static void convertMazeBlockToLogoBlock(Block inputBLock,String logoType) {
            LogoBlock working = new LogoBlock(inputBLock.getLocation(),inputBLock.getBlockIndex(),logoType,true);
            working.setWallNorth(inputBLock.getWallNorth());
            working.setWallSouth(inputBLock.getWallSouth());
            working.setWallEast(inputBLock.getWallEast());
            working.setWallWest(inputBLock.getWallWest());
            working.setBlockPanel(inputBLock.getBlockPanel());

            getCurrentMaze().getMazeMap().set(inputBLock.getBlockIndex(),working);
        }

        /**
         * converts a Logo block to a Maze block.
         * @param inputBLock block to be converted.
         * @throws Exception on invalid conversion of block.
         */
        public static void convertLogoBlockToMazeBlock(Block inputBLock) throws Exception {
            if (Objects.equals(currentMaze.getMazeMap().get(inputBLock.getBlockIndex()).getBlockType(), "MazeBlock"))
            {
                throw new Exception("Invalid Conversion of " + inputBLock.getBlockType());
            }
            MazeBlock working = new MazeBlock(inputBLock.getLocation(),inputBLock.getBlockIndex(),true);
            working.setWallNorth(inputBLock.getWallNorth());
            working.setWallSouth(inputBLock.getWallSouth());
            working.setWallEast(inputBLock.getWallEast());
            working.setWallWest(inputBLock.getWallWest());
            working.setBlockPanel(inputBLock.getBlockPanel());

            getCurrentMaze().getMazeMap().set(inputBLock.getBlockIndex(),working);
        }

    }


    /**
     * Constructs and initialises a new Maze. Resulting maze is blank with only border walls activated.
     *
     * @param sizeX size of the x-axis for the maze in number of blocks
     * @param sizeY size of the y-axis for the maze in number of blocks
     * @param mazeType Whether the maze is a kids or adult maze
     * @param name The name of the maze
     * @param mazeDescription a description of the maze
     * @param authorName The name of the author
     */
    public Maze(int sizeX, int sizeY, String name, String mazeType, String mazeDescription, String authorName) {
        this.size = new int[]{sizeX, sizeY};
        this.mazeWidth = sizeX;
        this.mazeHeight = sizeY;
        this.mazeDescription = mazeDescription;
        this.authorName = authorName;
        this.mazeMap = new ArrayList<>();
        this.mazeName = name;
        this.solvable = false;
        this.mazeType = mazeType;
        resetMaze(sizeX, sizeY);
        MazeTools.setCurrentMaze(this);
    }


    /**
     * Sets type of maze
     * @param mazeType String maze type ("Kids" or "Adult")
     */
    public void setMazeType(String mazeType) {
        this.mazeType = mazeType;
    }

    /**
     * Returns width of maze
     * @return Integer width of maze
     */
    public int getWidth() {
        return mazeWidth;
    }

    /**
     * Returns width of maze as string
     * @return String version of integer width of maze
     */
    public String getWidthAsString(){ return Integer.toString(mazeWidth);}

    /**
     * Returns height of maze
     * @return Integer height of maze
     */
    public int getHeight() {
        return mazeHeight;
    }

    /**
     * Returns height of maze as string
     * @return String version of integer height of maze
     */
    public String getHeightAsString(){ return Integer.toString(mazeHeight);}

    /**
     * Returns maze description of maze
     * @return String maze description of maze
     */
    public String getMazeDescription() {
        return mazeDescription;
    }

    /**
     * Returns authors name of maze
     * @return String authors name maze
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Set authors name of maze
     * @param authorName String authors name of maze
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Sets description of maze
     * @param mazeDescription String description of maze
     */
    public void setMazeDescription(String mazeDescription) {
        this.mazeDescription = mazeDescription;
    }

    /**
     * Sets width of maze
     * @param mazeWidth Integer width of maze
     */
    public void setWidth(int mazeWidth) {
        this.mazeWidth = mazeWidth;
    }

    /**
     * Sets height of maze
     * @param mazeHeight Int height of maze
     */
    public void setHeight(int mazeHeight) {
        this.mazeHeight = mazeHeight;
    }

    /**
     * Activates all the wall objects that sit on the border of the maze. All the block and corresponding wall objects
     * in the maze must have already been created.
     */
    public void activateBorderWalls() {
        int sizeX = size[0];
        int sizeY = size[1];
        int[] current = {0, 0};  //used to hold the current position and get the index in mazeMap
        //set the east and west border walls
        for (int y = 0; y < sizeY; y++) {
            current[0] = sizeX - 1;
            current[1] = y;
            mazeMap.get(getIndex(current)).getWallEast().setBorder(true);
            current[0] = 0;
            mazeMap.get(getIndex(current)).getWallWest().setBorder(true);
        }
        //set the north and south walls
        for (int x = 0; x < sizeX; x++) {
            current[0] = x;
            current[1] = 0;
            mazeMap.get(getIndex(current)).getWallNorth().setBorder(true);
            current[1] = sizeY - 1;
            mazeMap.get(getIndex(current)).getWallSouth().setBorder(true);
        }
    }

    /**
     * Auto generates a new maze. Destroys old maze while generating a new one
     *
     * @param algorithm  the algorithm used to generate the maze "DFSIterative", "DFSRecursive"
     * @param startPosXY the starting position int[x,y]
     */
    public void generateNewMaze(String algorithm, int[] startPosXY) {
        int startIndex = getIndex(startPosXY);
        resetMaze(false);
        MazeGenerator.GenerateMaze(this, startIndex, algorithm);
    }

    /**
     * Overload Auto generates a new maze. Destroys old maze while generating a new one
     */
    public void generateNewMaze() {
        generateNewMaze("DFSIterative", new int[]{0, 0});
    }


    /**
     * Resets the maze map to new clear blocks with only the outer border walls activated.
     *
     * @param sizeX      X-axis size of the maze
     * @param sizeY      Y-axis size of the maze
     * @param clearWalls Boolean to set internal maze walls
     */
    public void resetMaze(int sizeX, int sizeY, Boolean clearWalls) {
        mazeMap.clear();
        kidsStartIndex = 0;
        kidsFinishIndex = MazeTools.getKidsFinishIndex(this);

        int currentIndex = 0;
        /*
         *  Iterates through all maze Cells and makes a new empty block
         */
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                if(mazeType.equalsIgnoreCase("Kids") && currentIndex == kidsStartIndex){
                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex,  "start",clearWalls));
                }
                else if(mazeType.equalsIgnoreCase("Kids") && currentIndex == kidsFinishIndex){
                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex,  "end",clearWalls));
                }
                else
                {
                    mazeMap.add(new MazeBlock(new int[]{x,y},currentIndex, clearWalls));
                }
                setMazeWalls(mazeMap.get(currentIndex));
                currentIndex++;
            }
        }
        activateBorderWalls();
    }

    /**
     * Overload Resets the maze map to new clear blocks with only the outer border walls activated.
     *
     * @param clearWalls boolean for clearing or setting walls.
     */
    public void resetMaze(Boolean clearWalls)  {
        this.resetMaze(size[0], size[1], clearWalls);
    }

    /**
     * Overload resetMaze - Passes a default true Parameter to resetMaze
     *
     * @param sizeX X-axis size of the maze
     * @param sizeY Y-axis size of the maze
     */
    public void resetMaze(int sizeX, int sizeY) {
        this.resetMaze(sizeX, sizeY, true);
    }


    /**
     * Instantiates all the walls for the passed block that must be contained within a mazeMap. It is given either new instantiated Wall class objects for northernWall and WesternWall or, if these walls exist in neighbour blocks, they are referenced from their neighbor’s wall object.
     *
     * @param currentBlock current Block from mazeMap Arraylist
     */
    public void setMazeWalls(Block currentBlock) {
        // If along the wall edge of y wall belongs to this block else get reference
        if (currentBlock.getLocation()[1] == 0) {
            currentBlock.setWallNorth(new MazeWall());
        } else {
            currentBlock.setWallNorth(getNeighbourBlock(currentBlock, "NORTH").getWallSouth());
        }

        // if along the wall edge of x wall belongs to this block else get reference
        if (currentBlock.getLocation()[0] == 0) {
            currentBlock.setWallWest(new MazeWall());
        } else {
            currentBlock.setWallWest(getNeighbourBlock(currentBlock, "WEST").getWallEast());
        }
    }


    /**
     * Return the index of requested neighbouring block "NORTH", "EAST", "SOUTH" ," WEST"
     *
     * @param referenceBlock origin maze block
     * @param direction      neighbouring block direction "NORTH", "EAST", "SOUTH" ," WEST"
     * @return the index or -1 for out of maze map boundary or -2 for direction invalid entry
     */
    public int getNeighbourIndex(Block referenceBlock, String direction) {

        int[] newLocation = referenceBlock.getLocation().clone();

        if (outOfBounds(getIndex(newLocation), direction)) {
            return -1;
        }

        /*
         * Updates newLocation [x,y] according to parameter direction
         */
        switch (direction.toUpperCase()) {
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
     *
     * @param index     array index number
     * @param direction neighbouring block direction "NORTH", "EAST", "SOUTH" ," WEST"
     * @return boolean true if out of bounds or false if not
     */
    public Boolean outOfBounds(int index, String direction) {

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
     *
     * @param referenceBlock referenceBlock origin maze block
     * @param direction      neighbouring block direction "NORTH", "EAST", "SOUTH" ," WEST"
     * @return returns the neighbour block
     */
    public Block getNeighbourBlock(Block referenceBlock, String direction) {

        return mazeMap.get(getNeighbourIndex(referenceBlock, direction));

    }

    /**
     * Gets the ArrayList index of the asked location
     *
     * @param location [x,y] of the cell for which you want to know the Arraylist index
     * @return the index of the supplied location
     */
    public int getIndex(int[] location) {
        int x = location[0];
        int y = location[1];


        return y * size[0] + x;
    }

    /**
     * Returns a boolean value of if the maze is currently solvable
     *
     * @return if maze is solvable ture or false
     */
    public boolean getSolvable() {
        return solvable;
    }

    /**
     * Returns current maze name
     *
     * @return mazes name
     */
    public String getMazeName() {
        return mazeName;
    }

    /**
     * Return Maze type - Adult/Kids
     * @return String value of maze type equal to either
     */
    public String getMazeType() {
        return mazeType;
    }


    /**
     * Returns the ArrayList mazes map
     *
     * @return maze map as an ArrayList
     */
    public ArrayList<Block> getMazeMap() {
        return mazeMap;
    }

    /**
     * Returns current maze size as copy
     *
     * @return current maze size [x,y]
     */
    public int[] getSize() {
        return size.clone();
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

    /**
     * Gets start block index of kids maze
     * @return Integer index of starting block
     */
    public int getKidsStartIndex() {
        return kidsStartIndex;
    }

    /**
     * Get finish block index of kids maze
     * @return Integer index of finish block
     */
    public int getKidsFinishIndex() {
        return kidsFinishIndex;
    }

    /**
     * Sets start block index of kids maze
     * @param kidsStartIndex Integer index of starting block
     */
    public void setKidsStartIndex(int kidsStartIndex) {
        this.kidsStartIndex = kidsStartIndex;
    }

    /**
     * Sets finish block index of kids maze
     * @param kidsFinishIndex Integer index of finish block
     */
    public void setKidsFinishIndex(int kidsFinishIndex) {
        this.kidsFinishIndex = kidsFinishIndex;
    }
}
