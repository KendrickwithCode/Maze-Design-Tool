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

    private static final String INSERT_MAZE = "INSERT INTO maze (Maze_Name, Author_Name, Author_Description, Width, Height, Image) VALUES (?, ?, ?, ?, ?, ?);";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "Maze_Name VARCHAR(30),"
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

    /**
     * Constructs and Initialises connection to database
     */
    public MazeDB(){
        try {
            connection = DBConnection.getInstance();
            Statement st = connection.createStatement();
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
    public void addMaze(String maze, String author, String description, String height, String width, GUI_Maze Maze) throws SQLException, IOException {
            addMaze.setString(1, maze);
            addMaze.setString(2, author);
            addMaze.setString(3, description);
            addMaze.setString(4, width);
            addMaze.setString(5, height);

            //Convert GUI_Maze object to image icon to be fed to blob
            Rectangle rec = Maze.getBounds();
            BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
            ImageIcon mazeImg = new ImageIcon(bufferedImage);

            //ImageIcons are Serializable
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);

            // serialize the ImageIcon and create a byte array for storage
            objectStream.writeObject(mazeImg);
            objectStream.close();
            byte[] data = byteStream.toByteArray();

                        // execute the INSERT PreparedStatement
            addMaze.setBinaryStream (6, new ByteArrayInputStream(data), data.length);
            addMaze.execute();
    }

    private ImageIcon getImage(ResultSet rs){
        byte[] data;
        ImageIcon Image = null;
        try{
            //use the getBlob method to retrieve the object from the DB
            Blob blob = rs.getBlob("Image");
            data = blob.getBytes(6, (int) blob.length());

            //convert bytes back to object stream
            ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
            ObjectInputStream objectStream = new ObjectInputStream(byteStream);
            Image = (ImageIcon) objectStream.readObject();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Image;
    }


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
