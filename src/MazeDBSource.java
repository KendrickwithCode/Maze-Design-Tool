import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public interface MazeDBSource {
    /**
     * Extracts all the details of a Person from the address book based on the
     * name passed in.
     *
     * @param name The name as a String to search for.
     * @return all details in a Person object for the name
     */
    Maze getMaze(String name) throws Exception;

    ImageIcon getImage() throws SQLException;

    void addMaze(String maze, String type, String author,
                 String description, String height, String width, GUI_Maze Maze) throws SQLException, IOException;

    /**
     * Finalizes any resources used by the data source and ensures data is
     * persisited.
     */
    void close();

    /**
     * Retrieves a set of names from the data source that are used in
     * the name list.
     *
     * @return set of names.
     */
    Set<String> nameSet();
}
