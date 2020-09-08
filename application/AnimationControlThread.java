package application;

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
        try {
            switch(algorithm) {
                case "Merge Sort":
                    delay(500);
                    MergeSort.mergeSort(array, 0, array.length - 1);
                    break;
                case "Quick Sort":
                    delay(500);
                    QuickSort.sort(array, 0, array.length - 1);
                    break;
            }
        } catch (ThreadTerminationException e) {
            
        } finally {
            running = false;
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
