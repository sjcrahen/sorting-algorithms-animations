package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class ShellSort {
    
    private static int insertIndex;
    private static Rectangle recToInsert, recToLeft;
    private static AnimationControlThread thrd;
    
    private ShellSort() {}

    public static void sort(Label[] a) {
        // loop through increments for sub arrays
        for (int incr = a.length / 2; incr > 2; incr /= 2) {
          if (incr % 2 == 0) // if even add 1
            incr++;
          for (int i = 0; i < incr; i++) // sort sub array created by increment
            subArrayInsert(a, i, incr);
        }
        subArrayInsert(a, 0, 1); // make final pass to sort whole array which is almost entirely pre-sorted
      }
    
    private static void subArrayInsert(Label[] a, int first, int incr) {
        thrd = (AnimationControlThread)Thread.currentThread();
        // iterate through sub array created with incr
        for (int i = first + incr; i < a.length; i += incr) {
            insertIndex = i;
            recToInsert = (Rectangle)a[i].getGraphic();
            recToLeft = (Rectangle)a[insertIndex - incr].getGraphic();
            while (insertIndex >= incr && recToInsert.getHeight() < recToLeft.getHeight()) { // compare itemToInsert with previous in sub array
                Platform.runLater(() -> {
                    a[insertIndex].setGraphic(recToLeft);}); // make room if necessary
                thrd.delay(25);
                insertIndex -= incr;
                if (insertIndex >= incr) recToLeft = (Rectangle)a[insertIndex - incr].getGraphic();
            }
            Platform.runLater(() -> {a[insertIndex].setGraphic(recToInsert);});
            thrd.delay(25);
        }
    }
}
