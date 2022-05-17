import java.util.HashMap;

/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block{
    private String pictureFile;

    /**
     * Overload Constructor, Constructs and initialises new LogoBlock which can display logos
     * @param blockIndex Index of logoBlock
     * @param location location of block on maze
     */
    public LogoBlock(int[] location, int blockIndex, Maze mazeMap, String picture) throws Exception {
        super(location, blockIndex,false);
        HashMap<String, String> images = new HashMap<>();
        images.put("start","img/icons/Dog.png");
        images.put("end","img/icons/Bone.png");
        images.put("logo","img/icons/MazeCo.png");
        pictureFile = images.get(picture);
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String filePath)
    {
        pictureFile = filePath;
    }

    /**
     * Return what type of block this block is
     * @return String the type of block this is.
     */
    @Override
    public String getBlockType() {
        return "LogoBlock";
    }
}

