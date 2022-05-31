import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;

/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block{
    private String pictureFile;
    private int logoSizeX;
    private int logoSizeY;
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

        ///Default Logo Sizes
        logoSizeX=2;
        logoSizeY=2;
    }

    public void changeLogo() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Image Files (*.png | *.jpg | *.bmp)", "png", "jpg", "bmp"));

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            File imageFile = fc.getSelectedFile();

//            LogoBlock current = (LogoBlock) currentMaze.getMazeMap().get(blockIndex);

            pictureFile = imageFile.getPath();

            MazeLogoTools.getCurrentGUIMaze().renderBlocks();
            rerenderIcons();
            //maze.renderBlocks();
        }
    }



    public String getPictureFile() {
        return pictureFile;
    }

    public void setPictureFile(String filePath)
    {
        pictureFile = filePath;
    }

    public int getLogoSizeX() {
        return logoSizeX;
    }

    public void setLogoSizeX(int logoSizeX) {
        this.logoSizeX = logoSizeX;
    }

    public int getLogoSizeY() {
        return logoSizeY;
    }

    public void setLogoSizeY(int logoSizeY) {
        this.logoSizeY = logoSizeY;
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

