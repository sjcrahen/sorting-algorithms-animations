package application;

import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InsertionSort {
    
    private static int i = 1, j;
    private static boolean unsortedItemFound;
    private static Rectangle rI, rJ;
    
    private InsertionSort() {}
    
    public static void reset() {
        i = 1;
        unsortedItemFound = false;
    }
    
    public static void sort(Label[] array, int arraySize, Timeline animationController) {
        if (i == arraySize) {
            rJ = (Rectangle)array[i-1].getGraphic();
            rJ.setFill(Color.GRAY);
            animationController.stop();
            reset();
        }
        else {
            step(array);
        }
    }
    
    private static void step(Label[] array) {
        if (unsortedItemFound) {
            if (j > 0 && rI.getHeight() < rJ.getHeight()) {
                array[j].setGraphic(rJ);
                j--;
                if (j > 0) rJ = (Rectangle)array[j-1].getGraphic();
            } else {
                array[j].setGraphic(rI);
                rI.setFill(Color.GRAY);
                unsortedItemFound = false;
                i++;
            }
        } else {
            rI = (Rectangle)array[i].getGraphic();
            rJ = (Rectangle)array[i-1].getGraphic();
            if (rI.getHeight() < rJ.getHeight()) {
                unsortedItemFound = true;
                j = i;
            } else {
                rJ.setFill(Color.GRAY);
                i++;
            }
        }
    }

}
