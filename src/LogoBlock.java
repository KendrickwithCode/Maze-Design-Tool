/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block{
    private String pictureFile;
    private int[] size;
    private int[][] startPictureCoor;

    /**
     * Constructs and initialises new LogoBlock which can display logos
     * @param blockIndex Index of logoBlock
     * @param location location of block on maze
     */
    public LogoBlock(int[] location, int blockIndex) {
        super(location, blockIndex,false);
    }
}
