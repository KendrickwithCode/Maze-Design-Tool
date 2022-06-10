import javax.swing.*;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.awt.event.*;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.util.Objects;


/**
 *  Abstract class used for Maze Block and Logo Blocks in the maze. Objects of this class represent the cells within a maze.
 */
public abstract class Block implements Serializable, MouseListener, ActionListener{

    @Serial
    private static final long serialVersionUID = 6L;

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private MazeWall wallNorth;
    private MazeWall wallSouth;
    private MazeWall wallEast;
    private MazeWall wallWest;
    private final int[] location;
    private final int blockIndex;
    private boolean visited;

    private JPanel blockPanel;

    private ArrayList<String> availableDirections;    // Stores available directions to traverse from this block



    /**
     * Constructs and initialises new Block
     * @param location int[x,y] x,y location of block on maze
     * @param blockIndex sets the block index for the maze map ArrayList of block.
     * @param clearWalls sets the default value for the walls to be cleared (true / inactive) or set (false / active)
     */
    public Block(int[] location, int blockIndex, Boolean clearWalls)
    {
        this.location = location;
        this.blockIndex = blockIndex;
        wallEast = new MazeWall();
        wallSouth = new MazeWall();
        wallEast.setActive(!clearWalls);
        wallSouth.setActive(!clearWalls);

        this.blockPanel = createPanel();

        availableDirections = new ArrayList<>();
    }

    /**
     * Gets the block type of the current block and returns as a string.
     * @return block type as a String.
     */
    public abstract String getBlockType();

    /**
     * Gets all available directions from objects fields
     * @return ArrayList of available directions to travel
     */
    public ArrayList<String> getAvailableDirections() {
        return availableDirections;
    }

    /**
     * Clears all the available directions array
     */
    public void clearAvailableDirections()
    {
        availableDirections.clear();
    }

    private void iconSizeChangeEvent(int newSize, boolean xAxis){
        LogoBlock workingBlock = (LogoBlock) Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex);

        int currentSizeY = workingBlock.getLogoSizeY();
        int currentSizeX = workingBlock.getLogoSizeX();
        if(xAxis) {
            if(checkIconResizeInbounds(newSize,currentSizeY)) {
                Maze.MazeTools.resetWalls(workingBlock);
                changeIconSize(newSize, currentSizeY);
                if (Objects.equals(workingBlock.getLogoType().toUpperCase(), "ADULT"))
                    Maze.MazeTools.setupAdultLogoBlocks(workingBlock, newSize, currentSizeY);
                renderIcons();
            }
        }else{
            if(checkIconResizeInbounds(currentSizeX,newSize)) {
                Maze.MazeTools.resetWalls(workingBlock);
                changeIconSize(currentSizeX, newSize);
                if (Objects.equals(workingBlock.getLogoType().toUpperCase(), "ADULT"))
                    Maze.MazeTools.setupAdultLogoBlocks(workingBlock, currentSizeX, newSize);
                renderIcons();
            }
        }

    }

    protected void renderIcons() {
        boolean gridStatus = Maze.MazeTools.getCurrentGUIMaze().getGrid();
        Maze.MazeTools.getCurrentGUIMaze().renderBlocks();
        Maze.MazeTools.getCurrentGUIMaze().renderMaze(gridStatus, true);
    }

    private void changeIconSize(int sizeX, int sizeY){
        try {
            LogoBlock working = (LogoBlock) Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex);
            working.setLogoSizeX(sizeX);
            working.setLogoSizeY(sizeY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean checkIconResizeInbounds(int endOffsetX, int endOffsetY)
    {
        int mazeSizeX = Maze.MazeTools.getCurrentMaze().getSize()[0];
        int mazeSizeY = Maze.MazeTools.getCurrentMaze().getSize()[1];
        int newX = location[0] + endOffsetX;
        int newY = location[1] + endOffsetY;

        if( newX > mazeSizeX || newY > mazeSizeY) {
            JOptionPane.showMessageDialog(null, "Invalid placement or sizing for image.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Sets available directions
     * @param availableDirections ArrayList of available directions "NORTH", "EAST", "SOUTH", "WEST"
     */
    public void setAvailableDirections(ArrayList<String> availableDirections) {
        this.availableDirections = availableDirections;
    }

    /**
     * Gets the boolean if the block has been visited
     * @return visited boolean.
     */
    public boolean hasNotBeenVisited() {
        return !visited;
    }

    /**
     * Sets the visited state to true or false.
     * @param visited boolean has been visited ture / false.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * Returns location of block in maze as a copy of the array
     * @return blocks location int[x,y]
     */
    public int[] getLocation(){
        return location.clone();
    }

    /**
     * Returns Wall object of north facing wall
     * @return north Wall object
     */
    public MazeWall getWallNorth() {
        return wallNorth;
    }

    /**
     * Returns Wall object of south facing wall
     * @return south Wall object
     */
    public MazeWall getWallSouth() {
        return wallSouth;
    }

    /**
     * Returns Wall object of east facing wall
     * @return east Wall object
     */
    public MazeWall getWallEast() {
        return wallEast;
    }

    /**
     * Returns Wall object of west facing wall
     * @return west Wall object
     */
    public MazeWall getWallWest() {
        return wallWest;
    }

    /**
     * Sets Wall object of north facing wall
     * @param wallNorth north facing wall
     */
    public void setWallNorth(MazeWall wallNorth) {
        this.wallNorth = wallNorth;
    }

    /**
     * Sets Wall object of south facing wall
     * @param wallSouth south facing wall
     */
    public void setWallSouth(MazeWall wallSouth) {
        this.wallSouth = wallSouth;
    }

    /**
     * Sets Wall object of east facing wall
     * @param wallEast east facing wall
     */
    public void setWallEast(MazeWall wallEast) {
        this.wallEast = wallEast;
    }

    /**
     * Sets Wall object of west facing wall
     * @param wallWest west facing wall
     */
    public void setWallWest(MazeWall wallWest) {
        this.wallWest = wallWest;
    }

    /**
     * Returns the blocks Arraylist index
     * @return blocks Arraylist index
     */
    public int getBlockIndex() {
        return blockIndex;
    }

    /**
     *  Returns JPanel associated with this block.
     * @return JPanel from this block.
     */
    public JPanel getBlockPanel() {
        return this.blockPanel;
    }

    /**
     * Sets the JPanel to be associated with this block.
     * @param blockPanel the blockPanel to be set
     */
    public void setBlockPanel(JPanel blockPanel) {
        this.blockPanel = blockPanel;
    }


//    public Point getBlockPanelLocation() {
//        return this.blockPanel.getLocation();
//    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
        panel.addMouseListener(this);

        return panel;
    }

    private void clearStarts(){
        getWallNorth().setStart(false);
        getWallEast().setStart(false);
        getWallSouth().setStart(false);
        getWallWest().setStart(false);
    }

    private void clearFinish(){
        getWallNorth().setFinish(false);
        getWallEast().setFinish(false);
        getWallSouth().setFinish(false);
        getWallWest().setFinish(false);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item1 = new JMenuItem("Place Logo Icon Here");


            item1.addActionListener(e1 -> {
                if(checkIconResizeInbounds(2,2))
                {
                    try {
                        Maze.MazeTools.convertMazeBlockToLogoBlock(Block.this,"logo");
                        Maze.MazeTools.setupAdultLogoBlocks(Block.this,2,2);
                        renderIcons();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JMenuItem item2 = new JMenuItem("Remove Icon");

            item2.addActionListener(e12 -> {
                try {

                    LogoBlock workingBlock = (LogoBlock) Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    Maze.MazeTools.resetWalls(workingBlock);

                    changeIconSize(1, 1);
                    renderIcons();
                    Block.this.clearStarts();
                    Block.this.clearFinish();
                    Maze.MazeTools.convertLogoBlockToMazeBlock(Block.this);
                    renderIcons();                                                     //needed to do twice

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });


            JMenuItem item3 = new JMenuItem("Change Icon Size X to 1");
            item3.addActionListener(e13 -> iconSizeChangeEvent(1,true));

            JMenuItem item4 = new JMenuItem("Change Icon Size X to 2");
            item4.addActionListener(e14 -> iconSizeChangeEvent(2,true));


            JMenuItem item5 = new JMenuItem("Change Icon Size X to 3");
            item5.addActionListener(e15 -> iconSizeChangeEvent(3,true));

            JMenuItem item6 = new JMenuItem("Change Icon Size X to 4");
            item6.addActionListener(e16 -> iconSizeChangeEvent(4,true));


            JMenuItem item7 = new JMenuItem("Change Icon Size X to 5");
            item7.addActionListener(e17 -> iconSizeChangeEvent(5,true));


            JMenuItem item8 = new JMenuItem("Change Icon Size X to 6");
            item8.addActionListener(e18 -> iconSizeChangeEvent(6,true));

            JMenuItem item9 = new JMenuItem("Change Icon Size X to 7");
            item9.addActionListener(e19 -> iconSizeChangeEvent(7,true));

            JMenuItem item10 = new JMenuItem("Change Icon Size X to 8");
            item10.addActionListener(e110 -> iconSizeChangeEvent(8,true));

            JMenuItem item11 = new JMenuItem("Change Icon Size X to 9");
            item11.addActionListener(e111 -> iconSizeChangeEvent(9,true));

            JMenuItem item12 = new JMenuItem("Change Icon Size X to 10");
            item12.addActionListener(e112 -> iconSizeChangeEvent(10,true));






            JMenuItem item13 = new JMenuItem("Change Icon Size Y to 1");
            item13.addActionListener(e113 -> iconSizeChangeEvent(1,false));

            JMenuItem item14 = new JMenuItem("Change Icon Size Y to 2");
            item14.addActionListener(e114 -> iconSizeChangeEvent(2,false));

            JMenuItem item15 = new JMenuItem("Change Icon Size Y to 3");
            item15.addActionListener(e115 -> iconSizeChangeEvent(3,false));

            JMenuItem item16 = new JMenuItem("Change Icon Size Y to 4");
            item16.addActionListener(e116 -> iconSizeChangeEvent(4,false));

            JMenuItem item17 = new JMenuItem("Change Icon Size Y to 5");
            item17.addActionListener(e117 -> iconSizeChangeEvent(5,false));

            JMenuItem item18 = new JMenuItem("Change Icon Size Y to 6");
            item18.addActionListener(e118 -> iconSizeChangeEvent(6,false));

            JMenuItem item19 = new JMenuItem("Change Icon Size Y to 7");
            item19.addActionListener(e119 -> iconSizeChangeEvent(7,false));

            JMenuItem item20 = new JMenuItem("Change Icon Size Y to 8");
            item20.addActionListener(e120 -> iconSizeChangeEvent(8,false));

            JMenuItem item21 = new JMenuItem("Change Icon Size Y to 9");
            item21.addActionListener(e121 -> iconSizeChangeEvent(9,false));

            JMenuItem item22 = new JMenuItem("Change Icon Size Y to 10");
            item22.addActionListener(e122 -> iconSizeChangeEvent(10,false));


            JMenuItem item23 = new JMenuItem("Change Icon");
            item23.addActionListener(e123 -> {
                LogoBlock currentBlock = (LogoBlock) Maze.MazeTools.getCurrentMaze().getMazeMap().get(Block.this.blockIndex);
                currentBlock.changeLogo();
            });

            JMenuItem item24 = new JMenuItem("Place Kids Start");
            item24.addActionListener(e124 -> {
                if(checkIconResizeInbounds(2,2)) {
                    try {

                        Block oldStart = Maze.MazeTools.getCurrentMaze().getMazeMap().get(Maze.MazeTools.getCurrentMaze().getKidsStartIndex());
                        if(Objects.equals(Maze.MazeTools.getCurrentMaze().getMazeMap().get(oldStart.getBlockIndex()).getBlockType(), "LogoBlock")) {
                            Maze.MazeTools.convertLogoBlockToMazeBlock(oldStart);                                //Remove Old Icon
                        }
                        oldStart.clearStarts();
                        Maze.MazeTools.convertMazeBlockToLogoBlock(Block.this, "start");
                        Maze.MazeTools.getCurrentMaze().setKidsStartIndex(Block.this.blockIndex);

                        renderIcons();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JMenuItem item25 = new JMenuItem("Place Kids Finish");
            item25.addActionListener(e125 -> {
                if(checkIconResizeInbounds(2,2)) {
                    try {
                        Block oldFinish = Maze.MazeTools.getCurrentMaze().getMazeMap().get(Maze.MazeTools.getCurrentMaze().getKidsFinishIndex());
                        if(Objects.equals(Maze.MazeTools.getCurrentMaze().getMazeMap().get(oldFinish.getBlockIndex()).getBlockType(), "LogoBlock")) {
                            Maze.MazeTools.convertLogoBlockToMazeBlock(oldFinish);                               //Remove Old Icon
                        }
                        oldFinish.clearFinish();
                        Maze.MazeTools.convertMazeBlockToLogoBlock(Block.this, "end");
                        Maze.MazeTools.getCurrentMaze().setKidsFinishIndex(Block.this.blockIndex);

                        //Clear default finish line
                        Maze.MazeTools.getCurrentMaze().getMazeMap().get(Maze.MazeTools.getCurrentMaze().getMazeMap().size() - 1).getWallSouth().setFinish(false);

                        renderIcons();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            if(!Objects.equals(Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex).getBlockType(), "LogoBlock"))
                menu.add(item1);


            if(Objects.equals(Maze.MazeTools.getCurrentMaze().getMazeType().toUpperCase(), "KIDS"))
            {
                menu.add(item24);
                menu.add(item25);
            }

            if(Objects.equals(Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex).getBlockType(), "LogoBlock"))
            {
                menu.add(item2);
                menu.add(item23);
                menu.addSeparator();
                menu.add(item3);
                menu.add(item4);
                menu.add(item5);
                menu.add(item6);
                menu.add(item7);
                menu.add(item8);
                menu.add(item9);
                menu.add(item10);
                menu.add(item11);
                menu.add(item12);
                menu.addSeparator();
                menu.add(item13);
                menu.add(item14);
                menu.add(item15);
                menu.add(item16);
                menu.add(item17);
                menu.add(item18);
                menu.add(item19);
                menu.add(item20);
                menu.add(item21);
                menu.add(item22);
            }

            menu.show(e.getComponent(), e.getX(), e.getY());
        }

//        else debugTools();

    }

//    private void debugTools(){
//            System.out.println(location[0] + " ," + location[1] + " Idx: " + blockIndex + " Tp: " + Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex));
//            System.out.println(Maze.MazeTools.getCurrentMaze().getMazeType());
//            System.out.println("CurrentWall: " +  this.getWallNorth().getActive() + " Old: " + this.getWallNorth().getOldWallState());
//            if(Objects.equals(Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex).getBlockType(), "LogoBlock"))
//                {
//                    LogoBlock current = (LogoBlock) Maze.MazeTools.getCurrentMaze().getMazeMap().get(blockIndex);
//                    System.out.println("Img: " + current.getPictureFile());
//                    System.out.println("LogoType: " + current.getLogoType());
//                    System.out.println("LogoKidsStart: " + current.isLogoStart());
//                }
//    }

}
