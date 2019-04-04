package za.co.fnb.ocep.views;

import za.co.fnb.pe.framework.api.UIView;
import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.entity.factories.mm.SymbolFactory;
import za.co.fnb.pe.framework.entity.mm.ActionType;
import za.co.fnb.pe.framework.entity.mm.StackedContainer;
import za.co.fnb.pe.framework.template.phone.MagicMountainViewTemplate;
import za.co.fnb.pe.framework.util.views.MagicMountainView;

public class EReceiptsStartView extends MagicMountainView implements UIView {

    @Override
    public void processMessage(UIViewContext context) {
        super.processMessage(context);
        MagicMountainViewTemplate ui = (MagicMountainViewTemplate) context.getUI();
        StackedContainer stackedcontainer = (StackedContainer) ui.getRootContainer().getSymbol("stackedcontainer.0");

        SymbolFactory.addActionPillButton("registerinvoices", stackedcontainer.getKey(), "3")
                .withText("Register")
                .withType(ActionType.NAVIGATE)
                .withUIID("ui.ereceipts.registration.popup")
                .addToContainer(stackedcontainer);

    }
}