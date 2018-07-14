package main;

import java.io.File;

public class SearchFile {
private File file;
private String lookFor;
public SearchFile(String lookFor) {
	this.lookFor = lookFor;
}
public SearchFile(File file, String lookFor) {
	this.file = file;
	this.lookFor = lookFor;
}
@Override
public String toString() {
	return "SearchFile [file=" + file + ", lookFor=" + lookFor + "]";
}
public File getFile() {
	return file;
}
public void setFile(File file) {
	this.file = file;
}
public String getLookFor() {
	return lookFor;
}
public void setLookFor(String lookFor) {
	this.lookFor = lookFor;
}
}
