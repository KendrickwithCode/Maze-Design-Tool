import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.Serial;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Block used to store images on the maze.
 */
public class LogoBlock extends Block implements Serializable{
    @Serial
    private static final long serialVersionUID = 7L;

    private String pictureFile;
    private final String logoType;
    private boolean logoStart;
    private int logoSizeX;
    private int logoSizeY;
    /**
     * Overload Constructor, Constructs and initialises new LogoBlock which can display logos
     * @param blockIndex Index of logoBlock
     * @param location location of block on maze
     * @param clearWalls true will clear walls on creation, false will activate them.
     * @param picture The type of logoBlock. Either "Start", "end" or, "logo"
     */
    public LogoBlock(int[] location, int blockIndex, String picture, boolean clearWalls) {
        super(location, blockIndex,clearWalls);
        HashMap<String, String> images = new HashMap<>();
        images.put("start","img/icons/Dog.png");
        images.put("end","img/icons/Bone.png");
        images.put("logo","img/icons/MazeCo.png");
        picture = picture.toLowerCase();

        pictureFile = images.get(picture);

        if(picture.equals("start") || picture.equals("end"))
        {
            logoType = "kids";
            logoStart = picture.equals("start");

        }
        else
            logoType = "adult";

        ///Default Logo Sizes
        logoSizeX=2;
        logoSizeY=2;
    }

    /**
     * Will display a JFile chooser. The logo image will be changed to the image picked from the chooser.
     */
    public void changeLogo() {
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Image Files (*.png | *.jpg | *.bmp)", "png", "jpg", "bmp"));

        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File imageFile = fc.getSelectedFile();
            pictureFile = imageFile.getPath();

            Maze.MazeTools.getCurrentGUIMaze().renderBlocks();
            renderIcons();
            Maze.MazeTools.getCurrentGUIMaze().revalidate();
            Maze.MazeTools.getCurrentGUIMaze().repaint();
        }
    }

    /**
     * Gets the picture file for the logo
     * @return the file path of the actual image
     */
    public String getPictureFile() {
        return pictureFile;
    }

//    public void setPictureFile(String filePath)
//    {
//        pictureFile = filePath;
//    }

    /**
     * Gets the logo cell width
     * @return the logo width in  number of blocks
     */
    public int getLogoSizeX() {
        return logoSizeX;
    }

    /**
     * sets the logo width in number of blocks
     * @param logoSizeX logo width in number of blocks
     */
    public void setLogoSizeX(int logoSizeX) {
        this.logoSizeX = logoSizeX;
    }

    /**
     * Gets the logo cell height
     * @return the logo height in number of blocks
     */
    public int getLogoSizeY() {
        return logoSizeY;
    }

    /**
     * sets the logo height in number of blocks
     * @param logoSizeY logo height in number of blocks
     */
    public void setLogoSizeY(int logoSizeY) {
        this.logoSizeY = logoSizeY;
    }

    /**
     * Gets the logoType as a string
     * @return String LogoType will be "kids" or "ADULT"
     */
    public String getLogoType() {
        return logoType;
    }

    /**
     * Will return true if the logo is a starting logo. A starting logo represents the start point in a kid maze.
     * @return true if logo is a starting logo
     */
    public boolean isLogoStart() {
        return logoStart;
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

