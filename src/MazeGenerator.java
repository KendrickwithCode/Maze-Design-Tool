
//  Automate generator algorithm is based of the Wikipedia algorithms.
//  https://en.wikipedia.org/wiki/Maze_generation_algorithm

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;


/**
 * Auto Generates A Maze and overwrites current one.
 */
public class MazeGenerator {
    private static ArrayDeque<Block> stackList;
    private static Maze currentMaze;

    /**
     * Automation maze generator. This function will overwrite the current maze's map with
     * an automated generated maze.
     * @param maze the maze object to work on
     * @param startPosIndex the starting block point as an index integer.
     * @param algorithm generation algorithm ("DFSIterative","DFSRecursive").
     */
    public static void GenerateMaze(Maze maze,int startPosIndex, String algorithm) {
        currentMaze = maze;
        stackList = new ArrayDeque<>();
        Block firstBlock = currentMaze.getMazeMap().get(startPosIndex);

        switch (algorithm.toUpperCase())
        {
            case "DFSRECURSIVE":
                depthFieldSearchRecursion(firstBlock);
                break;

            case "DFSITERATIVE":
                depthFieldSearchIterative(firstBlock);
            default:
                depthFieldSearchIterative(firstBlock);
                break;
        }
    }


    /**
     * Generate a maze via Depth Field Search Iterative Algorithm.
     *  Algorithm From Wikipedia
     *  web address = en.wikipedia.org/wiki/Maze_generation_algorithm
     *
     *       1.  Choose the initial cell, mark it as visited and push it to the stack
     *       2.  While the stack is not empty
     *               1.  Pop a cell from the stack and make it a current cell
     *               2.  If the current cell has any neighbours which have not been visited
     *                       1.  Push the current cell to the stack
     *                       2.  Choose one of the unvisited neighbours
     *                       3.  Remove the wall between the current cell and the chosen cell
     *                       4.  Mark the chosen cell as visited and push it to the stack
     *
     * @param firstBlock block to start from.
     */
    private static void depthFieldSearchIterative(Block firstBlock) {
        firstBlock.setVisited(true);
        stackList.push(firstBlock);

        while (!stackList.isEmpty())
        {
            Block currentBlock = stackList.pop();
            setupDirections(currentBlock);
            if(!currentBlock.getAvailableDirections().isEmpty())
            {
                stackList.push(currentBlock);
                String nextDirection = randomSelector(currentBlock.getAvailableDirections());
                if(!nextDirection.equals("END")) {
                    Block nextBlock=setupMoveToNextBlock(currentBlock, nextDirection);
                    nextBlock.setVisited(true);
                    stackList.push(nextBlock);
                }
            }
        }
    }

    /**
     * Generate a maze via Depth Field Search Recursive Algorithm.
     *  Algorithm From Wikipedia
     *  web address = en.wikipedia.org/wiki/Maze_generation_algorithm
     *
     *       1.  Given a current cell as a parameter
     *       2.  Mark the current cell as visited
     *       3.  While the current cell has any unvisited neighbour cells
     *                 1.  Choose one of the unvisited neighbours
     *                 2.  Remove the wall between the current cell and the chosen cell
     *                 3.  Invoke the routine recursively for a chosen cell
     *                     which is invoked once for any initial cell in the area.
     *
     * @param currentBlock current block that is being worked on.
     */
    private static void depthFieldSearchRecursion(Block currentBlock) {
        if(currentBlock.hasNotBeenVisited()){
            stackList.push(currentBlock);
        }

        currentBlock.setVisited(true);
        setupDirections(currentBlock);
        String nextDirection = randomSelector(currentBlock.getAvailableDirections());

        if (!nextDirection.equals("END") ) {
            Block nextBlock = setupMoveToNextBlock(currentBlock,nextDirection);
            depthFieldSearchRecursion(nextBlock);
        }
        else
        {
            stackList.pop();
            if (stackList.size() > 0) {
                depthFieldSearchRecursion(stackList.getFirst());
            }
        }
    }

    /**
     * Removes wall needed to move to the next block
     * @param currentBlock reference block (block that you are working from).
     * @param nextDirection the direction you wish to move to for the next block "NORTH", "EAST", "SOUTH", "WEST"
     * @return the next block from the direction you chose to move.
     */
    private static Block setupMoveToNextBlock(Block currentBlock, String nextDirection) {
        setWallState(currentBlock,nextDirection);
        return currentMaze.getNeighbourBlock(currentBlock,nextDirection);
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
     * Gets the available direction from current block. Check out of bounds and if the block has already been visited.
     * @param currentBlock is the current block of reference to get all available directions
     */
    private static void setupDirections(Block currentBlock) {
        currentBlock.clearAvailableDirections();
        int currentBlockIndex = currentMaze.getIndex(currentBlock.getLocation());

        for (String direction: new String[]{"NORTH","EAST","SOUTH","WEST"}
             ) {
            // If next block is not out of bounds and has not been visited add as a direction that can be travelled.
            if (!currentMaze.outOfBounds(currentBlockIndex,direction) && currentMaze.getNeighbourBlock(currentBlock, direction).hasNotBeenVisited())
            {
                currentBlock.getAvailableDirections().add(direction);
            }
        }
    }
}
