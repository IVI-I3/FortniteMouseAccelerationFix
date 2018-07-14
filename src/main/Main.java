package main;

//This program is written with JavaFX
//For any further question feel free to message me at: dr-evil3@web.de
//Date: 14-07-2018
//© IVI_I3 

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;



public class Main extends Application{
	public static final double windowWidth=350,windowHeight=250,buttonPrefWidth=200,buttonPrefHeight=50;
	private VBox buttonBox;
	private AnchorPane pane;
	private Environment e1;
	private Text text;
	boolean isFound = false;
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Fortnite Mouse Acceleration Fix");
		//Buttons to Choose from
		Button disable = new Button("Disable Mouse Acceleration");
		Button enable = new Button("Enable Mouse Accelration");	
		//Display Status message - Worked / Didn't work
		text = new Text("");
		disable.setPrefWidth(buttonPrefWidth);
		enable.setPrefWidth(buttonPrefWidth);
		disable.setPrefHeight(buttonPrefHeight);
		enable.setPrefHeight(buttonPrefHeight);
		
		e1 = new Environment(primaryStage);
		//EventHandling for Disable Button
		disable.setOnAction(action -> {
			if(!isFound) {
				e1.findAndPatch(false);
				isFound = true;
			}else {
				e1.patch(false, e1.getIniFile());
			}
			refreshStage();
		});
		//EventHandling for Enable Button
		enable.setOnAction(action ->{
			if(!isFound) {
				e1.findAndPatch(true);
				isFound = true;
			}else {
				e1.patch(true, e1.getIniFile());
			}
			refreshStage();
			refreshStage();
		});
		
		
		//Add Button To Vertical Box and build JavaFX GUI
		pane = new AnchorPane();
		buttonBox = new VBox(10);
		buttonBox.getChildren().add(disable);
		buttonBox.getChildren().add(enable);
		
		paint();
		
		primaryStage.setScene(new Scene(pane,windowWidth,windowHeight));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public void refreshStage() {
		text.setText(""+e1.getErrorMessage());
		paint();
	}
	//To refresh the GUI after the user performed some action f.e clicked a button, so the text gets refreshed
	public void paint() {
		pane.getChildren().removeAll(buttonBox,text);
		pane.getChildren().add(buttonBox);
		pane.getChildren().add(text);
		AnchorPane.setRightAnchor(buttonBox, windowWidth/2-buttonPrefWidth/2);
		AnchorPane.setTopAnchor(buttonBox,  windowHeight/2-buttonPrefHeight*2);
		AnchorPane.setRightAnchor(text, buttonPrefWidth/1.3-text.getText().length()*2.5);
		AnchorPane.setBottomAnchor(text,  windowHeight/4.00);
	}
}
