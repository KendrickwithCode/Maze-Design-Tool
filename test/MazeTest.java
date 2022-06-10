import org.junit.jupiter.api.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit testing class for MazeCraft.
 */
public class MazeTest {
    Maze testMaze;
    Maze kidsTest;
    DBSource data;
    int[] location = {5,2};

    @BeforeEach
    public void Before() {
        data = new DBSource();
        testMaze = new Maze(7,3, "Test","Adult", "Cool Maze!", "Jason");
        kidsTest = new Maze(7,3,"kidsTest","KIDS", "Cool Maze!", "Dan");
    }


    @Test void testConstructor()
    {
        assertNotNull(testMaze);
    }

    @Test void testGetSolvable(){
        assertFalse(testMaze.getSolvable());}

    @Test
    public void testMaze(){
        testMaze.setMazeType("kids");
        assertEquals("kids",testMaze.getMazeType());
        testMaze.setMazeType("adult");
        assertEquals("adult",testMaze.getMazeType());
    }

    @Test
    public void testWidthHeight(){
        testMaze.setWidth(6);
        testMaze.setHeight(2);
        assertEquals(6,testMaze.getWidth());
        assertEquals("6",testMaze.getWidthAsString());
        assertEquals(2,testMaze.getHeight());
        assertEquals("2",testMaze.getHeightAsString());
    }

    @Test
    public void testAuthorAndDescription(){
        testMaze.setAuthorName("testMan");
        assertEquals("testMan",testMaze.getAuthorName());
        testMaze.setMazeDescription("Test mans first maze.");
        assertEquals("Test mans first maze.",testMaze.getMazeDescription());
    }

    @Test
    public void testGetIndex(){
        int result = testMaze.getIndex(location);
        assertEquals(19,result);
    }

    @Test
    public void testGetNeighbourIndex() {
        int testBlockIndex = 10;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);
        int result = testMaze.getNeighbourIndex(testBlock, "NORTH");
        assertEquals(3,result);
        result = testMaze.getNeighbourIndex(testBlock, "EAST");
        assertEquals(11,result);

        result = testMaze.getNeighbourIndex(testBlock, "GROUP127");
        assertEquals(-2,result);
    }

    @Test
    public void testGetNeighbourBlock() {
        int testBlockIndex = 12;
//        int testErrorBlockIndex = 20;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);

        Block result = testMaze.getNeighbourBlock(testBlock, "SOUTH");

        assertEquals(5,result.getLocation()[0]);
        assertEquals(2,result.getLocation()[1]);
    }

    @Test
    public void testGetSetName()
    {
        assertEquals("Test", testMaze.getMazeName());
        testMaze.setMazeName("GROUP127");
        assertEquals("GROUP127", testMaze.getMazeName());
    }


    @Test
    public void testOutOfBounds()
    {
        assertTrue(testMaze.outOfBounds(0,"WEST"));
        assertTrue(testMaze.outOfBounds(4,"NORTH"));
        assertTrue(testMaze.outOfBounds(17,"SOUTH"));
        assertTrue(testMaze.outOfBounds(20,"EAST"));
        assertFalse(testMaze.outOfBounds(20,"GROUP127"));
    }

    @Test
    public void arrayTest() {

        Block currentBlock = testMaze.getMazeMap().get(10);
        assertEquals(currentBlock.getWallSouth(), testMaze.getNeighbourBlock(currentBlock, "SOUTH").getWallNorth());


        // Enable if statement to see memory map
        AtomicBoolean showMemMap = new AtomicBoolean(false);

        if (showMemMap.get()) {
            for (Block current : testMaze.getMazeMap()
            ) {
                System.out.println("I: " + current.getBlockIndex() + "\t X,Y: " + current.getLocation()[0] + "," + current.getLocation()[1]
                        + "\tWall N: " + current.getWallNorth() + "\t" + current.getWallNorth().getActive()
                        + "\tWall S: " + current.getWallSouth() + "\t" + current.getWallSouth().getActive()
                        + "\tWall E:" + current.getWallEast() +  "\t" + current.getWallEast().getActive()
                        + "\tWall W:" + current.getWallWest() + "\t" + current.getWallWest().getActive());
            }
        }
    }

    @Test
    public void testKidsMap() {
        String result = kidsTest.getMazeType();
        assertEquals("KIDS",result);

    }

    @Test
    public void testKidsLogos()
    {
        //Kids logo tests
        LogoBlock working = (LogoBlock) kidsTest.getMazeMap().get(kidsTest.getKidsStartIndex());
        int resultLogosIndex = kidsTest.getKidsStartIndex();
        String blockType  = working.getBlockType();
        assertEquals(0,resultLogosIndex);

        assertEquals("LogoBlock",blockType);
        String resultFileName  = working.getPictureFile()  ;
        assertEquals("img/icons/Dog.png",resultFileName);

        resultLogosIndex = kidsTest.getKidsFinishIndex();
        assertEquals(12,resultLogosIndex);

        working = (LogoBlock) kidsTest.getMazeMap().get(kidsTest.getKidsFinishIndex());
        resultFileName  = working.getPictureFile()  ;
        assertEquals("img/icons/Bone.png",resultFileName);

        testMaze.setKidsStartIndex(2);
        assertEquals(2,testMaze.getKidsStartIndex());

        testMaze.setKidsFinishIndex(4);
        assertEquals(4,testMaze.getKidsFinishIndex());
    }

    @Test
    public void testBlockConversions() throws Exception {
        Maze.MazeTools.setCurrentMaze(testMaze);
        Maze.MazeTools.convertMazeBlockToLogoBlock(testMaze.getMazeMap().get(5),"logo");
        LogoBlock currentTestLogoBlock = (LogoBlock) testMaze.getMazeMap().get(5);
        assertEquals("LogoBlock", testMaze.getMazeMap().get(5).getBlockType());
        assertEquals("img/icons/MazeCo.png",currentTestLogoBlock.getPictureFile());
        Maze.MazeTools.setupAdultLogoBlocks(testMaze.getMazeMap().get(5),2,2);
        assertTrue(currentTestLogoBlock.getWallWest().getActive());
        assertTrue(currentTestLogoBlock.getWallWest().getIsBorder());

        Maze.MazeTools.resetWalls((LogoBlock) testMaze.getMazeMap().get(5));
        Maze.MazeTools.convertLogoBlockToMazeBlock(testMaze.getMazeMap().get(5));
        assertEquals("MazeBlock", testMaze.getMazeMap().get(5).getBlockType());
        assertFalse(currentTestLogoBlock.getWallWest().getActive());
        assertFalse(currentTestLogoBlock.getWallWest().getIsBorder());
    }

    @Test
    public void testInvalidBlockConversion() {
        Maze.MazeTools.setCurrentMaze(testMaze);
        assertThrows(Exception.class, () -> Maze.MazeTools.convertLogoBlockToMazeBlock(testMaze.getMazeMap().get(5)));
    }

    @Test
    public void testMazeReturns() {

        GUI_Maze testMazeGui = new GUI_Maze(testMaze,false);
        Maze.MazeTools.setCurrentMaze(testMaze);
        Maze.MazeTools.setCurrentGUIMaze(testMazeGui);
        assertEquals(testMazeGui, Maze.MazeTools.getCurrentGUIMaze());
        assertEquals(testMaze, Maze.MazeTools.getCurrentMaze());

    }

    @Test
    public void testMazeBlock(){
        Block working = testMaze.getMazeMap().get(5);
        String resultBlockType = working.getBlockType();
        assertEquals("MazeBlock",resultBlockType);
        assertEquals(5,working.getBlockIndex());

        //Working Directions Test
        working.clearAvailableDirections();
        assertEquals(0,working.getAvailableDirections().size());
        ArrayList<String> directions = new ArrayList<>();
        directions.add("west");
        directions.add("east");
        working.setAvailableDirections(directions);
        assertEquals(2,working.getAvailableDirections().size());
    }

    @Test
    public void testSetWallsMemAllocation(){
        Block working = kidsTest.getMazeMap().get(11);
        working.setWallSouth(working.getWallNorth());
        working.setWallEast(working.getWallWest());

        assertEquals(working.getWallWest(),working.getWallEast());
        assertEquals(working.getWallNorth(),working.getWallSouth());
    }

    @Test
    public void testBlockPanel(){
        Block working = kidsTest.getMazeMap().get(11);

        assertEquals(new Dimension(0,0),working.getBlockPanel().getSize());
    }

    @Test
    public void wallTest(){
        Block working = kidsTest.getMazeMap().get(11);

        working.getWallNorth().setActive(false);
        boolean result = working.getWallNorth().getActive();
        assertFalse(result);

        working.getWallEast().setActive(false);
        result = working.getWallEast().getActive();
        assertFalse(result);

        working.getWallSouth().setActive(false);
        result = working.getWallSouth().getActive();
        assertFalse(result);

        working.getWallWest().setActive(false);
        result = working.getWallWest().getActive();
        assertFalse(result);



        working.getWallNorth().setActive(true);
        result = working.getWallNorth().getActive();
        assertTrue(result);

        working.getWallEast().setActive(true);
        result = working.getWallEast().getActive();
        assertTrue(result);

        working.getWallSouth().setActive(true);
        result = working.getWallSouth().getActive();
        assertTrue(result);

        working.getWallWest().setActive(true);
        result = working.getWallWest().getActive();
        assertTrue(result);
    }


    @Test
    public void testMazeGen() {
        AtomicBoolean displayMazeMap = new AtomicBoolean(false);

        testMaze.generateNewMaze();

        if (displayMazeMap.get()) {


            char C = ' ';
            char A = '=';

            char[][] testDisplay = new char[testMaze.getSize()[0] * testMaze.getSize()[0]][4];                   // 0 1
            // 2 3
            int i = 0;

            for (Block block : testMaze.getMazeMap()
            ) {

                testDisplay[i][3] = '.';


                if (block.getWallNorth().getActive()) {
                    testDisplay[i][0] = A;
                    testDisplay[i][1] = A;
                } else {
                    testDisplay[i][0] = C;
                    testDisplay[i][1] = C;
                }

                if (block.getWallWest().getActive()) {
                    testDisplay[i][0] = A;
                    testDisplay[i][2] = A;
                } else {
                    testDisplay[i][2] = C;
                }
                i++;
            }


            ArrayList<String> displayBuffer1 = new ArrayList<>();
            ArrayList<String> displayBuffer2 = new ArrayList<>();
//            int sizeY = testMaze.getSize()[1];
//            int sizeX = testMaze.getSize()[0];
//            boolean toggle = true;

            for (char[] item : testDisplay
            ) {
                displayBuffer1.add(Character.toString(item[0]));
                displayBuffer1.add(Character.toString(item[1]));
            }

            for (char[] item : testDisplay
            ) {
                displayBuffer2.add(Character.toString(item[2]));
                displayBuffer2.add(Character.toString(item[3]));
            }


            for (String item : displayBuffer1
            ) {
                System.out.print(item);

            }

            System.out.println();
            for (String item : displayBuffer2
            ) {
                System.out.print(item);
            }
        }

        Block working = testMaze.getMazeMap().get(0);
        int wallsCount = working.getAvailableDirections().size();
        assertTrue(wallsCount < 4);

        kidsTest.generateNewMaze("DFSRecursive",new int[]{0,0});
        working = kidsTest.getMazeMap().get(10);
        wallsCount = working.getAvailableDirections().size();
        assertTrue(wallsCount < 4);
    }
    @Test
    public void testDBConnection(){
        assertNotNull(data.connection);
    }

    @Test
    public void testDBInvalidQuery() {
        final String TEST = "not a valid query";
        assertThrows(Exception.class, () -> {
            ResultSet rs;
            data.addMaze(testMaze);
            PreparedStatement testAdd = data.connection.prepareStatement(TEST);
            rs = testAdd.executeQuery();
            rs.close();
        });
    }

    @Test
    public void testDBAdd() throws Exception {
        ResultSet rs;
        final String TEST = "Select Maze_Name FROM maze WHERE Maze_Name = 'Test'";
        data.addMaze(testMaze);
        PreparedStatement testAdd = data.connection.prepareStatement(TEST);
        rs = testAdd.executeQuery();
        rs.next();
        assertEquals("Test", rs.getString("Maze_Name"), "Cannot Add Entry To Database");
        rs.close();
    }

    @Test
    public void testDBUpdate() throws Exception{
        testDBAdd();
        ResultSet rs;
        final String TEST = "UPDATE maze SET Maze_Type = 'KIDS' WHERE Maze_Name = 'Test'";
        final String SELECT = "Select Maze_Type FROM maze WHERE Maze_Name = 'Test'";
        PreparedStatement testUpdate = data.connection.prepareStatement(TEST);
        PreparedStatement testSelect = data.connection.prepareStatement(SELECT);
        testUpdate.execute();
        rs = testSelect.executeQuery();
        rs.next();
        assertEquals("KIDS", rs.getString("Maze_Type"), "Cannot Update Entry to Database");
        rs.close();
    }

    @AfterEach @Test
    public void testDBDelete() throws Exception{
        ResultSet rs;
        final String TEST = "DELETE FROM maze WHERE Maze_Name = 'Test'";
        final String SELECT = "Select Maze_Type FROM maze WHERE Maze_Name = 'Test'";
        PreparedStatement testDelete = data.connection.prepareStatement(TEST);
        PreparedStatement testSelect = data.connection.prepareStatement(SELECT);
        testDelete.execute();
        rs = testSelect.executeQuery();
        assertFalse(rs.next());
        rs.close();
    }

    @Test
    public void testTimeOffsetString(){
        String inputTime = "2022-06-09 04:17:08";
        assertEquals("    9/6/2022       14:17:08", data.dateTimeZoneFix(inputTime, 10));
        String inputTime2 = "2022-06-10 08:37:30";
        assertEquals("    10/6/2022       18:37:30", data.dateTimeZoneFix(inputTime2, 10));
    }
}