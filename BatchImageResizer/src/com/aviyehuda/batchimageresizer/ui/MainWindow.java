package com.aviyehuda.batchimageresizer.ui;

import java.io.File;

import org.apache.pivot.collections.Map;
import org.apache.pivot.collections.Sequence;
import org.apache.pivot.wtk.Alert;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.ButtonPressListener;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.FileBrowserSheet;
import org.apache.pivot.wtk.MessageType;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Sheet;
import org.apache.pivot.wtk.SheetCloseListener;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtkx.WTKX;
import org.apache.pivot.wtkx.WTKXSerializer;

import com.aviyehuda.batchimageresizer.Manager;

public class MainWindow implements Application {
    private Window window = null;

    @WTKX private PushButton browseButtonIn = null;
    @WTKX private PushButton browseButtonOut = null;
    @WTKX private TextInput inputFolder = null;
    @WTKX private TextInput outputFolder = null;
    @WTKX private PushButton resizeButton = null;
    @WTKX private TextInput sizePercentage = null;
    //@WTKX private Checkbox foregroundCB = null; 

    @Override
    public void startup(Display display, Map<String, String> properties)
        throws Exception {
    	
        WTKXSerializer wtkxSerializer = new WTKXSerializer();

        window = (Window)wtkxSerializer.readObject(getClass().getResource("window.wtkx"));
      
        wtkxSerializer.bind(this, MainWindow.class);

      
        browseButtonIn.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {

                final FileBrowserSheet fileBrowserSheet =
                    new FileBrowserSheet(FileBrowserSheet.Mode.SAVE_TO);
                
                
                //TODO: add file filter
                
                if(inputFolder != null && inputFolder.getText().length() > 0 &&
                		inputFolder.getText().indexOf("\\") >0  ){
                    try{
                        String text = inputFolder.getText();
                        fileBrowserSheet.setRootDirectory(new File(text));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                ;
                
                fileBrowserSheet.open(window, new SheetCloseListener() {
                    @Override
                    public void sheetClosed(Sheet sheet) {
                        if (sheet.getResult()) {
                            Sequence<File> selectedFiles = fileBrowserSheet.getSelectedFiles();

                            if(selectedFiles.getLength() > 0){
                            	
                            	File folder = selectedFiles.get(0);
                            	if(folder.isDirectory()){
	                            	String fileName = folder.getAbsoluteFile().toString();
	                            	inputFolder.setText(fileName);
                            	}
                            	else{
                            		Alert.alert(MessageType.ERROR, "Please select a folder", window);
                            	}
                            	
                            }
                        } 
                        
                        
                        
                    }
                });
                
            }
        });

        
        browseButtonOut.getButtonPressListeners().add(new ButtonPressListener() {
            @Override
            public void buttonPressed(Button button) {

                final FileBrowserSheet fileBrowserSheet =
                    new FileBrowserSheet(FileBrowserSheet.Mode.SAVE_TO);
                
                fileBrowserSheet.open(window, new SheetCloseListener() {
                    @Override
                    public void sheetClosed(Sheet sheet) {
                    	
                        if (sheet.getResult()) {
                            Sequence<File> selectedFiles = fileBrowserSheet.getSelectedFiles();

	                            if(selectedFiles.getLength() > 0){
	                            	
	                            	File folder = selectedFiles.get(0);
	                            	if(folder.isDirectory()){
		                            	String fileName = folder.getAbsoluteFile().toString();
		                            	outputFolder.setText(fileName);
	                            	}
	                            	else{
	                            		Alert.alert(MessageType.ERROR, "Please select a folder", window);
	                            	}
	                            	
	                            }
                            }
                        } 
                        
                });
            }
        });
        
        
        resizeButton.getButtonPressListeners().add(new ButtonPressListener() {
			
			@Override
			public void buttonPressed(Button arg0) {
			    
				if(inputFolder.getText() == null || inputFolder.getText().length() == 0  ){
			        Alert.alert(MessageType.ERROR, "Please insert input location.", window);
			        return;
			    }
				
				if(sizePercentage.getText() == null || sizePercentage.getText().length() == 0  ){
			        Alert.alert(MessageType.ERROR, "Please insert size percentage.", window);
			        return;
			    }
				
				if(outputFolder.getText() == null || outputFolder.getText().length() == 0  ){
			        Alert.alert(MessageType.ERROR, "Please insert output location.", window);
			        return;
			    }
				
				int percentage = 0;
				
				try{
					percentage = Integer.parseInt(sizePercentage.getText());
				}
				catch (Exception e) {
					Alert.alert(MessageType.ERROR, "Invalid size percentage value.", window);
			        return;
				}
				
				Manager.batchResize(inputFolder.getText(),
						outputFolder.getText(),
						percentage);

				Alert.alert(MessageType.INFO, "Finished sucessfully", window);
				
			}
		});
        
        window.open(display);
    }

    @Override
    public boolean shutdown(boolean optional) {
        if (window != null) {
            window.close();
        }

        return false;
    }

    @Override
    public void suspend() {
    }

    @Override
    public void resume() {
    }
}

