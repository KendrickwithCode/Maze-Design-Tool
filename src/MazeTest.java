import org.junit.jupiter.api.*;


import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Maze Classes Test Class.
 */
public class MazeTest {
Maze testMaze;
int[] location = {5,2};

    @BeforeEach
    public void Before(){
    testMaze = new Maze(7,3, "Test");
    }

    /**
     * Constructor Test
     */
    @Test void testConstructor()
    {
        assertNotNull(testMaze);
    }

    /**
     * Retrieve Solvable Variable Test
     */
    @Test void testGetSolvable(){
        assertFalse(testMaze.getSolvable());}

    /**
     * Retrieve Difficulty Variable Test
     */
    @Test void testGetSetDifficulty()
    {
        testMaze.setDifficulty(3);
        int result = testMaze.getDifficulty();
        assertEquals(3,result);
    }

    /**
     * Retrieves the Index From Block and checks if GetIndex Function is correct.
     */
    @Test
    public void testGetIndex(){
        int result = testMaze.getIndex(location);
        assertEquals(19,result);
    }

    /**
     * Checks the GetNeighborIndex Function is correct
     */
    @Test
    public void testGetNeighborIndex()
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

    /**
     * Retrieves the Block and checks if GetIndex Function is correct.
     */
    @Test
    public void testGetNeighbourBlock()
    {
        int testBlockIndex = 12;

        Block testBlock = testMaze.getMazeMap().get(testBlockIndex);

        Block result = testMaze.getNeighbourBlock(testBlock, "SOUTH");

        assertEquals(5,result.getLocation()[0]);
        assertEquals(2,result.getLocation()[1]);

    }

    /**
     * Check the GetSetName function for initial naming and renaming.
     */
    @Test
    public void testGetSetName()
    {
        assertEquals("Test", testMaze.getMazeName());
        testMaze.setMazeName("GROUP127");
        assertEquals("GROUP127", testMaze.getMazeName());
    }

    /**
     * Check the OutOfBounds functions to ensure it reports out of bounds
     * on all border edges and direction errors
     */
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
    public void testGeneralGetSet()
    {
        //Dificulty
        testMaze.setDifficulty(2);
        assertEquals(2,testMaze.getDifficulty());

        //GetSize
        assertEquals(7,testMaze.getSize()[0]);
        assertEquals(3,testMaze.getSize()[1]);
    }

    @Test
    public void testWallClass()
    {
        Block testBlock = testMaze.getMazeMap().get(10);
        //Maze Wall Class
        boolean result = testBlock.getWallEast().getStart();
        assertFalse(result);

        testBlock.getWallEast().setStart(true);
        result = testBlock.getWallEast().getStart();
        assertTrue(result);


        testBlock.getWallEast().setStart(false);
        result = testBlock.getWallEast().getStart();
        assertFalse(result);

        result = testBlock.getWallEast().getFinish();
        assertFalse(result);

        testBlock.getWallEast().setFinish(true);
        result = testBlock.getWallEast().getFinish();
        assertTrue(result);

        testBlock.getWallEast().setFinish(false);
        result = testBlock.getWallEast().getFinish();
        assertFalse(result);

        testBlock = testMaze.getMazeMap().get(0);
        testBlock.getWallWest().setBorder();
        result= testBlock.getWallWest().getBorder();

        assertTrue(result);
    }

    /**
     * Test Abstract Block Class
     */
    @Test
    public void testBlockClass()
    {
        Block testBlock = testMaze.getMazeMap().get(10);

        // Check Directions Array
        testBlock.setAvailableDirections(new ArrayList<>(List.of("East","South")));
        assertSame("East", testBlock.getAvailableDirections().get(0));
        assertSame("South", testBlock.getAvailableDirections().get(1));

        // Check other unused wall assignment setters.
        int result = testBlock.getBlockIndex();
        assertSame(10,result);

        testBlock.setWallEast(testBlock.getWallWest());
        assertSame(testBlock.getWallEast(),testBlock.getWallWest());

        testBlock.setWallSouth(testBlock.getWallNorth());
        assertSame(testBlock.getWallSouth(),testBlock.getWallNorth());


    }

    /**
     * Checks if the Maze map array wall object allocation shares walls correctly
     * amongst the neighbouring blocks.
     */
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
        assertEquals(testMaze.getMazeMap().get(0).getWallSouth(),testMaze.getMazeMap().get(7).getWallNorth());
        assertEquals(testMaze.getMazeMap().get(9).getWallSouth(),testMaze.getMazeMap().get(16).getWallNorth());
        assertEquals(testMaze.getMazeMap().get(5).getWallEast(),testMaze.getMazeMap().get(6).getWallWest());
        assertEquals(testMaze.getMazeMap().get(18).getWallEast(),testMaze.getMazeMap().get(19).getWallWest());
    }

    /**
     * Test clearing and filling the maze map via ResetMaze
     */
    @Test
    public void testResetMaze()
    {
        testMaze.resetMaze(7,3,true);
        assertFalse(testMaze.getMazeMap().get(8).getWallEast().getActive());
        assertFalse(testMaze.getMazeMap().get(8).getWallNorth().getActive());
        assertFalse(testMaze.getMazeMap().get(8).getWallSouth().getActive());
        assertFalse(testMaze.getMazeMap().get(8).getWallWest().getActive());

        testMaze.resetMaze(7,3,false);
        assertTrue(testMaze.getMazeMap().get(8).getWallEast().getActive());
        assertTrue(testMaze.getMazeMap().get(8).getWallNorth().getActive());
        assertTrue(testMaze.getMazeMap().get(8).getWallSouth().getActive());
        assertTrue(testMaze.getMazeMap().get(8).getWallWest().getActive());

        testMaze.resetMaze(true);
        assertFalse(testMaze.getMazeMap().get(8).getWallEast().getActive());
        assertFalse(testMaze.getMazeMap().get(8).getWallNorth().getActive());
        assertFalse(testMaze.getMazeMap().get(8).getWallSouth().getActive());
        assertFalse(testMaze.getMazeMap().get(8).getWallWest().getActive());
    }

    /**
     * Generator test.
     */
    @Test
    public void testGenerator() {
        AtomicBoolean displayMazeMap = new AtomicBoolean(false);

        testMaze.generateNewMaze();

        assertTrue(testMaze.getMazeMap().get(0).getAvailableDirections().size() <= 3);
        assertTrue(testMaze.getMazeMap().get(11).getAvailableDirections().size() <= 3);
        testMaze.generateNewMaze("DFSRecursive",new int[]{0,0});
        assertTrue(testMaze.getMazeMap().get(0).getAvailableDirections().size() <= 3);
        assertTrue(testMaze.getMazeMap().get(11).getAvailableDirections().size() <= 3);




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
    }

}

