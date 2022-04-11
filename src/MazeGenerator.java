import java.util.ArrayDeque;
import java.util.ArrayList;

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

    private static ArrayDeque<Block> stackList;
    private static Maze currentMaze;



    public static void GenerateMaze(Maze maze,int[] startPos){
        currentMaze = maze;
        maze.resetMaze(maze.getSize()[0],maze.getSize()[1],false);
        int startPosIndex = maze.getIndex(startPos);
        Block currentBlock = maze.getMazeMap().get(startPosIndex);

        setupDirections(currentBlock);      //Sets directions that can be traversed
        stackList.push(currentBlock);       //Get Start Pos Add to stack

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
            if (currentMaze.outOfBounds(currentBlockIndex,direction))
            {
                currentBlock.availableDirections.add(direction);
            }
        }
    }
}
