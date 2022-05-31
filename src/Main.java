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
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.INFORMATION_MESSAGE);
        }
        windowTimer.stop();
        startup.dispose();
    }

    public Main()
    {
        windowTimer = new Timer(1500,this);    // Timer in 1.5 seconds (1500)
        windowTimer.start();
        startup = new StartupWindow();
    }

    public static void main(String[] args){
        new Main();
    }

}
