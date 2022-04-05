import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI{

    public void show_gui() {
        JFrame jframe = new JFrame("Maze Generator");
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Progress bar could be useful for when we load mazes in. Add EventListener and it should work.
        //JProgressBar progressBar = new JProgressBar(0, 100);
        //progressBar.setValue(50);

        //Set frame size
        jframe.setPreferredSize(new Dimension(1000, 800));

        //Menu items top left for exporting
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Export");
        jframe.setJMenuBar(menuBar);
        menu.add("As .png");
        menu.add("As .jpg");
        menuBar.add(menu);

        //This for saving an export
        //JOptionPane.showMessageDialog(null "File Successfully Saved");

        //final JFileChooser fc = new JFileChooser();
        //int returnVal = fc.showOpenDialog(this);
        //if(returnVal==JFileChooser.APPROVE_OPTION){
        //File file = fc.getSelectedFile();
        //String filename = file.getAbsolutePath();
        //} else if(returnVal==JFileChooser.CANCEL_OPTION){
        //}

        //Frame contents - Specify width/height
        JPanel panel = new JPanel();
        JSpinner width_spinner = new JSpinner();
        JSpinner height_spinner = new JSpinner();
        width_spinner.setValue(100);
        height_spinner.setValue(100);
        panel.setBorder(BorderFactory.createTitledBorder("Settings"));
        panel.add(new JLabel("Width: "));
        panel.add(width_spinner );
        panel.add(new JLabel("Height: "));
        panel.add(height_spinner);

        //Frame contents - Buttons
        JButton create = new JButton("Create");
        JButton clear = new JButton("Clear");
        JButton solve = new JButton("Solve");
        create.addActionListener(e -> JOptionPane.showMessageDialog((Component)e.getSource(), "You clicked me"));
        clear.addActionListener(e -> JOptionPane.showMessageDialog((Component)e.getSource(), "You clicked me"));
        solve.addActionListener(e -> JOptionPane.showMessageDialog((Component)e.getSource(), "You clicked me"));
        panel.add(create);
        panel.add(clear);
        panel.add(solve);

        jframe.getContentPane().add(panel);

        jframe.pack();
        jframe.setLocationRelativeTo(null);


        jframe.setVisible(true);
    }
}
