import javax.swing.*;
import java.awt.*;

public class StartupWindow extends JFrame {

    public StartupWindow()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon startupImage = new ImageIcon("img/StartUp.png");
        JLabel startUpLabel = new JLabel(startupImage);

        JPanel window = new JPanel();
        window.setBackground(Color.ORANGE);

        getContentPane().add(startUpLabel);

        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.setSize(500,350);
        this.setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

}
