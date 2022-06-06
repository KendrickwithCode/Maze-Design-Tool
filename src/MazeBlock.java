import java.io.Serializable;

/**
 * Standard maze block/cell used in the mazes map.
 */
public class MazeBlock extends Block implements Serializable {

    /**
     * Constructs and initialises new Block
     *
     * @param location   location of block on maze
     * @param blockIndex inter index of block
     * @param clearWalls boolean sets the value for clearing walls true / false.
     */
    public MazeBlock(int[] location, int blockIndex, boolean clearWalls) {
        super(location, blockIndex, clearWalls);
    }

    /**
     * Return what type of block this block is
     *
     * @return String the type of block this is.
     */
    @Override
    public String getBlockType() {
        return "MazeBlock";
    }
}
