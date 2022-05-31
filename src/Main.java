import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class Main implements ActionListener{

    private static Timer windowTimer;
    private static StartupWindow startup;

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            new GUI(new MazeDB());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        windowTimer.stop();
        startup.dispose();
    }

    public Main()
    {
        windowTimer = new Timer(1,this);    // Timer in 3 seconds
        windowTimer.start();
        startup = new StartupWindow();
    }

    public static void main(String[] args){
        new Main();
    }

}
