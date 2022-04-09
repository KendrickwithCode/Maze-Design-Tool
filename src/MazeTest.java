import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {
Maze testMaze;
int[] location = {5,2};
int[] locationBottomRight = {8,2};

    @BeforeEach
    public void Before(){
    testMaze = new Maze(7,3, "Test");
    }

    @Test void testConstructor()
    {
        assertNotNull(testMaze);
    }

    @Test void testGetSolvable(){
        assertFalse(testMaze.getSolvable());}

    @Test
    public void testGetIndex(){
        int result = testMaze.getIndex(location);
        assertEquals(19,result);
    }

    @Test
    public void testGetNeighbourIndex()
    {
        int testBlockIndex = 10;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);
        int result = testMaze.getNeighbourIndex(testBlock, "NORTH");
        assertEquals(3,result);
    }

    @Test
    public void testGetNeighbourBlock()
    {
        int testBlockIndex = 12;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);

        Block result = testMaze.getNeighbourBlock(testBlock, "SOUTH");

        assertEquals(5,result.getLocation()[0]);
        assertEquals(2,result.getLocation()[1]);
    }

    @Test
    public void testGetName()
    {
        assertEquals("Test", testMaze.getMazeName());
    }


    @Test
    public void testOutOfBounds()
    {
        assertTrue(testMaze.outOfBounds(0,"WEST"));
        assertTrue(testMaze.outOfBounds(4,"NORTH"));
        assertTrue(testMaze.outOfBounds(17,"SOUTH"));
        assertTrue(testMaze.outOfBounds(20,"EAST"));
    }

    @Test
    public void arrayTest() {

        Block currentBlock = testMaze.getMazeMap().get(0);
        assertEquals(currentBlock.getWallSouth(), testMaze.getNeighbourBlock(currentBlock, "SOUTH").getWallNorth());

        // Enable if statement to see memory map
        boolean showMemMap = false;
        if (showMemMap) {
            for (Block current : testMaze.getMazeMap()
            ) {
                System.out.println("I: " + current.getBlockIndex() + "\t X,Y: " + current.getLocation()[0] + "," + current.getLocation()[1]
                        + "\tWall N: " + current.getWallNorth() + "\tWall S: " + current.getWallSouth()
                        + "\tWall E:" + current.getWallEast() + "\tWall W:" + current.getWallWest());
            }


        }
    }

}
