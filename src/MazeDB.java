import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Maze Database
 */
public class MazeDB{

    public static final String SELECT = "SELECT * FROM maze WHERE idx = 1";
    private static final String INSERT_MAZE = "INSERT INTO maze " +
            "(Maze_Name, Maze_Type, Author_Name, Author_Description, Width, Height, Image) VALUES (?, ?, ?, ?, ?, ?, ?);";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "Maze_Name VARCHAR(30),"
                    + "Maze_Type VARCHAR(5),"
                    + "Date_Created DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + "Last_Edited TIMESTAMP CURRENT_TIMESTAMP,"
                    + "Author_Name VARCHAR(30),"
                    + "Author_Description VARCHAR(180),"
                    + "Width VARCHAR(3),"
                    + "Height VARCHAR(3),"
                    + "Image LONGBLOB" + ");";

    public Connection connection;
    private PreparedStatement addMaze;
    private PreparedStatement getMaze;
    private PreparedStatement deleteMaze;
    private PreparedStatement rowCount;
    private PreparedStatement image;
    public Statement st;

    /**
     * Constructs and Initialises connection to database
     */
    public MazeDB(){
        try {
            connection = DBConnection.getInstance();
            st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addMaze = connection.prepareStatement(INSERT_MAZE);
            //getMaze = connection.prepareStatement(GET_PERSON);
            //deleteMaze = connection.prepareStatement(DELETE_PERSON);
            //rowCount = connection.prepareStatement(COUNT_ROWS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Attempts to read data saved from previous invocations of the application
     */
    public void mazeData(){

    }

    /**
     * Add a maze to the database.
     * @param maze The maze to be added.
     * @param Maze
     */
    public void addMaze(String maze, String type, String author,
                        String description, String height, String width, GUI_Maze Maze) throws SQLException, IOException {
            addMaze.setString(1, maze);
            addMaze.setString(2, type);
            addMaze.setString(3, author);
            addMaze.setString(4, description);
            addMaze.setString(5, width);
            addMaze.setString(6, height);

//            //Convert GUI_Maze object to image icon to be fed to blob
            Rectangle rec = Maze.getBounds();
            BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
            Maze.paint(bufferedImage.getGraphics());

            // Create temp file
            File temp = File.createTempFile("screenshot", ".png");

            // Use the ImageIO API to write the bufferedImage to a temporary file that deletes on exit
            ImageIO.write(bufferedImage, "png", temp);
            temp.deleteOnExit();

            //Upload the binary stream to database
            FileInputStream input = new FileInputStream(temp);
            addMaze.setBinaryStream(7, input, (int)temp.length());
            addMaze.execute();
    }

    public ImageIcon getImage() throws SQLException {
        ResultSet rs = st.executeQuery(SELECT);
        ImageIcon printImage = new ImageIcon();
        while (rs.next()) {
            byte[] image = rs.getBytes("Image");
            ImageIcon icon = new ImageIcon(image);
            Image img = icon.getImage();
            Image img2 = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            printImage = new ImageIcon(img2);
        }
        return printImage;
    }




//        byte[] data;
//        ImageIcon Image = null;
//        try{
//            ResultSet rs = st.executeQuery(SELECT);
//            //use the getBlob method to retrieve the object from the DB
//            Blob blob = rs.getBlob("Image");
//            data = blob.getBytes(6, (int)blob.length());
//
//            //convert bytes back to object stream
//            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
//            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
//            Image = (ImageIcon) objectStream.readObject();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return Image;


    /**
     * Delete a Maze from the database.
     * @param key The maze to be removed.
     */
    public void removeMaze(Object key){

    }


    /**
     * Retrieve a Maze from the database.
     * @param key The maze to be retrieved.
     */
    public void getMaze(Object key){

    }

    /**
     * Close connection to database
     */
    public void close(){

    }
}
