import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import java.awt.event.MouseEvent;
import java.util.Objects;


/**
 *  Astract class used for Maze Block and Logo Blocks in the maze.
 */
public abstract class Block implements IBlock, MouseListener, ActionListener {
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
    public Block(int[] location, int blockIndex,Boolean clearWalls)
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

    private void rerenderIcons() {
        boolean gridStatus = MazeLogoTools.getCurrentGUIMaze().getGrid();
        MazeLogoTools.getCurrentGUIMaze().renderBlocks();
        MazeLogoTools.getCurrentGUIMaze().renderMaze(gridStatus, true);
    }

    private void changeIconSize(int sizeX, int sizeY){
        MazeLogoTools.convertLogoBlockToWallBlock(Block.this);
        try {
            MazeLogoTools.convertMazeBlockToLogoBlock(Block.this);
            LogoBlock working = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
            working.setLogoSizeX(sizeX);
            working.setLogoSizeY(sizeY);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public boolean getVisited() {
        return visited;
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

    public void setBlockPanel(JPanel blockPanel) {
        this.blockPanel = blockPanel;
    }


    public Point getBlockPanelLocation() {
        return this.blockPanel.getLocation();
    }

    private JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.white);
        panel.setBorder(BorderFactory.createLineBorder(Color.black, 0));
        panel.addMouseListener(this);

        return panel;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)){
            JPopupMenu menu = new JPopupMenu();
            JMenuItem item = new JMenuItem("Place Icon Here");


            item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        MazeLogoTools.convertMazeBlockToLogoBlock(Block.this);
                        MazeLogoTools.setupAdultLogoBlocks(Block.this,2,2);
                        rerenderIcons();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            JMenuItem item2 = new JMenuItem("Remove Icon Here");

            item2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {

                        changeIconSize(1, 1);
                        rerenderIcons();
                        MazeLogoTools.convertLogoBlockToWallBlock(Block.this);
                        rerenderIcons();                                                     //needed to do twice

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });


            JMenuItem item3 = new JMenuItem("Change Icon Size X to 1");
            item3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(1, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,1,currentSizeY);
                    rerenderIcons();
                }
            });

            JMenuItem item4 = new JMenuItem("Change Icon Size X to 2");
            item4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(2, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,2,currentSizeY);
                    rerenderIcons();
                }
            });


            JMenuItem item5 = new JMenuItem("Change Icon Size X to 3");
            item5.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(3, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,3,currentSizeY);
                    rerenderIcons();
                }
            });

            JMenuItem item6 = new JMenuItem("Change Icon Size X to 4");
            item6.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(4, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,4,currentSizeY);
                    rerenderIcons();
                }
            });


            JMenuItem item7 = new JMenuItem("Change Icon Size X to 5");
            item7.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(5, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,5,currentSizeY);
                    rerenderIcons();
                }
            });


            JMenuItem item8 = new JMenuItem("Change Icon Size X to 6");
            item8.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(6, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,6,currentSizeY);
                    rerenderIcons();
                }
            });

            JMenuItem item9 = new JMenuItem("Change Icon Size X to 7");
            item9.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(7, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,7,currentSizeY);
                    rerenderIcons();
                }
            });

            JMenuItem item10 = new JMenuItem("Change Icon Size X to 8");
            item10.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(8, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,8,currentSizeY);
                    rerenderIcons();
                }
            });

            JMenuItem item11 = new JMenuItem("Change Icon Size X to 9");
            item11.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(9, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,9,currentSizeY);
                    rerenderIcons();
                }
            });

            JMenuItem item12 = new JMenuItem("Change Icon Size X to 10");
            item12.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeY = workingBlock.getLogoSizeY();
                    changeIconSize(10, currentSizeY);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,10,currentSizeY);
                    rerenderIcons();
                }
            });






            JMenuItem item13 = new JMenuItem("Change Icon Size Y to 1");
            item13.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 1);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,1);
                    rerenderIcons();
                }
            });

            JMenuItem item14 = new JMenuItem("Change Icon Size Y to 2");
            item14.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 2);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,2);
                    rerenderIcons();
                }
            });

            JMenuItem item15 = new JMenuItem("Change Icon Size Y to 3");
            item15.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 3);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,3);
                    rerenderIcons();
                }
            });

            JMenuItem item16 = new JMenuItem("Change Icon Size Y to 4");
            item16.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 4);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,4);
                    rerenderIcons();
                }
            });

            JMenuItem item17 = new JMenuItem("Change Icon Size Y to 5");
            item17.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 5);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,5);
                    rerenderIcons();
                }
            });

            JMenuItem item18 = new JMenuItem("Change Icon Size Y to 6");
            item18.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 6);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,6);
                    rerenderIcons();
                }
            });

            JMenuItem item19 = new JMenuItem("Change Icon Size Y to 7");
            item19.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 7);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,7);
                    rerenderIcons();
                }
            });

            JMenuItem item20 = new JMenuItem("Change Icon Size Y to 8");
            item20.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 8);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,8);
                    rerenderIcons();
                }
            });

            JMenuItem item21 = new JMenuItem("Change Icon Size Y to 9");
            item21.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 9);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,9);
                    rerenderIcons();
                }
            });

            JMenuItem item22 = new JMenuItem("Change Icon Size Y to 10");
            item22.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    LogoBlock workingBlock = (LogoBlock) MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex);
                    int currentSizeX = workingBlock.getLogoSizeX();
                    changeIconSize(currentSizeX, 10);
                    MazeLogoTools.setupAdultLogoBlocks(workingBlock,currentSizeX,10);
                    rerenderIcons();
                }
            });


            JMenuItem item23 = new JMenuItem("Change Icon");
            item23.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });


            menu.add(item);


            if(Objects.equals(MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex).getBlockType(), "LogoBlock"))
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

        else
            System.out.println(location[0] + " ," + location[1] + " Idx: " + blockIndex + " Tp: " + MazeLogoTools.getCurrentMaze().getMazeMap().get(blockIndex));

    }



}
