package application;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class AnimationControlThread extends Thread {
    
    private String algorithm;
    private boolean running;
    private Label[] array;
    
    public AnimationControlThread(Label[] array, String algorithm) {
        this.array = array;
        this.algorithm = algorithm;
        setDaemon(true);
    }
    
    public void run() {
        running = true;
        delay(500);
        try {
            switch(algorithm) {
                case "Selection Sort":
                    SelectionSort.sort(array);
                    break;
                case "Insertion Sort":
                    InsertionSort.sort(array);
                    break;
                case "Merge Sort":
                    MergeSort.sort(array);
                    break;
                case "Quick Sort":
                    QuickSort.sort(array, 0, array.length - 1);
                    break;
            }
        } catch (ThreadTerminationException e) {
            
        } finally {
            running = false;
            Platform.runLater(() -> Main.sortButton.setText("Shuffle & Sort"));
        }
    }
    
    public void delay(int millis) throws ThreadTerminationException {
        if (!running)
            throw new ThreadTerminationException();
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException e) {
        }
        if (!running)
            throw new ThreadTerminationException();
    }
    
    public void setRunning(boolean status) {
        running = status;
    }
    
    public boolean getRunning() {
        return running;
    }

}
