package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionSort {
    
    private static int i, minIndex;
    private static Rectangle recI, recJ, recMin;
    private static AnimationControlThread thrd;
    
    private SelectionSort() {}
    
    public static void sort(Label[] a) {
        thrd = (AnimationControlThread)Thread.currentThread();

        for (i = 0; i < a.length - 1; i++) {
            minIndex = i;
            recI = (Rectangle)a[i].getGraphic();
            recMin = (Rectangle)a[minIndex].getGraphic();
            for (int j = i + 1; j < a.length; j++) {
                recJ = (Rectangle)a[j].getGraphic();
                Platform.runLater(() -> {recJ.setFill(Color.GRAY);});
                thrd.delay(20);
                if (recJ.getHeight() < recMin.getHeight()) {
                    Platform.runLater(() -> {
                        recJ.setFill(Color.RED);
                        recMin.setFill(Color.BLACK);});
                    thrd.delay(20);
                    minIndex = j;
                    recMin = (Rectangle)a[minIndex].getGraphic();
                } else {
                    Platform.runLater(() -> {recJ.setFill(Color.BLACK);});
                    thrd.delay(20);
                }
            }
            if (i == minIndex) continue;
            swap(a, i, minIndex, recI, recMin);
        }
    }
    
    private static void swap(Label[] a, int i, int j, Rectangle rI, Rectangle rJ) {
        thrd = (AnimationControlThread)Thread.currentThread();
        Platform.runLater(() -> {
            a[i].setGraphic(rJ);
            a[j].setGraphic(rI);;
            rJ.setFill(Color.BLACK);});
        thrd.delay(20);
    }
}
