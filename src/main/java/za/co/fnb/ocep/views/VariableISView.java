package za.co.fnb.ocep.views;

import java.util.Map;

import za.co.fnb.ocep.utils.StringUtil;
import za.co.fnb.pe.framework.api.UIViewContext;

public class VariableISView extends ISView {
	
	@Override
	protected String formatLabel(String html, UIViewContext context) {		
		return( formatContent(html, context) );
	}
	
	public String formatContent(String html, UIViewContext context) {
		for( String key : context.getClipboard().keySet() ) {
			Object value = context.getClipboard().get( key );
			html = StringUtil.replaceVar( html, key, value != null ? value.toString() : "" );
			html = StringUtil.replaceIfVar(html, key, value != null ? value.toString() : "" );
		}		
		//  check if any sections to remove
		for( String key : context.getClipboard().keySet() ) {
			Object value = context.getClipboard().get( key );
			html  = StringUtil.replaceIfVar(html, key, value != null ? value.toString() : "");
		}	
		
		return( StringUtil.replaceIfVarWithDefault(html) );
	}	

	protected String getString(Map<String, Object> clipboard, String key) {
		Object o = clipboard.get( key );
		if( o != null && o instanceof String ) {
			return( (String)o );
		}
		else if( o != null ) {
			return( o.toString() );
		}
		return( "" );
	}
}