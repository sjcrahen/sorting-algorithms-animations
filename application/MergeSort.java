package application;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class MergeSort {
    
    private static Label[] temp;
    
    private MergeSort() {}
    
    public static void mergeSort(Label[] arr, int first, int last) {
        temp = new Label[arr.length];
        syncTempArray(arr);
        mergeSort(arr, temp, first, last);
    }

    private static void mergeSort(Label[] arr, Label[] temp, int first, int last) {
        if (first < last) { // base case - end recursion when first >= last
            int mid = first + (last - first)/ 2; // find mid index
      
            mergeSort(arr, temp, first, mid); // recursively sort right half
            mergeSort(arr, temp, mid + 1, last); // recursively sort left half
      
            merge(arr, temp, first, mid, last); // merge halves in ascending order
            syncTempArray(arr);
        }
    }
    
    public static void merge(Label[] arr, Label[] temp, int first, int mid, int last) {
      
        int leftIndex = first;
        int leftLast = mid;
        int rightIndex = mid + 1;
        int rightLast = last;
        int tempIndex = 0;

        while (leftIndex <= leftLast && rightIndex <= rightLast) { // while both halves have remaining entries
            Rectangle rLeft = (Rectangle)temp[leftIndex].getGraphic();
            Rectangle rRight = (Rectangle)temp[rightIndex].getGraphic();
            if (rLeft.getHeight() <= rRight.getHeight()) { 
                int i = tempIndex;
                Platform.runLater(() -> {
                    arr[first + i].setGraphic(rLeft);
                });
                AnimationControlThread thrd = (AnimationControlThread)Thread.currentThread();
                thrd.delay(30);
                leftIndex++;
            }
            else {
                int i = tempIndex;
                Platform.runLater(() -> {
                    arr[first + i].setGraphic(rRight);
                });
                AnimationControlThread thrd = (AnimationControlThread)Thread.currentThread();
                thrd.delay(30);
                rightIndex++;
            }
            tempIndex++;
        }
      
        while (leftIndex <= leftLast) { // left half has remaining entries
            Rectangle rLeft = (Rectangle)temp[leftIndex].getGraphic();
            int i = tempIndex;
            Platform.runLater(() -> {
                arr[first + i].setGraphic(rLeft);
            });
            AnimationControlThread thrd = (AnimationControlThread)Thread.currentThread();
            thrd.delay(30);
            leftIndex++;
            tempIndex++;
        }
      
        while (rightIndex <= rightLast) { // right half has remaining entries
            Rectangle rRight = (Rectangle)temp[rightIndex].getGraphic();
            int i = tempIndex;
            Platform.runLater(() -> {
                arr[first + i].setGraphic(rRight);
            });
            AnimationControlThread thrd = (AnimationControlThread)Thread.currentThread();
            thrd.delay(30);
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