package za.co.fnb.ocep.views;

import za.co.fnb.ocep.cache.SessionContext;
import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.template.phone.OverviewViewTemplate;
import za.co.fnb.pe.framework.util.views.OverviewView;

public class DynamicOverviewView extends OverviewView {

	@Override
	public void processMessage(UIViewContext context) {
		super.processMessage(context);
		SessionContext cache = (SessionContext) context.getSessionContext();
		OverviewViewTemplate view = (OverviewViewTemplate) context.getUI();
		cache.setOverviews(view.getOverviews().getOverviews());
	}
}
