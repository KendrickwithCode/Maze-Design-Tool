import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartupWindow extends JFrame  implements ActionListener {

    private final Timer windowTimer;

    @Override
    public void actionPerformed(ActionEvent e) {

        new GUI();
        windowTimer.stop();
        this.dispose();

    }

    public StartupWindow()
    {
        windowTimer = new Timer(3000, this);    // Timer in 3 seconds
        windowTimer.start();

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
