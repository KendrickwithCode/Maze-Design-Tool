import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    public JPanel panel;
    private JPanel borderleft, borderight, bordertop, borderbottom;
    private GUI_Tools menu;
    private GUI_Maze maze;


    public GUI(){
        initializeFrame();
    }


    private void initializeFrame(){

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLayout(new BorderLayout());


        borderbottom = createPanel(Color.DARK_GRAY);
        bordertop = createPanel(Color.DARK_GRAY);
        borderleft = createPanel(Color.DARK_GRAY);
        borderight = createPanel(Color.DARK_GRAY);
        maze = new GUI_Maze(10, 10);

        setResizable(false);
        menu = new GUI_Tools(borderleft); //<-- Call GUI_Tools to set menu items on left side



        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(maze, BorderLayout.CENTER);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);

        setVisible(true);
    }


    private JPanel createPanel(Color c){
        JPanel temp = new JPanel();
        temp.setBackground(c);
        return temp;
        }
}


