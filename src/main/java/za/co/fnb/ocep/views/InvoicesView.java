package za.co.fnb.ocep.views;

import za.co.fnb.pe.framework.api.UIView;
import za.co.fnb.pe.framework.api.UIViewContext;
import za.co.fnb.pe.framework.entity.OrderType;
import za.co.fnb.pe.framework.entity.factories.mm.ContainerFactory;
import za.co.fnb.pe.framework.entity.factories.mm.SymbolFactory;
import za.co.fnb.pe.framework.entity.mm.StackedContainer;
import za.co.fnb.pe.framework.entity.mm.SymbolColour;
import za.co.fnb.pe.framework.template.phone.MagicMountainViewTemplate;
import za.co.fnb.pe.framework.util.views.MagicMountainView;

public class InvoicesView extends MagicMountainView implements UIView {
		
	@Override
	public void processMessage(UIViewContext context) {
	super.processMessage(context);
	
	// Get handle on the view
	MagicMountainViewTemplate view = (MagicMountainViewTemplate)context.getUI();
		// Create a new empty stacked container (Stacks on the y-axis) and add it to the root container.
	// Get a handle on the newly created stacked container
	StackedContainer stackedcontainer = (StackedContainer) ContainerFactory.createStackedContainer("stacked.container.1", "root", "0", OrderType.ASC)
			.addToContainer(view.getRootContainer());
	
		// Add readouts to the stacked container in a loop
		for(int i=0;i<5;i++){
			
			SymbolFactory.addDefaultMessageGroup("pill"+i,stackedcontainer.getKey(), Integer.toString(i))
			// .withPillInfo("R50",SymbolColour.PAPER,SymbolColour.PRIMARY)
	         //.withText("", "Funds")
	        // .withTextColour(SymbolColour.INK, SymbolColour.GHOST_DARK)
			 .withGroupName("eReceipt")
			 .withDescriptor("Message")
			 .withTime("Time")
			 .withNavigateUIID("uiid")
				// .withLeftSwipe("messaging.delete.v1", SymbolColour.ACTION, "Delete", SymbolColour.PAPER)
	         .addToContainer(stackedcontainer);
			
			
			  /* SymbolFactory.addReadOutWithPillButton("pill"+i,stackedcontainer.getKey(), Integer.toString(i))
			         .withPillInfo("R50",SymbolColour.PAPER,SymbolColour.PRIMARY)
			         .withText("", "Funds")
			         .withTextColour(SymbolColour.INK, SymbolColour.GHOST_DARK)
			         .addToContainer(stackedcontainer);*/
		}		
	}	
}
