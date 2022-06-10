import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

/**
 * The bottom left maze statistics area of the GUI and methods to interface
 */
public class MazePanel extends JPanel{

    private final Maze maze;
    private boolean renderSolution;
    private JLabel solvableLabel;
    private JLabel percentageDeadEndLabel;
    private JLabel percentageTravelledLabel;


    /**
     * MazePanel constructor
     * @param maze maze object to create panel from
     */
    public MazePanel(Maze maze) {
        this.maze = maze;
        percentageDeadEndLabel = new JLabel();
        percentageTravelledLabel = new JLabel();
        solvableLabel = new JLabel();
        setBackground(Color.white);
    }

    /**
     * Sets render field to be used to conditionally render solution line
     * @param render boolean to conditionally render solution
     */
    public void setRenderSolution(boolean render){
        this.renderSolution = render;
        repaint();
    }

    /**
     * Resets all walls in maze so that no wall is a finish wall
     */
    public void resetStartWalls(){
        for ( Block block : maze.getMazeMap() ) {
            block.getWallNorth().setStart(false);
            block.getWallEast().setStart(false);
            block.getWallSouth().setStart(false);
            block.getWallWest().setStart(false);
        }
    }

    /**
     * Resets all walls in maze so no wall is a finish wall
     */
    public void resetFinishWalls(){
        for ( Block block : maze.getMazeMap() ) {
            block.getWallNorth().setFinish(false);
            block.getWallSouth().setFinish(false);
            block.getWallEast().setFinish(false);
            block.getWallWest().setFinish(false);
        }
    }

    /**
     * Sets solvable label
     * @param label JLabel solvable label
     */
    public void setSolvableLabel(JLabel label){
        this.solvableLabel = label;
    }

    private ArrayList<Block> solveMaze(){
        int totalCells = maze.getSize()[0] * maze.getSize()[1];

        MazeSolver mazeSolver = new MazeSolver();

        ArrayList<Block> solution = mazeSolver.solveMaze(maze);

        double percentageTravelled = (solution.size() / (double)totalCells) * 100;
        double percentageDeadEnds = mazeSolver.deadEndCount(maze) / (double)totalCells * 100;

        this.percentageDeadEndLabel.setText(((int)percentageDeadEnds) + "%");

        if(solution.isEmpty()){
            maze.setSolvable(false);
            solvableLabel.setText("False");
            solvableLabel.setForeground(Color.red);
            percentageTravelledLabel.setText("0%");
        } else {
            maze.setSolvable(true);
            solvableLabel.setText("True");
            solvableLabel.setForeground(Color.green);
            percentageTravelledLabel.setText(((int)percentageTravelled) + "%");
        }

        return solution;
    }

    /**
     * Set PercentageDeadEnd Label
     * @param label JLabel PercentageDeadEnd Label
     */
    public void setPercentageDeadEndLabel(JLabel label) {
        this.percentageDeadEndLabel = label;
    }

    /**
     * Sets PercentageTravelled label
     * @param label JLabel PercentageTravelled Label
     */
    public void setPercentageTravelledLabel(JLabel label) {
        this.percentageTravelledLabel = label;
    }

    /**
     * Renders maze panel with override for start / finish walls and solution line
     * @param g  the <code>Graphics</code> context in which to paint
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ArrayList<Block> solution = solveMaze();
        MazeWall startWall = new MazeWall();
        MazeWall finishWall= new MazeWall();

        String startWallLocation  = "";
        String finishWallLocation  = "";

        for(Block block: maze.getMazeMap()){
            if(block.getWallNorth().isStart()){
                startWall = block.getWallNorth();
                startWallLocation = "north";
            }
            if(block.getWallNorth().getFinish()){
                finishWall = block.getWallNorth();
                finishWallLocation = "north";
            }
            if(block.getWallSouth().isStart()){
                startWall = block.getWallSouth();
                startWallLocation = "south";
            }
            if(block.getWallSouth().getFinish()){
                finishWall = block.getWallSouth();
                finishWallLocation = "south";
            }
            if(block.getWallEast().isStart()){
                startWall = block.getWallEast();
                startWallLocation = "east";
            }
            if(block.getWallEast().getFinish()){
                finishWall = block.getWallEast();
                finishWallLocation = "east";
            }
            if(block.getWallWest().isStart()){
                startWall = block.getWallWest();
                startWallLocation = "west";
            }
            if(block.getWallWest().getFinish()){
                finishWall = block.getWallWest();
                finishWallLocation = "west";
            }
        }

        int startWallX = startWall.getX() + startWall.getWidth() / 2;
        int startWallY = startWall.getY() + startWall.getHeight() / 2;

        int finishWallX = finishWall.getX() + finishWall.getWidth() / 2;
        int finishWallY = finishWall.getY() + finishWall.getHeight() / 2;

        Graphics2D g3 = (Graphics2D) g;
        g3.setStroke(new BasicStroke(2));
        g3.setColor(Color.black);

        if(startWall.getIsBorder()){
            switch (startWallLocation) {
                case "north" -> {
                    int triangleWidth = startWall.getHeight() / 2;
                    int point1X = startWallX - triangleWidth;
                    int point1Y = startWallY - triangleWidth + 2;
                    int point2X = startWallX + triangleWidth;
                    int point2Y = startWallY - triangleWidth + 2;
                    int point3Y = startWallY + triangleWidth + 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(startWallX, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
                case "south" -> {
                    int triangleWidth = startWall.getHeight() / 2;
                    int point1X = startWallX - triangleWidth;
                    int point1Y = startWallY + triangleWidth - 2;
                    int point2X = startWallX + triangleWidth;
                    int point2Y = startWallY + triangleWidth - 2;
                    int point3Y = startWallY - triangleWidth - 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(startWallX, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
                case "east" -> {
                    int triangleWidth = startWall.getWidth() / 2;
                    int point1X = startWallX + triangleWidth - 2;
                    int point1Y = startWallY - triangleWidth;
                    int point2X = startWallX + triangleWidth - 2;
                    int point2Y = startWallY + triangleWidth;
                    int point3X = startWallX - triangleWidth - 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, startWallY);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
                case "west" -> {
                    int triangleWidth = startWall.getWidth() / 2;
                    int point1X = startWallX - triangleWidth + 2;
                    int point1Y = startWallY - triangleWidth;
                    int point2X = startWallX - triangleWidth + 2;
                    int point2Y = startWallY + triangleWidth;
                    int point3X = startWallX + triangleWidth + 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, startWallY);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
            }
        } else if(!Maze.MazeTools.getCurrentMaze().getMazeType().equalsIgnoreCase("KIDS")){
            int triangleWidth;
            if(startWallLocation.equals("south") || startWallLocation.equals("north")){
                triangleWidth = startWall.getHeight() / 2;
            } else {
                triangleWidth = startWall.getWidth() / 2;
            }

            int point1Y = startWallY - triangleWidth;
            int point2X = startWallX - triangleWidth;
            int point3Y = startWallY + triangleWidth;
            int point4X = startWallX + triangleWidth;

            Path2D path = new Path2D.Double();
            path.moveTo(startWallX, point1Y);
            path.lineTo(point2X, startWallY);
            path.lineTo(startWallX, point3Y);
            path.lineTo(point4X, startWallY);
            path.closePath();

            g3.draw(path);
            g3.fill(path);
        }

        if(finishWall.getIsBorder()){
            switch (finishWallLocation) {
                case "south" -> {
                    int triangleWidth = finishWall.getHeight() / 2;
                    int point1X = finishWallX - triangleWidth;
                    int point1Y = finishWallY - triangleWidth - 2;
                    int point2X = finishWallX + triangleWidth;
                    int point2Y = finishWallY - triangleWidth - 2;
                    int point3Y = finishWallY + triangleWidth - 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(finishWallX, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
                case "north" -> {
                    int triangleWidth = finishWall.getHeight() / 2;
                    int point1X = finishWallX - triangleWidth;
                    int point1Y = finishWallY + triangleWidth + 2;
                    int point2X = finishWallX + triangleWidth;
                    int point2Y = finishWallY + triangleWidth + 2;
                    int point3Y = finishWallY - triangleWidth + 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(finishWallX, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
                case "west" -> {
                    int triangleWidth = finishWall.getWidth() / 2;
                    int point1X = finishWallX + triangleWidth + 2;
                    int point1Y = finishWallY - triangleWidth;
                    int point2X = finishWallX + triangleWidth + 2;
                    int point2Y = finishWallY + triangleWidth;
                    int point3X = finishWallX - triangleWidth + 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, finishWallY);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
                case "east" -> {
                    int triangleWidth = finishWall.getWidth() / 2;
                    int point1X = finishWallX - triangleWidth - 2;
                    int point1Y = finishWallY - triangleWidth;
                    int point2X = finishWallX - triangleWidth - 2;
                    int point2Y = finishWallY + triangleWidth;
                    int point3X = finishWallX + triangleWidth - 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, finishWallY);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
            }
        } else if(!Maze.MazeTools.getCurrentMaze().getMazeType().equalsIgnoreCase("KIDS")){
            int triangleWidth;
            if(finishWallLocation.equals("south") || finishWallLocation.equals("north")){
                triangleWidth = finishWall.getHeight() / 2 + 2;
            } else {
                triangleWidth = finishWall.getWidth() / 2 + 2;
            }
            int point1Y = finishWallY - triangleWidth;
            int point2X = finishWallX - triangleWidth;
            int point3Y = finishWallY + triangleWidth;
            int point4X = finishWallX + triangleWidth;


            Path2D path = new Path2D.Double();
            path.moveTo(finishWallX, point1Y);
            path.lineTo(point2X, finishWallY);
            path.lineTo(finishWallX, point3Y);
            path.lineTo(point4X, finishWallY);
            path.closePath();

            g3.draw(path);
            g3.fill(path);
        }

        // Solution render
        if(renderSolution){
           if(solution.isEmpty()) { return; }

            for (int i = 0; i < solution.size() - 1; i++) {

                Graphics2D g2 = (Graphics2D) g;

                int xStart = solution.get(i).getBlockPanel().getLocation().x + solution.get(i).getBlockPanel().getWidth() / 2;
                int yStart = solution.get(i).getBlockPanel().getLocation().y + solution.get(i).getBlockPanel().getHeight() / 2;
                int xFinish = solution.get(i + 1).getBlockPanel().getLocation().x + solution.get(i + 1).getBlockPanel().getWidth() / 2;
                int yFinish = solution.get(i + 1).getBlockPanel().getLocation().y + solution.get(i + 1).getBlockPanel().getHeight() / 2;

                Line2D line = new Line2D.Float(xStart, yStart, xFinish, yFinish);
                g2.setStroke(new BasicStroke(3));
                g2.setColor(Color.RED);
                g2.draw(line);
            }
        }
    }
}
