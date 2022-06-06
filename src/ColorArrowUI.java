import javax.swing.*;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;


/**
 * based from on Code found from web address = www.javased.com/?post=3008447
 */
public class ColorArrowUI extends BasicComboBoxUI {

    public static ComboBoxUI createUI(JComponent c) {
        return new ColorArrowUI();
    }

    @Override protected JButton createArrowButton() {
        Color buttonColor = new Color (155, 155, 155);
        return new BasicArrowButton(


                BasicArrowButton.SOUTH,
                buttonColor, Color.black,
                Color.black, Color.darkGray);
    }
}