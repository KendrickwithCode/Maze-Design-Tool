import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
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

       if(renderSolution){
            Graphics2D g2 = (Graphics2D) g;

            if(solution.isEmpty()) { return; }

            for (int i = 0; i < solution.size() - 1; i++) {

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
