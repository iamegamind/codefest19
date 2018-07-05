package za.co.fnb.ocep.views;

import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.util.views.ListView;
import za.co.fnb.pe.framework.utils.HtmlUtil;
import za.co.fnb.pe.framework.utils.UIMessageConfig;

public class VariableListView extends ListView {
	
	protected String resolveHtml(final UIViewContext context, final String htmlFile) {
        final String skin         = context.getRequest().getChannel().getSkin();
        final String platform     = context.getRequest().getDevice().getPlatform().toString();
        final String explodedHtml = UIMessageConfig.getMessage(skin, platform, htmlFile, true);
        return HtmlUtil.resolveIfVariables(explodedHtml, context.getClipboard());
    }
}