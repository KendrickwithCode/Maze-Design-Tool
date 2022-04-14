import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame{

    public JPanel panel;
    private JPanel borderleft, borderight, bordertop, borderbottom;
    private GUI_Tools menu;
    private GUI_Maze maze;
    private final ImageIcon icon = new ImageIcon("img/TopIcon.png");


    public GUI(){
        initializeFrame();
    }

    private void initializeFrame(){

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLayout(new BorderLayout());


        setIconImage(icon.getImage());

        borderbottom = createPanel(Color.DARK_GRAY);
        bordertop = createPanel(Color.DARK_GRAY);
        borderleft = createPanel(Color.DARK_GRAY);
        borderight = createPanel(Color.DARK_GRAY);
        maze = new GUI_Maze(new Maze(40,40, "blank"));

        setResizable(false);
        menu = new GUI_Tools(borderleft,this); //<-- Call GUI_Tools to set menu items on left side



        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(maze, BorderLayout.CENTER);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);



        setVisible(true);
    }

    public GUI_Maze getMaze() {
        return maze;
    }

    public void setMaze(GUI_Maze maze) {
        this.maze = maze;
    }

    private JPanel createPanel(Color c){
        JPanel temp = new JPanel();
        temp.setBackground(c);
        return temp;
        }
}


