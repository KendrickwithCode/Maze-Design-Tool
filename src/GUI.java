import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame{

    public JPanel panel;
    private JPanel borderleft, borderight, bordertop, borderbottom, center;
    private GUI_Tools menu;


    public GUI(){
        initializeFrame();
    }


    private void initializeFrame(){

        setTitle("Maze Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        GridLayout grid = new GridLayout(3, 4); // <-- to be updated to sizeX, sizeY
        setLayout(new BorderLayout());


        borderbottom = createPanel(Color.DARK_GRAY);
        bordertop = createPanel(Color.DARK_GRAY);
        borderleft = createPanel(Color.DARK_GRAY);
        borderight = createPanel(Color.DARK_GRAY);
        center = createPanel(Color.WHITE);

        menu = new GUI_Tools(borderleft); //<-- Call GUI_Tools to set menu items on left side

        center.setLayout(grid);
        for (int i = 0; i <= 11; i++){
            createBlock();
        }

        this.getContentPane().add(bordertop, BorderLayout.PAGE_START);
        this.getContentPane().add(borderleft, BorderLayout.LINE_START);
        this.getContentPane().add(center, BorderLayout.CENTER);
        this.getContentPane().add(borderbottom, BorderLayout.PAGE_END);
        this.getContentPane().add(borderight, BorderLayout.LINE_END);

        setVisible(true);
    }

    private void createBlock(){
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        Wall topWall2 = createWall(new Dimension(30, 50));
        Wall bottomWall2 = createWall(new Dimension(30, 50));
        Wall leftWall2 = createWall(new Dimension(50, 30));
        Wall rightWall2 = createWall(new Dimension(50, 30));

        panel.add(topWall2, BorderLayout.PAGE_START);
        panel.add(bottomWall2, BorderLayout.PAGE_END);
        panel.add(leftWall2, BorderLayout.LINE_START);
        panel.add(rightWall2, BorderLayout.LINE_END);
        center.add(panel);

    }


    private Wall createWall(Dimension dim){
        Wall temp = new Wall();
        temp.setPreferredSize(dim);
        temp.addActionListener(e -> {
            Wall btn = (Wall) e.getSource();
            if (btn.isOpen()){
                btn.setBackground(Color.WHITE);
            } else {
                btn.setBackground(Color.BLACK);
            }
            btn.setOpen(!btn.isOpen());
        });
        return temp;
    }

    private JPanel createPanel(Color c){
        JPanel temp = new JPanel();
        temp.setBackground(c);
        return temp;
        }
}


