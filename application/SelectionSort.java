package application;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionSort {
    
    private static int i, min, j = 1;
    private static Rectangle r1, r2, r3;
    
    private SelectionSort() {}
    
    public static void reset() {
        i = 0;
        j = 1;
        min = 0;
    }
    
    public static void sort(Label[] array, int arraySize, Timeline animationController) {
        if (i == arraySize - 1) {
            animationController.stop();
            reset();
        }
        else {
            r1 = (Rectangle)array[i].getGraphic();
            r1.setFill(Color.RED);
            step(array, arraySize);
        }
    }    
    
    private static void step(Label[] array, int arraySize) {
        if (j == arraySize) {
            swap(array, i, min);
            r1.setFill(Color.BLACK);
            r2.setFill(Color.BLACK);
            r3.setFill(Color.BLACK);
            Main.redraw();
            i++;
            min = i;
            j = i+1;
        }
        else {
            if (r2 != null && r2.getFill() != Color.RED) r2.setFill(Color.BLACK);
            r2 = (Rectangle)array[j].getGraphic();
            r3 = (Rectangle)array[min].getGraphic();
            r2.setFill(Color.GRAY);
            if (r2.getHeight() < r3.getHeight()) {
                r2.setFill(Color.RED);
                min = j;
                r3.setFill(Color.BLACK);
            }
            j++;
        }
    }
    
    private static void swap(Label[] a, int i, int j) {
        Label temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}
