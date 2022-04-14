import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener{

    private static Timer windowTimer;
    private static StartupWindow startup;

    @Override
    public void actionPerformed(ActionEvent e) {
        new GUI();
        windowTimer.stop();
        startup.dispose();
    }

    public Main()
    {
        windowTimer = new Timer(3000,this);    // Timer in 3 seconds
        windowTimer.start();
        startup = new StartupWindow();
    }

    public static void main(String[] args){
        new Main();
    }

}
