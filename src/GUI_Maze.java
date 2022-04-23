import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class GUI_Maze extends JPanel{

    private int mazeHeight;
    private int mazeWidth;

    private Maze maze;


    public GUI_Maze(Maze maze) {
    this(maze,false);
    }

    public GUI_Maze(Maze maze,boolean generate) {
        // Set Maze amount of blocks for maze width and height
        this.mazeHeight = maze.getSize()[1];
        this.mazeWidth = maze.getSize()[0];
        maze.generateNewMaze("DPSIterative",new int[]{0,0});

//        if (generate)
//        {
//            //maze.generateNewMaze("DPSRecursive",new int[] {0,0});
//            //maze.generateNewMaze("DPSIterative",new int[]{0,0});
//        }

        // Set maze padding and layout
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        setBorder(padding);
        setLayout(new GridBagLayout());

        // Create and set maze JPanel to be placed in center of area
        JPanel mazePanel = new JPanel();
        GridBagLayout mazeLayout = new GridBagLayout();
        mazePanel.setLayout(mazeLayout);

        // Set mazePanel Constraints and dimensions
        GridBagConstraints mazePanelConstraints = new GridBagConstraints();
        mazePanelConstraints.anchor = GridBagConstraints.CENTER;
        mazePanelConstraints.weightx = 1;
        mazePanelConstraints.weighty = 1;
        mazePanel.setMaximumSize(new Dimension(mazeWidth * 50, mazeHeight * 50));

        // Add mazePanel to parent JPanel component
        add(mazePanel, mazePanelConstraints);

        // Set block and wall sizing to suit amount of blocks
        int blockSize = mazeBlockSize(mazeHeight, mazeWidth);
        int wallWidth = mazeWallWidth(mazeHeight, mazeWidth);

        // Set maze components common constraints
        GridBagConstraints mazeComponentConstraints = new GridBagConstraints();
        mazeComponentConstraints.weightx = 1;
        mazeComponentConstraints.weighty = 1;

        // Iterate through maze blocks and populate mazePanel
        for ( Block block : maze.getMazeMap() ){

            // Get location of block
            int blockXLocation = (block.getLocation()[0] + 1) * 2 -1;
            int blockYLocation = (block.getLocation()[1] + 1) * 2 -1;

            // ---
            // North wall
            // Get north wall button
            JButton northButton = block.wallNorth.getButton();
            // Set button constraints
            northButton.setPreferredSize(new Dimension(blockSize, wallWidth));
            mazeComponentConstraints.fill = GridBagConstraints.HORIZONTAL;
            mazeComponentConstraints.anchor = GridBagConstraints.PAGE_START;
            mazeComponentConstraints.gridx = blockXLocation - 1;
            mazeComponentConstraints.gridy = blockYLocation - 1;
            mazeComponentConstraints.gridwidth = 3;
            mazeComponentConstraints.gridheight = 1;
            // Add button to mazePanel with constraints
            mazePanel.add(northButton, mazeComponentConstraints);
            // ---


            // ---
            // West wall
            // Get West wall button
            JButton westButton = block.wallWest.getButton();
            // Set button constraints
            westButton.setPreferredSize(new Dimension(wallWidth, blockSize));
            mazeComponentConstraints.fill = GridBagConstraints.VERTICAL;
            mazeComponentConstraints.anchor = GridBagConstraints.LINE_START;
            mazeComponentConstraints.gridx = blockXLocation - 1;
            mazeComponentConstraints.gridy = blockYLocation - 1;
            mazeComponentConstraints.gridwidth = 1;
            mazeComponentConstraints.gridheight = 3;
            // Add button to mazePanel with constraints
            mazePanel.add(westButton, mazeComponentConstraints);
            // ---


            // ---
            // East wall
            // Get east wall button
            JButton eastButton = block.wallEast.getButton();
            // Set button constraints
            eastButton.setPreferredSize(new Dimension(wallWidth, blockSize));
            mazeComponentConstraints.fill = GridBagConstraints.VERTICAL;
            mazeComponentConstraints.anchor = GridBagConstraints.LINE_END;
            mazeComponentConstraints.gridx = blockXLocation + 1;
            mazeComponentConstraints.gridy = blockYLocation - 1;
            mazeComponentConstraints.gridwidth = 1;
            mazeComponentConstraints.gridheight = 3;
            // Add button to mazePanel with constraints
            mazePanel.add(eastButton, mazeComponentConstraints);
            // ---


            // ---
            // South wall
            // Get south wall button
            JButton southButton = block.wallSouth.getButton();
            // Set button constraints
            southButton.setPreferredSize(new Dimension(blockSize, wallWidth));
            mazeComponentConstraints.fill = GridBagConstraints.HORIZONTAL;
            mazeComponentConstraints.anchor = GridBagConstraints.PAGE_END;
            mazeComponentConstraints.gridx = blockXLocation - 1;
            mazeComponentConstraints.gridy = blockYLocation + 1;
            mazeComponentConstraints.gridwidth = 3;
            mazeComponentConstraints.gridheight = 1;
            // Add button to mazePanel with constraints
            mazePanel.add(southButton, mazeComponentConstraints);
            // ---


            // ---
            // Block
            // Get block panel
            JPanel blockPanel = block.getBlockPanel();
            // Set block panel sizing
            blockPanel.setPreferredSize(new Dimension(blockSize, blockSize));
            blockPanel.setMaximumSize(new Dimension(blockSize, blockSize));
            blockPanel.setMinimumSize(new Dimension(blockSize, blockSize));
            // Set blockPanel constraints
            mazeComponentConstraints.fill = GridBagConstraints.BOTH;
            mazeComponentConstraints.anchor = GridBagConstraints.CENTER;
            mazeComponentConstraints.gridwidth = 1;
            mazeComponentConstraints.gridheight = 1;
            mazeComponentConstraints.gridx = blockXLocation;
            mazeComponentConstraints.gridy = blockYLocation;
            // Add block panel to mazePanel
            mazePanel.add(blockPanel, mazeComponentConstraints);
            // ---

        }

    }


    private int mazeWallWidth (int mazeHeight, int mazeWidth){
        int amountOfCells = mazeHeight * mazeWidth;
        if(amountOfCells < 100){
            return 10;
        } else if (amountOfCells >= 100 && amountOfCells < 400){
            return 8;
        } else if (amountOfCells >= 400 && amountOfCells < 2500){
            return 6;
        } else {
            return 4;
        }
    }

    private int mazeBlockSize (int mazeHeight, int mazeWidth){
        int amountOfCells = mazeHeight * mazeWidth;
        if(amountOfCells < 100){
            return (600 / mazeHeight) + 10;
        } else if (amountOfCells >= 100 && amountOfCells < 400){
            return (450 / mazeHeight) + 10;
        } else if (amountOfCells >= 400 && amountOfCells < 2500){
            return (300 / mazeHeight)+ 10;
        } else {
            return (100 / mazeHeight) + 10;
        }
    }
}
