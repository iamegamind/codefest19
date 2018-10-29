package za.co.fnb.ocep.views;

import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.template.phone.PopupViewTemplate;
import za.co.fnb.pe.framework.utils.UIMessageConfig;

public class PopupView extends BaseUIView {
	@Override
	public void processMessage(UIViewContext context) {
		PopupViewTemplate view = (PopupViewTemplate) context.getUI();
		if (view.getContent().getText() != null) {
			view.getContent().setText(UIMessageConfig.getMessage(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), view.getContent().getText()));
			view.getContent().setText(this.formatContent(view.getContent().getText(), context));
		}
	}

	protected String formatContent(String html, UIViewContext context) {
		return html;
	}

	@Override
	public void postProcessMessage(UIViewContext context) {
		// TODO Auto-generated method stub

	}
}