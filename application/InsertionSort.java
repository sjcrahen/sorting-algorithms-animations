package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class InsertionSort {
    
    private static int insertIndex;
    private static Rectangle rI, rJ, rToLeft;
    private static AnimationControlThread thrd;
    
    private InsertionSort() {}
    
    public static void sort(Label[] a) {
        for (int i = 1; i < a.length; i++) {
            rI = (Rectangle)a[i].getGraphic();
            rJ = (Rectangle)a[i-1].getGraphic();
            if (rI.getHeight() < rJ.getHeight()) {
                insert(a, i);
            }
        }
    }
    
    private static void insert(Label[] a, int itemIndex) {
        thrd = (AnimationControlThread)Thread.currentThread();
        Rectangle itemToInsert = (Rectangle)a[itemIndex].getGraphic();
        insertIndex = itemIndex;
        rToLeft = (Rectangle)a[insertIndex - 1].getGraphic();
        while (insertIndex > 0 && itemToInsert.getHeight() < rToLeft.getHeight()) {
            Platform.runLater(() -> {a[insertIndex].setGraphic(rToLeft);});
            thrd.delay(20);
            insertIndex--;
            if (insertIndex > 0) rToLeft = (Rectangle)a[insertIndex - 1].getGraphic();
        }
        Platform.runLater(() -> {a[insertIndex].setGraphic(itemToInsert);});
        thrd.delay(20);
    }
}
