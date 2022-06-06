import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class produces a solution to the maze if it is solvable and stores the shortest path.
 */
public class MazeSolver {

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

    /**
     * Returns amount cells that are a dead end
     * @return integer amount of cells that are dead ends
     */
    public int deadEndCount(Maze maze) {
        int deadEndCount = 0;

        for(Block block : maze.getMazeMap()){
            int walls = 0;

            if ( block.getWallSouth().getActive() || block.getWallSouth().getborder() ){walls += 1;}
            if ( block.getWallNorth().getActive() || block.getWallNorth().getborder() ){walls += 1;}
            if ( block.getWallEast().getActive() || block.getWallEast().getborder() ){walls += 1;}
            if ( block.getWallWest().getActive() || block.getWallWest().getborder() ){walls += 1;}

            if(walls >= 3){
                deadEndCount += 1;
            }
        }

        return deadEndCount;
    }

    private solvingBlock createPathBFS(Maze maze){

        Queue<solvingBlock> queue = new LinkedList<>();

        for (Block block : maze.getMazeMap()){
            if(isStartingBlock(block)){
                queue.add(new solvingBlock(block, null));
            }
        }

        while (!queue.isEmpty()){

            solvingBlock currentBlock = queue.remove();

            if(isFinishingBlock(currentBlock.getBlock())){ return currentBlock; }

            if ( !currentBlock.getBlock().getWallSouth().getActive() && !currentBlock.getBlock().getWallSouth().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "SOUTH");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));
                }
            }
            if ( !currentBlock.getBlock().getWallEast().getActive() && !currentBlock.getBlock().getWallEast().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "EAST");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));
                }
            }
            if ( !currentBlock.getBlock().getWallNorth().getActive() && !currentBlock.getBlock().getWallNorth().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "NORTH");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));
                }
            }
            if ( !currentBlock.getBlock().getWallWest().getActive() && !currentBlock.getBlock().getWallWest().getborder() ) {
                Block neighborBlock = maze.getNeighbourBlock(currentBlock.getBlock(), "WEST");
                if (!neighborBlock.getVisited()) {
                    neighborBlock.setVisited(true);
                    queue.add(new solvingBlock(neighborBlock, currentBlock));
                }
            }

        }

        return null;
    }

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
                if(((LogoBlock) block).getLogoType().equalsIgnoreCase("kids")) {
                    if (((LogoBlock) block).isLogoStart()) {
                        block.getWallSouth().setStart(true);
                    } else {
                        block.getWallSouth().setFinish(true);
                    }
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