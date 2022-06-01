import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;

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

    public void resetWall(){
        setActive(oldWallState);
    }

    public boolean getOldWallState() {
        return oldWallState;
    }

    /**
     * Overlaod
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
        setButtonColor();
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
        setButtonColor();
    }

    /**
     * Sets wall GUI and variables if wall is a border
     */
    public void setBorder(){
        this.border = true;
        setButtonColor();
    }

    /**
     * Returns if wall is border wall
     * @return Boolean - is wall a border wall
     */
    public boolean getborder(){
        return this.border;
    }

    public void setButtonColor(){
        Color unsetColor = Color.white;
        Color activeColor = Color.black;
        Color startingColor = Color.green;
        Color finishingColor = Color.red;

        this.setContentAreaFilled(true);

        if (start) {
            setBackground(startingColor);
        } else if (finish) {
            setBackground(finishingColor);
        } else if(border){
            setBackground(activeColor);
        } else if(active){
            setBackground(activeColor);
        }  else {
            setContentAreaFilled(false);
            setBackground(unsetColor);
        }
    }

    public void setButtonEnableVisible(boolean buttonState)
    {
        setButtonEnable(buttonState);
        setButtonVisible(buttonState);
    }

    public void setButtonEnable(boolean buttonState)
    {
        setEnabled(buttonState);
    }

    public void setButtonVisible(boolean buttonState)
    {
        setVisible(buttonState);
    }

    public void addListeners() {

        MouseListener[] listeners  = getMouseListeners();
        for (MouseListener mouseListener : listeners) {
            removeMouseListener(mouseListener);
        }

        Color unsetColor = Color.white;
        Color activeColor = Color.black;
        Color hoverColor = Color.gray;

        setFocusPainted(false);
        setRolloverEnabled(false);

        if(border) {
            setButtonColor();
            setBorder();
        }

        if(!border) {
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    MazePanel mazePanel = (MazePanel) getParent();

                    super.mouseClicked(e);
                    if (SwingUtilities.isRightMouseButton(e)){
                        JPopupMenu menu = new JPopupMenu();
                        JMenuItem item = new JMenuItem("Set as starting wall");
                        item.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                mazePanel.resetStartWalls();
                                setStart(!start);
                            }
                        });
                        JMenuItem item2 = new JMenuItem("Set as finishing wall");
                        item2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                mazePanel.resetFinishWalls();
                                setFinish(true);
                            }
                        });
                        menu.add(item);
                        menu.add(item2);
                        menu.show(e.getComponent(), e.getX(), e.getY());
                    } else {
                        Object src = e.getSource();
                        if(border) return;
                        setButtonColor();
                        setActive(!active);
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
    }
}