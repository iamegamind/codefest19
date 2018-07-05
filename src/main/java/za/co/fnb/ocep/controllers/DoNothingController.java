package za.co.fnb.ocep.controllers;

import za.co.fnb.pe.framework.api.UIController;
import za.co.fnb.pe.framework.api.UIControllerContext;

public class DoNothingController implements UIController {
	@Override
	public String processMessage(UIControllerContext context) {		
		return( DEFAULT );
	}
}
