package application;

import java.util.Arrays;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;

public class MergeSort {
    
    private static Label[] tempArray;
    private static int chunkSize = 1, first, leftIndex, leftLast, rightIndex, rightLast, i, last;
    private static boolean mergeComplete, firstPass = true;
    
    private MergeSort() {}
    
    public static void reset() {
        chunkSize = 1;
        first = 0;
        mergeComplete = false;
        firstPass = true;
    }
    
    public static void sort(Label[] array, int arraySize, Timeline animationController) {
        if (chunkSize > arraySize) {
            animationController.stop();
            reset();
        }
        else {
            step(array, arraySize);
        }
    }

    private static void step(Label[] array, int arraySize) {
        if (firstPass) {
            tempArray = Arrays.copyOf(array, arraySize);
            leftIndex = first;
            leftLast = Math.min(first + chunkSize-1, arraySize-1);
            last = Math.min(first+2*chunkSize-1, arraySize-1);
            rightIndex = leftLast+1;
            rightLast = last;
            i = 0;
            fillBlank(array);
            firstPass = false;
        }

        if (mergeComplete) {
            tempArray = Arrays.copyOf(array, arraySize);
            leftIndex = first;
            leftLast = Math.min(first + chunkSize-1, arraySize-1);
            last = Math.min(first+2*chunkSize-1, arraySize-1);
            rightIndex = leftLast+1;
            rightLast = last;
            i = 0;
            fillBlank(array);
            mergeComplete = false;
        }

        merge(array);
        
        if (mergeComplete) {
            if (first < arraySize - 2*chunkSize) {
                first += 2*chunkSize;
            } else if (chunkSize < arraySize) {
                chunkSize = 2*chunkSize;
                first = 0;
            }
        }
    }
    
    private static void merge(Label[] array) {
        // fill temp from left and right halves in ascending order
        if (leftIndex <= leftLast && rightIndex <= rightLast) { // while both halves have remaining entries
            Rectangle m = (Rectangle)tempArray[leftIndex].getGraphic();
            Rectangle n = (Rectangle)tempArray[rightIndex].getGraphic();
            if (m.getHeight() <= n.getHeight()) {
                array[first+i] = tempArray[leftIndex];
                leftIndex++;
            }
            else {
                array[first+i] = tempArray[rightIndex];
                rightIndex++;
            }
            i++;
        } else if (leftIndex <= leftLast) { // left half has remaining entries
            array[first+i] = tempArray[leftIndex];
            leftIndex++;
            i++;
        } else if (rightIndex <= rightLast) { // right half has remaining entries
            array[first+i] = tempArray[rightIndex];
            rightIndex++;
            i++;
        }
        Main.redraw();
        if (leftIndex > leftLast && rightIndex > rightLast) {
            mergeComplete = true;
        }
    }
    
    private static void fillBlank(Label[] array) {
        for (int i = first; i <= last; i++) {
            Label label = new Label(null, new Rectangle(4, 0));
            label.setMinSize(4, 200);
            label.setMaxSize(4, 200);
            label.setAlignment(Pos.BOTTOM_CENTER);
            array[i] = label;
        }
        Main.redraw();
    }
}
