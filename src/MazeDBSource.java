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
     * Adds a Maze entry to the database.
     * @param maze A Maze Object
     */
    boolean addMaze(Maze maze) throws SQLException, IOException;

    /**
     * Adds a Maze entry to the database.
     * @param maze A Maze Object
     */
    void updateMaze(Maze maze) throws SQLException, IOException;

    /**
     * Get the "Last Edited" column from the database.
     * @param name The name of the Maze
     * @return The date as a string
     */
    String getLastEdited(String name);

    /**
     * Set the "Last Edited" column from the database.
     * @param name The name of the Maze
     */
    void setLastEdited(String name);

    /**
     * Delete the row in the database found by the name of the maze.
     * @param name The name of the maze to be searched for.
     */
    void deleteEntry(String name);

    /**
     * Get the "Date Created" column from the database.
     * @param name The name of the Maze
     * @return The date as a string
     */
    String getDateCreated(String name);

    /**
     * Reads the byte stream from the database "Image" column and converts into a Maze object.
     * @param name The name of the Maze from the database to be searched for.
     * @return The maze object.
     */
    Maze getGUIMaze(String name) throws Exception;

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
