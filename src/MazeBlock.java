public class MazeBlock extends Block{

    /**
     * Constructs and initialises new Block
     * @param location location of block on maze
     * @param blockIndex inter index of block
     */
    public MazeBlock(int[] location, int blockIndex) {
        super(location, blockIndex);
    }

    /**
     * Overload Constructs and initialises new Block
     * @param location location of block on maze
     * @param blockIndex inter index of block
     * @param clearWalls boolean sets the value for clearing walls true / false.
     */
    public MazeBlock(int[] location, int blockIndex, boolean clearWalls) {
        super(location, blockIndex, clearWalls);
    }
}
