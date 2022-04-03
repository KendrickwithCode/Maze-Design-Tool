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
        int result = testMaze.getNeighbourIndex(testBlock, "UP");
        assertEquals(3,result);
    }

    @Test
    public void testGetNeighbourBlock()
    {
        int testBlockIndex = 12;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);

        Block result = testMaze.getNeighbourBlock(testBlock, "DOWN");

        assertEquals(5,result.getLocation()[0]);
        assertEquals(2,result.getLocation()[1]);
    }

    @Test
    public void testGetName()
    {
        assertEquals("Test", testMaze.getMazeName());
    }

    @Test
    public void showArray()
    {
        for (Block item: testMaze.getMazeMap()) {
            System.out.println(item.getLocation()[0] + "\t" + item.getLocation()[1]);

        }
    }
}
