public class MazeTools {

    public MazeTools() {
    }
    static int counter = 0;


    public static int[] randomLogoPlacerIndex(int[] size){
        int minX = 1;
        int minY = 1;
        int maxX = size[0]-2;
        int maxY = size[1]-2;
        int randomX = (int) ((Math.random()) * (maxX-minX) + minX);
        int randomY = (int) ((Math.random()) * (maxY-minY) + minY);

        return new int[]{randomX,randomY};
    }


    public static void setupLogoBlockNeighbours(Block logoBlock, Maze currentMaze)
    {
        logoBlock.setVisited(true);
        logoBlock.getWallNorth().setActive(true);
        logoBlock.getWallWest().setActive(true);

        currentMaze.getNeighbourBlock(logoBlock,"East").setVisited(true);
        currentMaze.getNeighbourBlock(logoBlock,"East").getWallNorth().setActive(true);
        currentMaze.getNeighbourBlock(logoBlock,"East").getWallEast().setActive(true);


        currentMaze.getNeighbourBlock(logoBlock,"South").setVisited(true);
        currentMaze.getNeighbourBlock(logoBlock,"South").getWallSouth().setActive(true);
        currentMaze.getNeighbourBlock(logoBlock,"South").getWallWest().setActive(true);

        Block temp = currentMaze.getNeighbourBlock(logoBlock,"East");
        currentMaze.getNeighbourBlock(temp,"South").setVisited(true);
        currentMaze.getNeighbourBlock(temp,"South").getWallSouth().setActive(true);
        currentMaze.getNeighbourBlock(temp,"South").getWallEast().setActive(true);
    }

//    /**
//     * Selects a random spot on the map to place the company logo.
//     * @throws Exception if trying to get out of bounds.
//     */
//    public static void randomLogoPlacer(Maze currentMaze) throws Exception {
//        int minX = 1;
//        int minY = 1;
//        int maxX = currentMaze.getSize()[0]-2;
//        int maxY = currentMaze.getSize()[1]-2;
//        int randomX = (int) ((Math.random()) * (maxX-minX) + minX);
//        int randomY = (int) ((Math.random()) * (maxY-minY) + minY);
//        int randomIndex = currentMaze.getIndex(new int[]{randomX, randomY});
//
//        Block currentBlock = currentMaze.getMazeMap().get(27);
//        //Create a new LogoBlock and replace in maze map array
//        replaceWithNewLogoBlock(currentBlock,currentMaze);
//
//        //Set walls to on
//        activateLogoWalls(currentBlock,currentMaze);
//
//        System.out.println("NeighbourR: "+currentMaze.getNeighbourBlock(currentBlock,"NORTH").getWallSouth());
//        System.out.println("Neighbour2:"+currentMaze.getMazeMap().get(2).getWallSouth());
//        MazeWall other = currentMaze.getNeighbourBlock(currentBlock,"NORTH").getWallSouth();
//        MazeWall mine = currentBlock.getWallNorth();
//
//        System.out.println("Mine      : "+currentBlock.getWallNorth());
//        System.out.println(counter);
//        counter++;
//    }

    /**
     * Replaces input block with a LogoBlock and places in maze map ArrayList
     * @param currentBlock  current Block you need to replace
     * @param currentMaze   current Maze you are working with
     * @throws Exception    out of bounds exception
     */
    public static void replaceWithNewLogoBlock(Block currentBlock, Maze currentMaze) throws Exception {
        //Create a new LogoBlock and replace in maze map array
//        currentBlock = new LogoBlock(currentBlock.getLocation(),currentBlock.getBlockIndex(),currentMaze,"mazeCo", "logo");
//        LogoBlock newBlock = (LogoBlock) currentBlock;
        currentBlock = new LogoBlock(currentBlock.getLocation(),currentBlock.getBlockIndex(),currentMaze,"mazeCo", "logo");
        //reAllocates BlockWalls memory references
        reAllocateBlockWalls(currentBlock,currentMaze);
    }

    /**
     * Re Allocates BlockWalls object references for wall relating to current block in current maze
     * @param currentBlock current block that is being re refenced
     * @param currentMaze current maze map that the block lives in.
     */
    public static void reAllocateBlockWalls(Block currentBlock, Maze currentMaze){
        //reAllocates BlockWalls memory references
        currentMaze.setMazeWalls(currentBlock);
        currentMaze.mazeMapUpdate(currentBlock.getBlockIndex(),currentBlock);
    }

    public static void activateLogoWalls(Block currentBlock, Maze currentMaze)
    {
        //Set walls to on
        currentBlock.getWallNorth().setActive(true);
        currentBlock.getWallWest().setActive(true);
        //Set visited so mazeGen does not over draw.
        currentBlock.setVisited(true);

        currentBlock = currentMaze.getNeighbourBlock(currentBlock,"east");
        currentBlock.setVisited(true);
        currentBlock.getWallNorth().setActive(true);
        currentBlock.getWallEast().setActive(true);

        currentBlock = currentMaze.getNeighbourBlock(currentBlock,"south");
        currentBlock.getWallSouth().setActive(true);
        currentBlock.getWallEast().setActive(true);
        currentBlock.setVisited(true);

        currentBlock = currentMaze.getNeighbourBlock(currentBlock,"west");
        currentBlock.getWallSouth().setActive(true);
        currentBlock.getWallWest().setActive(true);
        currentBlock.setVisited(true);
    }
}
