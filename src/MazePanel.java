import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class MazePanel extends JPanel{

    private Maze maze;
    private boolean renderSolution;

    public MazePanel(Maze maze) {
        this.maze = maze;
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

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if(renderSolution){

            ArrayList<Block> solution = new MazeSolver().solveMaze(maze);

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


}
