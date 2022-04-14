import javax.swing.*;
import java.awt.*;

public class StartupWindow extends JFrame {

    public StartupWindow()
    {

        ImageIcon icon = new ImageIcon("img/TopIcon.png");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon startupImage = new ImageIcon("img/StartUp.png");
        JLabel startUpLabel = new JLabel(startupImage);

        JPanel window = new JPanel();
        window.setBackground(Color.ORANGE);

        getContentPane().add(startUpLabel);

        this.setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

}
