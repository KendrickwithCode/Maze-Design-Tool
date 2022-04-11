
//  Automate generator algorithm is based of the Wikipedia recursive algorithm (Recursive implementation).
//  https://en.wikipedia.org/wiki/Maze_generation_algorithm
//
//  The depth-first search algorithm of maze generation is frequently implemented using backtracking.
//  This can be described with a following recursive routine:
//
//        1.    Given a current cell as a parameter
//        2.    Mark the current cell as visited
//        3.    While the current cell has any unvisited neighbour cells
//                  1. Choose one of the unvisited neighbours
//                  2. Remove the wall between the current cell and the chosen cell
//                  3. Invoke the routine recursively for a chosen cell
//                     which is invoked once for any initial cell in the area.


import java.util.ArrayList;
import java.util.Random;



/**
 * Auto Generates A Maze and overwrites current one.
 */
public class MazeGenerator {
//    private static ArrayDeque<Block> stackList;
    private static Maze currentMaze;

    /**
     * Code entry point for recursive depth field search
     * @param startPosIndex set starting point block as an index
     */
    private static void depthFieldSearch(int startPosIndex)
    {
        Block currentBlock = currentMaze.getMazeMap().get(startPosIndex);
        depthFieldSearchRecursion(currentBlock);
    }

    /**
     * Recursive function for depth fielf search
     * @param currentBlock current block that is being worked on
     */
    private static void depthFieldSearchRecursion(Block currentBlock)
    {
        currentBlock.setVisited(true);
        setupDirections(currentBlock);
        String nextDirection = randomSelector(currentBlock.getAvailableDirections());

        if (!nextDirection.equals("END") ) {
            setWallState(currentBlock,nextDirection);
            Block nextBlock = currentMaze.getNeighbourBlock(currentBlock,nextDirection);
            depthFieldSearchRecursion(nextBlock);
        }
    }

    /**
     * Sets the Wall State of the currentBlock.
     * @param currentBlock block to be worked on.
     * @param direction direction of wall in block to be changed.
     */
    private static void setWallState(Block currentBlock, String direction)
    {
        switch (direction.toUpperCase()) {
            case "EAST" -> currentBlock.getWallEast().setActive(false);
            case "WEST" -> currentBlock.getWallWest().setActive(false);
            case "NORTH" -> currentBlock.getWallNorth().setActive(false);
            case "SOUTH" -> currentBlock.getWallSouth().setActive(false);
            default -> {
            }
        }
    }

    /**
     * selects a direction from the directionList that is set if no options it will return "END".
     * @param directionList the list of available directions to choose from (usually passed from a block field)
     * @return the next selected direction as a string. If there are no options it will return "END"
     */
    private static String randomSelector(ArrayList <String> directionList){
        Random randomIndex= new Random();
        if (directionList.size() > 0)
        return directionList.get(randomIndex.nextInt(directionList.size()));
        else return "END";
    }

    /**
     * Automation maze generator. This function will overwrite the current maze's map with
     * an automated generated maze.
     * @param maze the maze object to work on
     * @param startPosIndex the starting block point as an index integer.
     */
    public static void GenerateMaze(Maze maze,int startPosIndex){
        currentMaze = maze;
        depthFieldSearch(startPosIndex);

//        maze.resetMaze(maze.getSize()[0],maze.getSize()[1],false);
//        int startPosIndex = maze.getIndex(startPos);
//        Block currentBlock = maze.getMazeMap().get(startPosIndex);
//
//        setupDirections(currentBlock);      //Sets directions that can be traversed
//        stackList.push(currentBlock);       //Get Start Pos Add to stack

    }


    /**
     * Gets the available direction from current block
     * @param currentBlock is the current block of reference to get all available directions
     */
    private static void setupDirections(Block currentBlock)
    {
        int currentBlockIndex = currentMaze.getIndex(currentBlock.getLocation());

        for (String direction: new String[]{"NORTH","EAST","SOUTH","WEST"}
             ) {
            if (!currentMaze.outOfBounds(currentBlockIndex,direction) && !currentMaze.getNeighbourBlock(currentBlock, direction).getVisited())
            {
                currentBlock.availableDirections.add(direction);
            }
        }
    }
}
