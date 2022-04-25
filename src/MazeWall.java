import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mazes physical walls that are each tied to each block or cell.
 */
public class MazeWall{
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
     * @param active boolean of the active wall if it is ture (enable / can't pass) / False can pass.
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
     * Returns the boolean value if the will is a start wall
     * @return boolean value if the wall is a start wall or not.
     */
    public boolean getStart() {
        return start;
    }


    /**
     * Sets the boolean field if the will is a start wall or not
     * @param start boolean true the wall is a start wall false it is not
     */
    public void setStart(boolean start) {
        this.start = start;
    }


    /**
     * Returns the boolean value if the will is a finish wall
     * @return boolean value if the wall is a finish wall or not.
     */
    public boolean getFinish() {
        return finish;
    }

    /**
     * Sets the boolean field if the will is a finish wall or not
     * @param finish boolean true the wall is a finish wall false it is not
     */
    public void setFinish(boolean finish) {
        this.finish = finish;
    }


    public void setBorder(){
        this.border = true;
        this.button.setContentAreaFilled(true);
        this.button.setBackground(Color.black);
    }

    public boolean getBorder() {
        return border;
    }

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
        btn.setBorder(BorderFactory.createLineBorder(activeColor, 1));


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