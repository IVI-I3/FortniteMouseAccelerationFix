package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class Environment {
	//user.home = C:/Users/[USERNAME] see this list for more info: https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
	private String userHomeDirectory = System.getProperty("user.home");
	private String osName = System.getProperty("os.name");
	private List<String> fileNamesToLookFor = Arrays.asList("appdata", "local", "fortnitegame", "saved","config", "windowsclient","gameusersettings.ini");
	private List<SearchFile> searchFiles = new ArrayList<>();
	private List<File> filesInFolder = new ArrayList<>();
	private File iniFile;
	private String errorMessage;
	private Stage primaryStage;
	
	public Environment(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	public void findAndPatch(boolean setStatus) {
		errorMessage = "An Unknown Error Occured";
		try {
			if(isWindows()){
				//Initialize Root Node and SearchFiles-> Own Object to look for which stores a File and the Name of the File
				searchFiles.add(new SearchFile(new File(userHomeDirectory),fileNamesToLookFor.get(0)));
					for(int i=1;i<fileNamesToLookFor.size();i++){
						searchFiles.add(new SearchFile(null,fileNamesToLookFor.get(i)));
					}
					
					//Localize Ini.File
					//Lists all Files in the current nodes folder and jumps into the next matching folder 
					/*f.e i=0: searchFile[0]=C/Users/[Username]
					 The program then lists all other files in C/Users/[Username] and looks for the next File which would be Appdata 
					 in this case
					 
					 This keeps on going till the program found the last node which is GameUserSettings.ini
					 */
					for(int i=0;i<searchFiles.size();i++){
						filesInFolder = Arrays.asList(searchFiles.get(i).getFile().listFiles());
						for(File fileInFolder:filesInFolder){
							if(fileInFolder.getName().equalsIgnoreCase(searchFiles.get(i).getLookFor())){
									if(searchFiles.size()>i+1){
										searchFiles.get(i+1).setFile(fileInFolder);
										break;
									}else{
										//The .ini File gets stored as File-Object with the variable name iniFile for later
										iniFile = fileInFolder;
									}
							}
						}
					}	
			}
		} catch(Exception e) {
			openManually();
		}
		
		
		//if somehow the program couldnt locate the ini-File by itself
		//the user has the chance to locate the .ini File for herself/himself
		if(iniFile==null) {
			openManually();
		}else {
			if(iniFile.getName().endsWith(".ini")) {
				patch(setStatus,iniFile);
			}else {
				errorMessage = "This program can only patch ini Files";
			}
		}
		
		
	}
	
	//After locating the .ini File we must now find the bDisableMouseAcceleration setting in the .ini File and change it to the opposite
	public void patch(boolean setStatus,File file) {
		//just to make sure iniFile is located
		if(iniFile==null) {
			openManually();
		}else {
			if(iniFile.getName().endsWith(".ini")) {
				try{
					//Reads the whole .ini File into the String text
						String text = new Scanner(file).useDelimiter("\\A").next();
						if(!text.toLowerCase().contains(""+setStatus)) {
							errorMessage= "MouseAcceleration already turned " + (setStatus ? "On" : "Off"); 
						}else {
							//Changes the bDisableMouseAcceleration setting to the opposite
							//The Substring ensures that the new boolean(true/false) starts with an uppercase letter
							text = text.replaceAll("bDisableMouseAcceleration="+((""+setStatus).substring(0, 1).toUpperCase()+(""+setStatus).substring(1)),
																	"bDisableMouseAcceleration="+((""+!setStatus).substring(0,1).toUpperCase()+(""+!setStatus).substring(1)));
							
							//If the text is corrected we must now write that text back into that .ini File
							BufferedWriter out = new BufferedWriter(new FileWriter(iniFile));
							out.write(text);
							out.close();
							errorMessage = "MouseAcceleration sucessfully turned " + (setStatus ? "On" : "Off");
						}
				} catch(FileNotFoundException e){
					errorMessage = "The Configuration(GameUserSettings.ini) File couldnt be located";
					e.printStackTrace();
				} catch(IOException e){
					errorMessage = "Couldn't write to ini File";
					e.printStackTrace();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}else {
				errorMessage = "This program can only patch ini Files";
			}
		}
		
	}
	// If the file couldnt be found under ...Appdata/Local/ForniteGame... see Variable fileNamesToLookFor
	// The user gets the chance to locate the .ini File with a FileChooser
	private void openManually() {
		try {
		FileChooser fC = new FileChooser();
		fC.setTitle("Open GameUserSettings.ini");
		errorMessage = "Please Open GameUserSettings.ini";
		iniFile = fC.showOpenDialog(primaryStage);
		}catch(Exception e) {
			e.printStackTrace();
			errorMessage = "Please Open GameUserSettings.ini";
		}
	}
	public File getIniFile() {
		return iniFile;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public String getuserHomeDirectory() {
		return userHomeDirectory;
	}

	public void setuserHomeDirectory(String userHomeDirectory) {
		this.userHomeDirectory = userHomeDirectory;
	}

	public boolean isWindows() {
		return osName.toLowerCase().startsWith("windows");
	}
}
