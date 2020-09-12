package application;

import javafx.application.Application;
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


public class Main extends Application {
    
    public static final int N = 100;
    private static Label[] array;
    public static Button sortButton;
    private static TilePane box;
    private ComboBox<String> comboBox;
    private String algorithm;
    private static AnimationControlThread controller;
    
	@Override
	public void start(Stage primaryStage) {
		try {
		    box = getTilePane(); // holds array of Label
		    array = buildNewLabelArray();
		    
            // create controls
            comboBox = new ComboBox<>();
            comboBox.getItems().addAll("Choose a Sorting Algorithm","Selection Sort","Insertion Sort","Shell Sort","Merge Sort","Quick Sort");
            comboBox.getSelectionModel().selectFirst();
            comboBox.setOnAction(e -> setAlgorithm()); 
            comboBox.setMinWidth(200);
            comboBox.setMaxWidth(200);
            sortButton = new Button("Shuffle & Sort");
            sortButton.setOnAction(e -> doSortOrStop());
            sortButton.setMinWidth(200);
            sortButton.setMaxWidth(200);
		    
            // create buttonBox
		    HBox buttonBox = new HBox();
		    buttonBox.setAlignment(Pos.CENTER);
            HBox.setMargin(sortButton, new Insets(20));
            HBox.setMargin(comboBox, new Insets(20));
		    buttonBox.setPadding(new Insets(20));
		    buttonBox.setMinWidth(900);
		    buttonBox.setMaxWidth(900);
		    buttonBox.getChildren().addAll(comboBox, sortButton);
		    
		    // container for array box and buttons
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

    private void doSortOrStop() {
        if (controller != null && controller.getRunning() == true) {
            sortButton.setText("Shuffle & Sort");
            controller.setRunning(false);
            controller.interrupt();
        } else {
            shuffle();
            sortButton.setText("Stop Sort");
            controller = new AnimationControlThread(array, algorithm);
            controller.start();
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
    
    private void shuffle() {
        resetArray();
        for (int i = 0; i < N; i++) {
            int j = (int)(Math.random()*N);
            swapGraphics(i, j);
        }
    }
    
    private void resetArray() {
        for (int i = 0; i < N; i++) {
            int j = i + 1;
            Rectangle r = new Rectangle(4, j*2, Color.BLACK);
            array[i].setGraphic(r);
        }
    }
    
    private void swapGraphics(int i, int j) {
        Rectangle recI = (Rectangle)array[i].getGraphic();
        Rectangle recJ = (Rectangle)array[j].getGraphic();
        array[i].setGraphic(recJ);
        array[j].setGraphic(recI);
    }
	
	public static void drawArray() {
	    box.getChildren().clear();
        for (int i = 0; i < N; i++) {
            box.getChildren().add(array[i]);
        }
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}
