package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//This program is written with JavaFX
//For any further question feel free to message me at: dr-evil3@web.de
//Date: 14-07-2018
//© IVI_I3 

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;



public class Main extends Application{
	public static final double windowWidth=350,windowHeight=350,buttonPrefWidth=300,buttonPrefHeight=50;
	private List<Button> buttons = new ArrayList<>();
	private VBox vBox1;
	private AnchorPane pane;
	private Environment e1;
	private TextArea textArea;
	boolean isFound = false;
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Fortnite Mouse Acceleration Fix");
		//Buttons to Choose from
		Button disable = new Button("Disable Mouse Acceleration");
		Button enable = new Button("Enable Mouse Acceleration");	
		Button check = new Button("Check Mouse Acceleration Status");
		Collections.addAll(buttons,disable,enable,check);
		for(Button b:buttons) {
			b.setPrefWidth(buttonPrefWidth);
			b.setPrefHeight(buttonPrefHeight);
		}
		
		
		//Display Status message - Worked / Didn't work
		textArea = new TextArea("");
		textArea.setPrefHeight(100);
		textArea.setPrefWidth(buttonPrefWidth);
		textArea.setEditable(false);
		
		e1 = new Environment(primaryStage);
		//EventHandling for Disable Button
		disable.setOnAction(action -> {
			e1.find();
			e1.patch(false);
			refreshStage();
		});
		//EventHandling for Enable Button
		enable.setOnAction(action ->{
			e1.find();
			e1.patch(true);
			refreshStage();
		});
		check.setOnAction(action ->{
			e1.find();
			if(e1.getIniFile()==null) {
				e1.setErrorMessage("Could not find .ini File");
			}else {
				if(!e1.checkPatchStatus()) {
					e1.setErrorMessage("MouseAcceleration is Enabled\nFound the .ini File\nunder\n"+e1.getIniFile().getAbsolutePath());
				}else {
					e1.setErrorMessage("MouseAcceleration is Disabled\nFound the .ini File\nunder\n"+e1.getIniFile().getAbsolutePath());
				}
				
			}
			refreshStage();
		});
		
		
		
		
		//Add Button To Vertical Box and build JavaFX GUI
		pane = new AnchorPane();
		vBox1 = new VBox(10);
		vBox1.getChildren().addAll(buttons);
		vBox1.getChildren().add(textArea);
		
		drawGUI();
		
		primaryStage.setScene(new Scene(pane,windowWidth,windowHeight));
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	public void refreshStage() {
		textArea.setText(""+e1.getErrorMessage());
		drawGUI();
	}
	//To refresh the GUI after the user performed some action f.e clicked a button, so the textArea gets refreshed
	public void drawGUI() {
		pane.getChildren().removeAll(vBox1);
		pane.getChildren().add(vBox1);
		AnchorPane.setRightAnchor(vBox1, windowWidth/2-buttonPrefWidth/2);
		AnchorPane.setTopAnchor(vBox1,  windowHeight/2-buttonPrefHeight*buttons.size());
	}
}
