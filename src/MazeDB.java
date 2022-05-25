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

    public void updateList(DefaultListModel l){
        for(String name : mazedata.nameSet()){
            l.addElement(name);
        }
    }

    /**
     * Retrieves Person details from the model.
     *
     * @param key the name to retrieve.
     * @return the Person object related to the name.
     */
    public Maze get(Object key) throws Exception {
        return mazedata.getMaze((String) key);
    }

//    public Maze getPerson(String name) {
//        //HOW TO RETRIEVE A PERSON FROM THE DATABASE AND STORE IN OBJECT
//        Person p = new Person();
//        ResultSet rs = null;
//        /* BEGIN MISSING CODE */
//        try {
//            getPerson.setString(1, name);
//            rs = getPerson.executeQuery();
//            rs.next();
//            p.setName(rs.getString("name"));
//            p.setStreet(rs.getString("street"));
//            p.setSuburb(rs.getString("suburb"));
//            p.setPhone(rs.getString("phone"));
//            p.setEmail(rs.getString("email"));
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        /* END MISSING CODE */
//        return p;
//    }






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
