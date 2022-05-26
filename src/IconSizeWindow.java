import javax.swing.*;
import java.awt.*;

/**
 * Startup Splash Screen.
 */
public class IconSizeWindow extends JFrame {

    /**
     * Displays application startup window
     */
    public IconSizeWindow()
    {

        JFrame frame = new JFrame("Icons");
        JLabel emptyLabel = new JLabel("asdasdasdsa");

        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(500,350);
        frame.setVisible(true);


        //        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        JFrame window = new JFrame("Icon Sizing");
//
//        JLabel startUpLabel = new JLabel("asdsadsadasd");
//        window.add(startUpLabel);
//
//        window.setUndecorated(true);
//        window.setAlwaysOnTop(true);
//        window.setSize(500,350);
//        window.setLocationRelativeTo(null);
//        window.pack();
//        window.setVisible(true);
    }

}
