import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.Random;


public class LoadingBar {
    JProgressBar progressBar;
    JFrame frame = new JFrame();


    class Task extends SwingWorker<Void, Void>{
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            while (progress < 100) {
                //Sleep for up to one second.
                try {
                    Thread.sleep(random.nextInt(1000));
                } catch (InterruptedException ignore) {}
                //Make random progress.
                progress += random.nextInt(10);
                setProgress(Math.min(progress, 100));
            }
            return null;
        }

        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
            frame.setCursor(null); //turn off the wait cursor
        }

    }



    LoadingBar(){
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setBounds(0,0,420, 50);
        progressBar.setStringPainted(true);
        frame.add(progressBar);
        frame.setSize(420, 100);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        Task task = new Task();
        task.addPropertyChangeListener(this::propertyChange);
        task.execute();
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
            progressBar.setValue(progress);
        }
    }

    public void setBarValue(int val){
        progressBar.setValue(val);
    }
}
