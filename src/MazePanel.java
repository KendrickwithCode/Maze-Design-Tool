import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

public class MazePanel extends JPanel{

    private Maze maze;
    private boolean renderSolution;
    private JLabel solvableLabel;
    private JLabel percentageDeadEndLabel;
    private JLabel percentageTravelledLabel;


    public MazePanel(Maze maze) {
        this.maze = maze;
        percentageDeadEndLabel = new JLabel();
        percentageTravelledLabel = new JLabel();
        solvableLabel = new JLabel();
        setBackground(Color.white);
    }

    public void setRenderSolution(boolean setValue){
        this.renderSolution = setValue;

        repaint();
    }

    public void resetStartWalls(){
        for ( Block block : maze.getMazeMap() ) {

            block.getWallNorth().setStart(false);
            block.getWallEast().setStart(false);
            block.getWallSouth().setStart(false);
            block.getWallWest().setStart(false);

        }
    }

    public void resetFinishWalls(){
        for ( Block block : maze.getMazeMap() ) {

            block.getWallNorth().setFinish(false);
            block.getWallSouth().setFinish(false);
            block.getWallEast().setFinish(false);
            block.getWallWest().setFinish(false);
        }
    }

    public void setSolvableLabel(JLabel label){
        this.solvableLabel = label;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ArrayList<Block> solution = solveMaze();
        if(!MazeLogoTools.getCurrentMaze().getMazeType().equalsIgnoreCase("KIDS")){

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

            if(startWall.getborder()){
                if(startWallLocation == "north"){
                    int triangleWidth = startWall.getHeight() / 2 + 2;
                    int point1X = startWallX - triangleWidth;
                    int point1Y = startWallY - triangleWidth + 2;
                    int point2X = startWallX + triangleWidth;
                    int point2Y = startWallY - triangleWidth + 2;
                    int point3X = startWallX;
                    int point3Y = startWallY + triangleWidth + 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                } else if(startWallLocation == "south"){
                    int triangleWidth = startWall.getHeight() / 2 + 2;
                    int point1X = startWallX - triangleWidth;
                    int point1Y = startWallY + triangleWidth - 2;
                    int point2X = startWallX + triangleWidth;
                    int point2Y = startWallY + triangleWidth - 2;
                    int point3X = startWallX;
                    int point3Y = startWallY - triangleWidth - 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                } else if(startWallLocation == "east"){
                    int triangleWidth = startWall.getWidth() / 2 + 2;
                    int point1X = startWallX + triangleWidth - 2;
                    int point1Y = startWallY - triangleWidth;
                    int point2X = startWallX + triangleWidth - 2;
                    int point2Y = startWallY + triangleWidth;
                    int point3X = startWallX - triangleWidth - 2;
                    int point3Y = startWallY;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                } else if(startWallLocation == "west"){
                    int triangleWidth = startWall.getWidth() / 2 + 2;
                    int point1X = startWallX - triangleWidth + 2;
                    int point1Y = startWallY - triangleWidth;
                    int point2X = startWallX - triangleWidth + 2;
                    int point2Y = startWallY + triangleWidth;
                    int point3X = startWallX + triangleWidth + 2;
                    int point3Y = startWallY;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
            } else {
                int triangleWidth = 0;
                if(startWallLocation == "south" || startWallLocation == "north"){
                    triangleWidth = startWall.getHeight() / 2 + 2;
                } else {
                    triangleWidth = startWall.getWidth() / 2 + 2;
                }
                int point1X = startWallX;
                int point1Y = startWallY - triangleWidth;
                int point2X = startWallX - triangleWidth;
                int point2Y = startWallY;
                int point3X = startWallX;
                int point3Y = startWallY + triangleWidth;
                int point4X = startWallX + triangleWidth;
                int point4Y = startWallY;

                Path2D path = new Path2D.Double();
                path.moveTo(point1X, point1Y);
                path.lineTo(point2X, point2Y);
                path.lineTo(point3X, point3Y);
                path.lineTo(point4X, point4Y);
                path.closePath();

                g3.draw(path);
                g3.fill(path);
            }

            if(finishWall.getborder()){
                if(finishWallLocation == "south"){
                    int triangleWidth = finishWall.getHeight() / 2 + 2;
                    int point1X = finishWallX - triangleWidth;
                    int point1Y = finishWallY - triangleWidth - 2;
                    int point2X = finishWallX + triangleWidth;
                    int point2Y = finishWallY - triangleWidth - 2;
                    int point3X = finishWallX;
                    int point3Y = finishWallY + triangleWidth - 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                } else if(finishWallLocation == "north"){
                    int triangleWidth = finishWall.getHeight() / 2 + 2;
                    int point1X = finishWallX - triangleWidth;
                    int point1Y = finishWallY + triangleWidth + 2;
                    int point2X = finishWallX + triangleWidth;
                    int point2Y = finishWallY + triangleWidth + 2;
                    int point3X = finishWallX;
                    int point3Y = finishWallY - triangleWidth + 2;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                } else if(finishWallLocation == "west"){
                    int triangleWidth = finishWall.getWidth() / 2 + 2;
                    int point1X = finishWallX + triangleWidth + 2;
                    int point1Y = finishWallY - triangleWidth;
                    int point2X = finishWallX + triangleWidth + 2;
                    int point2Y = finishWallY + triangleWidth;
                    int point3X = finishWallX - triangleWidth + 2;
                    int point3Y = finishWallY;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                } else if(finishWallLocation == "east"){
                    int triangleWidth = finishWall.getWidth() / 2 + 2;
                    int point1X = finishWallX - triangleWidth - 2;
                    int point1Y = finishWallY - triangleWidth;
                    int point2X = finishWallX - triangleWidth - 2;
                    int point2Y = finishWallY + triangleWidth;
                    int point3X = finishWallX + triangleWidth - 2;
                    int point3Y = finishWallY;

                    Path2D path = new Path2D.Double();
                    path.moveTo(point1X, point1Y);
                    path.lineTo(point2X, point2Y);
                    path.lineTo(point3X, point3Y);
                    path.closePath();

                    g3.draw(path);
                    g3.fill(path);
                }
            } else {
                int triangleWidth = 0;
                if(finishWallLocation == "south" || finishWallLocation == "north"){
                    triangleWidth = finishWall.getHeight() / 2 + 2;
                } else {
                    triangleWidth = finishWall.getWidth() / 2 + 2;
                }
                int point1X = finishWallX;
                int point1Y = finishWallY - triangleWidth;
                int point2X = finishWallX - triangleWidth;
                int point2Y = finishWallY;
                int point3X = finishWallX;
                int point3Y = finishWallY + triangleWidth;
                int point4X = finishWallX + triangleWidth;
                int point4Y = finishWallY;

                Path2D path = new Path2D.Double();
                path.moveTo(point1X, point1Y);
                path.lineTo(point2X, point2Y);
                path.lineTo(point3X, point3Y);
                path.lineTo(point4X, point4Y);
                path.closePath();

                g3.draw(path);
                g3.fill(path);
            }

        }



        // Solution line
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


    public void setPercentageDeadEndLabel(JLabel label) {
        this.percentageDeadEndLabel = label;
    }

    public void setPercentageTravelledLabel(JLabel label) {
        this.percentageTravelledLabel = label;
    }
}
