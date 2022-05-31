import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.HashMap;

/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block{
    private String pictureFile;
    private String logoType;
    private boolean logoStart;
    private int logoSizeX;
    private int logoSizeY;
    /**
     * Overload Constructor, Constructs and initialises new LogoBlock which can display logos
     * @param blockIndex Index of logoBlock
     * @param location location of block on maze
     */
    public LogoBlock(int[] location, int blockIndex, String picture) throws Exception {
        super(location, blockIndex,false);
        HashMap<String, String> images = new HashMap<>();
        images.put("start","img/icons/Dog.png");
        images.put("end","img/icons/Bone.png");
        images.put("logo","img/icons/MazeCo.png");
        picture = picture.toLowerCase();

        pictureFile = images.get(picture);

        if(picture.equals("start") || picture.equals("finish"))
        {
            logoType = "kids";
            if(picture.equals("start"))
                logoStart = true;
            else
                logoStart = false;

        }
        else
            logoType = "adult";

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
            pictureFile = imageFile.getPath();

            MazeLogoTools.getCurrentGUIMaze().renderBlocks();
            rerenderIcons();
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

    public String getLogoType() {
        return logoType;
    }

    public void setLogoType(String logoType) {
        this.logoType = logoType;
    }

    public boolean isLogoStart() {
        return logoStart;
    }

    public void setLogoStart(boolean logoStart) {
        this.logoStart = logoStart;
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

