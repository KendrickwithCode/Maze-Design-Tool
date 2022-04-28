import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * The editable Maze part of the GUI.
 */
public class GUI_Maze extends JPanel{

    private int mazeHeight;
    private int mazeWidth;

    private int blockSize;

    private int wallWidth;

    private Maze maze;

    /**
     * Maze GUI constructor, creates layout for maze blocks and walls and displays them appropriately
     * @param maze Maze object to display
     * @param generate rue to generate maze or false to create blank canvas
     */
    public GUI_Maze(Maze maze,boolean generate) {
        // Set Maze
        this.maze = maze;

        // Set Maze amount of blocks for maze width and height
        this.mazeHeight = maze.getSize()[1];
        this.mazeWidth = maze.getSize()[0];

        // Set block and wall sizing to suit amount of blocks
        blockSize = mazeBlockSize(mazeHeight, mazeWidth);
        wallWidth = mazeWallWidth(mazeHeight, mazeWidth);

        if (generate)
        {
            //maze.generateNewMaze("DFSRecursive",new int[] {0,0});
            maze.generateNewMaze("DFSIterative",new int[]{0,0});
        }

        // Set maze padding and layout
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        setBorder(padding);
        setLayout(new GridBagLayout());

        // Create and set maze JPanel to be placed in center of area
        JPanel mazePanel = createMazePanel();

        // Render Maze to GUI
        renderMaze(mazePanel);
    }

    private void renderMaze(JPanel mazePanel){
        // Set maze components common constraints
        GridBagConstraints mazeComponentConstraints = new GridBagConstraints();
        mazeComponentConstraints.weightx = 1;
        mazeComponentConstraints.weighty = 1;


        // Iterate through maze blocks and populate mazePanel
        for ( Block block : maze.getMazeMap() ){

            // Get location of block
            int blockXLocation = (block.getLocation()[0] + 1) * 2 -1;
            int blockYLocation = (block.getLocation()[1] + 1) * 2 -1;
            int[] location = new int[] {blockXLocation, blockYLocation};

            // North wall
            JButton northWallButton = createNorthWallButton(block, mazeComponentConstraints, location);
            // Add button to mazePanel with constraints
            mazePanel.add(northWallButton, mazeComponentConstraints);

            // West wall
            JButton westWallButton = createWestWallButton(block, mazeComponentConstraints, location);
            // Add button to mazePanel with constraints
            mazePanel.add(westWallButton, mazeComponentConstraints);

            // East wall
            JButton eastWallButton = createEastWallButton(block, mazeComponentConstraints, location);
            // Add button to mazePanel with constraints
            mazePanel.add(eastWallButton, mazeComponentConstraints);

            // South wall
            JButton southWallButton = createSouthWallButton(block, mazeComponentConstraints, location);
            // Add button to mazePanel with constraints
            mazePanel.add(southWallButton, mazeComponentConstraints);

            // Block
            JPanel blockPanel = createBlockPanel(block, mazeComponentConstraints, location);
            // Add block panel to mazePanel
            mazePanel.add(blockPanel, mazeComponentConstraints);
        }
    }

    private JButton createNorthWallButton(Block block, GridBagConstraints constraints, int[] location) {
        // Get north wall button
        JButton northButton = block.getWallNorth().getButton();
        // Set button constraints
        northButton.setPreferredSize(new Dimension(blockSize, wallWidth));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridx = location[0] - 1;
        constraints.gridy = location[1] - 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        return northButton;
    }

    private JButton createWestWallButton(Block block, GridBagConstraints constraints, int[] location){
        // Get West wall button
        JButton westButton = block.getWallWest().getButton();
        // Set button constraints
                westButton.setPreferredSize(new Dimension(wallWidth, blockSize));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = location[0] - 1;
        constraints.gridy = location[1] - 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 3;

        return westButton;
    }

    private JButton createEastWallButton(Block block, GridBagConstraints constraints, int[] location){
        // Get east wall button
        JButton eastButton = block.getWallEast().getButton();
        // Set button constraints
        eastButton.setPreferredSize(new Dimension(wallWidth, blockSize));
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.anchor = GridBagConstraints.LINE_END;
        constraints.gridx = location[0]  + 1;
        constraints.gridy = location[1]  - 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 3;

        return eastButton;
    }

    private JButton createSouthWallButton(Block block, GridBagConstraints constraints, int[] location){
        // Get south wall button
        JButton southButton = block.getWallSouth().getButton();
        // Set button constraints
        southButton.setPreferredSize(new Dimension(blockSize, wallWidth));
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.PAGE_END;
        constraints.gridx = location[0] - 1;
        constraints.gridy = location[1] + 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;

        return southButton;
    }

    private JPanel createBlockPanel(Block block, GridBagConstraints constraints, int[] location){
        // Get block panel
        JPanel blockPanel = block.getBlockPanel();
        // Set block panel sizing
        blockPanel.setPreferredSize(new Dimension(blockSize, blockSize));
        blockPanel.setMaximumSize(new Dimension(blockSize, blockSize));
        blockPanel.setMinimumSize(new Dimension(blockSize, blockSize));
        // Set blockPanel constraints
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = location[0];
        constraints.gridy = location[1];

        return blockPanel;
    }

    private JPanel createMazePanel() {
        JPanel panel = new JPanel();
        GridBagLayout mazeLayout = new GridBagLayout();
        panel.setLayout(mazeLayout);

        // Set mazePanel Constraints and dimensions
        GridBagConstraints mazePanelConstraints = new GridBagConstraints();
        mazePanelConstraints.anchor = GridBagConstraints.CENTER;
        mazePanelConstraints.weightx = 1;
        mazePanelConstraints.weighty = 1;
        panel.setMaximumSize(new Dimension(mazeWidth * 50, mazeHeight * 50));

        // Add mazePanel to parent JPanel component
        add(panel, mazePanelConstraints);
        return panel;
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
