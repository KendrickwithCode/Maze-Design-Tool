import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

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
        //Creates DB
        Connection connection = DBConnection.getInstance();
        windowTimer = new Timer(1,this);    // Timer in 3 seconds
        windowTimer.start();
        startup = new StartupWindow();
    }

    public static void main(String[] args){
        new Main();
    }

}
