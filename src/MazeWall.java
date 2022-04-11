import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MazeWall{
    public boolean active;
    private boolean start;
    private boolean finish;
    private JButton button;
    private boolean border;


    public MazeWall() {
        this.button = createBtn();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public void setBorder(){
        this.border = true;
        this.button.setContentAreaFilled(true);
        this.button.setBackground(Color.black);
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


        if(border == true) {
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