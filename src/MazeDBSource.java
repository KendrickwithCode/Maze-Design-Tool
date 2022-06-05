import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Database Interface methods
 */
public interface MazeDBSource {
    /**
     * Extracts all the details of a Maze from the database based on the
     * name passed in.
     *
     * @param name The name as a String to search for.
     * @return all details in a Person object for the name
     */
    Maze getMaze(String name) throws Exception;

    /**
     * Adds an ImageIcon of the Maze to the database
     * @return The ImageIcon of the Maze
     * @throws SQLException
     */
    ImageIcon getImage() throws SQLException;

    /**
     * Adds a Maze entry to the database.
     * @param maze The name of the Maze
     * @param type The type of Maze - Adult/Kids
     * @param author The author's name who created the maze.
     * @param description A description of the maze - can be an empty string
     * @param height The height as a String
     * @param width The width as a String
     * @throws SQLException
     * @throws IOException
     */
    boolean addMaze(String maze, String type, String author,
                 String description, String height, String width) throws SQLException, IOException;


    /**
     * Finalizes any resources used by the data source and ensures data
     * persists.
     */
    void close();

    /**
     * Retrieves a set of names from the database that are used in
     * the name list.
     *
     * @return set of names.
     */
    ArrayList<String> nameList();
}
