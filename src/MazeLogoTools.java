public class MazeLogoTools {

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
     * @param logoBlock block to be the origin point for a logo block
     * @param currentMaze maze object that the logo-block lives in.
     */
    public static void setupAdultLogoBlocks(Block logoBlock, Maze currentMaze)
    {
        logoBlock.setVisited(true);
        logoBlock.getWallNorth().setBorder();
        logoBlock.getWallWest().setBorder();

        currentMaze.getNeighbourBlock(logoBlock,"East").setVisited(true);
        currentMaze.getNeighbourBlock(logoBlock,"East").getWallNorth().setBorder();
        currentMaze.getNeighbourBlock(logoBlock,"East").getWallEast().setBorder();

        currentMaze.getNeighbourBlock(logoBlock,"South").setVisited(true);
        currentMaze.getNeighbourBlock(logoBlock,"South").getWallSouth().setBorder();
        currentMaze.getNeighbourBlock(logoBlock,"South").getWallWest().setBorder();

        MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).setVisited(true);
        MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).getWallSouth().setBorder();
        MazeLogoTools.getSouthEastBlock(logoBlock,currentMaze).getWallEast().setBorder();

        clearLogoBlockCenter(logoBlock,currentMaze);
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
}