import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class DBSource implements MazeDBSource {
    public static final String SELECT = "SELECT * FROM maze WHERE idx = 1";

    private static final String INSERT_MAZE = "INSERT INTO maze " +
            "(Maze_Name, Maze_Type, Author_Name, Author_Description, Width, Height, Image) VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_NAME = "SELECT * from maze WHERE Maze_Name=?";
    private static final String GET_ALL_NAMES = "SELECT Maze_Name, Author_Name from maze";

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
    private PreparedStatement getAllMaze;
    private PreparedStatement rowCount;
    private PreparedStatement image;
    public Statement st;


    /**
     * Initialises connection and prepared statements for the database.
     */
    public DBSource(){
        try {
            connection = DBConnection.getInstance();
            st = connection.createStatement();
            st.execute(CREATE_TABLE);
            addMaze = connection.prepareStatement(INSERT_MAZE);
            getMaze = connection.prepareStatement(GET_NAME);
            getAllMaze = connection.prepareStatement(GET_ALL_NAMES);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Maze getMaze(String name) throws Exception {
        Maze m = new Maze();
        ResultSet rs = null;

        try {
            getMaze.setString(1, name);
            rs = getMaze.executeQuery();
            rs.next();
            m.setMazeName(rs.getString("Maze_Name"));
            m.setMazeType(rs.getString("Maze_Type"));
            m.setAuthorName(rs.getString("Author_Name"));
            m.setMazeDescription(rs.getString("Author_Description"));
            m.setSize(rs.getInt("Width"), rs.getInt("Height"));
            m.setWidth(rs.getInt("Width"));
            m.setHeight(rs.getInt("Height"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }


    /**
     * Reads the byte stream from the database "Image" column and converts into a Maze object.
     * @param name The name of the Maze from the database to be searched for.
     * @return The maze object.
     * @throws Exception
     */
    public Maze getGUIMaze(String name) throws Exception {

        Maze readMaze = null;
        ResultSet rs = null;
        getMaze.setString(1, name);
        rs = getMaze.executeQuery();
        rs.next();
        byte[] data;
        data = rs.getBytes("Image");
        ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream);
        for (;;) {
            try {
                readMaze = (Maze) objectStream.readObject();
            } catch (EOFException exc) {
                break;
            }
        }
        rs.close();
        return readMaze;
    }

    @Override
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
        rs.close();
        return printImage;
    }

    @Override
    public void addMaze(String maze, String type, String author, String description, String height, String width, GUI_Maze Maze) throws SQLException, IOException {
        addMaze.setString(1, maze);
        addMaze.setString(2, type);
        addMaze.setString(3, author);
        addMaze.setString(4, description);
        addMaze.setString(5, width);
        addMaze.setString(6, height);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(MazeLogoTools.getCurrentMaze());

        byte[] data = byteStream.toByteArray();
        addMaze.setBinaryStream(7, new ByteArrayInputStream(data), data.length);
        addMaze.execute();
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Retrieves the Maze_Name for the list selector
     *
     * @return A set containing names as a string
     */
    @Override
    public Set<String> nameSet() {
        Set<String> names = new TreeSet<String>();
        ResultSet rs = null;

        try {
            rs = getAllMaze.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("Maze_Name"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return names;
    }
}
