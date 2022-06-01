
public class MazeLogoTools {


    private static Maze currentMaze;
    private static GUI_Maze currentGUIMaze;

    /**
     * Random placement index generator for placing logo on the maze.
     * @param mazeSize size of maze [x,y].
     * @return index for maze map ArrayList.
     */
    public static int[] randomLogoPlacerIndex(int[] mazeSize){
        int minX = 1;
        int minY = 1;
        int maxX = mazeSize[0]-2;
        int maxY = mazeSize[1]-2;
        int randomX = (int) ((Math.random()) * (maxX-minX) + minX);
        int randomY = (int) ((Math.random()) * (maxY-minY) + minY);

        return new int[]{randomX,randomY};
    }

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
        if(westWall)
            workingBlock.getWallWest().resetWall();
        else
            workingBlock.getWallEast().resetWall();

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

        if(northWall)
            workingBlock.getWallNorth().resetWall();
        else
            workingBlock.getWallSouth().resetWall();

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
        if(westWall)
            workingBlock.getWallWest().setActive(true,false);
        else
            workingBlock.getWallEast().setActive(true,false);

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

        if(northWall)
            workingBlock.getWallNorth().setActive(true,false);
        else
            workingBlock.getWallSouth().setActive(true,false);

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
        MazeLogoTools.currentMaze = currentMaze;
    }

    public static void setCurrentGUIMaze(GUI_Maze currentGUIMaze) {
        MazeLogoTools.currentGUIMaze = currentGUIMaze;
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
        LogoBlock working = new LogoBlock(inputBLock.getLocation(),inputBLock.getBlockIndex(),logoType);
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
