public class MazeBlock extends Block{
    private boolean visited;

    /**
     * Constructs and initialises new Block
     *
     * @param location location of block on maze
     */
    public MazeBlock(int[] location, int blockIndex) {
        super(location, blockIndex);
    }

    public MazeBlock(int[] location, int blockIndex, boolean clearWalls) {
        super(location, blockIndex, clearWalls);
    }
}
