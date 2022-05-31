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
     * Configure the logo block walls and cells for the Adult maze type
     * @param logoBlock block to be the origin point for a logo block*
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


////        logoBlock.setVisited(true);
//        logoBlock.getWallNorth().setBorder();
//        logoBlock.getWallWest().setBorder();
//
////        currentMaze.getNeighbourBlock(logoBlock,"East").setVisited(true);
//        currentMaze.getNeighbourBlock(logoBlock,"East").getWallNorth().setBorder();
//        currentMaze.getNeighbourBlock(logoBlock,"East").getWallEast().setBorder();
//
////        currentMaze.getNeighbourBlock(logoBlock,"South").setVisited(true);
//        currentMaze.getNeighbourBlock(logoBlock,"South").getWallSouth().setBorder();
//        currentMaze.getNeighbourBlock(logoBlock,"South").getWallWest().setBorder();
//
////        MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).setVisited(true);
//        MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).getWallSouth().setBorder();
//        MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).getWallEast().setBorder();
//
////        clearLogoBlockCenter(logoBlock,currentMaze);
    }

    private static void setWallsEastWest(int endY, int currentIndexY, boolean westWall)
    {
        Block workingBlock = currentMaze.getMazeMap().get(currentIndexY);
        int nextIndex = currentMaze.getNeighbourIndex(workingBlock,"south");
        int currentY = workingBlock.getLocation()[1];
        if(westWall)
            workingBlock.getWallWest().setActive(true);
        else
            workingBlock.getWallEast().setActive(true);
        System.out.println(currentIndexY + " - "+ nextIndex + " - " + workingBlock.getLocation()[0] + "," + workingBlock.getLocation()[1]);

        if (endY == currentY)                       //base Case
            return;
        setWallsEastWest(endY,nextIndex,westWall);     //Recurse
    }


    private static void setWallsNorthSouth(int endX, int currentIndexX, boolean northWall)
    {

        Block workingBlock = currentMaze.getMazeMap().get(currentIndexX);
        int nextIndex = currentMaze.getNeighbourIndex(workingBlock,"east");
        int currentX = workingBlock.getLocation()[0];

        if(northWall)
            workingBlock.getWallNorth().setActive(true);
        else
            workingBlock.getWallSouth().setActive(true);
//        System.out.println(currentIndexX + " - "+ nextIndex + " - " + workingBlock.getLocation()[0] + "," + workingBlock.getLocation()[0]);
//        System.out.println("CurrentX: "+currentX);
//        System.out.println("EndX: " + endX);

        if (endX == currentX)                       //base Case
            return;

        setWallsNorthSouth(endX,nextIndex,northWall);               //Recurse
    }


    /**
     * Configure the logo block walls and cells for the Kids maze type
     * @param logoBlock block to be the origin point for a logo block
     * @param currentMaze maze object that the logo-block lives in.
     * @param start sets the image for a start or end image
     */
    public static void setupKidsLogoBlockNeighbours(Block logoBlock, Maze currentMaze, Boolean start){
        if(start){
            MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).setVisited(true);
        }
        else{
            logoBlock.setVisited(true);
        }
        clearLogoBlockCenter(logoBlock,currentMaze);
    }

    /**
     * Removes walls and disables them for logo-block centres.
     * @param logoBlock block logo block object to be worked on.
     * @param currentMaze the current maze the logo block comes from.
     */
    public static void clearLogoBlockCenter(Block logoBlock,Maze currentMaze)
    {
        logoBlock.getWallEast().setActive(false);
        logoBlock.getWallEast().setButtonEnableVisible(false);
        logoBlock.getWallSouth().setActive(false);
        logoBlock.getWallSouth().setButtonEnableVisible(false);
        getSouthEastBlock(logoBlock,currentMaze).getWallNorth().setActive(false);
        getSouthEastBlock(logoBlock,currentMaze).getWallNorth().setButtonEnableVisible(false);
        getSouthEastBlock(logoBlock,currentMaze).getWallWest().setActive(false);
        getSouthEastBlock(logoBlock,currentMaze).getWallWest().setButtonEnableVisible(false);
    }

    /**
     * Gets the south-east neighbouring block
     * @param currentBlock current block that is being referenced from.
     * @param currentMaze the current maze the block comes from.
     * @return the block from the south-east of our reference block (currentBlock).
     */
    public static Block getSouthEastBlock(Block currentBlock, Maze currentMaze)
    {
        Block southBlock = currentMaze.getNeighbourBlock(currentBlock,"south");
        return currentMaze.getNeighbourBlock(southBlock,"east");
    }

    /**
     * Sets up the kids finish index for any size maze and returns the index
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

    public static void convertMazeBlockToLogoBlock(Block inputBLock) throws Exception {
        LogoBlock working = new LogoBlock(inputBLock.getLocation(),inputBLock.getBlockIndex(),getCurrentMaze(),"logo");
        working.setWallNorth(inputBLock.getWallNorth());
        working.setWallSouth(inputBLock.getWallSouth());
        working.setWallEast(inputBLock.getWallEast());
        working.setWallWest(inputBLock.getWallWest());
        working.setBlockPanel(inputBLock.getBlockPanel());

        getCurrentMaze().getMazeMap().set(inputBLock.getBlockIndex(),working);
    }

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
