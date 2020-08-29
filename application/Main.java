package application;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    
    public static final int N = 200;
    private Label[] arrayA, arrayB, arrayC, arrayD, mergeSortTempArray;
    private Button shuffleButton, sortButton;
    private TilePane boxA, boxB, boxC, boxD;
    private ArrayList<TilePane> boxes;
    private Timeline selSortAnimationController, insSortAnimationController, mergeSortAnimationController;
    private int iSelectionSort, jSelectionSort, minSelectionSort;
    private int iInsertionSort, jInsertionSort;
    private boolean unsortedItemFound;
    private Label itemToInsert;
    private Rectangle m, n;
    private int mergeSortSizeIndex, mergeSortFirst;
    private ComboBox<String> comboBox;
    private String algorithm = "";
    
	@Override
	public void start(Stage primaryStage) {
		try {
		    comboBox = new ComboBox<>();
		    comboBox.getItems().addAll("Choose a Sorting Algorithm","Selection Sort","Insertion Sort","Merge Sort");
		    comboBox.getSelectionModel().selectFirst();
		    comboBox.setOnAction(e -> setAlgorithm());
		    
		    selSortAnimationController = new Timeline(new KeyFrame(Duration.millis(1), selSortStep));
		    selSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    iSelectionSort = 0;
		    jSelectionSort = 1;
		    minSelectionSort = 0;
		    insSortAnimationController = new Timeline(new KeyFrame(Duration.millis(5), insSortStep));
		    insSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    iInsertionSort = 1;
		    unsortedItemFound = false;
		    mergeSortAnimationController = new Timeline(new KeyFrame(Duration.millis(50), mergeSortStep));
		    mergeSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    mergeSortSizeIndex = 1;
		    mergeSortFirst = 0;
		    
		    boxA = getTilePane();
		    boxB = getTilePane();
		    boxC = getTilePane();
		    boxD = getTilePane();
		    boxes = new ArrayList<>();
		    boxes.add(boxA);
		    boxes.add(boxB);
		    boxes.add(boxC);
		    boxes.add(boxD);
		    
		    arrayA = buildNewLabelArray();
		    arrayB = buildNewLabelArray();
		    arrayC = buildNewLabelArray();
		    arrayD = buildNewLabelArray();
		    mergeSortTempArray = new Label[N];
		    
		    HBox buttonBox = new HBox();
		    buttonBox.setAlignment(Pos.CENTER);
		    shuffleButton = new Button("SHUFFLE");
		    shuffleButton.setOnAction(e -> shuffle());
		    sortButton = new Button("SORT");
		    sortButton.setOnAction(e -> playAnimations());
            HBox.setMargin(shuffleButton, new Insets(20));
            HBox.setMargin(sortButton, new Insets(20));
            HBox.setMargin(comboBox, new Insets(20));
		    buttonBox.setPadding(new Insets(20));
		    buttonBox.setMinWidth(900);
		    buttonBox.setMaxWidth(900);
		    buttonBox.getChildren().addAll(comboBox, shuffleButton, sortButton);
		    
			FlowPane root = new FlowPane(boxA, boxB, boxC, boxD, buttonBox);
			FlowPane.setMargin(boxA, new Insets(20, 10, 10, 20));
			FlowPane.setMargin(boxB, new Insets(20, 20, 10, 10));
            FlowPane.setMargin(boxC, new Insets(10, 10, 20, 20));
            FlowPane.setMargin(boxD, new Insets(10, 20, 20, 10));
            redrawAll();
			
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setAlgorithm() {
        algorithm = comboBox.getValue();
    }

    private void playAnimations() {
        switch (algorithm) {
            case "Selection Sort":
                selSortAnimationController.play(); break;
            case "Insertion Sort":
                insSortAnimationController.play(); break;
            case "Merge Sort":
                mergeSortAnimationController.play(); break;
            default:
                break;
        }
    }

    private Label[] buildNewLabelArray() {
        Label[] a = new Label[N];
        for (int i = 0; i < N; i++) {
            Label label = new Label(null, new Rectangle(1, i, Color.BLACK));
            label.setMinSize(1, 200);
            label.setMaxSize(1, 200);
            label.setAlignment(Pos.BOTTOM_CENTER);
            a[i] = label;
        }
        return a;
    }

    private TilePane getTilePane() {
	    TilePane box = new TilePane();
        box = new TilePane(1,1);
        box.setAlignment(Pos.CENTER);
        box.setMinHeight(220);
        box.setMaxHeight(220);
        box.setMinWidth(420);
        box.setMaxWidth(420);
        box.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
        return box;
    }
	
	private void shuffle() {
	    shuffle(arrayA);
	    shuffle(arrayB);
	    shuffle(arrayC);
	    shuffle(arrayD);
	}

    private void shuffle(Label[] a) {
        resetSortIndices();
	    for (int i = 0; i < N; i++) {
	        int j = (int)(Math.random()*N);
	        swap(a, i, j);
	    }
	    redrawAll();
    }
	
	private void redrawAll() {
        for (TilePane box : boxes) {
            box.getChildren().clear();
        }
        for (int i = 0; i < N; i++) {
            boxA.getChildren().add(arrayA[i]);
            boxB.getChildren().add(arrayB[i]);
            boxC.getChildren().add(arrayC[i]);
            boxD.getChildren().add(arrayD[i]);
        }        
    }

    private void resetSortIndices() {
        iSelectionSort = 0;
        iInsertionSort = 1;
        mergeSortFirst = 0;
        mergeSortSizeIndex = 1;
    }

    private void swap(Label[] a, int i, int j) {
	    Label temp = a[i];
	    a[i] = a[j];
	    a[j] = temp;
	}
	
	private void redraw(TilePane box, Label[] a) {
        box.getChildren().clear();
        for (int i = 0; i < N; i++) {
            box.getChildren().add(a[i]);
        }
	}
	
	private EventHandler<ActionEvent> mergeSortStep = e -> {
	    if (mergeSortSizeIndex > N) {
	        mergeSortAnimationController.stop();
	        mergeSortFirst = 0;
	        mergeSortSizeIndex = 1;
	    }
	    else {
	        mergeSortStep();
	        redraw(boxC, arrayC);
	    }
	};
	
	private void mergeSortStep() {
	    int mid = Math.min(mergeSortFirst + mergeSortSizeIndex-1, N-1);
	    int last = Math.min(mergeSortFirst + 2*mergeSortSizeIndex-1, N-1);
	    merge(arrayC, mergeSortTempArray, mergeSortFirst, mid, last);
	    //adjust indexes
	    if (mergeSortFirst < N - 2*mergeSortSizeIndex) {
	        mergeSortFirst += 2*mergeSortSizeIndex;
	    } else if (mergeSortSizeIndex < N) {
	        mergeSortSizeIndex = 2*mergeSortSizeIndex;
	        mergeSortFirst = 0;
	    }
	}
	
	private void merge(Label[] a, Label[] temp, int first, int mid, int last) {
	      
	    // set index for left half
	    int leftIndex = first;
	    int leftLast = mid;
	    // set index for right half
	    int rightIndex = mid + 1;
	    int rightLast = last;
	    // set index for temp
	    int tempIndex = 0;
	      
	    // fill temp from left and right halves in ascending order
	    while (leftIndex <= leftLast && rightIndex <= rightLast) { // while both halves have remaining entries
	        Rectangle m = (Rectangle)a[leftIndex].getGraphic();
            Rectangle n = (Rectangle)a[rightIndex].getGraphic();
	        if (m.getHeight() <= n.getHeight()) {
	            temp[tempIndex] = a[leftIndex];
	            leftIndex++;
	        }
	        else {
	            temp[tempIndex] = a[rightIndex];
	            rightIndex++;
	        }
	        tempIndex++;
	    }
	      
	    while (leftIndex <= leftLast) { // left half has remaining entries
	        temp[tempIndex] = a[leftIndex];
	        leftIndex++;
	        tempIndex++;
	    }
	      
	    while (rightIndex <= rightLast) { // right half has remaining entries
	        temp[tempIndex] = a[rightIndex];
	        rightIndex++;
	        tempIndex++;
	    }
	      
	    // copy temp into arr
	    for (int i = 0; i <= last - first; i++) {
	        a[first + i] = temp[i];
	    }
    }
    
    private EventHandler<ActionEvent> selSortStep = e -> {
        if (iSelectionSort == N-1) {
            selSortAnimationController.stop();
            iSelectionSort = 0;
            jSelectionSort = 1;
            minSelectionSort = 0;
        }
        else {
            //selSort(arrayA);
            //redraw(boxA, arrayA);
            selSortStep();
        }
    };
	
//    private void selSort(Label[] a) {
//        int minIndex = iSelectionSort;
//        for (int j = iSelectionSort + 1; j < N; j++) {
//            Rectangle m = (Rectangle)a[j].getGraphic();
//            Rectangle n = (Rectangle)a[minIndex].getGraphic();
//            if (m.getHeight() < n.getHeight()) {
//                minIndex = j;
//            }
//        }
//        swap(a, iSelectionSort, minIndex);
//        iSelectionSort++;
//    }
    
    private void selSortStep() {
        if (jSelectionSort == N) {
            swap(arrayA, iSelectionSort, minSelectionSort);
            redraw(boxA, arrayA);
            iSelectionSort++;
            minSelectionSort = iSelectionSort;
            jSelectionSort = iSelectionSort+1;
        }
        else {
            Rectangle m = (Rectangle)arrayA[jSelectionSort].getGraphic();
            Rectangle n = (Rectangle)arrayA[minSelectionSort].getGraphic();
            if (m.getHeight() < n.getHeight()) {
                minSelectionSort = jSelectionSort;
            }
            jSelectionSort++;
        }
    }
    
    private EventHandler<ActionEvent> insSortStep = e -> {
        if (iInsertionSort == N) {
            insSortAnimationController.stop();
            iInsertionSort = 1;
            unsortedItemFound = false;
        }
        else {
//            insSort(arrayB);
//            redraw(boxB, arrayB);
            insertionSortStep();
        }
    };
    
    private void insertionSortStep() {
        if (unsortedItemFound) {
            if (jInsertionSort > 0 && m.getHeight() < n.getHeight()) {
                Label label = new Label(null, n);
                label.setMinSize(1, 200);
                label.setMaxSize(1, 200);
                label.setAlignment(Pos.BOTTOM_CENTER);
                arrayB[jInsertionSort] = label;
                jInsertionSort--;
                if (jInsertionSort > 0) n = (Rectangle)arrayB[jInsertionSort-1].getGraphic();
                redraw(boxB, arrayB);
            } else {
                arrayB[jInsertionSort] = itemToInsert;
                unsortedItemFound = false;
                iInsertionSort++;
                redraw(boxB, arrayB);
            }
        } else {
            m = (Rectangle)arrayB[iInsertionSort].getGraphic();
            n = (Rectangle)arrayB[iInsertionSort-1].getGraphic();
            if (m.getHeight() < n.getHeight()) {
                unsortedItemFound = true;
                itemToInsert = arrayB[iInsertionSort];
                jInsertionSort = iInsertionSort;
            } else {
                iInsertionSort++;
            }
        }
    }
    
    private void insSort(Label[] a) {
        boolean found = false;
        while(!found && iInsertionSort < N) {
            Rectangle m = (Rectangle)a[iInsertionSort].getGraphic();
            Rectangle n = (Rectangle)a[iInsertionSort-1].getGraphic();
            if (m.getHeight() < n.getHeight()) {
                found = true;
                insert(a, iInsertionSort);
                break;
            }
            iInsertionSort++;
        }
    }
    
    private void insert(Label[] a, int itemIndex) {
        
        Label itemToInsert = a[itemIndex];
        int i = itemIndex;
        while (i > 0 && ((Rectangle)itemToInsert.getGraphic()).getHeight() < ((Rectangle)a[i-1].getGraphic()).getHeight()) {
            a[i] = a[i-1];
            i--;
        }
        a[i] = itemToInsert;
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}
