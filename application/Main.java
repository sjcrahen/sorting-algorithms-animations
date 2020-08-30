package application;

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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Main extends Application {
    
    public static final int N = 100;
    private Label[] array, mergeSortTempArray;
    private Button shuffleButton, sortButton, stopButton;
    private TilePane box;
    private Timeline selSortAnimationController, insSortAnimationController, mergeSortAnimationController;
    private int iSelectionSort, jSelectionSort, minSelectionSort;
    private int iInsertionSort, jInsertionSort;
    private boolean unsortedItemFound;
    private Label itemToInsert;
    private Rectangle rectangle1, rectangle2, rectangle3;
    private int mergeSortSizeIndex, mergeSortFirst;
    private ComboBox<String> comboBox;
    private String algorithm = "";
    
	@Override
	public void start(Stage primaryStage) {
		try {
		    resetSortIndices();
		    
		    // Animation Controllers
		    selSortAnimationController = new Timeline(new KeyFrame(Duration.millis(20), selSortStep));
		    selSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    insSortAnimationController = new Timeline(new KeyFrame(Duration.millis(20), insSortStep));
		    insSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    mergeSortAnimationController = new Timeline(new KeyFrame(Duration.millis(20), mergeSortStep));
		    mergeSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    
		    // TilePane
		    box = getTilePane();
		    
		    // create array
		    array = buildNewLabelArray();
		    mergeSortTempArray = new Label[N];
		    
            // create controls
            comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Choose a Sorting Algorithm","Selection Sort","Insertion Sort","Merge Sort");
            comboBox.getSelectionModel().selectFirst();
            comboBox.setOnAction(e -> setAlgorithm());          
            shuffleButton = new Button("SHUFFLE");
            shuffleButton.setOnAction(e -> shuffle());
            sortButton = new Button("SORT");
            sortButton.setOnAction(e -> playAnimation());
            stopButton = new Button("STOP");
            stopButton.setOnAction(e -> stopAnimation());
		    
            // create buttonBox
		    HBox buttonBox = new HBox();
		    buttonBox.setAlignment(Pos.CENTER);
            HBox.setMargin(shuffleButton, new Insets(20));
            HBox.setMargin(sortButton, new Insets(20));
            HBox.setMargin(comboBox, new Insets(20));
            HBox.setMargin(stopButton, new Insets(20));
		    buttonBox.setPadding(new Insets(20));
		    buttonBox.setMinWidth(640);
		    buttonBox.setMaxWidth(640);
		    buttonBox.getChildren().addAll(comboBox, shuffleButton, sortButton, stopButton);
		    
			FlowPane root = new FlowPane(box, buttonBox);
			FlowPane.setMargin(box, new Insets(20));
            redraw();
			
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

    private void playAnimation() {
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
    
    private void stopAnimation() {
        switch (algorithm) {
            case "Selection Sort":
                selSortAnimationController.stop(); break;
            case "Insertion Sort":
                insSortAnimationController.stop(); break;
            case "Merge Sort":
                mergeSortAnimationController.stop(); break;
            default:
                break;
        }
    }

    private Label[] buildNewLabelArray() {
        Label[] a = new Label[N];
        for (int i = 1; i <= N; i++) {
            Label label = new Label(null, new Rectangle(4, i*2, Color.BLACK));
            label.setMinSize(4, 200);
            label.setMaxSize(4, 200);
            label.setAlignment(Pos.BOTTOM_CENTER);
            a[i-1] = label;
        }
        return a;
    }

    private TilePane getTilePane() {
	    TilePane box = new TilePane();
        box = new TilePane(4,1);
        box.setAlignment(Pos.CENTER);
        box.setMinHeight(420);
        box.setMaxHeight(420);
        box.setMinWidth(860);
        box.setMaxWidth(860);
        return box;
    }
	
	private void shuffle() {
	    array = buildNewLabelArray();
	    shuffle(array);
	}

    private void shuffle(Label[] a) {
        resetSortIndices();
	    for (int i = 0; i < N; i++) {
	        int j = (int)(Math.random()*N);
	        swap(a, i, j);
	    }
	    redraw();
    }
	
	private void redraw() {
	    box.getChildren().clear();
        for (int i = 0; i < N; i++) {
            box.getChildren().add(array[i]);;
        }        
    }

    private void resetSortIndices() {
        iSelectionSort = 0;
        jSelectionSort = 1;
        minSelectionSort = 0;
        iInsertionSort = 1;
        unsortedItemFound = false;
        mergeSortSizeIndex = 1;
        mergeSortFirst = 0;
    }

    private void swap(Label[] a, int i, int j) {
	    Label temp = a[i];
	    a[i] = a[j];
	    a[j] = temp;
	}
	
	private EventHandler<ActionEvent> mergeSortStep = e -> {
	    if (mergeSortSizeIndex > N) {
	        mergeSortAnimationController.stop();
	        mergeSortFirst = 0;
	        mergeSortSizeIndex = 1;
	    }
	    else {
	        mergeSortStep();
	        redraw();
	    }
	};
	
	private void mergeSortStep() {
	    int mid = Math.min(mergeSortFirst + mergeSortSizeIndex-1, N-1);
	    int last = Math.min(mergeSortFirst + 2*mergeSortSizeIndex-1, N-1);
	    merge(array, mergeSortTempArray, mergeSortFirst, mid, last);
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
            rectangle1 = (Rectangle)array[iSelectionSort].getGraphic();
            rectangle1.setFill(Color.RED);
            selSortStep();
        }
    };
    
    private void selSortStep() {
        if (jSelectionSort == N) {
            swap(array, iSelectionSort, minSelectionSort);
            rectangle1.setFill(Color.BLACK);
            rectangle2.setFill(Color.BLACK);
            rectangle3.setFill(Color.BLACK);
            redraw();
            iSelectionSort++;
            minSelectionSort = iSelectionSort;
            jSelectionSort = iSelectionSort+1;
        }
        else {
            if (rectangle2 != null && rectangle2.getFill() != Color.RED) rectangle2.setFill(Color.BLACK);
            rectangle2 = (Rectangle)array[jSelectionSort].getGraphic();
            rectangle3 = (Rectangle)array[minSelectionSort].getGraphic();
            rectangle2.setFill(Color.GRAY);
            if (rectangle2.getHeight() < rectangle3.getHeight()) {
                rectangle2.setFill(Color.RED);
                minSelectionSort = jSelectionSort;
                rectangle3.setFill(Color.BLACK);
            }
            jSelectionSort++;
        }
    }
    
    private EventHandler<ActionEvent> insSortStep = e -> {
        if (iInsertionSort == N) {
            rectangle3 = (Rectangle)array[iInsertionSort-1].getGraphic();
            rectangle3.setFill(Color.GRAY);
            insSortAnimationController.stop();
            iInsertionSort = 1;
            unsortedItemFound = false;
        }
        else {
            insertionSortStep();
        }
    };
    
    private void insertionSortStep() {
        if (unsortedItemFound) {
            if (jInsertionSort > 0 && rectangle2.getHeight() < rectangle3.getHeight()) {
                Label label = new Label(null, rectangle3);
                label.setMinSize(4, 200);
                label.setMaxSize(4, 200);
                label.setAlignment(Pos.BOTTOM_CENTER);
                array[jInsertionSort] = label;
                jInsertionSort--;
                if (jInsertionSort > 0) rectangle3 = (Rectangle)array[jInsertionSort-1].getGraphic();
                redraw();
            } else {
                array[jInsertionSort] = itemToInsert;
                Rectangle r = (Rectangle)itemToInsert.getGraphic();
                r.setFill(Color.GRAY);
                unsortedItemFound = false;
                iInsertionSort++;
                redraw();
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
    
    public static void main(String[] args) {
		launch(args);
	}
}
