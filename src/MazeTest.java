import org.junit.jupiter.api.*;


import java.util.ArrayList;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {
    Maze testMaze;
    int[] location = {5,2};
//int[] locationBottomRight = {8,2};

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
    public void testGeneralGetSet()
    {
        //Difficulty
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
        AtomicBoolean showMemMap = new AtomicBoolean(true);

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
    public void testMap() {
        AtomicBoolean displayMazeMap = new AtomicBoolean(false);

        if (displayMazeMap.get()) {
            testMaze.generateNewMaze();

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
    }

}