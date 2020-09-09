package application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class MergeSort {
    
    private static Label[] temp;
    private static int leftIndex, leftLast, rightIndex, rightLast, tempIndex;
    private static AnimationControlThread thrd;
    
    private MergeSort() {}
    
    public static void sort(Label[] arr) {
        temp = new Label[arr.length];
        syncTempArray(arr);
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(Label[] arr, int first, int last) {
        if (first < last) { // base case - end recursion when first >= last
            int mid = first + (last - first)/ 2; // find mid index
      
            mergeSort(arr, first, mid); // recursively sort right half
            mergeSort(arr, mid + 1, last); // recursively sort left half
      
            merge(arr, first, mid, last); // merge halves in ascending order
            syncTempArray(arr);
        }
    }
    
    public static void merge(Label[] arr, int first, int mid, int last) {
        thrd = (AnimationControlThread)Thread.currentThread();
        leftIndex = first;
        leftLast = mid;
        rightIndex = mid + 1;
        rightLast = last;
        tempIndex = 0;

        while (leftIndex <= leftLast && rightIndex <= rightLast) { // while both halves have remaining entries
            Rectangle rLeft = (Rectangle)temp[leftIndex].getGraphic();
            Rectangle rRight = (Rectangle)temp[rightIndex].getGraphic();
            if (rLeft.getHeight() <= rRight.getHeight()) {
                Platform.runLater(() -> {arr[first + tempIndex].setGraphic(rLeft);});
                thrd.delay(25);
                leftIndex++;
            } else {
                Platform.runLater(() -> {arr[first + tempIndex].setGraphic(rRight);});
                thrd.delay(25);
                rightIndex++;
            }
            tempIndex++;
        }
      
        while (leftIndex <= leftLast) { // left half has remaining entries
            Rectangle rLeft = (Rectangle)temp[leftIndex].getGraphic();
            Platform.runLater(() -> {arr[first + tempIndex].setGraphic(rLeft);});
            thrd.delay(25);
            leftIndex++;
            tempIndex++;
        }
      
        while (rightIndex <= rightLast) { // right half has remaining entries
            Rectangle rRight = (Rectangle)temp[rightIndex].getGraphic();
            Platform.runLater(() -> {arr[first + tempIndex].setGraphic(rRight);});
            thrd.delay(25);
            rightIndex++;
            tempIndex++;
        }
    }
    
    private static void syncTempArray(Label[] arr) {
        for (int i = 0; i < arr.length; i++) {
            Rectangle r = (Rectangle)arr[i].getGraphic();
            Label label = new Label(null, r);
            label.setMinSize(4, 200);
            label.setMaxSize(4, 200);
            label.setAlignment(Pos.BOTTOM_CENTER);
            temp[i] = label;
        }
    }
}