package za.co.fnb.ocep.views;

import java.util.List;
import java.util.Map;

import za.co.fnb.pe.framework.api.UIView;
import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.entity.Item;
import za.co.fnb.pe.framework.template.elements.ControlType;
import za.co.fnb.pe.framework.template.elements.Rows;
import za.co.fnb.pe.framework.template.tablet.OCEPTabletFormTemplate;
import za.co.fnb.pe.framework.utils.CoreStringUtil;
import za.co.fnb.pe.framework.utils.UIMessageConfig;

public class ExtensionVariableISView implements UIView {
	
	@Override
	public void processMessage(UIViewContext context) {
		OCEPTabletFormTemplate view = (OCEPTabletFormTemplate)context.getUI();
		
		Rows rows = view.getRows();
		List<Item> items = rows.getItems();

        processLayoutControls(context, items);
    }

    private void processLayoutControls(UIViewContext context, List<Item> items) {
        for (Item label : items) {
            if (label.getType() == ControlType.HTML && label.getId().equalsIgnoreCase("instruction")){
                label.setText(this.formatContent( label.getText(), context ));
                label.setText( UIMessageConfig.getInstruction(context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), label.getText()) );
                label.setText( this.formatContent( label.getText(), context ) );
            }else if (label.getType() == ControlType.HTML){
                label.setText(this.formatContent( label.getText(), context ));
                label.setText( UIMessageConfig.getMessage( context.getRequest().getChannel().getSkin(), context.getRequest().getDevice().getPlatform().toString(), label.getText() ) );
                label.setText( this.formatContent( label.getText(), context ) );
            }
        }
    }

    protected String formatContent(String html, UIViewContext context) {
		for( String key : context.getClipboard().keySet() ) {
			html = CoreStringUtil.replaceVar( html, key, getString( context.getClipboard(), key ) );
		}
		return( html );
	}

	private String getString(Map<String, Object> clipboard, String key) {
		Object o = clipboard.get( key );
		if( o != null && o instanceof String ) {
			return( (String)o );
		}
		else if( o != null ) {
			return( o.toString() );
		}
		return( null );
	}

	@Override
	public void postProcessMessage(UIViewContext context) {
		// TODO Auto-generated method stub
		
	}
}
