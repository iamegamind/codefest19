package za.co.fnb.ocep.views;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.template.elements.ControlType;
import za.co.fnb.pe.framework.template.elements.FormControl;
import za.co.fnb.pe.framework.template.elements.MarketingElement;
import za.co.fnb.pe.framework.template.elements.Radio;
import za.co.fnb.pe.framework.template.phone.MarketingViewTemplate;
import za.co.fnb.pe.framework.utils.UIMessageConfig;

public class MarketingView extends BaseUIView {

	@Override
	public void processMessage(UIViewContext context) {
		MarketingViewTemplate view = (MarketingViewTemplate) context.getUI();
		for (MarketingElement element : view.getPage().getMarketingElements()) {
			for (FormControl control : element.getControls()) {
				control.setRealUIID(view.getId());

				// Applies the marketingElements colour to the individual controls
				control.setTextColour(element.getTextColour());

				if (control.getType().equals(ControlType.MAP_DETAIL)) {
					if (control.getPinList() != null) {
						control.getPinList().stream().filter(pin -> pin.getType().equalsIgnoreCase("CONTENT")).forEach(pin -> {
							pin.setLabel(UIMessageConfig.getMessage(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), pin.getLabel()));
							pin.setLabel(this.formatLabel(pin.getLabel(), context));
						});
					}
				}

				if (control.getType().equals(ControlType.HTML_CAROUSEL)) {
					if (control.getPageHtmlList() != null) {
						control.getPageHtmlList().stream().forEach(pageHtml -> {
							pageHtml.setValue(UIMessageConfig.getMessage(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), pageHtml.getValue(), true));
							pageHtml.setValue(this.formatLabel(pageHtml.getValue(), context));
							pageHtml.setParent(control.getKey());
						});
					}
				}

				if (control.getType().equals(ControlType.RADIO_GROUP)) {
					if (control.getRadioList() != null) {
						for (Radio entry : control.getRadioList()) {
							entry.setParent(control.getKey());
						}
					}
				}

				if (control.getType().equals(ControlType.BUTTON_CAROUSEL)) {
					control.getPageButtonList().stream().forEach(pageButton -> pageButton.setParent(control.getKey()));
				}

				if (control.getType().equals(ControlType.LABEL)) {
					if (!control.getLabel().equals("")) {
						control.setLabel(UIMessageConfig.getLabel(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), control.getLabel()));
						control.setLabel(this.formatLabel(control.getLabel(), context));
					}
				}
				if (control.getType().equals(ControlType.SPACE)) {
					if (StringUtils.isBlank(control.getValue())) {
						control.setValue("10");
					}
				}
				else if (control.getType() == ControlType.SOCIALSHARE) {
					control.setSubject(UIMessageConfig.getMessage(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), control.getSubject()));
					control.setSubject(this.formatLabel(control.getSubject(), context));
					control.setMessage(UIMessageConfig.getMessage(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), control.getMessage()));
					control.setMessage(this.formatLabel(control.getMessage(), context));
					control.setUrl(UIMessageConfig.getMessage(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), control.getUrl()));
					control.setUrl(this.formatLabel(control.getUrl(), context));
				}
				else if (control.getType() == ControlType.ALPHA_NUMERIC_TEXTBOX || control.getType() == ControlType.NUMERIC_TEXTBOX || control.getType() == ControlType.DROP_DOWN || control.getType() == ControlType.IMAGE) {
					control.setValue(this.formatLabel(control.getValue(), context));
				}
			}
		}

	}

	protected void processControl(UIViewContext context, FormControl control) {
		if (control.getType().equals(ControlType.LABEL)) {
			if (!control.getLabel().equals("")) {
				control.setLabel(UIMessageConfig.getLabel(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), control.getLabel()));
				control.setLabel(this.formatLabel(control.getLabel(), context));
			}
		}
		else if (control.getType() == ControlType.ALPHA_NUMERIC_TEXTBOX || control.getType() == ControlType.NUMERIC_TEXTBOX || control.getType() == ControlType.DROP_DOWN || control.getType() == ControlType.IMAGE) {
			control.setValue(this.formatLabel(control.getValue(), context));
		}
	}

	protected String formatLabel(String html, UIViewContext context) {
		return html;
	}

	/**
	 * This method removes a control from the FormContols for a ISView
	 *
	 * @param controls
	 *            - List of controls
	 * @param key
	 *            - The key of the control to remove from the list
	 */
	public static void removeControl(List<FormControl> controls, String key) {
		List<FormControl> toRemove = new ArrayList<FormControl>();

		for (FormControl control : controls) {
			if (control.getKey().equalsIgnoreCase(key)) {
				toRemove.add(control);
			}
		}

		for (FormControl control : toRemove) {
			controls.remove(control);
		}
	}

	public static void removeControlWithCorrespondingSpace(List<FormControl> controls, String key) {
		controls.removeIf(control -> key.equalsIgnoreCase(control.getKey()));
		controls.removeIf(control -> control.getType() == ControlType.SPACE && control.getKey().startsWith(key));
	}

	/**
	 * This method gets a first control from the FormContols for a ISView with a specific key
	 *
	 * @param controls
	 *            - List of controls
	 * @param key
	 *            - The key of the control to retrieve from the list
	 */
	public static FormControl getControl(List<FormControl> controls, String key) {

		for (FormControl control : controls) {
			if (control.getKey().equalsIgnoreCase(key)) {
				return control;
			}
		}

		return null;
	}

	@Override
	public void postProcessMessage(UIViewContext context) {
		// TODO Auto-generated method stub

	}
}
