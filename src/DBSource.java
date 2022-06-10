import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implements MazeDBSource Interface which utilizes JDBC.
 */
public class DBSource implements MazeDBSource {

    private static final String INSERT_MAZE = "INSERT INTO maze " +
            "(Maze_Name, Maze_Type, Author_Name, Author_Description," +
            " Width, Height, Image) VALUES (?, ?, ?, ?, ?, ?, ?);";
    private static final String UPDATE_MAZE = "UPDATE maze " +
            "SET Maze_Name = ?, Maze_Type = ?, Author_Name = ?," +
            " Author_Description = ?, Width = ?, Height = ?, Image = ? WHERE Maze_Name = ?;";
    private static final String GET_NAME = "SELECT * from maze WHERE Maze_Name=?";
    private static final String GET_ALL_NAMES = "SELECT Maze_Name, Author_Name from maze";
    private static final String GET_DATE = "SELECT Date_Created from maze WHERE Maze_Name =?";
    private static final String GET_EDITED = "SELECT Last_Edited from maze WHERE Maze_Name =?";
    private static final String UPDATE_DATE = "UPDATE maze SET Last_Edited = CURRENT_TIMESTAMP" +
            " WHERE Maze_Name = ?";
    private static final String CHECK_ENTRIES = "SELECT COUNT(Maze_Name) FROM maze WHERE Maze_Name =?";
    private static final String DELETE_ENTRY = "DELETE FROM maze WHERE Maze_Name = ?";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "idx INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */" +
                    " NOT NULL UNIQUE," // from https://stackoverflow.com/a/41028314
                    + "Maze_Name VARCHAR(30),"
                    + "Maze_Type VARCHAR(5),"
                    + "Date_Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                    + "Last_Edited TIMESTAMP CURRENT_TIMESTAMP,"
                    + "Author_Name VARCHAR(30),"
                    + "Author_Description VARCHAR(180),"
                    + "Width VARCHAR(3),"
                    + "Height VARCHAR(3),"
                    + "Image LONGBLOB" + ");";

    public Connection connection;
    private PreparedStatement addMaze, updateMaze, getMaze, getAllMaze,
            getDateCreated, getLastEdited, updateLastEdited, checkEntries, deleteEntry;
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
            getDateCreated = connection.prepareStatement(GET_DATE);
            getLastEdited = connection.prepareStatement(GET_EDITED);
            updateMaze = connection.prepareStatement(UPDATE_MAZE);
            updateLastEdited = connection.prepareStatement(UPDATE_DATE);
            checkEntries = connection.prepareStatement(CHECK_ENTRIES);
            deleteEntry = connection.prepareStatement(DELETE_ENTRY);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Maze getMaze(String name) {
        Maze m = null;
        ResultSet rs;

        try {
            getMaze.setString(1, name);
            rs = getMaze.executeQuery();
            rs.next();
            m = new Maze(rs.getInt("Width"), rs.getInt("Height"),
                    rs.getString("Maze_Name"), rs.getString("Maze_Type"),
                    rs.getString("Author_Description"), rs.getString("Author_Name"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return m;
    }

    /**
     * Checks if the year is a leap year
     * @param year to check if a leap year
     * @return true if leap year false if it is a normal year.
     */
    private boolean isLeapYear(int year){
            int leapYearCheck = 0;

            if (year %4 == 0 )
            {
                leapYearCheck = 1;
            }

            if (year %100 == 0 && year %400 != 0 )
            {
                leapYearCheck = 0;
            }

        return leapYearCheck == 1;
        }

    /**
     * Adds timezone offset to java date times.
     * @param inputDate date to change to localtime
     * @param offsetHrs time offset form GMT
     * @return GMT Offset date time
     */
    public String dateTimeZoneFix(String inputDate,int offsetHrs){
        int [] dayMonths = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

        String date = inputDate.split(" ")[0];

        String day = date.split("-")[2];
        String month = date.split("-")[1];
        String year = date.split("-")[0];


        int workingDay = Integer.parseInt(day);
        int workingMonth = Integer.parseInt(month);
        int workingYear = Integer.parseInt(year);

        if(isLeapYear(workingYear))
        {
            dayMonths[1] = 29;
        }

        String time = inputDate.split(" ")[1];
        String hrs = time.split(":")[0];
        String mins = time.split(":")[1];
        String secs = time.split(":")[2];

        int workingHrs = Integer.parseInt(hrs) + offsetHrs;

        if(workingHrs > 24)
        {
            workingHrs -= 24;
            workingDay += 1;
            if(workingDay > dayMonths[workingMonth-1])
            {
                workingDay = 1;
                workingMonth++;
                if(workingMonth > 12)
                {
                    workingMonth = 1;
                    workingYear++;
                }
            }
        }

        hrs = String.valueOf(workingHrs);
        year = String.valueOf(workingYear);
        month = String.valueOf(workingMonth);
        day = String.valueOf(workingDay);

        inputDate= "    " + day + "/" + month + "/" + year +  "       " + hrs + ":" + mins + ":" + secs;
        return  inputDate;
    }

    @Override
    public String getDateCreated(String name) {
        ResultSet rs;
        String dateCreated = "";
        try {
            getDateCreated.setString(1, name);
            rs = getDateCreated.executeQuery();
            rs.next();
            dateCreated =   dateTimeZoneFix(rs.getString("Date_Created"),10);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dateCreated;
    }

    @Override
    public String getLastEdited(String name){
        ResultSet rs;
        String lastEdited = "";
        try {
            getLastEdited.setString(1, name);
            rs = getLastEdited.executeQuery();
            rs.next();
            if (rs.getString("Last_Edited") != null){
                lastEdited = dateTimeZoneFix(rs.getString("Last_Edited"),10);
            } else {
                return "N/A";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastEdited;
    }

    @Override
    public void deleteEntry(String name){
        try {
            deleteEntry.setString(1, name);
            deleteEntry.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setLastEdited(String name){
        try {
            updateLastEdited.setString(1, name);
            updateLastEdited.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Maze getGUIMaze(String name) throws Exception {

        Maze readMaze = null;
        ResultSet rs;
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
    public boolean addMaze(Maze maze) throws SQLException, IOException {
        checkEntries.setString(1, maze.getMazeName());
        ResultSet rs = checkEntries.executeQuery();
        if (rs.getInt("Count(Maze_Name)") > 0){
            int update = JOptionPane.showConfirmDialog
                    (null, "Overwrite " + maze.getMazeName() + "?", "WARNING", JOptionPane.YES_NO_OPTION);
            if(update == JOptionPane.YES_OPTION) {
                //Overwrite Maze
                updateMaze(maze);
                JOptionPane.showMessageDialog(null,
                        "Maze Successfully Updated.", "Okay", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        addMaze.setString(1, maze.getMazeName());
        addMaze.setString(2, maze.getMazeType());
        addMaze.setString(3, maze.getAuthorName());
        addMaze.setString(4, maze.getMazeDescription());
        addMaze.setString(5, maze.getWidthAsString());
        addMaze.setString(6, maze.getHeightAsString());
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(Maze.MazeTools.getCurrentMaze());
        byte[] data = byteStream.toByteArray();
        addMaze.setBinaryStream(7, new ByteArrayInputStream(data), data.length);
        addMaze.execute();
        return true;
    }

    @Override
    public void updateMaze(Maze maze) throws SQLException, IOException {
        updateMaze.setString(8, maze.getMazeName());
        updateMaze.setString(1, maze.getMazeName());
        updateMaze.setString(2, maze.getMazeType());
        updateMaze.setString(3, maze.getAuthorName());
        updateMaze.setString(4, maze.getMazeDescription());
        updateMaze.setString(5, maze.getWidthAsString());
        updateMaze.setString(6, maze.getHeightAsString());
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
        objectStream.writeObject(Maze.MazeTools.getCurrentMaze());
        byte[] data = byteStream.toByteArray();
        updateMaze.setBinaryStream(7, new ByteArrayInputStream(data), data.length);
        setLastEdited(maze.getMazeName());
        updateMaze.execute();
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
    public ArrayList<String> nameList() {
        ArrayList<String> names = new ArrayList<>();
        ResultSet rs;

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
