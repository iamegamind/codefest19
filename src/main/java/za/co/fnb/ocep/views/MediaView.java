package za.co.fnb.ocep.views;

import za.co.fnb.ocep.cache.SessionContext;
import za.co.fnb.pe.framework.api.UIView;
import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.template.phone.OCEPCameraViewTemplate;
import za.co.fnb.pe.framework.template.phone.OCEPFilePickerViewTemplate;
import za.co.fnb.pe.framework.template.phone.OCEPGalleryViewTemplate;

public class MediaView implements UIView {

	@Override
	public void processMessage(UIViewContext context) {
		Object viewObject = context.getUI();

		// Add the file name to the sessionCache
		((SessionContext) context.getSessionContext()).setMediaFileName(this.getFileName());

		if (viewObject instanceof OCEPCameraViewTemplate) {
			((OCEPCameraViewTemplate) viewObject).getUpload().setKey(this.getFileName());
		}
		else if (viewObject instanceof OCEPGalleryViewTemplate) {
			((OCEPGalleryViewTemplate) viewObject).getUpload().setKey(this.getFileName());
		}
		else if (viewObject instanceof OCEPFilePickerViewTemplate) {
			((OCEPFilePickerViewTemplate) viewObject).getUpload().setKey(this.getFileName());
		}
	}

	protected String getFileName() {
		return "";
	}

	@Override
	public void postProcessMessage(UIViewContext context) {
	}
}
