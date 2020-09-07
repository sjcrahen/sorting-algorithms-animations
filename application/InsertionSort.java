package application;

import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class InsertionSort {
    
    private static int iInsertionSort = 1, jInsertionSort;
    private static boolean unsortedItemFound;
    private static Label itemToInsert;
    private static Rectangle rectangle2, rectangle3;
    
    private InsertionSort() {}
    
    public static void reset() {
        iInsertionSort = 1;
        unsortedItemFound = false;
    }
    
    public static void sort(Label[] array, int arraySize, Timeline animationController) {
        if (iInsertionSort == arraySize) {
            rectangle3 = (Rectangle)array[iInsertionSort-1].getGraphic();
            rectangle3.setFill(Color.GRAY);
            animationController.stop();
            reset();
        }
        else {
            step(array);
        }
    }
    
    private static void step(Label[] array) {
        if (unsortedItemFound) {
            if (jInsertionSort > 0 && rectangle2.getHeight() < rectangle3.getHeight()) {
                Label label = new Label(null, rectangle3);
                label.setMinSize(4, 200);
                label.setMaxSize(4, 200);
                label.setAlignment(Pos.BOTTOM_CENTER);
                array[jInsertionSort] = label;
                jInsertionSort--;
                if (jInsertionSort > 0) rectangle3 = (Rectangle)array[jInsertionSort-1].getGraphic();
                Main.redraw();
            } else {
                array[jInsertionSort] = itemToInsert;
                Rectangle r = (Rectangle)itemToInsert.getGraphic();
                r.setFill(Color.GRAY);
                unsortedItemFound = false;
                iInsertionSort++;
                Main.redraw();
            }
        } else {
            rectangle2 = (Rectangle)array[iInsertionSort].getGraphic();
            rectangle3 = (Rectangle)array[iInsertionSort-1].getGraphic();
            if (rectangle2.getHeight() < rectangle3.getHeight()) {
                unsortedItemFound = true;
                itemToInsert = array[iInsertionSort];
                jInsertionSort = iInsertionSort;
            } else {
                rectangle3.setFill(Color.GRAY);
                iInsertionSort++;
            }
        }
    }

}
