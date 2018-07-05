package za.co.fnb.ocep.views;

import za.co.fnb.ocep.cache.SessionContext;
import za.co.fnb.pe.framework.api.UIView;
import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.entity.header.Form;
import za.co.fnb.pe.framework.template.elements.HTMLOption;
import za.co.fnb.pe.framework.template.phone.HTML5ViewTemplate;

public class HTML5TemplateTesterView implements UIView {

	@Override
	public void postProcessMessage(UIViewContext arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage(UIViewContext context) {
		SessionContext cache = (SessionContext) context.getSessionContext();
		HTML5ViewTemplate view = (HTML5ViewTemplate) context.getUI();
		
		for( Form form : context.getRequest().getFormValues() ) {
			for(HTMLOption option : view.getOptions()){
				if(form.getKey().equalsIgnoreCase(option.getKey())){
					option.setValue(form.getValue());
					break;
				}
			}
		}	
		
		//Read the url to use
		for( Form form : context.getRequest().getFormValues() ) {
			if(form.getKey().equalsIgnoreCase("url")){
				view.getUrl().setValue(form.getValue() != null ? form.getValue() : "");
			}
		}
		
	}

}
