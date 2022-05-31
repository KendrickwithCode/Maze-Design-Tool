import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.io.Serializable;

public class MazeWall implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;
    public boolean active;
    private boolean start;
    private boolean finish;
    private final JButton button;
    private boolean border;

    /**
     * Maze wall constructor
     */
    public MazeWall() {
        this.button = createBtn();
    }


    /**
     * returns if the current wall is an Active wall that can't be passed
     * @return boolean of the active wall if it is ture (enable / can't pass) / False can pass.
     */
    public boolean getActive() {
        return active;
    }

    /**
     * Sets if the current wall is an Active wall that can't be passed
     * @param active boolean of the active wall if it is true (enable / can't pass) / False can pass.
     */
    public void setActive(boolean active) {
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

    /**
     * Returns button associated with this wall
     * @return JButton object
     */
    public JButton getButton(){
        return this.button;
    }

    private void setButtonColor(){
        Color unsetColor = Color.white;
        Color activeColor = Color.black;
        Color startingColor = Color.green;
        Color finishingColor = Color.red;

        button.setContentAreaFilled(true);

        if (start) {
            this.button.setBackground(startingColor);
        } else if (finish) {
            this.button.setBackground(finishingColor);
        } else if(border){
            button.setBackground(activeColor);
        } else if(active){
            this.button.setBackground(activeColor);
        }  else {
            this.button.setContentAreaFilled(false);
            this.button.setBackground(unsetColor);
        }
    }

    public void setButtonEnableVisible(boolean buttonState)
    {
        setButtonEnable(buttonState);
        setButtonVissible(buttonState);
    }

    public void setButtonEnable(boolean buttonState)
    {
        button.setEnabled(buttonState);
    }

    public void setButtonVissible(boolean buttonState)
    {
        button.setVisible(buttonState);
    }



    private JButton createBtn () {
        Color unsetColor = Color.white;
        Color activeColor = Color.black;
        Color hoverColor = Color.gray;

        JButton btn = new JButton();

        btn.setFocusPainted(false);
        btn.setRolloverEnabled(false);

        if(border) {
            setButtonColor();
            setBorder();
        }

        if(!border) {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    MazePanel mazePanel = (MazePanel) btn.getParent();

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
                    btn.setBackground(hoverColor);
                    btn.setContentAreaFilled(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    if(border) return;
                    setButtonColor();
                    btn.getParent().repaint();
                }
            });
        }
        return btn;
    }
}