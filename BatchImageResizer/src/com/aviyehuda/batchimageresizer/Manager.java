package com.aviyehuda.batchimageresizer;

import java.io.File;

import org.apache.pivot.wtk.DesktopApplicationContext;

import com.aviyehuda.batchimageresizer.ui.MainWindow;

public class Manager {
	public static void main(String[] args) {
		DesktopApplicationContext.main(MainWindow.class, args);
	}

	public static void batchResize(String inputFolder, String outputFolder, int percentage) {
		
		File dir = new File(inputFolder);
		File [] files = dir.listFiles();
        
        for (File file : files) {
        	if(isImage(file)){
        	    Image m1 = new Image(file);
	            m1.resize(percentage);
	            m1.saveAs(outputFolder+"/"+file.getName());
        	}
        }
		
	}

	private static boolean isImage(File file) {
		String fileName = file.getName();
		return fileName.endsWith(".png")|| 
		fileName.endsWith(".gif")|| 
		fileName.endsWith(".jpg")|| 
		fileName.endsWith(".bmp")|| 
		fileName.endsWith(".tif")||
		fileName.endsWith(".PNG")|| 
        fileName.endsWith(".GIF")|| 
        fileName.endsWith(".JPG")|| 
        fileName.endsWith(".BMP")|| 
        fileName.endsWith(".TIF");
	}

}
