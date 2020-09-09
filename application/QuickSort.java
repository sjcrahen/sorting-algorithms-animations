package application;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class QuickSort {
    
    private static int insertIndex;
    private static Rectangle rToLeft;
    private static AnimationControlThread thrd;
    
    private QuickSort() {}
    
    public static void sort(Label[] a, int first, int last) {
        if (last - first + 1 < 10) {
            insSortRecursive(a, first, last);
        }
        else {
            int pivotIndex = partition(a, first, last);
            sort(a, first, pivotIndex - 1);
            sort(a, pivotIndex + 1, last);
        }
    }
      
    private static int partition(Label[] a, int first, int last) {
        int mid = (last + first) / 2;
        Rectangle rFirst = (Rectangle)a[first].getGraphic();
        Rectangle rMid = (Rectangle)a[mid].getGraphic();
        Rectangle rLast = (Rectangle)a[last].getGraphic();
        if (rMid.getHeight() < rFirst.getHeight())
            swap(a, mid, first, rMid, rFirst);
        rFirst = (Rectangle)a[first].getGraphic();
        rMid = (Rectangle)a[mid].getGraphic();        
        if (rLast.getHeight() < rMid.getHeight()) {
            swap(a, last, mid, rLast, rMid);
            rMid = (Rectangle)a[mid].getGraphic();
            rLast = (Rectangle)a[last].getGraphic();
            if (rMid.getHeight() < rFirst.getHeight()) {
                swap(a, mid, first, rMid, rFirst);
                rFirst = (Rectangle)a[first].getGraphic();
                rMid = (Rectangle)a[mid].getGraphic();
            }
        }
        Rectangle rSecondLast = (Rectangle)a[last-1].getGraphic();
        swap(a, mid, last - 1, rMid, rSecondLast); // move pivot to last - 1
        
        int pivotIndex = last - 1;
        int i = first + 1;
        int j = pivotIndex - 1;
        Rectangle rPivot = (Rectangle)a[pivotIndex].getGraphic();
        Rectangle rLT = (Rectangle)a[i].getGraphic();
        Rectangle rGT = (Rectangle)a[j].getGraphic();

        while (i < last) {
            while (rLT.getHeight() < rPivot.getHeight()) {
                i++;
                rLT = (Rectangle)a[i].getGraphic(); 
            }
            while (rGT.getHeight() > rPivot.getHeight()) {
                j--;
                rGT = (Rectangle)a[j].getGraphic();
            }
            if (i < j) {
                swap(a, i, j, rLT, rGT);
                i++;
                j--;
                rLT = (Rectangle)a[i].getGraphic();
                rGT = (Rectangle)a[j].getGraphic();
            }
            else
                break;
        }
        swap(a, pivotIndex, i, rPivot, rLT);
        pivotIndex = i;
        return pivotIndex;
    }
      
    public static void insSortRecursive(Label[] a, int first, int last) {
        if (first < last) {
            insSortRecursive(a, first, last - 1);
            insert(a, last);
        }
    }
      
    private static void swap(Label[] a, int i, int j, Rectangle rI, Rectangle rJ) {
        thrd = (AnimationControlThread)Thread.currentThread();
        Platform.runLater(() -> {
            a[i].setGraphic(rJ);
            a[j].setGraphic(rI);});
        thrd.delay(25);
    }
      
    private static void insert(Label[] a, int itemIndex) {
        thrd = (AnimationControlThread)Thread.currentThread();
        Rectangle itemToInsert = (Rectangle)a[itemIndex].getGraphic();
        insertIndex = itemIndex;
        rToLeft = (Rectangle)a[insertIndex - 1].getGraphic();
        while (insertIndex > 0 && itemToInsert.getHeight() < rToLeft.getHeight()) {
            Platform.runLater(() -> {
                a[insertIndex].setGraphic(rToLeft);
            });
            thrd.delay(25);
            insertIndex--;
            if (insertIndex > 0) rToLeft = (Rectangle)a[insertIndex - 1].getGraphic();
        }
        Platform.runLater(() -> {a[insertIndex].setGraphic(itemToInsert);});
        thrd.delay(25);
    }
    
}
