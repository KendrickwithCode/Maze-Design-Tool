import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Maze Database
 */
public class MazeDB{

    private static final String INSERT_MAZE = "INSERT INTO maze (Maze_Name, Author_Name, Author_Description, Width, Height) VALUES (?, ?, ?, ?, ?);";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "Maze_Name VARCHAR(30),"
                    + "Author_Name VARCHAR(30),"
                    + "Author_Description VARCHAR(180),"
                    + "Width VARCHAR(3),"
                    + "Height VARCHAR(3)" + ");";

    private Connection connection;
    private PreparedStatement addMaze;
    private PreparedStatement getMaze;
    private PreparedStatement deleteMaze;
    private PreparedStatement rowCount;

    /**
     * Constructs and Initialises connection to database
     */
    public MazeDB(){
        connection = DBConnection.getInstance();
        try {
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
     */
    public void addMaze(String maze, String author, String description, String height, String width) throws SQLException {
            addMaze.setString(1, maze);
            addMaze.setString(2, author);
            addMaze.setString(2, description);
            addMaze.setString(3, width);
            addMaze.setString(4, height);
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
