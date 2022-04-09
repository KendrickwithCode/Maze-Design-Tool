import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicBoolean;

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

    @Test void testGetSetDifficulty()
    {
        testMaze.setDifficulty(3);
        int result = testMaze.getDifficulty();
        assertEquals(3,result);
    }


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
        result = testMaze.getNeighbourIndex(testBlock, "EAST");
        assertEquals(11,result);

        result = testMaze.getNeighbourIndex(testBlock, "GROUP127");
        assertEquals(-2,result);
    }

    @Test
    public void testGetNeighbourBlock()
    {
        int testBlockIndex = 12;
//        int testErrorBlockIndex = 20;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);

        Block result = testMaze.getNeighbourBlock(testBlock, "SOUTH");

        assertEquals(5,result.getLocation()[0]);
        assertEquals(2,result.getLocation()[1]);


//        Block testBlockError = testMaze.getMazeMap().get(testErrorBlockIndex);
//        Block resultError = testMaze.getNeighbourBlock(testBlockError, "SOUTH");
//        assertNull(resultError);
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

        // Enable if statement to see memory map (Atomic will silence the IDE from always true or false error)
        AtomicBoolean showMemMap = new AtomicBoolean(false);


        if (showMemMap.get()) {
            for (Block current : testMaze.getMazeMap()
            ) {
                System.out.println("I: " + current.getBlockIndex() + "\t X,Y: " + current.getLocation()[0] + "," + current.getLocation()[1]
                        + "\tWall N: " + current.getWallNorth() + "\t" + current.getWallNorth().isActive()
                        + "\tWall S: " + current.getWallSouth() + "\t" + current.getWallSouth().isActive()
                        + "\tWall E:" + current.getWallEast() +  "\t" + current.getWallEast().isActive()
                        + "\tWall W:" + current.getWallWest() + "\t" + current.getWallWest().isActive());
            }
        }
    }

    @Test void testGetSetSize(){
        int[] result = testMaze.getSize().clone();
        assertArrayEquals(new int[]{7,3,},result);

        testMaze.setSize(7,2);
        result = testMaze.getSize().clone();
        assertArrayEquals(new int[]{7,2,},result);

        testMaze.setSize(7,3);
        result = testMaze.getSize().clone();
        assertArrayEquals(new int[]{7,3,},result);

    }

}
