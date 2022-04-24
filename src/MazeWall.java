import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeWall{
    public boolean active;
    private boolean start;
    private boolean finish;
    private final JButton button;
    private JButton btn;
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

        Color activeColor = Color.black;
        Color unsetColor = Color.white;
        if(active){
            this.button.setBackground(activeColor);
            this.button.setContentAreaFilled(true);
        } else {
            this.button.setContentAreaFilled(false);
            this.button.setBackground(unsetColor);
        }

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
    }

    /**
     * Sets wall GUI and variables if wall is a border
     */
    public void setBorder(){
        this.border = true;
        this.button.setContentAreaFilled(true);
        this.button.setBackground(Color.black);
    }

    /**
     * Returns button associated with this wall
     * @return JButton object
     */
    public JButton getButton(){
        return this.button;
    }

    private JButton createBtn () {
        Color unsetColor = Color.white;
        Color activeColor = Color.black;
        Color hoverColor = Color.gray;

        JButton btn = new JButton();
        btn.setFocusPainted(false);
        btn.setRolloverEnabled(false);


        if(border) {
            btn.setContentAreaFilled(true);
            btn.setBackground(activeColor);
        } else {
            btn.setBackground(unsetColor);
        }


        if(!border) {
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    Object src = e.getSource();
                    if(border) return;

                    if (!active){
                        btn.setBackground(activeColor);
                        btn.setContentAreaFilled(true);
                    } else {
                        btn.setContentAreaFilled(false);
                        btn.setBackground(unsetColor);
                    }
                    setActive(!active);
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
                    if (!active){
                        btn.setBackground(unsetColor);
                        btn.setContentAreaFilled(false);
                    } else {
                        btn.setContentAreaFilled(true);
                        btn.setBackground(activeColor);
                    }
                }
            });
        }
        return btn;
    }
}