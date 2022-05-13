import java.util.HashMap;
import java.util.Objects;

/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block{
    private String pictureFile;
    private final HashMap<String,String> images;
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
     * Overload Constructor, Constructs and initialises new LogoBlock which can display logos
     * @param blockIndex Index of logoBlock
     * @param location location of block on maze
     */
    public LogoBlock(int[] location, int blockIndex, Maze mazeMap, String picture, String imageType) throws Exception {
        super(location, blockIndex,false);
//        setupNeighbourBlocks(mazeMap);
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

    private void logoInnerWalls(boolean wallGuiState)
    {
//        this.getWallEast().setButtonEnableVisible(wallGuiState);
//        this.getWallSouth().setButtonEnableVisible(wallGuiState);
//        neighbourSE.getWallWest().setButtonEnableVisible(wallGuiState);
//        neighbourSE.getWallNorth().setButtonEnableVisible(wallGuiState);
    }

    public void setupLogoWalls(Maze mazeMap) {

        if(Objects.equals(imageType, "END")){
            logoInnerWalls(false);
        }
        else if(Objects.equals(imageType, "START")){
            logoInnerWalls(false);
            }
        else if(Objects.equals(imageType, "LOGO")){
            logoInnerWalls(false);
        }
    }

}

