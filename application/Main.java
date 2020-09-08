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
    private static Label[] array;
    private Button shuffleButton, sortButton, stopButton;
    private static TilePane box;
    private Timeline selSortAnimationController, insSortAnimationController;
    private ComboBox<String> comboBox;
    private String algorithm;
    private static AnimationControlThread controller;
    
	@Override
	public void start(Stage primaryStage) {
		try {
		    
		    // Animation Controllers
		    selSortAnimationController = new Timeline(new KeyFrame(Duration.millis(30), selSortStep));
		    selSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    insSortAnimationController = new Timeline(new KeyFrame(Duration.millis(30), insSortStep));
		    insSortAnimationController.setCycleCount(Timeline.INDEFINITE);
		    
		    // TilePane
		    box = getTilePane();
		    
		    // create array
		    array = buildNewLabelArray();
		    
            // create controls
            comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Choose a Sorting Algorithm","Selection Sort","Insertion Sort","Merge Sort", "Quick Sort");
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
		    buttonBox.setMinWidth(900);
		    buttonBox.setMaxWidth(900);
		    buttonBox.getChildren().addAll(comboBox, shuffleButton, sortButton, stopButton);
		    
			FlowPane root = new FlowPane(box, buttonBox);
			FlowPane.setMargin(box, new Insets(20));
            drawArray();
			
			Scene scene = new Scene(root, 900, 600);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Sorting Animations");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setAlgorithm() {
        algorithm = comboBox.getValue();
    }

    private void playAnimation() {
        shuffle();
        switch (algorithm) {
            case "Selection Sort":
                selSortAnimationController.play(); break;
            case "Insertion Sort":
                insSortAnimationController.play(); break;
            case "Merge Sort":
                controller = new AnimationControlThread(array, algorithm);
                controller.start();
                break;
            case "Quick Sort":
                controller = new AnimationControlThread(array, algorithm);
                controller.start();
                break;
            default:
                break;
        }
    }
    
    private void stopAnimation() {
        SelectionSort.reset();
        InsertionSort.reset();
        switch (algorithm) {
            case "Selection Sort":
                selSortAnimationController.stop(); break;
            case "Insertion Sort":
                insSortAnimationController.stop(); break;
            case "Merge Sort":
                controller.setRunning(false);
                controller.interrupt();
                break;
            case "Quick Sort":
                controller.setRunning(false);
                controller.interrupt();
                break;
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
    
    private void resetArray() {
        for (int i = 0; i < N; i++) {
            int j = i + 1;
            Rectangle r = new Rectangle(4, j*2, Color.BLACK);
            array[i].setGraphic(r);
        }
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
        resetArray();
        for (int i = 0; i < N; i++) {
            int j = (int)(Math.random()*N);
            swapGraphics(i, j);
        }
    }
    
    private void swapGraphics(int i, int j) {
        Rectangle rectangleI = (Rectangle)array[i].getGraphic();
        Rectangle rectangleJ = (Rectangle)array[j].getGraphic();
        array[i].setGraphic(rectangleJ);
        array[j].setGraphic(rectangleI);
    }
	
	public static void drawArray() {
	    box.getChildren().clear();
        for (int i = 0; i < N; i++) {
            box.getChildren().add(array[i]);
        }
    }
	
    private EventHandler<ActionEvent> selSortStep = e -> SelectionSort.sort(array, N, selSortAnimationController);   
    private EventHandler<ActionEvent> insSortStep = e -> InsertionSort.sort(array, N, insSortAnimationController);
    
    public static void main(String[] args) {
		launch(args);
	}
}
