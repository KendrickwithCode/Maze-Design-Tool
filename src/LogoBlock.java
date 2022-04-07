public class LogoBlock extends Block{
    private String pictureFile;
    private int[] size;
    private int[][] startPictureCoor;

    /**
     * Constructs and initialises new Block
     *
     * @param location location of block on maze
     */
    public LogoBlock(int[] location, int blockIndex) {
        super(location, blockIndex);
    }
}
