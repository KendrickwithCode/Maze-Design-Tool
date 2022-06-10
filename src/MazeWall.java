import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class defining maze wall objects which are all contained within Block objects.
 */
public class MazeWall extends JButton implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    private boolean active;
    private boolean start;
    private boolean finish;
    private boolean border;
    private boolean oldWallState;

    /**
     * Maze wall constructor
     */
    public MazeWall() {
        addListeners();
    }

    /**
     * returns if the current wall is an Active wall that can't be passed
     * @return boolean of the active wall if it is ture (enable / can't pass) / False can pass.
     */
    public boolean getActive() {
        return active;
    }

    /**
     *  Resets this wall to its previous wall state (saved due to placement of logo block)
     */
    public void resetWall(){
        setActive(oldWallState);
    }

//    /**
//     * Returns previous wall state (saved due to placement of logo block)
//     * @return old wall state
//     */
//    public boolean getOldWallState() {
//        return oldWallState;
//    }

    /**
     * Overload
     * Sets if the current wall is an Active wall that can't be passed
     * @param active boolean of the active wall if it is true (enable / can't pass) / False can pass.
     */
    public void setActive(boolean active) {
        setActive(active,true);
    }

    /**
     * Sets if the current wall is an Active wall that can't be passed
     * @param active boolean of the active wall if it is true (enable / can't pass) / False can pass.
     * @param updateOldState when set it will update the oldWallState to current wall state.
     */
    public void setActive(boolean active, boolean updateOldState ) {
        if(updateOldState)
        {
            this.oldWallState = active;
        }
        this.active = active;
        setButtonColor();
    }

    /**
     * Returns if wall is starting wall
     * @return boolean true/false if wall is starting wall
     */
    public boolean isStart() {
        return start;
    }

    /**
     * Sets wall as starting wall
     * @param start boolean true/false if wall is starting wall
     */
    public void setStart(boolean start) {
        this.start = start;
        getParent().repaint();
    }

    /**
     * Returns if wall is finishing wall
     * @return boolean true/false if wall is finishing wall
     */
    public boolean getFinish() {
        return finish;
    }

    /**
     * Sets wall as finishing wall
     * @param finish boolean true/false if wall is finishing wall
     */
    public void setFinish(boolean finish) {
        this.finish = finish;
        getParent().repaint();
    }

    /**
     * Sets wall GUI and variables if wall is a border
     * @param state True will set as boarder wall. False will clear the boarder wall state.
     */
    public void setBorder(boolean state){
        this.border = state;
        setButtonColor();
    }

    /**
     * Returns if wall is border wall
     * @return Boolean - is wall a border wall
     */
    public boolean getIsBorder(){
        return this.border;
    }


    private void setButtonColor(){
        Color unsetColor = Color.white;
        Color activeColor = Color.black;

        this.setContentAreaFilled(true);

        if(border){
            setBackground(activeColor);
        } else if(active){
            setBackground(activeColor);
        }  else {
            setContentAreaFilled(false);
            setBackground(unsetColor);
        }
    }

    /**
     * Add Mouse listeners to mazeWall JButton (This is public as loading new mazes in requires listeners to be reset)
     */
    public void addListeners() {

        MouseListener[] listeners  = getMouseListeners();
        for (MouseListener mouseListener : listeners) {
            removeMouseListener(mouseListener);
        }

        Color hoverColor = Color.gray;

        setFocusPainted(false);
        setRolloverEnabled(false);

        if(border) {
            setButtonColor();
            setBorder(true);
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MazePanel mazePanel = (MazePanel) getParent();
                ArrayList<JMenuItem> items = new ArrayList<>();
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)){
                    JPopupMenu menu = new JPopupMenu();
                    if(Maze.MazeTools.getCurrentMaze().getMazeType().equals("Adult")){
                        JMenuItem item1 = new JMenuItem("Set as starting wall");
                        item1.addActionListener(e1 -> {
                            mazePanel.resetStartWalls();
                            setStart(!start);
                        });
                        JMenuItem item2 = new JMenuItem("Set as finishing wall");
                        item2.addActionListener(e12 -> {
                            mazePanel.resetFinishWalls();
                            setFinish(true);
                        });
                        items.add(item1);
                        items.add(item2);
                    } else {
                        JMenuItem item1 = new JMenuItem("Set as start and finish points by placing icons");
                        items.add(item1);
                    }
                    for(JMenuItem item: items){
                        menu.add(item);
                    }
                    menu.show(e.getComponent(), e.getX(), e.getY());

                } else {
                    if(border) return;
                    setButtonColor();
                    setActive(!active);
                    getParent().repaint();
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                if(border || active) return;
                setBackground(hoverColor);
                setContentAreaFilled(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                if(border) return;
                setButtonColor();
                getParent().repaint();
            }
        });

    }

    @Override
    public void paint(Graphics g) {
        if(!start && !finish){
            super.paint(g);
        }
    }
}