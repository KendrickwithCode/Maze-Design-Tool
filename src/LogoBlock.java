import java.util.HashMap;
import java.util.Objects;

/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block{
    private String pictureFile;
    private final HashMap<String,String> images;
    private int[] size;
    private int[][] startPictureCoor;
    private Block neighbourNE;
    private Block neighbourN;
    private Block neighbourNW;
    private Block neighbourE;
    private Block neighbourW;
    private Block neighbourSE;
    private Block neighbourS;
    private Block neighbourSW;
    private final String imageType;

    /**
     * Constructs and initialises new LogoBlock which can display logos
     * @param blockIndex Index of logoBlock
     * @param location location of block on maze
     */
    public LogoBlock(int[] location, int blockIndex, Maze mazeMap, String picture, String imageType) throws Exception {
        super(location, blockIndex,false);
        setupNeighbourBlocks(mazeMap);
        images = new HashMap<>();
        images.put("dog","img/icons/Dog.png");
        images.put("bone","img/icons/Bone.png");
        images.put("mazeCo","img/icons/MazeCo.png");
        pictureFile = images.get(picture);
        this.imageType = imageType.toUpperCase();
    }

    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String pictureFile) {
        this.pictureFile = pictureFile;
    }

    private void setupNeighbourBlocks(Maze mazeMap) {

        try {
            neighbourN = mazeMap.getNeighbourBlock(this, "north");
        }catch (Exception e)
        {
            neighbourN = null;
        }

        try {
            neighbourNE =  mazeMap.getNeighbourBlock(neighbourN,"east");
        }catch (Exception e)
        {
            neighbourNE = null;
        }

        try {
            neighbourNW =  mazeMap.getNeighbourBlock(neighbourN,"west");
        }catch (Exception e)
        {
            neighbourNW = null;
        }

        try {
            neighbourE =  mazeMap.getNeighbourBlock(this,"east");
        }catch (Exception e)
        {
            neighbourE = null;
        }

        try {
            neighbourW =  mazeMap.getNeighbourBlock(this,"west");
        }catch (Exception e)
        {
            neighbourW = null;
        }

        try {
            neighbourS =  mazeMap.getNeighbourBlock(this,"south");
        }catch (Exception e)
        {
            neighbourS = null;
        }

        try {
            neighbourSE =  mazeMap.getNeighbourBlock(neighbourS,"east");
        }catch (Exception e)
        {
            neighbourSE = null;
        }

        try {
            neighbourSW =  mazeMap.getNeighbourBlock(neighbourS,"west");
        }catch (Exception e)
        {
            neighbourSW = null;
        }
    }

    public void setupLogoWalls(Maze mazeMap) {
//        System.out.println(this.getBlockIndex());
//        int lastIndex = mazeMap.getMazeMap().size()-1;
        if(Objects.equals(imageType, "END")){
            this.getWallEast().setFinish(true);
        }
        else if(Objects.equals(imageType, "START")){
            this.getWallEast().setStart(true);
            }

        this.getWallEast().setActive(false);
        this.getWallEast().setButtonEnable(false);
        this.getWallSouth().setActive(false);
        this.getWallSouth().setButtonEnable(false);

        neighbourSE.getWallWest().setActive(false);
        neighbourSE.getWallWest().setButtonEnable(false);
        neighbourSE.getWallNorth().setActive(false);
        neighbourSE.getWallNorth().setButtonEnable(false);

    }

}

