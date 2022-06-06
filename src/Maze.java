import java.io.*;
import java.util.ArrayList;

/**
 * Main class for holding all the contents and information of a maze.
 */
public class Maze implements Serializable {


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
                if(workingBlock.getLocation()[0] != currentMaze.getWidth())
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
                if(workingBlock.getLocation()[1] != currentMaze.getHeight())
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
        public static int getKidsFinishIndex(Maze currentMaze){
            int posX = currentMaze.getSize()[0]-2;
            int posY = currentMaze.getSize()[1]-2;

            return currentMaze.getIndex(new int[]{posX,posY});
        }

        public static Maze getCurrentMaze() {
            return currentMaze;
        }

        public static void setCurrentMaze(Maze currentMaze) {
            MazeTools.currentMaze = currentMaze;
        }

        public static void setCurrentGUIMaze(GUI_Maze currentGUIMaze) {
            MazeTools.currentGUIMaze = currentGUIMaze;
        }

        public static GUI_Maze getCurrentGUIMaze() {
            return currentGUIMaze;
        }

        public static void deleteMazeObj(){
            currentMaze = null;
            currentGUIMaze = null;
        }

        /**
         * converts a Maze block to a Logo block.
         * @param inputBLock block to be converted.
         * @param logoType logo type to use (logo, start, finish).
         * @throws Exception on error of logoBLock creation.
         */
        public static void convertMazeBlockToLogoBlock(Block inputBLock,String logoType) throws Exception {
            LogoBlock working = new LogoBlock(inputBLock.getLocation(),inputBLock.getBlockIndex(),logoType,true);
            working.setWallNorth(inputBLock.getWallNorth());
            working.setWallSouth(inputBLock.getWallSouth());
            working.setWallEast(inputBLock.getWallEast());
            working.setWallWest(inputBLock.getWallWest());
            working.setBlockPanel(inputBLock.getBlockPanel());

            getCurrentMaze().getMazeMap().set(inputBLock.getBlockIndex(),working);
        }

        /**
         * converts a Maze block to a Logo block.
         * @param inputBLock block to be converted.
         */
        public static void convertLogoBlockToWallBlock(Block inputBLock) {
            MazeBlock working = new MazeBlock(inputBLock.getLocation(),inputBLock.getBlockIndex(),true);
            working.setWallNorth(inputBLock.getWallNorth());
            working.setWallSouth(inputBLock.getWallSouth());
            working.setWallEast(inputBLock.getWallEast());
            working.setWallWest(inputBLock.getWallWest());
            working.setBlockPanel(inputBLock.getBlockPanel());

            getCurrentMaze().getMazeMap().set(inputBLock.getBlockIndex(),working);
        }

    }



    @Serial
    private static final long serialVersionUID = 4L;
    private int difficulty;
    public String mazeName;
    private String mazeType;
    private String mazeDescription;
    private int mazeHeight;
    private int mazeWidth;
    private String authorName;
    private boolean solvable;
    private int[] size;
    private ArrayList<Block> mazeMap;
    private MazeGenerator mazeHolder;
    private int kidsStartIndex;
    private int kidsFinishIndex;



    /**
     * Constructs and initialises a new Maze. Resulting maze is blank with only border walls activated.
     *
     * @param sizeX size of the x-axis for the maze
     * @param sizeY size of the y-axis for the maze
     * @param name  name of the maze
     */
    public Maze(int sizeX, int sizeY, String name, String mazeType, String mazeDescription, String authorName) throws Exception {
        this.size = new int[]{sizeX, sizeY};
        this.mazeWidth = sizeX;
        this.mazeHeight = sizeY;
        this.mazeDescription = mazeDescription;
        this.authorName = authorName;
        this.mazeMap = new ArrayList<>();
        this.mazeName = name;
        this.solvable = false;
        this.mazeType = mazeType.toUpperCase();
        resetMaze(sizeX, sizeY);
        MazeTools.setCurrentMaze(this);
    }


    public void setMazeType(String mazeType) {
        this.mazeType = mazeType;
    }

    public int getWidth() {
        return mazeWidth;
    }

    public String getWidthAsString(){ return Integer.toString(mazeWidth);}

    public int getHeight() {
        return mazeHeight;
    }
    public String getHeightAsString(){ return Integer.toString(mazeHeight);}

    public String getMazeDescription() {
        return mazeDescription;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setMazeDescription(String mazeDescription) {
        this.mazeDescription = mazeDescription;
    }

    public void setWidth(int mazeWidth) {
        this.mazeWidth = mazeWidth;
    }

    public void setHeight(int mazeHeight) {
        this.mazeHeight = mazeHeight;
    }

    /**
     * Activates all the wall objects that sit on the border of the maze. All the block and corresponding wall objects
     * in the maze must have already been created.
     *
     * @param sizeX size of the x-axis of the maze
     * @param sizeY size of the y-axis of the maze
     */
    private void activateBorderWalls(int sizeX, int sizeY) {
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
    public void generateNewMaze(String algorithm, int[] startPosXY) throws Exception {
        int startIndex = getIndex(startPosXY);
        resetMaze(false);
        MazeGenerator.GenerateMaze(this, startIndex, algorithm);
    }

    /**
     * Overload Auto generates a new maze. Destroys old maze while generating a new one
     */
    public void generateNewMaze() throws Exception {
        generateNewMaze("DFSIterative", new int[]{0, 0});
    }


    /**
     * Resets the maze map to new clear blocks with only the outer border walls activated.
     *
     * @param sizeX      X-axis size of the maze
     * @param sizeY      Y-axis size of the maze
     * @param clearWalls Boolean to set internal maze walls
     */
    public void resetMaze(int sizeX, int sizeY, Boolean clearWalls) throws Exception {
        mazeMap.clear();
        kidsStartIndex = 0;
        kidsFinishIndex = MazeTools.getKidsFinishIndex(this);
//        logoBlockIndex = 0;

//        int[] logoOriginXY = MazeLogoTools.randomLogoPlacerIndex(size);

        int currentIndex = 0;
        /*
         *  Iterates through all maze Cells and makes a new empty block
         */
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
//                if(mazeType.equalsIgnoreCase("ADULT") && x == logoOriginXY[0] && logoOriginXY[1] == y) {
//                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex, this, "logo"));
//                    logoBlockIndex = currentIndex;
//                }
                if(mazeType.equalsIgnoreCase("KIDS") && currentIndex == kidsStartIndex){
                    mazeMap.add(new LogoBlock(new int[]{x, y}, currentIndex,  "start",clearWalls));
                }
                else if(mazeType.equalsIgnoreCase("KIDS") && currentIndex == kidsFinishIndex){
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
        activateBorderWalls(sizeX, sizeY);
    }


    /**
     * Overload Resets the maze map to new clear blocks with only the outer border walls activated.
     *
     * @param clearWalls boolean for clearing or setting walls.
     */
    public void resetMaze(Boolean clearWalls) throws Exception {
        this.resetMaze(size[0], size[1], clearWalls);
    }

    /**
     * Overload resetMaze - Passes a default true Parameter to resetMaze
     *
     * @param sizeX X-axis size of the maze
     * @param sizeY Y-axis size of the maze
     */
    public void resetMaze(int sizeX, int sizeY) throws Exception {
        this.resetMaze(sizeX, sizeY, true);
    }


    /**
     * Sets all the walls in the maze
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
     * @param location [x,y] of the cell you like to know the Arraylist index
     * @return the index of the supplied location2
     */
    public int getIndex(int[] location) {
        int x = location[0];
        int y = location[1];


        return y * size[0] + x;
    }

    /**
     * Returns current difficulty level
     *
     * @return difficulty level
     */

    public int getDifficulty() {
        return difficulty;
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
     * Sets difficulty level
     *
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
     *
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


    public void setKidsStartIndex(int kidsStartIndex) {
        this.kidsStartIndex = kidsStartIndex;
    }

    public void setKidsFinishIndex(int kidsFinishIndex) {
        this.kidsFinishIndex = kidsFinishIndex;
    }
}
