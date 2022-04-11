import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

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

public class MazeGenerator {

//    private static ArrayDeque<Block> stackList;
    private static Maze currentMaze;

    public static void depthFieldSearch(int startPosIndex)
    {
        Block currentBlock = currentMaze.getMazeMap().get(startPosIndex);
        depthFieldSearchRecursion(currentBlock);
    }

    private static void depthFieldSearchRecursion(Block currentBlock)
    {
        currentBlock.setVisited(true);
        setupDirections(currentBlock);
        String nextDirection = randomSelector(currentBlock.getAvailableDirections());

        if ( !nextDirection.isEmpty()) {
            setWallState(currentBlock,nextDirection);
            Block nextBlock = currentMaze.getNeighbourBlock(currentBlock,nextDirection);
            depthFieldSearchRecursion(nextBlock);
        }
    }

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

    private static String randomSelector(ArrayList <String> directionList){
        Random randomIndex= new Random();
        return directionList.get(randomIndex.nextInt(directionList.size()));
    }

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
            if (currentMaze.outOfBounds(currentBlockIndex,direction) && !currentMaze.getNeighbourBlock(currentBlock, direction).getVisited())
            {
                currentBlock.availableDirections.add(direction);
            }
        }
    }
}
