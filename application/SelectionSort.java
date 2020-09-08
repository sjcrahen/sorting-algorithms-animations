package application;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SelectionSort {
    
    private static int i, min, j = 1;
    private static Rectangle rI, rJ, rMin;
    
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
            rI = (Rectangle)array[i].getGraphic();
            rI.setFill(Color.RED);
            step(array, arraySize);
        }
    }    
    
    private static void step(Label[] array, int arraySize) {
        if (j == arraySize) {
            array[i].setGraphic(rMin);
            array[min].setGraphic(rI);
            rI.setFill(Color.BLACK);
            rJ.setFill(Color.BLACK);
            rMin.setFill(Color.BLACK);
            i++;
            min = i;
            j = i+1;
        }
        else {
            if (rJ != null && rJ.getFill() != Color.RED) rJ.setFill(Color.BLACK);
            rJ = (Rectangle)array[j].getGraphic();
            rMin = (Rectangle)array[min].getGraphic();
            rJ.setFill(Color.GRAY);
            if (rJ.getHeight() < rMin.getHeight()) {
                rJ.setFill(Color.RED);
                min = j;
                rMin.setFill(Color.BLACK);
            }
            j++;
        }
    }
}
