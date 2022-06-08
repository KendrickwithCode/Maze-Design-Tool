import javax.swing.*;
import java.sql.*;

/**
 * Maze Database Connection with getters and updates
 * to the database through DBSource.
 */
public class MazeDB{
    MazeDBSource mazeData;
    /**
     * Constructs and Initialises connection to database through DBSource.
     */
    public MazeDB() {
        mazeData = new DBSource();
    }

    /**
     * Update the JList with names from MazeDB
     * @param l The List Model to add to.
     */
    public void updateList(DefaultListModel l) throws SQLException {
        for(String name : mazeData.nameList()){
            if (!l.contains(name)){
                l.addElement(name);
            }
        }
    }

    /**
     * Retrieves Maze from the model.
     *
     * @param key the name to retrieve.
     * @return the Maze object related to the name.
     */
    public Maze get(Object key) throws Exception {
        return mazeData.getMaze((String) key);
    }


    /**
     * Close connection to database
     */
    public void close(){

    }

}
