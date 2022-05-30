import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class produces a solution to the maze if it is solvable and stores the shortest path.
 */
public class MazeSolver extends JPanel{
    private int deadEndCount = 0;
    private static class solvingBlock {

        private Block block;
        private solvingBlock parent;

        public solvingBlock (Block block, solvingBlock parent){
            this.block = block;
            this.parent = parent;
        }

        public Block getBlock() {
            return this.block;
        }

        public solvingBlock getParent() {
            return this.parent;
        }

    }

    private solvingBlock createPathBFS(Maze maze){

        Queue<solvingBlock> queue = new LinkedList<>();

        for (Block block : maze.getMazeMap()){
            if(isStartingBlock(block)){
                queue.add(new solvingBlock(block, null));
            }
        }
        solvingBlock solutionBlock = null;

        while (!queue.isEmpty()){

            solvingBlock currentBlock = queue.remove();

            if(isFinishingBlock(currentBlock.getBlock())){
                solutionBlock = new solvingBlock(currentBlock.getBlock(), currentBlock.parent);
            }

            int borderCount = 4;
            if ( !currentBlock.getBlock().getWallSouth().getActive() && !currentBlock.getBlock().getWallSouth().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "SOUTH");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));

                }
                borderCount -= 1;
            }
            if ( !currentBlock.getBlock().getWallEast().getActive() && !currentBlock.getBlock().getWallEast().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "EAST");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));

                }
                borderCount -= 1;
            }
            if ( !currentBlock.getBlock().getWallNorth().getActive() && !currentBlock.getBlock().getWallNorth().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "NORTH");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));

                }
                borderCount -= 1;
            }
            if ( !currentBlock.getBlock().getWallWest().getActive() && !currentBlock.getBlock().getWallWest().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "WEST");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));

                }
                borderCount -= 1;
            }

            if(borderCount == 3 && !isStartingBlock(currentBlock.getBlock()) && !isFinishingBlock(currentBlock.getBlock())){
                this.deadEndCount += 1;
            }

        }
        if(solutionBlock != null){
            return solutionBlock;
        } else {
            return null;
        }
    }

    /**
     * Returns amount cells that are a dead end
     * @return integer amount of cells that are dead ends
     */
    public int getDeadEndCount() {
        return deadEndCount;
    }

    /**
     * Return an array list for of locations in order to solve maze using breadth first search
     * @param maze Maze object to solve
     * @return Solution locations in order to solve maze
     */
    public ArrayList<Block> solveMaze ( Maze maze){
        resetMaze(maze);

        ArrayList<Block> solution = new ArrayList<>();

        solvingBlock block = createPathBFS(maze);

        if(block == null){ return solution;}

        while(block != null){
            solution.add(block.getBlock());
            block = block.getParent();
        }

        return solution;
    }

    private void setDefaultStartingWall(Maze maze){
        maze.getMazeMap().get(0).getWallNorth().setStart(true);
    }

    private void setDefaultFinishingWall(Maze maze){
        maze.getMazeMap().get(maze.getMazeMap().size() - 1).getWallSouth().setFinish(true);
    }

    private void resetMaze(Maze maze) {
        // reset maze visited and set maze start and finish blocks
        for ( Block block : maze.getMazeMap() ) {
            block.setVisited(false);
            if(block instanceof LogoBlock && maze.getMazeType().equalsIgnoreCase("KIDS")){
                if(block.getBlockIndex() == 0){
                    block.getWallSouth().setStart(true);
                } else {
                    block.getWallSouth().setFinish(true);
                }
            }

        }
        if(!checkForFinishingWall(maze)){setDefaultFinishingWall(maze);}

        if(!checkForStartingWall(maze)){setDefaultStartingWall(maze);}
    }

    private boolean checkForStartingWall(Maze maze){
        boolean startingWall = false;
        for ( Block block : maze.getMazeMap() ) {
            if(isStartingBlock(block)){
                startingWall = true;
            }
        }
        return startingWall;
    }

    private boolean checkForFinishingWall(Maze maze){
        boolean finishingWall = false;
        for ( Block block : maze.getMazeMap() ) {
            if(isFinishingBlock(block)){
                finishingWall = true;
            }
        }
        return finishingWall;
    }

    private boolean isStartingBlock(Block block){
        return (block.getWallNorth().isStart() || block.getWallSouth().isStart() || block.getWallWest().isStart() || block.getWallEast().isStart());
    }

    private boolean isFinishingBlock(Block block){
        return (block.getWallNorth().getFinish() || block.getWallSouth().getFinish() || block.getWallWest().getFinish() || block.getWallEast().getFinish());
    }


}