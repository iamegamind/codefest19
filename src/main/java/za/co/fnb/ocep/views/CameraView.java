package za.co.fnb.ocep.views;

import za.co.fnb.pe.framework.api.UIViewContext;

public class CameraView extends MediaView {
	
	@Override
	public void processMessage(UIViewContext context) {
		super.processMessage(context);
	}
	
	@Override
	protected String getFileName() {
		String fileName = "camera.file";
		
		// Do some shit here to make a file name unique if you like
		
		return fileName;
	}
}
