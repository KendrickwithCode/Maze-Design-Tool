import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.Set;
import java.util.TreeSet;

/**
 * Maze Database
 */
public class MazeDB{

    MazeDBSource mazedata;

    /**
     * Constructs and Initialises connection to database
     */
    public MazeDB() {
        mazedata = new DBSource();

    }

    /**
     * Update the JList with names from MazeDB
     * @param l The List Model to add to.
     */
    public void updateList(DefaultListModel l){
        for(String name : mazedata.nameSet()){
            l.addElement(name);
        }
    }

    /**
     * Retrieves Maze from the model.
     *
     * @param key the name to retrieve.
     * @return the Maze object related to the name.
     */
    public Maze get(Object key) throws Exception {
        return mazedata.getMaze((String) key);
    }

    /**
     * Delete a Maze from the database.
     * @param key The maze to be removed.
     */
    public void removeMaze(Object key){

    }

    /**
     * Close connection to database
     */
    public void close(){

    }

}
