import org.junit.jupiter.api.*;

import java.util.ArrayList;
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
    public void testMap()
    {

        //testMaze.resetMaze(false);
        testMaze.generateNewMaze();

        char NW = '╔';
        char N = '═';
        char NE = '╗';
        char E = '║';
        char SE = '╝';
        char S = '═';
        char SW = '╚';
        char W = '║';
        char C = ' ';

        char A = '≡';

        char[][] testDisp = new char[testMaze.getSize()[0]*testMaze.getSize()[0]][4];                   // 0 1
                                                                                                        // 2 3


        int i = 0;

        for (Block block: testMaze.getMazeMap()
             ) {

            testDisp[i][3] = '.';


            if (block.getWallNorth().getActive() )
            {
                testDisp[i][0] = A;
                testDisp[i][1] = A;
            }else
            {
                testDisp[i][0] = C;
                testDisp[i][1] = C;
            }

            if (block.getWallWest().getActive() )
            {
                testDisp[i][0] = A;
                testDisp[i][2] = A;
            }
            else
            {
                testDisp[i][2] = C;
            }
            i++;
        }



//        for (char[] item:testDisp
//             ) {
//            System.out.print(item);
//        }



        ArrayList <String> displayBuffer1 = new ArrayList<>();
        ArrayList <String> displayBuffer2 = new ArrayList<>();
        int sizeY = testMaze.getSize()[1];
        int sizeX = testMaze.getSize()[0];
        boolean toggle = true;

        for (char[] item: testDisp
             ) {
            displayBuffer1.add(Character.toString(item[0]));
            displayBuffer1.add(Character.toString(item[1]));
        }

        for (char[] item: testDisp
        ) {
            displayBuffer2.add(Character.toString(item[2]));
            displayBuffer2.add(Character.toString(item[3]));
        }


            for (String item: displayBuffer1
            ) {
                System.out.print(item);

            }

            System.out.println();
            for (String item: displayBuffer2
            ) {
                System.out.print(item);
            }
    }

}

