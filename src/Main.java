import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main{

    private static void Run()
    {
        StartupWindow startup = new StartupWindow();

        Timer timer = new Timer(3000, e -> {
            new GUI();
            startup.dispose();
        });

        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Entry Point - instantiates a Main class.
     * @param args system arguments
     */
    public static void main(String[] args){
        Run();
    }

}