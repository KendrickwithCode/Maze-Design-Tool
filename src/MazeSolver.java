import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class produces a solution to the maze if it is solvable and stores the shortest path.
 */
public class MazeSolver{
    private ArrayList<int[]> solution;

    /**
     * Return an array list for of locations in order to solve maze
     * @param maze Maze object to solve
     * @return Solution locations in order to solve maze
     */
    public ArrayList<Block> solveMaze (Maze maze){
        // Create stack
        ArrayList<Block> solution = new ArrayList<Block>();

        // reset maze visited and set maze start and finish blocks
        for ( Block block : maze.getMazeMap() ) {
            block.setVisited(false);
            if (Arrays.equals(block.getLocation(), new int[]{0, 0})){
                block.startingBlock = true;
                solution.add(block);
            } else if (Arrays.equals(block.getLocation(), new int[]{maze.getSize()[0] - 1, maze.getSize()[1] - 1})) {
                block.finishingBlock = true;
            }
        }

        // while Stack isn't empty
        while ( solution.size() > 0 ){

            // Get block at top of stack
            Block currentBlock = solution.get(solution.size() - 1);

            // set current block visited
            currentBlock.setVisited(true);

            // if block is end block return (solution)
            if ( currentBlock.finishingBlock ){
//                solution.removeIf( block -> !block.getVisited());
                for ( Block block : solution) {
                    block.getBlockPanel().setBackground(Color.CYAN);
                    if (block.finishingBlock) {
                        block.getBlockPanel().setBackground(Color.RED);
                    }
                    if (block.startingBlock) {
                        block.getBlockPanel().setBackground(Color.GREEN);
                    }
                }
                return solution;
            }

            if ( !currentBlock.getWallNorth().getActive() && !currentBlock.getWallNorth().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock, "NORTH");
                if (!neighborBlock.getVisited()) {
                    solution.add(neighborBlock);
                    continue;
                }
            }
            if ( !currentBlock.getWallSouth().getActive() && !currentBlock.getWallSouth().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock, "SOUTH");
                if (!neighborBlock.getVisited()) {
                    solution.add(neighborBlock);
                    continue;
                }
            }
            if ( !currentBlock.getWallWest().getActive() && !currentBlock.getWallWest().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock, "WEST");
                if (!neighborBlock.getVisited()) {
                    solution.add(neighborBlock);
                    continue;
                }
            }
            if ( !currentBlock.getWallEast().getActive() && !currentBlock.getWallEast().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock, "EAST");
                if (!neighborBlock.getVisited()) {
                    solution.add(neighborBlock);
                    continue;
                }
            }

            solution.remove(solution.size()-1);

        }

        return solution;
    }
}
