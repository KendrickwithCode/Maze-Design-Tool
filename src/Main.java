import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Entry Point to MazeCraft Editor.
 */
public class Main implements ActionListener{

    private static Timer windowTimer;
    private static StartupWindow startup;

    /**
     * Timer tick event.
     * @param e trigger.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        new GUI();
        windowTimer.stop();
        startup.dispose();
    }

    /**
     * Main class - instantiates from static Main.
     * Begins timer for splash screen. Tick event on timer disposes splash screen and
     * starts GUI (See actionPefromed in main class).
     */
    public Main()
    {
        windowTimer = new Timer(2000,this);    // Timer in 2 seconds
        windowTimer.start();
        startup = new StartupWindow();
    }

    /**
     * Entry Point - instantiates a Main class.
     * @param args system arguments
     */
    public static void main(String[] args){
        new Main();
    }

}
